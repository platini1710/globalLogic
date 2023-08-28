package com.global.tareas.controllers;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.global.tareas.exception.ResourceFoundException;
import com.global.tareas.helper.Constantes;
import com.global.tareas.helper.EmailValidatorSimple;
import com.global.tareas.helper.ErrorResp;
import com.global.tareas.helper.PasswordValidator;
import com.global.tareas.helper.Respuesta;
import com.global.tareas.model.Usuario;
import com.global.tareas.services.ConsultaUsuarioService;
import com.global.tareas.services.RegistraUsuarioServices;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiOperation;



@RestController
@RequestMapping("/registro/usuario")

public class ControllerRegistation {
	
	@Autowired
	private RegistraUsuarioServices registraUsuarioServices;
	@Autowired
	private ConsultaUsuarioService consultaUsuarioService;
	


	DateTimeFormatter ZDT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z");
	private static final Logger logger = LoggerFactory.getLogger(ControllerRegistation.class);

	/**
	 * Update user response entity.
	 *
	 * @param producto the user id
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@ApiOperation(value = "guarda un registro de la tarea  en caso de que exista mandara el respectivo msg ", notes = "Return clase Respuesta "
			+ " retorna el resultado en campo Msg ")
	@RequestMapping(value = "/sign-up", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Respuesta saveTarea(@RequestBody Usuario usuario) {
		String str = ZDT_FORMATTER.format(ZonedDateTime.now());
		Respuesta response = new Respuesta();
		  Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try {
			logger.info("grabar tareas");
			byte[] byte_id= usuario.getId().getBytes();
			UUID uuid = UUID.nameUUIDFromBytes(byte_id);

			logger.info("uuid" + uuid.toString());
			usuario.setId(uuid.toString());
			Usuario user = consultaUsuarioService.findUsuario(uuid.toString());
			
			logger.info("In prod" + user);

			if (user != null) {
				logger.debug("producto existe");
				throw new ResourceFoundException(Constantes.msgFound);
			}
			
			if  (!EmailValidatorSimple.isValid(usuario.getEmail())) {
				logger.debug("email invalido");
				throw new ResourceFoundException(Constantes.emailInvalid);
			}
			if  (!PasswordValidator.isValid(usuario.getPassword())) {
				logger.debug("password  invalida");
				throw new ResourceFoundException(Constantes.passwordInvalid);
			}
			usuario.setCreated(str);
			usuario.setLastLogin(str);
			usuario.getPhone().setId(uuid.toString());
			usuario.setToken(  getJWTToken(usuario.getName())  );
			registraUsuarioServices.update(usuario);
			usuario.setActive(true);
			response.setUuid(uuid.toString());
			//response.setMsg(Constantes.insert);
			response.setPassword(usuario.getPassword());
			response.setName(usuario.getName());
			response.setLastLogin(usuario.getLastLogin());
			response.setCreated(str);
			response.setEmail(usuario.getEmail());
			response.setToken(usuario.getToken());
			response.setIsActive("true");

		} catch (ResourceFoundException e) {
			logger.error(e.getMessage());
	
			ErrorResp error=new ErrorResp();
			error.setCodigo(500);
			error.setDetail(e.getMessage());
			error.setTimestamp(timestamp);
			response.setErrorResp(error);;
		} catch (Exception e) {
			logger.error(e.getMessage());
			
			response.setMsg(e.getStackTrace().toString());
			ErrorResp error=new ErrorResp();
			error.setCodigo(500);
			error.setDetail(e.getMessage());
			error.setTimestamp(timestamp);
			response.setErrorResp(error);
		}
		return response;
	}

	

	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}

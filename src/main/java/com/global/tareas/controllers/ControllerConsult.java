package com.global.tareas.controllers;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.global.tareas.exception.ResourceFoundException;
import com.global.tareas.helper.Constantes;
import com.global.tareas.helper.EmailValidatorSimple;
import com.global.tareas.helper.ErrorResp;
import com.global.tareas.helper.PasswordValidator;
import com.global.tareas.helper.PhoneDto;
import com.global.tareas.helper.Respuesta;
import com.global.tareas.model.Usuario;
import com.global.tareas.services.ConsultaUsuarioService;
import com.global.tareas.services.RegistraUsuarioServices;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiOperation;



@RestController
@RequestMapping("/consult/usuario")

public class ControllerConsult {
	
	@Autowired
	private RegistraUsuarioServices registraUsuarioServices;
	@Autowired
	private ConsultaUsuarioService consultaUsuarioService;
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerConsult.class);

	@ApiOperation(value = "Find todos los Usuarios", notes = "Return clase Respuesta "
			+ "resultado ")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/allUsuarios")
	@ResponseBody
	public List<Respuesta> getAllUsuarios() {
		logger.info("todo los productos");
		List<Usuario> listAllUsuarios=new ArrayList<>();
		List<Respuesta> listRespuesta=new ArrayList<>();
		try {
			 listAllUsuarios=consultaUsuarioService.findAllUsuario();
	
			listAllUsuarios.forEach(c->
			{
				Respuesta r=new Respuesta();
				r.setCreated(c.getCreated());
				r.setEmail(c.getEmail());
				r.setUuid(c.getId());
				r.setName(c.getName());
				r.setToken(c.getToken());
				r.setLastLogin(c.getLastLogin());
				listRespuesta.add(r);
				
			}
			 
					 );
			 
			 

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

		return listRespuesta;
	}

	
	/**
	 * Gets users by id.
	 *
	 * @param userId the user id
	 * @return the users by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@ApiOperation(value = "Find un tarea por  id de la tarea", notes = "Return tarea "
			+ "resultado en campoProducto maneja su propias excepcion")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/login")
	public Respuesta getProductoById(@RequestBody Usuario usuario) {
		Usuario user = null;
		Respuesta r=new Respuesta();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		logger.info("id  <:::" + usuario.getId());
		try {
			byte[] byte_id= usuario.getId().getBytes();
			UUID uuid = UUID.nameUUIDFromBytes(byte_id);
			
			logger.info("uuid id  <:::" + uuid.toString());
			user = consultaUsuarioService.findUsuario(uuid.toString());
			if (usuario!=null) {
				PhoneDto phoneDto=new PhoneDto();
				r.setCreated(user.getCreated());
				r.setEmail(user.getEmail());
				r.setUuid(user.getId());
				r.setName(user.getName());
				r.setToken(user.getToken());
				r.setLastLogin(user.getLastLogin());
				phoneDto.setCityCode(user.getPhone().getCityCode());
				phoneDto.setCountryCode(user.getPhone().getCountryCode());
				phoneDto.setNumber(user.getPhone().getNumber());;
				r.setPhone(phoneDto);
				if (user.isActive())
				  r.setIsActive("true");
				else
					 r.setIsActive("false"); 
			}

	
		} catch (Exception e) {
			r=new Respuesta();
			ErrorResp errorResp=new ErrorResp();
			errorResp.setCodigo(0);
			errorResp.setDetail(e.getMessage());
			errorResp.setTimestamp(timestamp);
			r.setErrorResp(errorResp);
			logger.error(e.getMessage(),e);
			return r;
			
		}

		return r;
	}
}

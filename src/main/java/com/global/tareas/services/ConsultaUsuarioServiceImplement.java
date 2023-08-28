package com.global.tareas.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.global.tareas.model.Usuario;
@Component
public class ConsultaUsuarioServiceImplement implements ConsultaUsuarioService {
	  @PersistenceContext
	  EntityManager entityManager; 
	  
	  

		
		@Override
		public Usuario findUsuario(String id) {
			// TODO Auto-generated method stub
		    	return  entityManager.find(Usuario.class,id);
		}


		
		
		@Override
		public ArrayList<Usuario> findAllUsuario() {
			// TODO Auto-generated method stub
	    	List<Usuario>  listUsuarios=entityManager.createQuery(
	                "SELECT p FROM Usuario p").getResultList();
	    	return (ArrayList<Usuario>) listUsuarios;
		}  
}

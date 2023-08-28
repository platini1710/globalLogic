package com.global.tareas.services;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.global.tareas.model.Usuario;



@Component 
public class RegistraUsuarioServicesImpl implements  RegistraUsuarioServices{
	  @PersistenceContext
	  EntityManager entityManager; 
	  
	@Transactional  
	@Override
	public void save(Usuario usuario) {
		// TODO Auto-generated method stub
		  entityManager.persist(usuario);
	}
	@Transactional
	@Override
	public void update(Usuario usuario) {
		// TODO Auto-generated method stub
		  entityManager.persist(usuario);
	}
    @Transactional
	@Override
	public void delete(Usuario usuario) {
		// TODO Auto-generated method stub
		  entityManager.remove(usuario);
	}


}
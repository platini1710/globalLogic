package com.global.tareas.services;

import java.util.ArrayList;
import java.util.List;

import com.global.tareas.model.Usuario;




public interface  ConsultaUsuarioService {

	public Usuario findUsuario(String id) ;
	public ArrayList<Usuario> findAllUsuario() ;
}

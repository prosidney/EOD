package br.com.eod.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.eod.dao.impl.UsuarioDao;

public class UsuarioService {
	@Autowired
	private UsuarioDao usuarioDao;
	
	public UsuarioDao getUsuarioDao(){
		return usuarioDao;
	}

}

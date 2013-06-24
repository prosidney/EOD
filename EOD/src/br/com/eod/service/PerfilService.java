package br.com.eod.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.eod.dao.impl.PerfilDao;

public class PerfilService {
	@Autowired
	private PerfilDao perfilDao;
	
	public PerfilDao getPerfilDao(){
		return perfilDao;
	}
}

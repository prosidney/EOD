package br.com.eod.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.eod.dao.impl.MetaDao;

public class MetaService {
	@Autowired
	private MetaDao metaDao;
	
	public MetaDao getMetaDao(){
		return metaDao;
	}
}

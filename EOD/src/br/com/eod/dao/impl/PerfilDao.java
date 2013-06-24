package br.com.eod.dao.impl;

import br.com.eod.dao.AbstractDao;
import br.com.eod.dao.GenericDao;
import br.com.eod.model.Perfil;

public class PerfilDao extends AbstractDao<Perfil> implements
		GenericDao<Perfil> {

	public PerfilDao() {
		super(Perfil.class);
	}

}

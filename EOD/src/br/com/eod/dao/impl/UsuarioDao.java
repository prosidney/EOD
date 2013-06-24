package br.com.eod.dao.impl;

import br.com.eod.dao.AbstractDao;
import br.com.eod.dao.GenericDao;
import br.com.eod.model.Usuario;

public class UsuarioDao extends AbstractDao<Usuario> implements GenericDao<Usuario> {

	public UsuarioDao() {
		super(Usuario.class);
	}

}

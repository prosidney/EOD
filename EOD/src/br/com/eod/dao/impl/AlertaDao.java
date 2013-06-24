package br.com.eod.dao.impl;

import br.com.eod.dao.AbstractDao;
import br.com.eod.dao.GenericDao;
import br.com.eod.model.Alerta;

public class AlertaDao extends AbstractDao<Alerta> implements
GenericDao<Alerta>{
	
	public AlertaDao() {
		super(Alerta.class);
	}

}

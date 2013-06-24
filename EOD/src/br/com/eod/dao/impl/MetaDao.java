package br.com.eod.dao.impl;

import br.com.eod.dao.AbstractDao;
import br.com.eod.dao.GenericDao;
import br.com.eod.model.Meta;

public class MetaDao extends AbstractDao<Meta> implements
			GenericDao<Meta>{

	public MetaDao() {
		super(Meta.class);
	}

}

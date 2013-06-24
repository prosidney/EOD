package br.com.eod.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.eod.dao.impl.AlertaDao;

public class AlertaService {
	@Autowired
	AlertaDao alertaDao;
	
	public AlertaDao getAlertaDao(){
		return alertaDao;
	}
}

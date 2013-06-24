package br.com.eod.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import br.com.eod.dao.AbstractDao;
import br.com.eod.dao.GenericDao;
import br.com.eod.model.Tarefa;

public class TarefaDao extends AbstractDao<Tarefa> implements GenericDao<Tarefa> {
	private static final Logger log =  Logger.getLogger(TarefaDao.class.getName());
	
	public TarefaDao() {
		super(Tarefa.class);
	}
	
	public List<Tarefa> findByExample(Tarefa eventoExample){
		//TODO implementar método
		log.info("finding " + eventoExample.getClass().getName() + " instances by example");

		List<Tarefa> listResult = Collections.EMPTY_LIST;
		try { 

			Session session = sessionFactory.getSession();
			session.flush();

			Criteria crit = session.createCriteria(eventoExample.getClass()); 

			Example example = Example.create(eventoExample)
			.excludeZeroes()           //exclude zero valued properties
			//.excludeProperty("color")  //exclude the property named "color"
			.ignoreCase()              //perform case insensitive string comparisons
			.enableLike();

			/*		Example exampleMembro = Example.create(eventoExample.getMembro())
				.excludeZeroes()           //exclude zero valued properties
				//.excludeProperty("color")  //exclude the property named "color"
				.ignoreCase()              //perform case insensitive string comparisons
				.enableLike();

		listResult = crit.add(example).createCriteria("membro").add(exampleMembro).list();
			 */

		} catch (HibernateException ex) { 
			throw new PersistenceException(ex); 
		} 

		return listResult; 
	}
}

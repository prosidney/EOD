package br.com.eod.dao;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;



public abstract class AbstractDao<T> implements GenericDao<T>{
	private static final Logger log =  Logger.getLogger(AbstractDao.class.getName()); 
	@SuppressWarnings("rawtypes")
	private final Class typeClass;
	
	@Autowired
	protected br.com.eod.factory.SessionFactory sessionFactory;

	/** 
	 * @param argClass 
	 */  
	@SuppressWarnings("rawtypes")
	public AbstractDao(Class argClass) {  
		typeClass = argClass;  
	} 	

	public Transaction getTransaction(Session session){
		if(session.getTransaction().isActive()){
			return session.getTransaction();
		}else{
			return session.beginTransaction();
		}
	}	

	public T insert(T instance){
		log.info("inserindo dados na tabela " + typeClass.getName());
		Session session = sessionFactory.getSession();
		Transaction transaction = getTransaction(session); 
		
		try {		
			session.save(instance);		
			log.info("dados na tabela " + typeClass.getName()+" inserido com sucesso");
			transaction.commit(); 
		}catch(Exception e){
			log.info("ocorreu um erro:"+e.getMessage());
			e.printStackTrace();
		}finally{			
			session.flush();
			//session.close();
		}

		return instance;		
	}
	
	public T saveOrUpdate(T instance){
		log.info("salvando ou atualizando dados na tabela " + typeClass.getName());
		Session session = sessionFactory.getSession();
		Transaction transaction = getTransaction(session); 
		
		try {		
			session.merge(instance);		
			transaction.commit(); 
			log.info("dados na tabela " + typeClass.getName()+" inserido com sucesso");
		}catch(Exception e){
			log.info("ocorreu um erro:"+e.getMessage());
			e.printStackTrace();
		}finally{			
			session.flush();
			//session.close();
		}

		return instance;		
	}	
	
	public T update(T instance){
		log.info("atualizando dados na tabela " + typeClass.getName());
		Session session = sessionFactory.getSession();
		Transaction transaction = getTransaction(session); 
		
		try {		
			session.update(instance);		
			log.info("dados na tabela " + typeClass.getName()+" atualido com sucesso");
			transaction.commit(); 
		}catch(Exception e){
			log.info("ocorreu um erro:"+e.getMessage());
			e.printStackTrace();
		}finally{			
			session.flush();
			//session.close();
		}

		return instance;		
	}
	
	public void refresh(T instance){
		sessionFactory.getSession().refresh(instance);
	}	


	public T findByKey(Integer key){
		//TODO implementar método
		return null;
	}


	public List<T> findAll(){
		log.info("finding all " + typeClass.getName() + " instances");
		Session session = sessionFactory.getSession();
		session.flush();
		
		Query query = session.createQuery("from " + typeClass.getName());
		@SuppressWarnings("unchecked")		
		List<T> results = (List<T>) query.list();

		return results;
	}
	
	public List<T> findAllBatch(){
		log.info("finding all " + typeClass.getName() + " instances");
		Session session = sessionFactory.getOtherSessionBatch();
		
		Query query = session.createQuery("from " + typeClass.getName());
		@SuppressWarnings("unchecked")		
		List<T> results = (List<T>) query.list();

		return results;
	}	


	public List<T> findAll(Integer begin, Integer end){
		//TODO implementar método
		return null;
	}


	public List<T> findByExample(T exampleParam){
		//TODO implementar método
		log.info("finding " + typeClass.getName() + " instances by example");

		List<T> listResult = Collections.EMPTY_LIST;
		try { 
			Session session = sessionFactory.getSession();

			Criteria crit = session.createCriteria(typeClass); 
			Example example = Example.create(exampleParam)
							         .excludeZeroes()           //exclude zero valued properties
									 //.excludeProperty("color")  //exclude the property named "color"
									 .ignoreCase()              //perform case insensitive string comparisons
									 .enableLike(); 

			listResult = crit.add(example).list(); 

		} catch (HibernateException ex) { 
			throw new PersistenceException(ex); 
		} 

		return listResult; 
	}


	public List<T> findByExample(T example, Integer begin, Integer end){
		//TODO implementar método
		return null;
	}


	public void remove(T instance){
		log.info("Deletando dados na tabela " + typeClass.getName());
		
		Session session = sessionFactory.getSession();
		Transaction transaction = getTransaction(session); 
		
		try {		
			session.delete(instance);			 
			transaction.commit(); 
			log.info("dado na tabela " + typeClass.getName()+" deletado com sucesso");
		}catch(Exception e){
			log.info("ocorreu um erro:"+e.getMessage());
			e.printStackTrace();
		}finally{
			session.flush();
			//session.close();
		}
	}
}

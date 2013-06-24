package br.com.eod.factory;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;

public class SessionFactory {
	private Session session;
	private Session sessionBatch;
	
	public Session getSession(){
		if(session == null){
/*			SessionFactory sessionFactory = new 
				Configuration().configure().buildSessionFactory();*/
			org.hibernate.SessionFactory sessionFactory = new
				AnnotationConfiguration().configure().buildSessionFactory(); 
			session = sessionFactory.openSession();
			session.setFlushMode(FlushMode.ALWAYS);
			return session;
		}else{
			if(!session.isOpen()){
				return null;
			}else{
				session.flush();
				return session;
			}
		}
	}
	public Session getOtherSessionBatch(){
		if(sessionBatch == null){
/*			SessionFactory sessionFactory = new 
				Configuration().configure().buildSessionFactory();*/
			org.hibernate.SessionFactory sessionFactory = new
				AnnotationConfiguration().configure().buildSessionFactory(); 
			sessionBatch = sessionFactory.openSession();
			return session;
		}else{
			if(!sessionBatch.isOpen()){
				org.hibernate.SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
				sessionBatch = sessionFactory.openSession();
				return null;
			}else{
				return sessionBatch;
			}
		}
	}	
}

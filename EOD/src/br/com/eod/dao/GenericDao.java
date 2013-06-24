package br.com.eod.dao;

import java.util.List;

public interface GenericDao<T> {
	/** 
	 * @param instance 
	 */	
	public T insert(T instance);
	
	/** 
	 * @param instance 
	 */	
	public T update(T instance);

	/** 
	 * @param id 
	 * @return 
	 */  
	public T findByKey(Integer key);  

	/** 
	 * @return 
	 */  
	public List<T> findAll();  

	/** 
	 * @param begin 
	 * @param end 
	 * @return 
	 */  
	public List<T> findAll(Integer begin, Integer end);  

	/** 
	 * @param example 
	 * @return 
	 */  
	public List<T> findByExample(T example);  

	/** 
	 * @param example 
	 * @param begin a
	 * @param end 
	 * @return 
	 */  
	public List<T> findByExample(T example, Integer begin, Integer end);  


	/** 
	 * @param isntance 
	 */  
	public void remove(T instance);  

}
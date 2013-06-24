package br.com.eod.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="metas")
public class Meta implements Serializable, Comparable<Meta>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	private String nome;
	
	private String descricao;
	
    @Temporal( TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	private Date ate;
    
	@ManyToOne(fetch=FetchType.EAGER)
	private Perfil perfil;
	
    public int getId() {
    	return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
    public String getNome() {
    	return nome;
    }
    
    public void setNome(String nome) {
    	this.nome = nome;
    }
    
    public String getDescricao() {
    	return descricao;
    }
    
    public void setDescricao(String descricao) {
    	this.descricao = descricao;
    }
    
    public Date getAte() {
    	return ate;
    }
    
    public void setAte(Date ate) {
    	this.ate = ate;
    }
    
	public Data getAteD() {
		return new Data(this.ate.getTime());
	}    

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Perfil getPerfil() {
		return perfil;
	}
	
	@Override
	public int hashCode() {
		if(this.nome != null){
			return this.nome.length()*8;
		}else{ 
			return 0;
		}
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Meta){
			return this.id == ((Meta) obj).getId(); 
		}else{
			return false;
		}
	}

	@Override
	public int compareTo(Meta o2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(this.ate);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(o2.getAte());
		
		return cal1.compareTo(cal2);
	}	
	
	public static class MetaComparator implements Comparator<Meta>
	{
		public int compare(Meta t1, Meta t2)
		{	
			Calendar cA1 = Calendar.getInstance();
			cA1.setTime(t1.getAte());
			
			Calendar cA2 = Calendar.getInstance();
			cA2.setTime(t2.getAte());
			
			return cA1.compareTo(cA2);
		} 
	}		
}

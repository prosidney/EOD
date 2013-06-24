package br.com.eod.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.CascadeType;
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
@Table(name="alertas")
public class Alerta implements Serializable, Comparable<Alerta>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	private String nome;
	
	private String repetir;
	
    @Temporal( TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	private Date data;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Tarefa tarefa;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Perfil perfil;
	
    @Temporal( TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	private Date enviado;

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

	public String getRepetir() {
		return repetir;
	}

	public void setRepetir(String repetir) {
		this.repetir = repetir;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	

	public Date getEnviado() {
		return enviado;
	}

	public void setEnviado(Date enviado) {
		this.enviado = enviado;
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
		if(obj instanceof Alerta){
			return this.id == ((Alerta) obj).getId(); 
		}else{
			return false;
		}
	}

	@Override
	public int compareTo(Alerta o2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(this.data);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(o2.getData());
		
		return cal1.compareTo(cal2);
	}	
	public static class AlertaComparator implements Comparator<Alerta>
	{
		public int compare(Alerta t1, Alerta t2)
		{	
			Calendar cA1 = Calendar.getInstance();
			cA1.setTime(t1.getData());
			
			Calendar cA2 = Calendar.getInstance();
			cA2.setTime(t2.getData());
			
			return cA1.compareTo(cA2);
		} 
	}	

}

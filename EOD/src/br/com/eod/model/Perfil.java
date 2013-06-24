package br.com.eod.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
import java.util.SortedSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

@Entity
@Table(name="perfis")
public class Perfil implements Serializable, Comparable<Perfil>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	private String nome;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Usuario usuario;
	
	@OneToMany(mappedBy="perfil", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@Sort(type = SortType.COMPARATOR, comparator = Tarefa.TarefaComparator.class)
	private SortedSet<Tarefa> tarefas;
	
	@OneToMany(mappedBy="perfil", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Meta> metas;  
	
	@OneToMany(mappedBy="perfil", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Alerta> alertas; 	
	
	public Perfil() {
		super();
		// TODO Auto-generated constructor stub
	}	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setTarefas(SortedSet<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	public SortedSet<Tarefa> getTarefas() {
		return tarefas;
	}
	
	public void setMetas(Set<Meta> metas) {
		this.metas = metas;
	}

	public Set<Meta> getMetas() {
		return metas;
	}

	public void setAlertas(Set<Alerta> alertas) {
		this.alertas = alertas;
	}

	public Set<Alerta> getAlertas() {
		return alertas;
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
		if(obj instanceof Perfil){
			return this.id == ((Perfil) obj).getId(); 
		}else{
			return false;
		}
	}	
	
	@Override
	public int compareTo(Perfil paramT) {
		if(this.nome != null && paramT.getNome() != null){
			return this.nome.compareTo(paramT.getNome());
		}
		return 0;
	}	
}

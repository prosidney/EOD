package br.com.eod.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="tarefas")
public class Tarefa implements Serializable, Comparable<Tarefa>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	private String nome;
	
	private String motivacao;
	
    @Temporal( TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	private Date data;
	
    @Temporal( TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	private Date dataInicio;
    
    @Temporal( TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	private Date dataFim;
	
	private String duracao;
	
	private String divisivel;
	
	private String descricao;	
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Perfil perfil;
	
	@OneToMany(mappedBy="tarefa", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@Sort(type=SortType.COMPARATOR, comparator=Alerta.AlertaComparator.class)
	private SortedSet<Alerta> alerta;	
	
	public Tarefa() {
		super();
		// TODO Auto-generated constructor stub
	}
	
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
	public String getMotivacao() {
		return motivacao;
	}
	public void setMotivacao(String motivacao) {
		this.motivacao = motivacao;
	}
	public void setData(Date data) {
		this.data = data;
	}

	public Date getData() {
		return data;
	}

	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public String getDuracao() {
		return duracao;
	}
	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}
	public String getDivisivel() {
		return divisivel;
	}
	public void setDivisivel(String divisivel) {
		this.divisivel = divisivel;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Data getDataInicioD() {
		return new Data(this.dataInicio.getTime());
	}

	public Data getDataFimD() {
		if(this.dataFim != null){
			return new Data(this.dataFim.getTime());
		}else{
			return null;
		}
	}
	
	public String getDataInicioStr(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");  
		
		return dateFormat.format(this.dataInicio);
	}
	public String getDataFimStr(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");  
		
		return dateFormat.format(this.dataFim);
	}
	
	public void setAlerta(SortedSet<Alerta> alerta) {
		this.alerta = alerta;
	}

	public SortedSet<Alerta> getAlerta() {
		return alerta;
	}

	public boolean isDeAteDesabilitado(){
		if("AD".equals(this.motivacao) || this.motivacao == null){
			return true;
		}else{
			return false;
		}
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
		if(obj instanceof Tarefa){
			return this.id == ((Tarefa) obj).getId(); 
		}else{
			return false;
		}
	}
	public static class TarefaComparator implements Comparator<Tarefa>
	{
		public int compare(Tarefa t1, Tarefa t2)
		{	
			if(t1.getData() != null){
				Calendar cT1 = Calendar.getInstance();
				cT1.setTime(t1.getData());
				
				Calendar cT2 = Calendar.getInstance();
				cT2.setTime(t2.getData());
				
				return cT1.compareTo(cT2);
			}
			return 1;
		} 
	}
	@Override
	public int compareTo(Tarefa paramT) {
		Calendar cT1 = Calendar.getInstance();
		cT1.setTime(this.data);
		
		Calendar cT2 = Calendar.getInstance();
		cT2.setTime(paramT.getData());
		
		return cT1.compareTo(cT2);
	}	
	
	public Tarefa clone(){
		Tarefa tarefaClonada = new Tarefa();
		
		tarefaClonada.setNome(this.nome);
		tarefaClonada.setDescricao(this.descricao);
		tarefaClonada.setDivisivel(this.divisivel);
		tarefaClonada.setMotivacao(this.motivacao);
		tarefaClonada.setPerfil(this.perfil);
		
		return tarefaClonada;
	}
}

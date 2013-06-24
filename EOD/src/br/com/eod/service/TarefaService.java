package br.com.eod.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.eod.dao.impl.TarefaDao;
import br.com.eod.model.Perfil;
import br.com.eod.model.Tarefa;
import br.com.eod.model.Usuario;

public class TarefaService {
	@Autowired
	private TarefaDao tarefaDao;
	
	public TarefaDao getTarefaDao(){
		return tarefaDao;
	}
	
	public Tarefa salvarTarefa(Usuario usuario, Tarefa tarefaIncluido){
		List<Tarefa> todasTarefas = new ArrayList<Tarefa>(); 
		for (Perfil perfil : usuario.getPerfis()) {
			todasTarefas.addAll(perfil.getTarefas());
		}		
		
		if(tarefaIncluido.getMotivacao().equals("AD")){
			corrigirDataTarefa(tarefaIncluido);
			
			for (int i = 0; i < todasTarefas.size(); i++) {
				Tarefa tarefaExistente = todasTarefas.get(i);
				
				corrigirDataTarefa(tarefaExistente);
				
				Tarefa verificador = verificarConflitos(tarefaIncluido, tarefaExistente);
				if(verificador != null){
					return verificador;
				}
			}
			
			getTarefaDao().saveOrUpdate(tarefaIncluido);
		}else{
			splitTarefa(todasTarefas, tarefaIncluido);
		}
		
		return null;
	}
	
	public void splitTarefa(List<Tarefa> todasTarefas, Tarefa tarefa){
		int duracao = Integer.parseInt(tarefa.getDuracao());
		tarefa.setData(tarefa.getDataInicio());
		
		Date de = tarefa.getDataInicio();
		Date ate = tarefa.getDataFim();
		
		corrigirDataTarefa(tarefa);
		boolean gravado = false;
		boolean processado = false;
		if(tarefa.getDivisivel().equalsIgnoreCase("N")){
			for (int i = 0; i < todasTarefas.size(); i++) {
				Tarefa tarefa1 = todasTarefas.get(i);
				
				if(tarefa1.getDataFim().before(de)){
					continue;
				}
				processado = true;
				
				Tarefa tarefaNext = null;
				if(i+1 < todasTarefas.size())
					tarefaNext = todasTarefas.get(i+1);
				
				Calendar dt1 = GregorianCalendar.getInstance();
				dt1.setTime(tarefa1.getDataFim());
				
				Calendar dt2 = GregorianCalendar.getInstance();
				if(tarefaNext == null){
					dt2.setTime(ate);
				}else{
					dt2.setTime(tarefaNext.getDataInicio());				
				}
				
				int range = 0;
				while (dt1.before(dt2) && range < Integer.parseInt(tarefa.getDuracao())) {
					range++;
					dt1.add(Calendar.MINUTE, 1);
					
					if(range == Integer.parseInt(tarefa.getDuracao())){
						tarefa.setData(tarefa1.getDataFim());
						corrigirDataTarefa(tarefa);
						getTarefaDao().saveOrUpdate(tarefa);
						
						gravado = true;
						break;
					}
				}
				if(gravado){
					break;
				}
			}
			if(!processado){
				Calendar dt1 = GregorianCalendar.getInstance();
				dt1.setTime(de);
				dt1.add(Calendar.MINUTE, duracao);
				
				Calendar dt2 = GregorianCalendar.getInstance();
				dt2.setTime(ate);
				
				if(dt1.before(dt2)){
					getTarefaDao().saveOrUpdate(tarefa);
					gravado = true;
				}
			}
			
			
			if(!gravado){
				//lançar exceção
			}
		}else{
			int duracaoRestante = duracao;
			List<Tarefa> tarefasSalvar = new ArrayList<Tarefa>();
			Date dataInicio = null;
			
			Integer pt = 0;
			for (int i = 0; i < todasTarefas.size(); i++) {
				Calendar dt1 = GregorianCalendar.getInstance();
				Calendar dt2 = GregorianCalendar.getInstance();
				
				Tarefa tarefa1 = todasTarefas.get(i);
				
				if(tarefa1.getDataFim().before(de)){
					if(todasTarefas.get(i+1).getDataInicio().after(de)){
						dt1.setTime(de);
						dt2.setTime(todasTarefas.get(i+1).getDataInicio());
					}else{
						continue;
					}
				}else{
					dt1.setTime(tarefa1.getDataFim());
				}
				
				processado = true;
				
				Tarefa tarefaNext = null;
				if(i+1 < todasTarefas.size())
					tarefaNext = todasTarefas.get(i+1);
				
				
				if(tarefaNext == null){
					dt2.setTime(ate);
				}else{
					dt2.setTime(tarefaNext.getDataInicio());				
				}
				
				int range = 0;
				while (dt1.before(dt2) && range <= 10 && duracaoRestante > 0) {
					range++;
					
					if(range == 10){
						if(dataInicio == null){
							dataInicio = tarefa.getDataInicio();
						}
						
						Tarefa tarefaSplit = tarefa.clone();
						tarefaSplit.setNome(tarefaSplit.getNome()+"(Pt."+pt.toString()+")");
						tarefaSplit.setData(dataInicio);
						
						if(duracaoRestante < 10){
							tarefaSplit.setDuracao(String.valueOf(duracaoRestante));
							duracaoRestante=0;
						}else{
							tarefaSplit.setDuracao(String.valueOf(10));
							duracaoRestante = duracaoRestante-10;
						}
						
						corrigirDataTarefa(tarefaSplit);
						tarefasSalvar.add(tarefaSplit);
						dataInicio = tarefaSplit.getDataFim();
						
						pt++;
						range = 0;
						
						//continue;
					}
					dt1.add(Calendar.MINUTE, 1);
				}
				if(!dt1.before(dt2)){
					if(tarefaNext != null){
						dataInicio = tarefaNext.getDataFim();
					}
				}
			}
			
			if(!processado){
				Calendar dt1 = GregorianCalendar.getInstance();
				dt1.setTime(de);
				dt1.add(Calendar.MINUTE, duracao);
				
				Calendar dt2 = GregorianCalendar.getInstance();
				dt2.setTime(ate);
				
				if(dt1.before(dt2)){
					duracaoRestante = 0;
					
					Date proxima = null;
					while(duracao > 0){
						Tarefa tarefaSplit = tarefa.clone();
						
						tarefaSplit.setNome(tarefaSplit.getNome()+"(Pt."+pt.toString()+")");
						
						if(duracao <10){
							tarefaSplit.setDuracao(String.valueOf(duracao));
						}else{
							tarefaSplit.setDuracao(String.valueOf(10));
						}
						
						if(proxima == null){
							tarefaSplit.setDataInicio(tarefa.getDataInicio());
							tarefaSplit.setData(tarefa.getDataInicio());
						}else{
							tarefaSplit.setDataInicio(proxima);
							tarefaSplit.setData(proxima);
						}
						
						corrigirDataTarefa(tarefaSplit);
						proxima=tarefaSplit.getDataFim();
						
						tarefasSalvar.add(tarefaSplit);
						duracao = duracao-10;
						pt++;
					}
				}
			}
			
			if(duracaoRestante <= 0){
				for (Tarefa tarefaSalvar : tarefasSalvar) {
					getTarefaDao().saveOrUpdate(tarefaSalvar);
				}
			}else{
				//lançar exceção
			}
		}
	}
	
	private void corrigirDataTarefa(Tarefa tarefa){
		Date dataInicio = tarefa.getData();
		
		Calendar dataFim = GregorianCalendar.getInstance();
		dataFim.setTime(tarefa.getData());
		dataFim.add(Calendar.MINUTE, Integer.parseInt(tarefa.getDuracao()));
		
		tarefa.setDataInicio(dataInicio);
		tarefa.setDataFim(dataFim.getTime());
	}
	
	private Tarefa verificarConflitos(Tarefa tarefaIncluido, Tarefa tarefaExistente){
		if(tarefaExistente.getDataInicio().after(tarefaIncluido.getDataInicio()) &&
			tarefaExistente.getDataFim().before(tarefaIncluido.getDataFim())){
			return tarefaExistente;
		}
		
		if(tarefaExistente.getDataInicio().before(tarefaIncluido.getDataInicio()) &&
			tarefaExistente.getDataFim().after(tarefaIncluido.getDataInicio())){
			return tarefaExistente;
		}	
		
		if(tarefaExistente.getDataInicio().before(tarefaIncluido.getDataFim()) &&
			tarefaExistente.getDataFim().after(tarefaIncluido.getDataFim())){
			return tarefaExistente;
		}		
		
		if(tarefaExistente.getDataInicio().before(tarefaIncluido.getDataInicio()) &&
			tarefaExistente.getDataFim().after(tarefaIncluido.getDataFim())){
			return tarefaExistente;
		}		
		return null;
	}
}


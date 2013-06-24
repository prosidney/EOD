package br.com.eod.converter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import br.com.eod.model.Tarefa;

public class TarefaFormatter implements Formatter<Tarefa>{
	
	@Override
	public String print(Tarefa tarefa, Locale paramLocale) {
		return tarefa.toString();
	}

	@Override
	public Tarefa parse(String paramString, Locale paramLocale)
			throws ParseException {
		Tarefa tarefa = new Tarefa();
		tarefa.setId(Integer.parseInt(paramString));
		
		return tarefa;
	}


}

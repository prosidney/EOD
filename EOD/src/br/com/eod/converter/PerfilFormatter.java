package br.com.eod.converter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import br.com.eod.model.Perfil;

public class PerfilFormatter implements Formatter<Perfil>{
	
	@Override
	public String print(Perfil perfil, Locale paramLocale) {
		return perfil.toString();
	}

	@Override
	public Perfil parse(String paramString, Locale paramLocale)
			throws ParseException {
		Perfil perfil = new Perfil();
		perfil.setId(Integer.parseInt(paramString));
		
		return perfil;
	}


}

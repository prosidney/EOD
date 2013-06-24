package br.com.eod.converter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import br.com.eod.model.Usuario;

public class UsuarioFormatter implements Formatter<Usuario>{
	
	@Override
	public String print(Usuario usuario, Locale paramLocale) {
		return usuario.toString();
	}

	@Override
	public Usuario parse(String paramString, Locale paramLocale)
			throws ParseException {
		Usuario usuario = new Usuario();
		usuario.setId(Integer.parseInt(paramString));
		
		return usuario;
	}


}

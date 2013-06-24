package br.com.eod.converter;

import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.stereotype.Component;

import br.com.eod.model.Perfil;
import br.com.eod.model.Tarefa;
import br.com.eod.model.Usuario;

@Component
public class FormattingConverters extends
        FormattingConversionServiceFactoryBean {
 
    @Override
    public void installFormatters(FormatterRegistry registry) {
        super.installFormatters(registry);
        registry.addFormatterForFieldType(Perfil.class, new PerfilFormatter());
        registry.addFormatterForFieldType(Tarefa.class, new TarefaFormatter());
        registry.addFormatterForFieldType(Usuario.class, new UsuarioFormatter());
    }
}
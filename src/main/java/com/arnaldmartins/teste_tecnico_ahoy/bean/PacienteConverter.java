package com.arnaldmartins.teste_tecnico_ahoy.bean;

import javax.enterprise.inject.spi.CDI;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.arnaldmartins.teste_tecnico_ahoy.model.Paciente;
import com.arnaldmartins.teste_tecnico_ahoy.service.PacienteService;

@FacesConverter(value = "pacienteConverter")
public class PacienteConverter implements Converter<Paciente> {
    
    private PacienteService getPacienteService() {
        return CDI.current().select(PacienteService.class).get();
    }

    @Override
    public Paciente getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                Long id = Long.valueOf(value);
                return getPacienteService().buscarPorId(id);
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de conversão de Paciente.", "Paciente inválido."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Paciente value) {
        if (value != null) {
            return String.valueOf(value.getId());
        } else {
            return null;
        }
    }
}

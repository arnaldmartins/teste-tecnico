package com.arnaldmartins.teste_tecnico_ahoy.bean;

import javax.enterprise.inject.spi.CDI;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.arnaldmartins.teste_tecnico_ahoy.model.Medicamento;
import com.arnaldmartins.teste_tecnico_ahoy.service.MedicamentoService;

@FacesConverter(value = "medicamentoConverter")
public class MedicamentoConverter implements Converter<Medicamento> {
    
    private MedicamentoService getMedicamentoService() {
        return CDI.current().select(MedicamentoService.class).get();
    }

    @Override
    public Medicamento getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                Long id = Long.valueOf(value);
                return getMedicamentoService().buscarPorId(id);
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de conversão de Medicamento.", "Medicamento inválido."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Medicamento value) {
        if (value != null) {
            return String.valueOf(value.getId());
        } else {
            return null;
        }
    }
}

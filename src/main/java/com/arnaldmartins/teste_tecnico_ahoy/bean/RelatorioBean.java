package com.arnaldmartins.teste_tecnico_ahoy.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.arnaldmartins.teste_tecnico_ahoy.service.ReceitaService;
import com.arnaldmartins.teste_tecnico_ahoy.dto.MedicamentoPrescritoDTO;
import com.arnaldmartins.teste_tecnico_ahoy.dto.PacienteComMedicamentosDTO;

@Named
@ViewScoped
public class RelatorioBean implements Serializable {

    @Inject
    private ReceitaService receitaService;

    private List<MedicamentoPrescritoDTO> top2Medicamentos;
    private List<PacienteComMedicamentosDTO> top2Pacientes;
    private List<PacienteComMedicamentosDTO> todosPacientesComTotalMedicamentos;

    @PostConstruct
    public void init() {
        carregarRelatorios();
    }

    public void carregarRelatorios() {
        top2Medicamentos = receitaService.getTop2MedicamentosMaisPrescritos();
        top2Pacientes = receitaService.getTop2PacientesComMaisMedicamentos();
        todosPacientesComTotalMedicamentos = receitaService.getPacientesComQuantidadeTotalMedicamentos();
    }

    public List<MedicamentoPrescritoDTO> getTop2Medicamentos() {
        return top2Medicamentos;
    }

    public List<PacienteComMedicamentosDTO> getTop2Pacientes() {
        return top2Pacientes;
    }

    public List<PacienteComMedicamentosDTO> getTodosPacientesComTotalMedicamentos() {
        return todosPacientesComTotalMedicamentos;
    }
}
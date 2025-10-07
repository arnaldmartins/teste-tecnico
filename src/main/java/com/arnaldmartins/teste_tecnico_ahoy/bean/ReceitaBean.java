package com.arnaldmartins.teste_tecnico_ahoy.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.arnaldmartins.teste_tecnico_ahoy.model.Medicamento;
import com.arnaldmartins.teste_tecnico_ahoy.model.MedicamentoReceita;
import com.arnaldmartins.teste_tecnico_ahoy.model.Paciente;
import com.arnaldmartins.teste_tecnico_ahoy.model.Receita;
import com.arnaldmartins.teste_tecnico_ahoy.service.MedicamentoService;
import com.arnaldmartins.teste_tecnico_ahoy.service.PacienteService;
import com.arnaldmartins.teste_tecnico_ahoy.service.ReceitaService;

@Named
@ViewScoped
public class ReceitaBean implements Serializable {

    @Inject
    private PacienteService pacienteService;
    @Inject
    private MedicamentoService medicamentoService;
    @Inject
    private ReceitaService receitaService;

    private Receita receita;
    private Paciente pacienteSelecionado; // Para o autocomplete do paciente
    private List<MedicamentoReceita> medicamentosReceita;
    private MedicamentoReceita medicamentoReceitaAtual; // Para adicionar um novo item
    private Medicamento medicamentoSelecionado; // Para o autocomplete do medicamento
    
    @PostConstruct
    void init() {
    	receita = new Receita();
    	receita.setDataReceita(new Date()); // Define a data atual
    	pacienteSelecionado = null;
    	medicamentosReceita = new ArrayList<>();
    	medicamentoReceitaAtual = new MedicamentoReceita();
    	medicamentoSelecionado = null;
    }

    // Métodos para autocomplete de Paciente
    public List<Paciente> completarPaciente(String query) {
        return pacienteService.pesquisar(query, null, 0, 10);
    }

    // Métodos para autocomplete de Medicamento
    public List<Medicamento> completarMedicamento(String query) {
        return medicamentoService.pesquisar(query, 0, 10);
    }

    // Método para adicionar um medicamento à lista de itens da receita
    public void adicionarMedicamentoReceita() {
        if (medicamentoSelecionado != null && medicamentoReceitaAtual.getQuantidade() != null
        		&& medicamentoReceitaAtual.getQuantidade() > 0) {
            medicamentoReceitaAtual.setMedicamento(medicamentoSelecionado);
            medicamentosReceita.add(medicamentoReceitaAtual);
            medicamentoReceitaAtual = new MedicamentoReceita(); // Reseta para o próximo item
            medicamentoSelecionado = null; // Limpa o campo de medicamento
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Preencha todos os campos do medicamento.", null));
        }
    }

    // Método para remover um medicamento da lista
    public void removerMedicamentoReceita(MedicamentoReceita item) {
        medicamentosReceita.remove(item);
    }

    // Método para salvar a receita completa
    public void salvarReceita() {
        if (pacienteSelecionado == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione um paciente.", null));
        }
        else if (medicamentosReceita.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Adicione pelo menos um medicamento à receita.", null));
        }else {
        	receita.setPaciente(pacienteSelecionado);
        	//receita.setDataReceita(new Date()); // Define a data atual
        	receita.getMedicamentos().clear(); // Limpa antes de adicionar para evitar duplicidade
        	for (MedicamentoReceita item : medicamentosReceita) {
        		item.setReceita(receita); // Garante a relação bidirecional
        		receita.getMedicamentos().add(item);
        	}
        	
        	try {
        		receitaService.salvarReceita(receita);
        		init();
        		
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Receita salva com sucesso!", null));
        		
        	}catch(Exception e) {
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar Receita "+e.getCause().getMessage(), null));
        	}
        	
        }        
    }

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}

	public Paciente getPacienteSelecionado() {
		return pacienteSelecionado;
	}

	public void setPacienteSelecionado(Paciente pacienteSelecionado) {
		this.pacienteSelecionado = pacienteSelecionado;
	}

	public List<MedicamentoReceita> getMedicamentosReceita() {
		return medicamentosReceita;
	}

	public void setMedicamentosReceita(List<MedicamentoReceita> medicamentosReceita) {
		this.medicamentosReceita = medicamentosReceita;
	}

	public MedicamentoReceita getMedicamentoReceitaAtual() {
		return medicamentoReceitaAtual;
	}

	public void setMedicamentoReceitaAtual(MedicamentoReceita medicamentoReceitaAtual) {
		this.medicamentoReceitaAtual = medicamentoReceitaAtual;
	}

	public Medicamento getMedicamentoSelecionado() {
		return medicamentoSelecionado;
	}

	public void setMedicamentoSelecionado(Medicamento medicamentoSelecionado) {
		this.medicamentoSelecionado = medicamentoSelecionado;
	}

    
}

package com.arnaldmartins.teste_tecnico_ahoy.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import com.arnaldmartins.teste_tecnico_ahoy.model.Medicamento;
import com.arnaldmartins.teste_tecnico_ahoy.model.Paciente;
import com.arnaldmartins.teste_tecnico_ahoy.model.Receita;
import com.arnaldmartins.teste_tecnico_ahoy.service.MedicamentoService;
import com.arnaldmartins.teste_tecnico_ahoy.service.PacienteService;
import com.arnaldmartins.teste_tecnico_ahoy.service.ReceitaService;

@Named
@ViewScoped
public class ConsultaMedicamentoBean implements Serializable {

    // Campos para a pesquisa
    private String termoPesquisaPaciente; // Para pesquisa por nome de paciente
    private String termoPesquisaMedicamento; // Para pesquisa por nome de medicamento
    private List<Receita> receitasEncontradas; // Lista para exibir os resultados da pesquisa

    private Paciente pacientePesquisaSelecionado;
    private Medicamento medicamentoPesquisaSelecionado;

    @Inject
    private ReceitaService receitaService;
    @Inject
    private PacienteService pacienteService;
    @Inject
    private MedicamentoService medicamentoService;
    
    private LazyDataModel<Receita> lazyReceitas;


    public void pesquisarReceitas() {
        
    	this.termoPesquisaPaciente = pacientePesquisaSelecionado != null ? pacientePesquisaSelecionado.getNome() : null;
    	this.termoPesquisaMedicamento = medicamentoPesquisaSelecionado != null ? medicamentoPesquisaSelecionado.getNome() : null;
        //this.receitasEncontradas = receitaService.buscarReceitasPorNome(termoPesquisaPaciente, termoPesquisaMedicamento, 1,10);
        
        lazyReceitas = new LazyDataModel<Receita>() {

			@Override
			public int count(Map<String, FilterMeta> filterBy) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<Receita> load(int first, int pageSize, Map<String, SortMeta> sortBy,
					Map<String, FilterMeta> filterBy) {
                List<Receita> data = receitaService.buscarReceitasPorNome(termoPesquisaPaciente, termoPesquisaMedicamento, first, pageSize);
                this.setRowCount(receitaService.contarPesquisa(termoPesquisaPaciente, termoPesquisaMedicamento));
                if (data.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nenhuma receita encontrada com os critérios informados.", null));
                }
                return data;
			}
			
			@Override
            public String getRowKey(Receita object) {
                return object.getId().toString();
            }

			@Override
            public Receita getRowData(String rowKey) {
                if (rowKey == null || rowKey.isEmpty()) {
                    return null;
                }
                try {
                    Long id = Long.valueOf(rowKey);
                    Receita foundInCurrentData = this.getWrappedData().stream()
                                                    .filter(p -> p.getId().equals(id))
                                                    .findFirst()
                                                    .orElse(null);
                    if (foundInCurrentData != null) {
                        return foundInCurrentData;
                    }
                    // Se não estiver na lista atual, busque no banco de dados
                    return receitaService.buscarPorId(id);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
			
			
        };

        /*
        if (receitasEncontradas.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nenhuma receita encontrada com os critérios informados.", null));
        }*/
    }

    // Métodos de autocomplete para os campos de pesquisa (se desejar usar)
    public List<Paciente> completarPacientePesquisa(String query) {
        return pacienteService.pesquisar(query,null,0,10);
    }

    public List<Medicamento> completarMedicamentoPesquisa(String query) {
        return medicamentoService.pesquisar(query,0,10);
    }
    public String getTermoPesquisaPaciente() {
        return termoPesquisaPaciente;
    }

    public void setTermoPesquisaPaciente(String termoPesquisaPaciente) {
        this.termoPesquisaPaciente = termoPesquisaPaciente;
    }

    public String getTermoPesquisaMedicamento() {
        return termoPesquisaMedicamento;
    }

    public void setTermoPesquisaMedicamento(String termoPesquisaMedicamento) {
        this.termoPesquisaMedicamento = termoPesquisaMedicamento;
    }

    public List<Receita> getReceitasEncontradas() {
        return receitasEncontradas;
    }

    public void setReceitasEncontradas(List<Receita> receitasEncontradas) {
        this.receitasEncontradas = receitasEncontradas;
    }

    public Paciente getPacientePesquisaSelecionado() {
        return pacientePesquisaSelecionado;
    }

    public void setPacientePesquisaSelecionado(Paciente pacientePesquisaSelecionado) {
        this.pacientePesquisaSelecionado = pacientePesquisaSelecionado;
    }

    public Medicamento getMedicamentoPesquisaSelecionado() {
        return medicamentoPesquisaSelecionado;
    }

    public void setMedicamentoPesquisaSelecionado(Medicamento medicamentoPesquisaSelecionado) {
        this.medicamentoPesquisaSelecionado = medicamentoPesquisaSelecionado;
    }

	public LazyDataModel<Receita> getLazyReceitas() {
		return lazyReceitas;
	}

	public void setLazyReceitas(LazyDataModel<Receita> lazyReceitas) {
		this.lazyReceitas = lazyReceitas;
	}
    
    
}
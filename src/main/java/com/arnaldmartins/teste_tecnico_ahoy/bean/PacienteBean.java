package com.arnaldmartins.teste_tecnico_ahoy.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import com.arnaldmartins.teste_tecnico_ahoy.model.Paciente;
import com.arnaldmartins.teste_tecnico_ahoy.service.PacienteService;

@Named 
@ViewScoped
public class PacienteBean implements Serializable {

    @Inject
    private PacienteService pacienteService;

    private Paciente paciente; // Para edição ou novo cadastro
    private Paciente pacienteSelecionado; // Para seleção na tabela
    private List<Paciente> pacientes; // Lista completa ou resultado da pesquisa
    private String nomePesquisa;
    private String cpfPesquisa;

    // Para Lazy Loading do PrimeFaces
    private LazyDataModel<Paciente> lazyPacientes;

    @PostConstruct
    public void init() {
        //paciente = new Paciente(); 
        lazyPacientes = new LazyDataModel<Paciente>() {

			@Override
			public int count(Map<String, FilterMeta> filterBy) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<Paciente> load(int first, int pageSize, Map<String, SortMeta> sortBy,
					Map<String, FilterMeta> filterBy) {
                List<Paciente> data = pacienteService.pesquisar(nomePesquisa, cpfPesquisa, first, pageSize);
                this.setRowCount(pacienteService.contarPesquisa(nomePesquisa, cpfPesquisa));
                return data;
			}
			
			@Override
            public String getRowKey(Paciente object) {
                return object.getId().toString(); // Retorna o ID do paciente como chave
            }

			@Override
            public Paciente getRowData(String rowKey) {
                if (rowKey == null || rowKey.isEmpty()) {
                    return null;
                }
                try {
                    Long id = Long.valueOf(rowKey);
                    // tentar encontrar o objeto na lista atualmente carregada primeiro
                    // isso evita uma ida desnecessária ao banco se o objeto já estiver na tela
                    Paciente foundInCurrentData = this.getWrappedData().stream()
                                                    .filter(p -> p.getId().equals(id))
                                                    .findFirst()
                                                    .orElse(null);
                    if (foundInCurrentData != null) {
                        return foundInCurrentData;
                    }
                    // Se não estiver na lista atual, busque no banco de dados
                    return pacienteService.buscarPorId(id);
                } catch (NumberFormatException e) {
                    return null;
                }
            }			
			
        };
    }

    public void novo() {
        paciente = new Paciente();
    }

    public void salvar() {
    	try {
    		pacienteService.salvar(paciente);
    		paciente = null; // Limpa o formulário
    		// Adicionar mensagem de sucesso
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Paciente salvo com sucesso!"));    		
    	
    	}catch(Exception e) {
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar Paciente: "+e.getCause().getMessage(), null));
    	}
    }

    public void editar() {
        // paciente já deve ser carregado via pacienteSelecionado
        paciente = pacienteSelecionado;
    }

    public void remover() {
        if (pacienteSelecionado != null && pacienteSelecionado.getId() != null) {
        	try {
        		pacienteService.remover(pacienteSelecionado);
        		pacienteSelecionado = null; // Limpa a seleção
        		paciente = null;
        		// Adicionar mensagem de sucesso
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Paciente removido com sucesso!"));        		
        	}catch(Exception e) {
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir Paciente: "+e.getCause().getMessage(), null));
        	}
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Nenhum paciente selecionado para remoção."));
        }
    }

    public void pesquisar() {
        // O lazyDataModel já vai recarregar os dados com os novos filtros
        // Apenas para forçar a atualização da tabela se necessário
    }

    // Getters e Setters para paciente, pacienteSelecionado, nomePesquisa, cpfPesquisa, lazyPacientes
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public Paciente getPacienteSelecionado() { return pacienteSelecionado; }
    public void setPacienteSelecionado(Paciente pacienteSelecionado) { this.pacienteSelecionado = pacienteSelecionado; }
    public String getNomePesquisa() { return nomePesquisa; }
    public void setNomePesquisa(String nomePesquisa) { this.nomePesquisa = nomePesquisa; }
    public String getCpfPesquisa() { return cpfPesquisa; }
    public void setCpfPesquisa(String cpfPesquisa) { this.cpfPesquisa = cpfPesquisa; }
    public LazyDataModel<Paciente> getLazyPacientes() { return lazyPacientes; }
}	

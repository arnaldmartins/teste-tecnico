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

import com.arnaldmartins.teste_tecnico_ahoy.model.Medicamento;
import com.arnaldmartins.teste_tecnico_ahoy.service.MedicamentoService;

@Named
@ViewScoped
public class MedicamentoBean implements Serializable {

    @Inject
    private MedicamentoService medicamentoService;

    private Medicamento medicamento; // Para edição ou novo cadastro
    private Medicamento medicamentoSelecionado; // Para seleção na tabela
    private List<Medicamento> medicamentos; // Lista completa ou resultado da pesquisa
    private String nomePesquisa;
    private String cpfPesquisa;

    // Para Lazy Loading do PrimeFaces
    private LazyDataModel<Medicamento> lazyMedicamentos;

    @PostConstruct
    public void init() {
        //medicamento = new Medicamento();
        lazyMedicamentos = new LazyDataModel<Medicamento>() {

			@Override
			public int count(Map<String, FilterMeta> filterBy) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<Medicamento> load(int first, int pageSize, Map<String, SortMeta> sortBy,
					Map<String, FilterMeta> filterBy) {
                List<Medicamento> data = medicamentoService.pesquisar(nomePesquisa, first, pageSize);
                this.setRowCount(medicamentoService.contarPesquisa(nomePesquisa));
                return data;
			}
			
			@Override
            public String getRowKey(Medicamento object) {
                return object.getId().toString(); // Retorna o ID do medicamento como chave
            }

			@Override
            public Medicamento getRowData(String rowKey) {
                if (rowKey == null || rowKey.isEmpty()) {
                    return null;
                }
                try {
                    Long id = Long.valueOf(rowKey);
                    Medicamento foundInCurrentData = this.getWrappedData().stream()
                                                    .filter(p -> p.getId().equals(id))
                                                    .findFirst()
                                                    .orElse(null);
                    if (foundInCurrentData != null) {
                        return foundInCurrentData;
                    }
                    // Se não estiver na lista atual, buscAr no banco de dado
                    return medicamentoService.buscarPorId(id);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
			
			
        };
    }

    public void novo() {
        medicamento = new Medicamento();
    }

    public void salvar() {
    	try {
    		medicamentoService.salvar(medicamento);
    		medicamento = null; // Limpa o formulário
    		// Adicionar mensagem de sucesso
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Medicamento salvo com sucesso!"));    		
    	}catch(Exception e) {
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar Medicamento: "+e.getCause().getMessage(), null));
    	}
    }

    public void editar() {
        // medicamento já deve ser carregado via medicamentoSelecionado
        medicamento = medicamentoSelecionado;
    }

    public void remover() {
        if (medicamentoSelecionado != null && medicamentoSelecionado.getId() != null) {
        	try {
        		medicamentoService.remover(medicamentoSelecionado);
        		medicamentoSelecionado = null; // Limpa a seleção
        		medicamento = null;
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Medicamento removido com sucesso!"));        		
        	}catch(Exception e) {
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir Medicamento: "+e.getCause().getMessage(), null));
        	}
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Nenhum medicamento selecionado para remoção."));
        }
    }

    public void pesquisar() {
        // O lazyDataModel já vai carregar os dados com os novos filtros
        // Apenas para forçar a atualização da tabela se necessário
    }

    // Getters e Setters para medicamento, medicamentoSelecionado, nomePesquisa, cpfPesquisa, lazyMedicamentos
    public Medicamento getMedicamento() { return medicamento; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }
    public Medicamento getMedicamentoSelecionado() { return medicamentoSelecionado; }
    public void setMedicamentoSelecionado(Medicamento medicamentoSelecionado) { this.medicamentoSelecionado = medicamentoSelecionado; }
    public String getNomePesquisa() { return nomePesquisa; }
    public void setNomePesquisa(String nomePesquisa) { this.nomePesquisa = nomePesquisa; }
    public String getCpfPesquisa() { return cpfPesquisa; }
    public void setCpfPesquisa(String cpfPesquisa) { this.cpfPesquisa = cpfPesquisa; }
    public LazyDataModel<Medicamento> getLazyMedicamentos() { return lazyMedicamentos; }
}	

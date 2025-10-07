package com.arnaldmartins.teste_tecnico_ahoy.service;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.arnaldmartins.teste_tecnico_ahoy.dao.MedicamentoDAO;
import com.arnaldmartins.teste_tecnico_ahoy.dao.PacienteDAO;
import com.arnaldmartins.teste_tecnico_ahoy.dao.ReceitaDAO;
import com.arnaldmartins.teste_tecnico_ahoy.dto.MedicamentoPrescritoDTO;
import com.arnaldmartins.teste_tecnico_ahoy.dto.PacienteComMedicamentosDTO;
import com.arnaldmartins.teste_tecnico_ahoy.model.Receita;

@Stateless
public class ReceitaService {

    @Inject
    private ReceitaDAO receitaDAO;
    @Inject
    private PacienteDAO pacienteDAO;
    @Inject
    private MedicamentoDAO medicamentoDAO;
    
    public Receita buscarPorId(Long id) {
    	return receitaDAO.findById(id);
    }

    public void salvarReceita(Receita receita) {
        receitaDAO.save(receita);
    }
    
    public List<Receita> buscarReceitasPorNome(String nomePaciente, String nomeMedicamento, int first, int pageSize) {
        if ((nomePaciente == null || nomePaciente.trim().isEmpty()) &&
            (nomeMedicamento == null || nomeMedicamento.trim().isEmpty())) {
            return Collections.emptyList();
        }
        return receitaDAO.findByNomeAndMedicamento(nomePaciente, nomeMedicamento, first, pageSize);
    }
    
    public int contarPesquisa(String nomePaciente, String nomeMedicamento) {
    	int count = receitaDAO.countByNomeAndMedicamento(nomePaciente, nomeMedicamento);
        return count;
    }
    
    /**
     * Retorna os 2 medicamentos mais prescritos.
     */
    public List<MedicamentoPrescritoDTO> getTop2MedicamentosMaisPrescritos() {
        return receitaDAO.findTopMedicamentosMaisPrescritos(2);
    }

    /**
     * Retorna os 2 pacientes com mais medicamentos prescritos.
     */
    public List<PacienteComMedicamentosDTO> getTop2PacientesComMaisMedicamentos() {
        return receitaDAO.findTopPacientesComMaisMedicamentos(2);
    }

    /**
     * Retorna uma lista de todos os pacientes e a quantidade total de medicamentos receitados.
     */
    public List<PacienteComMedicamentosDTO> getPacientesComQuantidadeTotalMedicamentos() {
        return receitaDAO.findAllPacientesComQuantidadeTotalMedicamentos();
    }

    
}
package com.arnaldmartins.teste_tecnico_ahoy.dao;

import java.util.List;

import com.arnaldmartins.teste_tecnico_ahoy.dto.MedicamentoPrescritoDTO;
import com.arnaldmartins.teste_tecnico_ahoy.dto.PacienteComMedicamentosDTO;
import com.arnaldmartins.teste_tecnico_ahoy.model.Receita;

public interface ReceitaDAO extends GenericDAO<Receita, Long> {
    List<Receita> findByNomeAndMedicamento(String nomePaciente, String nomeMedicamento, int first, int pageSize);
    int countByNomeAndMedicamento(String nomePaciente, String nomeMedicamento);
    
    List<MedicamentoPrescritoDTO> findTopMedicamentosMaisPrescritos(int limit);
    List<PacienteComMedicamentosDTO> findTopPacientesComMaisMedicamentos(int limit);
    List<PacienteComMedicamentosDTO> findAllPacientesComQuantidadeTotalMedicamentos();
}

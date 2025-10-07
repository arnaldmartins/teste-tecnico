package com.arnaldmartins.teste_tecnico_ahoy.dao;

import java.util.List;

import com.arnaldmartins.teste_tecnico_ahoy.model.Medicamento;

public interface MedicamentoDAO extends GenericDAO<Medicamento, Long> {
    List<Medicamento> findByNome(String nome, int first, int pageSize);
    int countByNome(String nome);
}

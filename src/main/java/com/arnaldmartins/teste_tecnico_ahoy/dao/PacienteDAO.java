package com.arnaldmartins.teste_tecnico_ahoy.dao;

import java.util.List;

import com.arnaldmartins.teste_tecnico_ahoy.model.Paciente;

public interface PacienteDAO extends GenericDAO<Paciente, Long> {
    List<Paciente> findByNomeAndCpf(String nome, String cpf, int first, int pageSize);
    int countByNomeAndCpf(String nome, String cpf);
}

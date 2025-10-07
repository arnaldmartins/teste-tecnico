package com.arnaldmartins.teste_tecnico_ahoy.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.arnaldmartins.teste_tecnico_ahoy.dao.PacienteDAO;
import com.arnaldmartins.teste_tecnico_ahoy.model.Paciente;

@Stateless // Ou @ApplicationScoped se preferir um escopo mais longo e não precisar de transações JTA a cada método
public class PacienteService implements Serializable {

    @Inject
    private PacienteDAO pacienteDAO;

    @Transactional // Garante que o método seja executado dentro de uma transação
    public Paciente salvar(Paciente paciente) {
        if (paciente.getId() == null) {
            return pacienteDAO.save(paciente);
        } else {
            return pacienteDAO.update(paciente);
        }
    }

    @Transactional
    public void remover(Paciente paciente) {
        pacienteDAO.delete(paciente);
    }

    public Paciente buscarPorId(Long id) {
        return pacienteDAO.findById(id);
    }

    public List<Paciente> listarTodos() {
        return pacienteDAO.findAll();
    }

    public List<Paciente> pesquisar(String nome, String cpf, int first, int pageSize) {
        return pacienteDAO.findByNomeAndCpf(nome, cpf, first, pageSize);
    }

    public int contarPesquisa(String nome, String cpf) {
        return pacienteDAO.countByNomeAndCpf(nome, cpf);
    }
}

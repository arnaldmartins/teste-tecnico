package com.arnaldmartins.teste_tecnico_ahoy.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.arnaldmartins.teste_tecnico_ahoy.dao.MedicamentoDAO;
import com.arnaldmartins.teste_tecnico_ahoy.model.Medicamento;

@Stateless // Ou @ApplicationScoped se preferir um escopo mais longo e não precisar de transações JTA a cada método
public class MedicamentoService implements Serializable {

    @Inject
    private MedicamentoDAO medicamentoDAO;

    @Transactional // Garante que o método seja executado dentro de uma transação
    public Medicamento salvar(Medicamento medicamento) {
        if (medicamento.getId() == null) {
            return medicamentoDAO.save(medicamento);
        } else {
            return medicamentoDAO.update(medicamento);
        }
    }

    @Transactional
    public void remover(Medicamento medicamento) {
        medicamentoDAO.delete(medicamento);
    }

    public Medicamento buscarPorId(Long id) {
        return medicamentoDAO.findById(id);
    }

    public List<Medicamento> listarTodos() {
        return medicamentoDAO.findAll();
    }

    public List<Medicamento> pesquisar(String nome, int first, int pageSize) {
        return medicamentoDAO.findByNome(nome, first, pageSize);
    }

    public int contarPesquisa(String nome) {
        return medicamentoDAO.countByNome(nome);
    }
}

package com.arnaldmartins.teste_tecnico_ahoy.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import com.arnaldmartins.teste_tecnico_ahoy.dto.MedicamentoPrescritoDTO;
import com.arnaldmartins.teste_tecnico_ahoy.dto.PacienteComMedicamentosDTO;
import com.arnaldmartins.teste_tecnico_ahoy.model.Receita;

@Stateless
public class ReceitaDAOImpl extends GenericDAOImpl<Receita, Long> implements ReceitaDAO {

    @Override
    public List<Receita> findByNomeAndMedicamento(String nomePaciente, String nomeMedicamento, int first, int pageSize) {
    	StringBuilder jpql = new StringBuilder("SELECT DISTINCT r FROM Receita r ");
        List<String> conditions = new ArrayList<>();
        
        jpql.append("JOIN FETCH r.paciente p ");
        jpql.append("JOIN FETCH r.medicamentos ir JOIN FETCH ir.medicamento m ");
        
        if (nomePaciente != null && !nomePaciente.trim().isEmpty()) {
            conditions.add("LOWER(p.nome) LIKE LOWER(:nomePaciente)");
        }
        if (nomeMedicamento != null && !nomeMedicamento.trim().isEmpty()) {
            conditions.add("LOWER(m.nome) LIKE LOWER(:nomeMedicamento)");
        }        

        if (!conditions.isEmpty()) {
            jpql.append("WHERE ");
            jpql.append(String.join(" AND ", conditions));
        }

        // Adiciona um ORDER BY para garantir uma ordem consistente, por exemplo, pela data da receita
        jpql.append(" ORDER BY r.dataReceita DESC");

        TypedQuery<Receita> query = em.createQuery(jpql.toString(), Receita.class);

        if (nomePaciente != null && !nomePaciente.trim().isEmpty()) {
            query.setParameter("nomePaciente", "%" + nomePaciente + "%");
        }
        if (nomeMedicamento != null && !nomeMedicamento.trim().isEmpty()) {
            query.setParameter("nomeMedicamento", "%" + nomeMedicamento + "%");
        }

        return query
        		.setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public int countByNomeAndMedicamento(String nomePaciente, String nomeMedicamento) {
    	StringBuilder jpql = new StringBuilder("SELECT count(DISTINCT r) FROM Receita r ");
        List<String> conditions = new ArrayList<>();
        
        jpql.append("JOIN r.paciente p ");
        jpql.append("LEFT JOIN r.medicamentos ir LEFT JOIN ir.medicamento m ");
        
        if (nomePaciente != null && !nomePaciente.trim().isEmpty()) {
            conditions.add("LOWER(p.nome) LIKE LOWER(:nomePaciente)");
        }
        if (nomeMedicamento != null && !nomeMedicamento.trim().isEmpty()) {
            conditions.add("LOWER(m.nome) LIKE LOWER(:nomeMedicamento)");
        }        

        if (!conditions.isEmpty()) {
            jpql.append("WHERE ");
            jpql.append(String.join(" AND ", conditions));
        }

        TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);

        if (nomePaciente != null && !nomePaciente.trim().isEmpty()) {
            query.setParameter("nomePaciente", "%" + nomePaciente + "%");
        }
        if (nomeMedicamento != null && !nomeMedicamento.trim().isEmpty()) {
            query.setParameter("nomeMedicamento", "%" + nomeMedicamento + "%");
        }

        return query.getSingleResult().intValue();
    }
    
    /**
     * Retorna os N medicamentos mais prescritos, ordenados pela quantidade total.
     */
    public List<MedicamentoPrescritoDTO> findTopMedicamentosMaisPrescritos(int limit) {
        String jpql = "SELECT NEW com.arnaldmartins.teste_tecnico_ahoy.dto.MedicamentoPrescritoDTO(m.nome, SUM(ir.quantidade)) " +
                      "FROM Receita r JOIN r.medicamentos ir JOIN ir.medicamento m " +
                      "GROUP BY m.nome " +
                      "ORDER BY SUM(ir.quantidade) DESC";

        return em.createQuery(jpql, MedicamentoPrescritoDTO.class)
                 .setMaxResults(limit)
                 .getResultList();
    }

    /**
     * Retorna os N pacientes com mais medicamentos prescritos no total, ordenados pela quantidade.
     * (Considera a soma das 'quantidade' de ItemReceita para cada paciente em todas as suas receitas)
     */
    public List<PacienteComMedicamentosDTO> findTopPacientesComMaisMedicamentos(int limit) {
        String jpql = "SELECT NEW com.arnaldmartins.teste_tecnico_ahoy.dto.PacienteComMedicamentosDTO(p.nome, SUM(ir.quantidade)) " +
                      "FROM Receita r JOIN r.paciente p JOIN r.medicamentos ir " +
                      "GROUP BY p.nome " +
                      "ORDER BY SUM(ir.quantidade) DESC";

        return em.createQuery(jpql, PacienteComMedicamentosDTO.class)
                 .setMaxResults(limit)
                 .getResultList();
    }

    /**
     * Retorna uma lista de todos os pacientes e a quantidade total de medicamentos receitados
     * em todas as receitas para cada um.
     */
    public List<PacienteComMedicamentosDTO> findAllPacientesComQuantidadeTotalMedicamentos() {
        String jpql = "SELECT NEW com.arnaldmartins.teste_tecnico_ahoy.dto.PacienteComMedicamentosDTO(p.nome, SUM(ir.quantidade)) " +
                      "FROM Receita r JOIN r.paciente p JOIN r.medicamentos ir " +
                      "GROUP BY p.nome " +
                      "ORDER BY p.nome ASC"; // Ordena pelo nome do paciente para consistência

        return em.createQuery(jpql, PacienteComMedicamentosDTO.class)
                 .getResultList();
    }

}
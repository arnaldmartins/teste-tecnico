package com.arnaldmartins.teste_tecnico_ahoy.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.criteria.internal.predicate.LikePredicate;

import com.arnaldmartins.teste_tecnico_ahoy.model.Paciente;

@Stateless // Ou @ApplicationScoped
public class PacienteDAOImpl extends GenericDAOImpl<Paciente, Long> implements PacienteDAO {

    @Override
    public List<Paciente> findByNomeAndCpf(String nome, String cpf, int first, int pageSize) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Paciente> cq = cb.createQuery(Paciente.class);
        Root<Paciente> paciente = cq.from(Paciente.class);
        cq.select(paciente);

        List<LikePredicate> predicates = new ArrayList<>();
        if (nome != null && !nome.trim().isEmpty()) {
            predicates.add((LikePredicate) cb.like(cb.lower(paciente.get("nome")), "%" + nome.toLowerCase() + "%"));
        }
        if (cpf != null && !cpf.trim().isEmpty()) {
            predicates.add((LikePredicate) cb.like(paciente.get("cpf"), "%" + cpf + "%"));
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.and((javax.persistence.criteria.Predicate[]) predicates.toArray(new LikePredicate[0])));
        }

        return em.createQuery(cq)
                 .setFirstResult(first)
                 .setMaxResults(pageSize)
                 .getResultList();
    }

    @Override
    public int countByNomeAndCpf(String nome, String cpf) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Paciente> paciente = cq.from(Paciente.class);
        cq.select(cb.count(paciente));

        List<LikePredicate> predicates = new ArrayList<>();
        if (nome != null && !nome.trim().isEmpty()) {
            predicates.add((LikePredicate) cb.like(cb.lower(paciente.get("nome")), "%" + nome.toLowerCase() + "%"));
        }
        if (cpf != null && !cpf.trim().isEmpty()) {
            predicates.add((LikePredicate) cb.like(paciente.get("cpf"), "%" + cpf + "%"));
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.and((javax.persistence.criteria.Predicate[]) predicates.toArray(new LikePredicate[0])));
        }

        return em.createQuery(cq).getSingleResult().intValue();
    }
}
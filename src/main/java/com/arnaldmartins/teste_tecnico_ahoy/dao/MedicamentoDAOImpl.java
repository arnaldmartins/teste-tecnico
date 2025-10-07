package com.arnaldmartins.teste_tecnico_ahoy.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.criteria.internal.predicate.LikePredicate;

import com.arnaldmartins.teste_tecnico_ahoy.model.Medicamento;
import com.arnaldmartins.teste_tecnico_ahoy.model.Medicamento;

@Stateless // Ou @ApplicationScoped
public class MedicamentoDAOImpl extends GenericDAOImpl<Medicamento, Long> implements MedicamentoDAO {

    @Override
    public List<Medicamento> findByNome(String nome, int first, int pageSize) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Medicamento> cq = cb.createQuery(Medicamento.class);
        Root<Medicamento> medicamento = cq.from(Medicamento.class);
        cq.select(medicamento);

        List<LikePredicate> predicates = new ArrayList<>();
        if (nome != null && !nome.trim().isEmpty()) {
            predicates.add((LikePredicate) cb.like(cb.lower(medicamento.get("nome")), "%" + nome.toLowerCase() + "%"));
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
    public int countByNome(String nome) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Medicamento> medicamento = cq.from(Medicamento.class);
        cq.select(cb.count(medicamento));

        List<LikePredicate> predicates = new ArrayList<>();
        if (nome != null && !nome.trim().isEmpty()) {
            predicates.add((LikePredicate) cb.like(cb.lower(medicamento.get("nome")), "%" + nome.toLowerCase() + "%"));
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.and((javax.persistence.criteria.Predicate[]) predicates.toArray(new LikePredicate[0])));
        }

        return em.createQuery(cq).getSingleResult().intValue();
    }
}
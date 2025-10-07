package com.arnaldmartins.teste_tecnico_ahoy.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;		

@Entity
@Table(name = "medicamento_receita")
public class MedicamentoReceita implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedicamentoReceitado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receita_id", nullable = false)
    private Receita receita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamento;

    private Integer quantidade; // quantos volumes do medicamento foram receitados (Poderia ser a dosagem tambem)

	public Long getIdMedicamentoReceitado() {
		return idMedicamentoReceitado;
	}

	public void setIdMedicamentoReceitado(Long idMedicamentoReceitado) {
		this.idMedicamentoReceitado = idMedicamentoReceitado;
	}

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

    
}

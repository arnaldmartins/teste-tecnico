package com.arnaldmartins.teste_tecnico_ahoy.dto;

public class MedicamentoPrescritoDTO {
	
    private String nomeMedicamento;
    private Long quantidadeTotalPrescrita;

    public MedicamentoPrescritoDTO(String nomeMedicamento, Long quantidadeTotalPrescrita) {
        this.nomeMedicamento = nomeMedicamento;
        this.quantidadeTotalPrescrita = quantidadeTotalPrescrita;
    }

    public String getNomeMedicamento() {
        return nomeMedicamento;
    }

    public Long getQuantidadeTotalPrescrita() {
        return quantidadeTotalPrescrita;
    }

    public void setNomeMedicamento(String nomeMedicamento) {
        this.nomeMedicamento = nomeMedicamento;
    }

    public void setQuantidadeTotalPrescrita(Long quantidadeTotalPrescrita) {
        this.quantidadeTotalPrescrita = quantidadeTotalPrescrita;
    }

    @Override
    public String toString() {
        return "MedicamentoPrescritoDTO{" +
               "nomeMedicamento='" + nomeMedicamento + '\'' +
               ", quantidadeTotalPrescrita=" + quantidadeTotalPrescrita +
               '}';
    }
}
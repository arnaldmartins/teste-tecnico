package com.arnaldmartins.teste_tecnico_ahoy.dto;

public class PacienteComMedicamentosDTO {
    private String nomePaciente;
    private Long quantidadeTotalMedicamentos; 

    public PacienteComMedicamentosDTO(String nomePaciente, Long quantidadeTotalMedicamentos) {
        this.nomePaciente = nomePaciente;
        this.quantidadeTotalMedicamentos = quantidadeTotalMedicamentos;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public Long getQuantidadeTotalMedicamentos() {
        return quantidadeTotalMedicamentos;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public void setQuantidadeTotalMedicamentos(Long quantidadeTotalMedicamentos) {
        this.quantidadeTotalMedicamentos = quantidadeTotalMedicamentos;
    }

    @Override
    public String toString() {
        return "PacienteComMedicamentosDTO{" +
               "nomePaciente='" + nomePaciente + '\'' +
               ", quantidadeTotalMedicamentos=" + quantidadeTotalMedicamentos +
               '}';
    }
}
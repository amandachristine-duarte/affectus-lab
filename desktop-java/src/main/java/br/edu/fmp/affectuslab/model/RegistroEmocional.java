
package br.edu.fmp.affectuslab.model;

import java.time.LocalDateTime;

public class RegistroEmocional {

    private int id;
    private Usuario usuario;
    private Emocao emocao;
    private int intensidade;
    private LocalDateTime dataRegistro;

    public RegistroEmocional(int id, Usuario usuario,
            Emocao emocao, int intensidade,
            LocalDateTime dataRegistro) {

        this.id = id;
        this.usuario = usuario;
        this.emocao = emocao;
        setIntensidade(intensidade);
        this.dataRegistro = dataRegistro;
    }

    public int getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Emocao getEmocao() {
        return emocao;
    }

    public int getIntensidade() {
        return intensidade;
    }

    public void setIntensidade(int intensidade) {
        if (intensidade < 1 || intensidade > 10) {
            throw new IllegalArgumentException(
                "A intensidade deve estar entre 1 e 10."
            );
        }

        this.intensidade = intensidade;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }
}

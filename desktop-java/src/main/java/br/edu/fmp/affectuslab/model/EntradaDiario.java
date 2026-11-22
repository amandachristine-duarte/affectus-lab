
package br.edu.fmp.affectuslab.model;

import java.time.LocalDateTime;

public class EntradaDiario {

    private int id;
    private Usuario usuario;
    private String conteudo;
    private LocalDateTime dataCriacao;

    public EntradaDiario(int id, Usuario usuario,
            String conteudo, LocalDateTime dataCriacao) {

        this.id = id;
        this.usuario = usuario;
        setConteudo(conteudo);
        this.dataCriacao = dataCriacao;
    }

    public int getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {

        if (conteudo == null || conteudo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "O conteúdo do diário não pode estar vazio."
            );
        }

        this.conteudo = conteudo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
}

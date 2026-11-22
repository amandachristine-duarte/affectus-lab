
package br.edu.fmp.affectuslab.model;

public class Usuario extends Pessoa {

    private int id;
    private String senha;

    public Usuario(int id, String nome, String email, String senha) {
        super(nome, email);
        this.id = id;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String getDescricaoPerfil() {
        return "Usuário: " + getNome() + " | Email: " + getEmail();
    }
}

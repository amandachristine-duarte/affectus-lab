
package br.edu.fmp.affectuslab.controller;

import br.edu.fmp.affectuslab.model.dao.UsuarioDAO;
import java.sql.SQLException;
import java.util.List;

public class UsuarioController {

    private final UsuarioDAO dao = new UsuarioDAO();

    public int cadastrar(String nome, String email, String senha)
            throws SQLException {

        validarNomeEmail(nome, email);

        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException(
                "A senha é obrigatória."
            );
        }

        return dao.cadastrar(
            nome.trim(),
            email.trim().toLowerCase(),
            senha
        );
    }

    public List<String[]> listar() throws SQLException {
        return dao.listar();
    }

    public boolean atualizar(int id, String nome, String email)
            throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                "Selecione um usuário válido."
            );
        }

        validarNomeEmail(nome, email);

        return dao.atualizar(
            id,
            nome.trim(),
            email.trim().toLowerCase()
        );
    }

    public boolean excluir(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                "Selecione um usuário válido."
            );
        }

        return dao.excluir(id);
    }

    private void validarNomeEmail(String nome, String email) {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                "Informe o nome do usuário."
            );
        }

        if (email == null || email.isBlank()
                || !email.contains("@")) {
            throw new IllegalArgumentException(
                "Informe um e-mail válido."
            );
        }
    }
}

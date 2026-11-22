
package br.edu.fmp.affectuslab.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public int cadastrar(String nome, String email, String senha)
            throws SQLException {

        String sql = """
            INSERT INTO usuario (nome, email, senha)
            VALUES (?, ?, ?)
            RETURNING id
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Math.toIntExact(rs.getLong("id"));
                }
            }
        }

        throw new SQLException("Não foi possível cadastrar o usuário.");
    }

    public List<String[]> listar() throws SQLException {

        List<String[]> usuarios = new ArrayList<>();

        String sql = """
            SELECT id, nome, email
            FROM usuario
            ORDER BY id
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(new String[]{
                    String.valueOf(rs.getLong("id")),
                    rs.getString("nome"),
                    rs.getString("email")
                });
            }
        }

        return usuarios;
    }

    public boolean atualizar(int id, String nome, String email)
            throws SQLException {

        String sql = """
            UPDATE usuario
            SET nome = ?, email = ?
            WHERE id = ?
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setInt(3, id);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {

        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Autenticacao provisoria para a demonstracao academica.
    // Retorna ID, nome e email do usuario autenticado.
    public String[] autenticar(String email, String senha)
            throws SQLException {

        String sql = """
            SELECT id, nome, email
            FROM usuario
            WHERE LOWER(email) = LOWER(?)
              AND senha = ?
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email.trim());
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new String[]{
                        String.valueOf(rs.getLong("id")),
                        rs.getString("nome"),
                        rs.getString("email")
                    };
                }
            }
        }

        return null;
    }
}

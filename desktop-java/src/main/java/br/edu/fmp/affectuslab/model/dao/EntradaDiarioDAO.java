
package br.edu.fmp.affectuslab.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntradaDiarioDAO {

    public int salvar(int usuarioId, String conteudo)
            throws SQLException {

        String sql = """
            INSERT INTO entrada_diario
                (usuario_id, conteudo, data_criacao)
            VALUES (?, ?, CURRENT_TIMESTAMP)
            RETURNING id
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            stmt.setString(2, conteudo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Math.toIntExact(rs.getLong("id"));
                }
            }
        }

        throw new SQLException(
                "Não foi possível salvar a entrada do diário."
        );
    }

    public List<String[]> listarPorUsuario(int usuarioId)
            throws SQLException {

        List<String[]> entradas = new ArrayList<>();

        String sql = """
            SELECT id, conteudo, data_criacao
            FROM entrada_diario
            WHERE usuario_id = ?
            ORDER BY data_criacao DESC, id DESC
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    entradas.add(new String[]{
                        String.valueOf(rs.getLong("id")),
                        rs.getString("conteudo"),
                        String.valueOf(
                                rs.getTimestamp("data_criacao"))
                    });
                }
            }
        }

        return entradas;
    }
}

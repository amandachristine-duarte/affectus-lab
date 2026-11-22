
package br.edu.fmp.affectuslab.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroEmocionalDAO {

    public int registrar(
            int usuarioId,
            int emocaoId,
            int intensidade
    ) throws SQLException {

        String sql = """
            INSERT INTO registro_emocional
                (usuario_id, emocao_id, intensidade)
            VALUES (?, ?, ?)
            RETURNING id
            """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            stmt.setInt(2, emocaoId);
            stmt.setInt(3, intensidade);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Math.toIntExact(rs.getLong("id"));
                }
            }
        }

        throw new SQLException(
                "Não foi possível salvar o registro emocional."
        );
    }

    public List<String[]> listarPorUsuario(int usuarioId)
            throws SQLException {

        List<String[]> registros = new ArrayList<>();

        String sql = """
            SELECT
                r.id,
                e.nome AS emocao,
                r.intensidade,
                r.data_registro
            FROM registro_emocional r
            JOIN emocao e ON e.id = r.emocao_id
            WHERE r.usuario_id = ?
            ORDER BY r.data_registro DESC, r.id DESC
            """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    registros.add(new String[]{
                        String.valueOf(rs.getLong("id")),
                        rs.getString("emocao"),
                        String.valueOf(rs.getInt("intensidade")),
                        rs.getTimestamp("data_registro")
                                .toString()
                    });
                }
            }
        }

        return registros;
    }
}

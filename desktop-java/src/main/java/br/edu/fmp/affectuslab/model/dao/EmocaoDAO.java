
package br.edu.fmp.affectuslab.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmocaoDAO {

    public List<String[]> listarEmocoes() throws SQLException {

        List<String[]> emocoes = new ArrayList<>();

        String sql = """
            SELECT id, nome, categoria,
                   padrao_ativacao_sugerido,
                   descricao_simples, sensacoes_corpo
            FROM emocao
            ORDER BY nome
            """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                emocoes.add(new String[]{
                    String.valueOf(rs.getLong("id")),
                    rs.getString("nome"),
                    rs.getString("categoria"),
                    rs.getString("padrao_ativacao_sugerido"),
                    rs.getString("descricao_simples"),
                    rs.getString("sensacoes_corpo")
                });
            }
        }

        return emocoes;
    }

    public List<String[]> listarCorrelacionadas(int emocaoId)
            throws SQLException {

        List<String[]> sugestoes = new ArrayList<>();

        String sql = """
            SELECT e.id, e.nome, e.descricao_simples
            FROM emocao_correlacionada ec
            JOIN emocao e
                ON e.id = ec.correlacionada_id
            WHERE ec.emocao_id = ?
            ORDER BY ec.ordem
            """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setInt(1, emocaoId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    sugestoes.add(new String[]{
                        String.valueOf(rs.getLong("id")),
                        rs.getString("nome"),
                        rs.getString("descricao_simples")
                    });
                }
            }
        }

        return sugestoes;
    }
}

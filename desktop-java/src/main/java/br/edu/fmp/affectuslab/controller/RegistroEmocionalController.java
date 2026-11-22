
package br.edu.fmp.affectuslab.controller;

import br.edu.fmp.affectuslab.model.dao.EmocaoDAO;
import br.edu.fmp.affectuslab.model.dao.RegistroEmocionalDAO;
import java.sql.SQLException;
import java.util.List;

public class RegistroEmocionalController {

    private final EmocaoDAO emocaoDAO = new EmocaoDAO();

    private final RegistroEmocionalDAO registroDAO =
            new RegistroEmocionalDAO();

    public List<String[]> listarEmocoes() throws SQLException {
        return emocaoDAO.listarEmocoes();
    }

    public List<String[]> listarCorrelacionadas(int emocaoId)
            throws SQLException {

        if (emocaoId <= 0) {
            throw new IllegalArgumentException(
                    "Selecione uma experiência emocional válida."
            );
        }

        return emocaoDAO.listarCorrelacionadas(emocaoId);
    }

    public int registrar(
            int usuarioId,
            int emocaoId,
            int intensidade
    ) throws SQLException {

        if (usuarioId <= 0) {
            throw new IllegalArgumentException(
                    "Selecione um usuário válido."
            );
        }

        if (emocaoId <= 0) {
            throw new IllegalArgumentException(
                    "Selecione uma experiência emocional."
            );
        }

        if (intensidade < 0 || intensidade > 10) {
            throw new IllegalArgumentException(
                    "A intensidade deve estar entre 0 e 10."
            );
        }

        return registroDAO.registrar(
                usuarioId,
                emocaoId,
                intensidade
        );
    }

    public List<String[]> listarHistorico(int usuarioId)
            throws SQLException {

        if (usuarioId <= 0) {
            throw new IllegalArgumentException(
                    "Selecione um usuário para consultar o histórico."
            );
        }

        return registroDAO.listarPorUsuario(usuarioId);
    }
}


package br.edu.fmp.affectuslab.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/affectus_lab";

    private static final String USUARIO = "postgres";

    public static Connection conectar() throws SQLException {

        String senha = System.getenv("AFFECTUS_DB_PASSWORD");

        if (senha == null || senha.isBlank()) {
            throw new SQLException(
                "Configure a variável AFFECTUS_DB_PASSWORD."
            );
        }

        return DriverManager.getConnection(
                URL, USUARIO, senha
        );
    }
}

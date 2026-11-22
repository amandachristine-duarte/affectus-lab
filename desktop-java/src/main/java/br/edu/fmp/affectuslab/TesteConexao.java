
package br.edu.fmp.affectuslab;

import br.edu.fmp.affectuslab.model.dao.Conexao;
import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) {

        try (Connection conexao = Conexao.conectar()) {

            System.out.println(
                "Conexão com PostgreSQL realizada com sucesso!"
            );

            System.out.println(
                "Banco: " + conexao.getCatalog()
            );

        } catch (SQLException e) {

            System.out.println(
                "Erro na conexão: " + e.getMessage()
            );
        }
    }
}

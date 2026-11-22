
package br.edu.fmp.affectuslab;

import br.edu.fmp.affectuslab.model.Pessoa;
import br.edu.fmp.affectuslab.model.Usuario;
import br.edu.fmp.affectuslab.model.Emocao;
import br.edu.fmp.affectuslab.model.RegistroEmocional;
import java.time.LocalDateTime;
import br.edu.fmp.affectuslab.model.EntradaDiario;

public class TestePOO {

    public static void main(String[] args) {

        // TESTE 1 - HERANÇA E POLIMORFISMO

        Pessoa pessoa = new Pessoa(
                "Pessoa Exemplo",
                "pessoa@exemplo.com"
        );

        Usuario usuario = new Usuario(
                1,
                "Amanda",
                "amanda@exemplo.com",
                "senhaTeste"
        );

        System.out.println("=== TESTE DE POO ===");

        System.out.println(pessoa.getDescricaoPerfil());
        System.out.println(usuario.getDescricaoPerfil());

        Pessoa referencia = usuario;

        System.out.println("=== POLIMORFISMO ===");
        System.out.println(referencia.getDescricaoPerfil());


        // TESTE 2 - REGISTRO EMOCIONAL

        System.out.println("\n=== REGISTRO EMOCIONAL ===");

        Emocao alegria = new Emocao(
                1,
                "Alegria",
                "Emoção associada a experiências positivas."
        );

        RegistroEmocional registro = new RegistroEmocional(
                1,
                usuario,
                alegria,
                8,
                LocalDateTime.now()
        );

        System.out.println("Usuário: "
                + registro.getUsuario().getNome());

        System.out.println("Emoção: "
                + registro.getEmocao().getNome());

        System.out.println("Intensidade: "
                + registro.getIntensidade());

        System.out.println("Data: "
                + registro.getDataRegistro());

   
        // TESTE 3 - DIÁRIO EMOCIONAL

        System.out.println("\n=== DIÁRIO EMOCIONAL ===");

        EntradaDiario diario = new EntradaDiario(
                1,
                usuario,
                "Hoje consegui reconhecer minhas emoções "
                + "e compreender melhor como estou me sentindo.",
                LocalDateTime.now()
        );

        System.out.println("Usuário: "
                + diario.getUsuario().getNome());

        System.out.println("Conteúdo: "
                + diario.getConteudo());

        System.out.println("Data: "
                + diario.getDataCriacao());
 }
}

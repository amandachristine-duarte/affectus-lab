
package br.edu.fmp.affectuslab.view;

import java.awt.*;
import javax.swing.*;

public class TelaPrincipal extends JFrame {
    private final int usuarioId;
private final String nomeUsuario;

    private final Color FUNDO = new Color(246, 242, 235);
    private final Color AZUL = new Color(151, 163, 174);
    private final Color TERRACOTA = new Color(138, 80, 51);
    private final Color TEXTO = new Color(41, 61, 76);

    public TelaPrincipal(int usuarioId, String nomeUsuario) {

    this.usuarioId = usuarioId;
    this.nomeUsuario = nomeUsuario;

    setTitle("Affectus Lab | Página Inicial");
    setSize(850, 650);
    setMinimumSize(new Dimension(650, 500));
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    construirInterface();
}

    private void construirInterface() {

        JPanel principal = new JPanel(new BorderLayout(20, 20));
        principal.setBackground(FUNDO);
        principal.setBorder(
                BorderFactory.createEmptyBorder(35, 45, 35, 45)
        );

        JPanel cabecalho = new JPanel();
        cabecalho.setLayout(
                new BoxLayout(cabecalho, BoxLayout.Y_AXIS));
        cabecalho.setBackground(FUNDO);

        JLabel marca = new JLabel("AFFECTUS LAB");
        marca.setFont(new Font("Serif", Font.BOLD, 34));
        marca.setForeground(TERRACOTA);
        marca.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel(
                "Um espaço para reconhecer, compreender e acolher suas emoções."
        );
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitulo.setForeground(TEXTO);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        cabecalho.add(marca);
        cabecalho.add(Box.createVerticalStrut(12));
        cabecalho.add(subtitulo);

        principal.add(cabecalho, BorderLayout.NORTH);

        JPanel menu = new JPanel(new GridLayout(3, 2, 18, 18));
        menu.setBackground(FUNDO);
        menu.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        JButton usuarios = criarBotao(
                "Gestão de Usuários", AZUL);
        JButton checkin = criarBotao(
                "Check-in Emocional", TERRACOTA);
        JButton diario = criarBotao(
                "Diário Emocional", AZUL);
        JButton praticas = criarBotao(
                "Práticas de Regulação", TERRACOTA);
        JButton historias = criarBotao(
                "Histórias Interativas", AZUL);
        JButton sair = criarBotao(
                "Sair do Affectus Lab", TEXTO);

        menu.add(usuarios);
        menu.add(checkin);
        menu.add(diario);
        menu.add(praticas);
        menu.add(historias);
        menu.add(sair);

        principal.add(menu, BorderLayout.CENTER);

        JLabel rodape = new JLabel(
                "Consciência emocional, autonomia e possibilidades de escolha."
        );
        rodape.setForeground(TEXTO);
        rodape.setHorizontalAlignment(SwingConstants.CENTER);

        principal.add(rodape, BorderLayout.SOUTH);

        setContentPane(principal);

        usuarios.addActionListener(e ->
                new TelaUsuarios().setVisible(true));

        checkin.addActionListener(e ->
        new TelaCheckin(usuarioId).setVisible(true));

        diario.addActionListener(e ->
        new TelaDiario(usuarioId).setVisible(true));

        praticas.addActionListener(e ->
                mostrarEmDesenvolvimento("Práticas de Regulação"));

        historias.addActionListener(e ->
                mostrarEmDesenvolvimento("Histórias Interativas"));

        sair.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente sair do Affectus Lab?",
                    "Confirmar saída",
                    JOptionPane.YES_NO_OPTION
            );

            if (resposta == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFont(
                new Font("SansSerif", Font.BOLD, 15));
        botao.setFocusPainted(false);
        return botao;
    }

    private void mostrarEmDesenvolvimento(String recurso) {
        JOptionPane.showMessageDialog(
                this,
                recurso + "\n\n"
                + "Esta funcionalidade faz parte da evolução "
                + "do Affectus Lab.",
                "Affectus Lab",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

   public static void main(String[] args) {
    SwingUtilities.invokeLater(() ->
            new TelaLogin().setVisible(true));
}
}


package br.edu.fmp.affectuslab.view;

import br.edu.fmp.affectuslab.model.dao.UsuarioDAO;

import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;

public class TelaLogin extends JFrame {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    private final Color FUNDO = new Color(246, 242, 235);
    private final Color TERRACOTA = new Color(138, 80, 51);
    private final Color AZUL_ESCURO = new Color(41, 61, 76);
    private final Color AZUL = new Color(151, 163, 174);

    private JTextField campoEmail;
    private JPasswordField campoSenha;

    public TelaLogin() {
        construirInterface();
    }

    private void construirInterface() {

        setTitle("Affectus Lab | Acesso");
        setSize(550, 520);
        setMinimumSize(new Dimension(480, 450));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel principal = new JPanel();
        principal.setLayout(
                new BoxLayout(principal, BoxLayout.Y_AXIS));
        principal.setBackground(FUNDO);
        principal.setBorder(
                BorderFactory.createEmptyBorder(40, 65, 40, 65));

        JLabel marca = new JLabel("AFFECTUS LAB");
        marca.setFont(new Font("Serif", Font.BOLD, 34));
        marca.setForeground(TERRACOTA);
        marca.setAlignmentX(Component.CENTER_ALIGNMENT);
        principal.add(marca);

        principal.add(Box.createVerticalStrut(12));

        JLabel subtitulo = new JLabel(
                "Um espaço para cuidar da sua consciência emocional.");
        subtitulo.setForeground(AZUL_ESCURO);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        principal.add(subtitulo);

        principal.add(Box.createVerticalStrut(35));

        JLabel titulo = new JLabel("Bem-vindo(a)!");
        titulo.setFont(new Font("Serif", Font.BOLD, 24));
        titulo.setForeground(AZUL_ESCURO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        principal.add(titulo);

        principal.add(Box.createVerticalStrut(25));

        principal.add(criarLabel("E-mail"));

        campoEmail = new JTextField();
        campoEmail.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 38));
        principal.add(campoEmail);

        principal.add(Box.createVerticalStrut(15));

        principal.add(criarLabel("Senha"));

        campoSenha = new JPasswordField();
        campoSenha.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 38));
        principal.add(campoSenha);

        principal.add(Box.createVerticalStrut(25));

        JButton entrar = criarBotao("Entrar", TERRACOTA);
        principal.add(entrar);

        principal.add(Box.createVerticalStrut(12));

        JButton cadastrar = criarBotao(
                "Criar uma conta", AZUL);
        principal.add(cadastrar);

        principal.add(Box.createVerticalStrut(20));

        JLabel aviso = new JLabel(
                "Reconhecer suas emoções é um passo de autocuidado.");
        aviso.setFont(
                new Font("SansSerif", Font.PLAIN, 11));
        aviso.setForeground(AZUL_ESCURO);
        aviso.setAlignmentX(Component.CENTER_ALIGNMENT);
        principal.add(aviso);

        setContentPane(principal);

        entrar.addActionListener(e -> realizarLogin());

        cadastrar.addActionListener(e ->
                new TelaUsuarios().setVisible(true));

        getRootPane().setDefaultButton(entrar);
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(AZUL_ESCURO);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFont(
                new Font("SansSerif", Font.BOLD, 14));
        botao.setFocusPainted(false);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 42));
        return botao;
    }

    private void realizarLogin() {

        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword());

        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Informe seu e-mail e sua senha.");
            return;
        }

        try {
            String[] usuario =
                    usuarioDAO.autenticar(email, senha);

            if (usuario == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "E-mail ou senha incorretos.",
                        "Acesso não autorizado",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Bem-vindo(a), " + usuario[1] + "!",
                    "Affectus Lab",
                    JOptionPane.INFORMATION_MESSAGE
            );

            int usuarioId = Integer.parseInt(usuario[0]);
String nomeUsuario = usuario[1];

new TelaPrincipal(usuarioId, nomeUsuario).setVisible(true);
dispose();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao realizar login:\n"
                    + ex.getMessage(),
                    "Affectus Lab - Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new TelaLogin().setVisible(true));
    }
}

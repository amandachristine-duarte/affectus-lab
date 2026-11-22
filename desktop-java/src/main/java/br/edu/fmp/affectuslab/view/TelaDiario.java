
package br.edu.fmp.affectuslab.view;

import br.edu.fmp.affectuslab.model.dao.EntradaDiarioDAO;
import br.edu.fmp.affectuslab.model.dao.UsuarioDAO;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;

public class TelaDiario extends JFrame {

    private final EntradaDiarioDAO diarioDAO =
            new EntradaDiarioDAO();

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    private final Color FUNDO = new Color(246, 242, 235);
    private final Color TERRACOTA = new Color(138, 80, 51);
    private final Color AZUL_ESCURO = new Color(41, 61, 76);

    private JComboBox<String> comboUsuarios;
    private JTextArea campoReflexao;
    private JTextArea areaHistorico;
    private List<String[]> usuarios;

    private int usuarioSelecionadoId = -1;

    
public TelaDiario() {
    construirInterface();
    carregarUsuarios();
}

public TelaDiario(int usuarioId) {
    this();

    if (usuarios != null) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (Integer.parseInt(usuarios.get(i)[0]) == usuarioId) {
                comboUsuarios.setSelectedIndex(i + 1);
                comboUsuarios.setEnabled(false);
                break;
            }
        }
    }
}
    private void construirInterface() {

        setTitle("Affectus Lab | Diário Emocional");
        setSize(800, 720);
        setMinimumSize(new Dimension(600, 550));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel principal = new JPanel(new BorderLayout(15, 15));
        principal.setBackground(FUNDO);
        principal.setBorder(
                BorderFactory.createEmptyBorder(25, 35, 25, 35)
        );

        JPanel cabecalho = new JPanel();
        cabecalho.setLayout(
                new BoxLayout(cabecalho, BoxLayout.Y_AXIS));
        cabecalho.setBackground(FUNDO);

        JLabel marca = new JLabel("AFFECTUS LAB");
        marca.setFont(new Font("Serif", Font.BOLD, 30));
        marca.setForeground(TERRACOTA);
        marca.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("Meu Diário Emocional");
        titulo.setFont(new Font("Serif", Font.BOLD, 24));
        titulo.setForeground(AZUL_ESCURO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel(
                "Um espaço para acolher e refletir sobre suas experiências."
        );
        subtitulo.setForeground(AZUL_ESCURO);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        cabecalho.add(marca);
        cabecalho.add(Box.createVerticalStrut(10));
        cabecalho.add(titulo);
        cabecalho.add(Box.createVerticalStrut(8));
        cabecalho.add(subtitulo);

        principal.add(cabecalho, BorderLayout.NORTH);

        JPanel conteudo = new JPanel();
        conteudo.setLayout(
                new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(FUNDO);

        JLabel labelUsuario = new JLabel(
                "Quem está escrevendo?");
        labelUsuario.setForeground(AZUL_ESCURO);
        conteudo.add(labelUsuario);

        comboUsuarios = new JComboBox<>();
        comboUsuarios.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 35));
        comboUsuarios.addActionListener(e -> selecionarUsuario());
        conteudo.add(comboUsuarios);

        conteudo.add(Box.createVerticalStrut(20));

        JLabel pergunta = new JLabel(
                "O que você gostaria de registrar sobre seu dia?");
        pergunta.setForeground(AZUL_ESCURO);
        pergunta.setFont(
                new Font("SansSerif", Font.BOLD, 14));
        conteudo.add(pergunta);

        conteudo.add(Box.createVerticalStrut(10));

        campoReflexao = new JTextArea(8, 40);
        campoReflexao.setLineWrap(true);
        campoReflexao.setWrapStyleWord(true);
        campoReflexao.setFont(
                new Font("SansSerif", Font.PLAIN, 14));
        campoReflexao.setBorder(
                BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JScrollPane scrollReflexao =
                new JScrollPane(campoReflexao);
        scrollReflexao.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 200));
        conteudo.add(scrollReflexao);

        conteudo.add(Box.createVerticalStrut(15));

        JButton salvar = new JButton(
                "Salvar minha reflexão");
        salvar.setBackground(TERRACOTA);
        salvar.setForeground(Color.WHITE);
        salvar.setFont(
                new Font("SansSerif", Font.BOLD, 14));
        salvar.setAlignmentX(Component.CENTER_ALIGNMENT);
        salvar.addActionListener(e -> salvarReflexao());
        conteudo.add(salvar);

        conteudo.add(Box.createVerticalStrut(25));

        JLabel tituloHistorico = new JLabel(
                "Minhas reflexões anteriores");
        tituloHistorico.setForeground(AZUL_ESCURO);
        tituloHistorico.setFont(
                new Font("Serif", Font.BOLD, 19));
        conteudo.add(tituloHistorico);

        conteudo.add(Box.createVerticalStrut(10));

        areaHistorico = new JTextArea(9, 40);
        areaHistorico.setEditable(false);
        areaHistorico.setLineWrap(true);
        areaHistorico.setWrapStyleWord(true);
        areaHistorico.setFont(
                new Font("SansSerif", Font.PLAIN, 13));

        JScrollPane scrollHistorico =
                new JScrollPane(areaHistorico);
        scrollHistorico.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 220));
        conteudo.add(scrollHistorico);

        principal.add(conteudo, BorderLayout.CENTER);

        JLabel aviso = new JLabel(
                "Suas reflexões são pessoais. "
                + "Este recurso não substitui acompanhamento profissional."
        );
        aviso.setFont(
                new Font("SansSerif", Font.PLAIN, 11));
        aviso.setForeground(Color.GRAY);
        aviso.setHorizontalAlignment(SwingConstants.CENTER);

        principal.add(aviso, BorderLayout.SOUTH);

        setContentPane(principal);
    }

    private void carregarUsuarios() {
        try {
            usuarios = usuarioDAO.listar();
            comboUsuarios.removeAllItems();
            comboUsuarios.addItem("Selecione um usuário");

            for (String[] usuario : usuarios) {
                comboUsuarios.addItem(
                        usuario[1] + " (" + usuario[2] + ")");
            }

        } catch (SQLException e) {
            mostrarErro(e);
        }
    }

    private void selecionarUsuario() {

        int indice = comboUsuarios.getSelectedIndex();

        if (usuarios != null
                && indice > 0
                && indice <= usuarios.size()) {

            usuarioSelecionadoId = Integer.parseInt(
                    usuarios.get(indice - 1)[0]);

            carregarHistorico();

        } else {
            usuarioSelecionadoId = -1;
            areaHistorico.setText("");
        }
    }

    private void salvarReflexao() {

        if (usuarioSelecionadoId <= 0) {
            JOptionPane.showMessageDialog(
                    this, "Selecione um usuário.");
            return;
        }

        String texto = campoReflexao.getText().trim();

        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Escreva sua reflexão antes de salvar.");
            return;
        }

        try {
            int id = diarioDAO.salvar(
                    usuarioSelecionadoId, texto);

            JOptionPane.showMessageDialog(
                    this,
                    "Sua reflexão foi salva com sucesso!\n"
                    + "Registro nº " + id,
                    "Affectus Lab",
                    JOptionPane.INFORMATION_MESSAGE
            );

            campoReflexao.setText("");
            carregarHistorico();

        } catch (SQLException e) {
            mostrarErro(e);
        }
    }

    private void carregarHistorico() {

        if (usuarioSelecionadoId <= 0) {
            return;
        }

        try {
            List<String[]> entradas =
                    diarioDAO.listarPorUsuario(
                            usuarioSelecionadoId);

            if (entradas.isEmpty()) {
                areaHistorico.setText(
                        "Você ainda não possui reflexões registradas.");
                return;
            }

            StringBuilder texto = new StringBuilder();

            for (String[] entrada : entradas) {
                texto.append("Registro nº ")
                     .append(entrada[0])
                     .append(" | ")
                     .append(entrada[2])
                     .append("\n\n")
                     .append(entrada[1])
                     .append("\n\n")
                     .append("------------------------------\n\n");
            }

            areaHistorico.setText(texto.toString());
            areaHistorico.setCaretPosition(0);

        } catch (SQLException e) {
            mostrarErro(e);
        }
    }

    private void mostrarErro(Exception e) {
        JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Affectus Lab - Erro",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new TelaDiario().setVisible(true));
    }
}

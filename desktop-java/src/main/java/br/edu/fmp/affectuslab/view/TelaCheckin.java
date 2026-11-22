
package br.edu.fmp.affectuslab.view;

import br.edu.fmp.affectuslab.controller.RegistroEmocionalController;
import br.edu.fmp.affectuslab.model.dao.UsuarioDAO;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;

public class TelaCheckin extends JFrame {

    private final RegistroEmocionalController controller =
            new RegistroEmocionalController();

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    private final Color FUNDO = new Color(246, 242, 235);
    private final Color TERRACOTA = new Color(138, 80, 51);
    private final Color AZUL_ESCURO = new Color(41, 61, 76);

    private JComboBox<String> comboUsuarios;
    private JPanel painelEmocoes;
    private JTextArea descricao;
    private JTextArea correlacionadas;
    private JSlider intensidade;
    private JLabel valorIntensidade;
    private JButton botaoConfirmar;

    private int usuarioSelecionadoId = -1;
    private int emocaoSelecionadaId = -1;

    private List<String[]> usuarios;

    public TelaCheckin() {
        construirTela();
        carregarUsuarios();
        carregarEmocoes();
    }

    public TelaCheckin(int usuarioId) {
        this();
        selecionarUsuario(usuarioId);
    }

    private void construirTela() {

        setTitle("Affectus Lab - Check-in Emocional");
        setSize(850, 780);
        setMinimumSize(new Dimension(650, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel principal = new JPanel();
        principal.setLayout(new BoxLayout(
                principal, BoxLayout.Y_AXIS));
        principal.setBackground(FUNDO);
        principal.setBorder(BorderFactory.createEmptyBorder(
                25, 35, 30, 35));

        JLabel marca = new JLabel(
                "AFFECTUS LAB | CONSCIÊNCIA EMOCIONAL");
        marca.setForeground(TERRACOTA);
        marca.setFont(new Font("SansSerif", Font.BOLD, 13));
        marca.setAlignmentX(Component.CENTER_ALIGNMENT);
        principal.add(marca);

        principal.add(Box.createVerticalStrut(15));

        JLabel titulo = new JLabel(
                "Como você está se sentindo?");
        titulo.setFont(new Font("Serif", Font.BOLD, 29));
        titulo.setForeground(AZUL_ESCURO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        principal.add(titulo);

        principal.add(Box.createVerticalStrut(8));

        JLabel subtitulo = new JLabel(
                "Reconhecer o que sentimos é um passo de autocuidado.");
        subtitulo.setForeground(AZUL_ESCURO);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        principal.add(subtitulo);

        principal.add(Box.createVerticalStrut(25));

        JLabel labelUsuario = criarLabel(
                "Quem está realizando o check-in?");
        principal.add(labelUsuario);

        comboUsuarios = new JComboBox<>();
        comboUsuarios.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 35));
        comboUsuarios.addActionListener(e -> atualizarUsuario());
        principal.add(comboUsuarios);

        principal.add(Box.createVerticalStrut(20));

        principal.add(criarLabel(
                "Escolha a experiência que mais se aproxima do que sente:"));

        principal.add(Box.createVerticalStrut(10));

        painelEmocoes = new JPanel(
                new GridLayout(0, 2, 12, 12));
        painelEmocoes.setBackground(FUNDO);
        painelEmocoes.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 210));
        principal.add(painelEmocoes);

        principal.add(Box.createVerticalStrut(20));

        principal.add(criarLabel(
                "Compreendendo sua experiência:"));

        descricao = criarAreaTexto(4);
        principal.add(descricao);

        principal.add(Box.createVerticalStrut(15));

        principal.add(criarLabel(
                "Outras experiências que podem estar relacionadas:"));

        correlacionadas = criarAreaTexto(3);
        principal.add(correlacionadas);

        principal.add(Box.createVerticalStrut(20));

        principal.add(criarLabel(
                "Qual a intensidade dessa experiência?"));

        intensidade = new JSlider(0, 10, 5);
        intensidade.setMajorTickSpacing(2);
        intensidade.setPaintTicks(true);
        intensidade.setPaintLabels(true);
        intensidade.setBackground(FUNDO);
        principal.add(intensidade);

        valorIntensidade = new JLabel("Intensidade: 5 de 10");
        valorIntensidade.setAlignmentX(
                Component.CENTER_ALIGNMENT);
        valorIntensidade.setForeground(AZUL_ESCURO);
        principal.add(valorIntensidade);

        intensidade.addChangeListener(e ->
                valorIntensidade.setText(
                        "Intensidade: "
                        + intensidade.getValue()
                        + " de 10"));

        principal.add(Box.createVerticalStrut(20));

        botaoConfirmar = new JButton(
                "Confirmar minha experiência");
        botaoConfirmar.setBackground(TERRACOTA);
        botaoConfirmar.setForeground(Color.WHITE);
        botaoConfirmar.setFont(
                new Font("SansSerif", Font.BOLD, 15));
        botaoConfirmar.setFocusPainted(false);
        botaoConfirmar.setEnabled(false);
        botaoConfirmar.setAlignmentX(
                Component.CENTER_ALIGNMENT);
        botaoConfirmar.addActionListener(e -> salvarCheckin());
        principal.add(botaoConfirmar);

        principal.add(Box.createVerticalStrut(12));

        JButton botaoHistorico = new JButton(
                "Consultar meu histórico emocional");
        botaoHistorico.setAlignmentX(
                Component.CENTER_ALIGNMENT);
        botaoHistorico.addActionListener(e -> mostrarHistorico());
        principal.add(botaoHistorico);

        principal.add(Box.createVerticalStrut(15));

        JLabel aviso = new JLabel(
                "Este recurso é educativo e não substitui acompanhamento profissional.");
        aviso.setFont(new Font("SansSerif", Font.PLAIN, 11));
        aviso.setForeground(Color.GRAY);
        aviso.setAlignmentX(Component.CENTER_ALIGNMENT);
        principal.add(aviso);

        JScrollPane scroll = new JScrollPane(principal);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBorder(null);
        setContentPane(scroll);
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(AZUL_ESCURO);
        label.setFont(new Font(
                "SansSerif", Font.BOLD, 13));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextArea criarAreaTexto(int linhas) {
        JTextArea area = new JTextArea(linhas, 30);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font(
                "SansSerif", Font.PLAIN, 13));
        area.setBackground(Color.WHITE);
        area.setForeground(AZUL_ESCURO);
        area.setBorder(BorderFactory.createEmptyBorder(
                10, 10, 10, 10));
        area.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 120));
        return area;
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

        } catch (SQLException ex) {
            mostrarErro("Erro ao carregar usuários", ex);
        }
    }

    private void atualizarUsuario() {
        int indice = comboUsuarios.getSelectedIndex();

        if (usuarios != null
                && indice > 0
                && indice <= usuarios.size()) {

            usuarioSelecionadoId = Integer.parseInt(
                    usuarios.get(indice - 1)[0]);

        } else {
            usuarioSelecionadoId = -1;
        }
    }

    private void selecionarUsuario(int id) {
        if (usuarios == null) {
            return;
        }

        for (int i = 0; i < usuarios.size(); i++) {
            if (Integer.parseInt(usuarios.get(i)[0]) == id) {
                comboUsuarios.setSelectedIndex(i + 1);
                comboUsuarios.setEnabled(false);
                return;
            }
        }
    }

    private void carregarEmocoes() {
        try {
            List<String[]> emocoes =
                    controller.listarEmocoes();

            painelEmocoes.removeAll();

            for (String[] emocao : emocoes) {

                int id = Integer.parseInt(emocao[0]);
                String nome = emocao[1];

                JButton botao = new JButton(nome);
                botao.setBackground(Color.WHITE);
                botao.setForeground(AZUL_ESCURO);
                botao.setFocusPainted(false);

                botao.addActionListener(e -> {
                    emocaoSelecionadaId = id;
                    mostrarDescricaoEmocao(emocao);
                    carregarCorrelacionadas(id);
                    botaoConfirmar.setEnabled(true);
                });

                painelEmocoes.add(botao);
            }

            painelEmocoes.revalidate();
            painelEmocoes.repaint();

        } catch (SQLException ex) {
            mostrarErro("Erro ao carregar experiências", ex);
        }
    }

    private void mostrarDescricaoEmocao(String[] emocao) {

        String texto =
                emocao[1] + "\n\n"
                + "Sobre esta experiência:\n"
                + textoSeguro(emocao[4])
                + "\n\nSensações corporais possíveis:\n"
                + textoSeguro(emocao[5]);

        descricao.setText(texto);
        descricao.setCaretPosition(0);
    }

    private void carregarCorrelacionadas(int emocaoId) {
        try {
            List<String[]> lista =
                    controller.listarCorrelacionadas(emocaoId);

            if (lista.isEmpty()) {
                correlacionadas.setText(
                        "Nenhuma experiência relacionada cadastrada.");
                return;
            }

            StringBuilder texto = new StringBuilder();

            for (String[] item : lista) {
                texto.append("• ")
                     .append(item[1])
                     .append("\n");

                if (item[2] != null
                        && !item[2].isBlank()) {
                    texto.append(item[2])
                         .append("\n");
                }

                texto.append("\n");
            }

            correlacionadas.setText(texto.toString());
            correlacionadas.setCaretPosition(0);

        } catch (SQLException ex) {
            mostrarErro(
                    "Erro ao consultar experiências relacionadas", ex);
        }
    }

    private String textoSeguro(String texto) {
        return texto == null || texto.isBlank()
                ? "Informação não cadastrada."
                : texto;
    }

    private void salvarCheckin() {

        if (usuarioSelecionadoId <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um usuário antes de continuar.");
            return;
        }

        if (emocaoSelecionadaId <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma experiência emocional.");
            return;
        }

        try {
            int idRegistro = controller.registrar(
                    usuarioSelecionadoId,
                    emocaoSelecionadaId,
                    intensidade.getValue()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Seu check-in foi registrado com sucesso!\n"
                    + "Registro nº " + idRegistro
                    + "\n\nObrigado por reservar este momento "
                    + "para reconhecer o que sente.",
                    "Affectus Lab",
                    JOptionPane.INFORMATION_MESSAGE
            );

            emocaoSelecionadaId = -1;
            botaoConfirmar.setEnabled(false);
            descricao.setText("");
            correlacionadas.setText("");
            intensidade.setValue(5);

        } catch (SQLException | IllegalArgumentException ex) {
            mostrarErro("Não foi possível salvar o check-in", ex);
        }
    }

    private void mostrarHistorico() {

        if (usuarioSelecionadoId <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um usuário para consultar o histórico.");
            return;
        }

        try {
            List<String[]> registros =
                    controller.listarHistorico(
                            usuarioSelecionadoId);

            if (registros.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Este usuário ainda não possui registros.");
                return;
            }

            StringBuilder texto = new StringBuilder(
                    "HISTÓRICO EMOCIONAL\n\n");

            for (String[] registro : registros) {
                texto.append("Registro: ")
                     .append(registro[0])
                     .append("\nExperiência: ")
                     .append(registro[1])
                     .append("\nIntensidade: ")
                     .append(registro[2])
                     .append("/10")
                     .append("\nData: ")
                     .append(registro[3])
                     .append("\n--------------------------\n");
            }

            JTextArea area = new JTextArea(
                    texto.toString(), 18, 42);
            area.setEditable(false);
            area.setCaretPosition(0);

            JOptionPane.showMessageDialog(
                    this,
                    new JScrollPane(area),
                    "Histórico emocional",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException | IllegalArgumentException ex) {
            mostrarErro("Erro ao consultar histórico", ex);
        }
    }

    private void mostrarErro(String mensagem, Exception ex) {
        JOptionPane.showMessageDialog(
                this,
                mensagem + ":\n" + ex.getMessage(),
                "Affectus Lab - Erro",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new TelaCheckin().setVisible(true));
    }
}

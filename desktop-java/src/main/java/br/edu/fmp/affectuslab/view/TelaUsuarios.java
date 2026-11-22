
package br.edu.fmp.affectuslab.view;

import br.edu.fmp.affectuslab.controller.UsuarioController;
import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaUsuarios extends JFrame {

    private final UsuarioController controller = new UsuarioController();

    private final JTextField campoNome = new JTextField();
    private final JTextField campoEmail = new JTextField();
    private final JPasswordField campoSenha = new JPasswordField();

    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "Nome", "E-mail"}, 0) {
        @Override
        public boolean isCellEditable(int linha, int coluna) {
            return false;
        }
    };

    private final JTable tabela = new JTable(modelo);
    private int idSelecionado = -1;

    private final Color FUNDO = new Color(246, 242, 235);
    private final Color AZUL = new Color(151, 163, 174);
    private final Color TERRACOTA = new Color(138, 80, 51);

    public TelaUsuarios() {
        setTitle("Affectus Lab | Gestão de Usuários");
        setSize(950, 650);
        setMinimumSize(new Dimension(750, 550));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        construirInterface();
        configurarTabela();
        carregarUsuarios();
    }

    private void construirInterface() {
        JPanel principal = new JPanel(new BorderLayout(20, 20));
        principal.setBackground(FUNDO);
        principal.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        JLabel titulo = new JLabel("Affectus Lab  |  Gestão de Usuários");
        titulo.setFont(new Font("Serif", Font.BOLD, 26));
        titulo.setForeground(TERRACOTA);
        principal.add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(0, 1, 5, 5));
        formulario.setBackground(FUNDO);

        formulario.add(new JLabel("Nome completo"));
        formulario.add(campoNome);
        formulario.add(new JLabel("E-mail"));
        formulario.add(campoEmail);
        formulario.add(new JLabel("Senha (obrigatória no cadastro)"));
        formulario.add(campoSenha);

        JButton cadastrar = criarBotao("Cadastrar", TERRACOTA);
        JButton atualizar = criarBotao("Atualizar", AZUL);
        JButton excluir = criarBotao("Excluir", TERRACOTA);
        JButton limpar = criarBotao("Limpar", AZUL);

        JPanel botoes = new JPanel(new GridLayout(2, 2, 10, 10));
        botoes.setBackground(FUNDO);
        botoes.add(cadastrar);
        botoes.add(atualizar);
        botoes.add(excluir);
        botoes.add(limpar);

        JPanel esquerda = new JPanel(new BorderLayout(10, 20));
        esquerda.setBackground(FUNDO);
        esquerda.setPreferredSize(new Dimension(290, 400));
        esquerda.add(formulario, BorderLayout.NORTH);
        esquerda.add(botoes, BorderLayout.CENTER);

        tabela.setRowHeight(28);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane rolagem = new JScrollPane(tabela);
        rolagem.setBorder(
                BorderFactory.createTitledBorder("Usuários cadastrados")
        );

        principal.add(esquerda, BorderLayout.WEST);
        principal.add(rolagem, BorderLayout.CENTER);

        add(principal);

        cadastrar.addActionListener(e -> cadastrarUsuario());
        atualizar.addActionListener(e -> atualizarUsuario());
        excluir.addActionListener(e -> excluirUsuario());
        limpar.addActionListener(e -> limparCampos());
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("SansSerif", Font.BOLD, 13));
        return botao;
    }

    private void configurarTabela() {
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()
                    && tabela.getSelectedRow() >= 0) {

                int linha = tabela.convertRowIndexToModel(
                        tabela.getSelectedRow()
                );

                idSelecionado = Integer.parseInt(
                        modelo.getValueAt(linha, 0).toString()
                );

                campoNome.setText(
                        modelo.getValueAt(linha, 1).toString()
                );
                campoEmail.setText(
                        modelo.getValueAt(linha, 2).toString()
                );
                campoSenha.setText("");
            }
        });
    }

    private void carregarUsuarios() {
        try {
            modelo.setRowCount(0);
            for (String[] usuario : controller.listar()) {
                modelo.addRow(usuario);
            }
        } catch (SQLException e) {
            mostrarErro(e);
        }
    }

    private void cadastrarUsuario() {
        try {
            controller.cadastrar(
                    campoNome.getText(),
                    campoEmail.getText(),
                    new String(campoSenha.getPassword())
            );
            JOptionPane.showMessageDialog(
                    this, "Usuário cadastrado com sucesso!"
            );
            carregarUsuarios();
            limparCampos();
        } catch (SQLException | IllegalArgumentException e) {
            mostrarErro(e);
        }
    }

    private void atualizarUsuario() {
        try {
            if (idSelecionado < 0) {
                throw new IllegalArgumentException(
                        "Selecione um usuário na tabela."
                );
            }

            controller.atualizar(
                    idSelecionado,
                    campoNome.getText(),
                    campoEmail.getText()
            );
            JOptionPane.showMessageDialog(
                    this, "Usuário atualizado com sucesso!"
            );
            carregarUsuarios();
            limparCampos();
        } catch (SQLException | IllegalArgumentException e) {
            mostrarErro(e);
        }
    }

    private void excluirUsuario() {
        if (idSelecionado < 0) {
            JOptionPane.showMessageDialog(
                    this, "Selecione um usuário na tabela."
            );
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir este usuário?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            controller.excluir(idSelecionado);
            JOptionPane.showMessageDialog(
                    this, "Usuário excluído com sucesso!"
            );
            carregarUsuarios();
            limparCampos();
        } catch (SQLException e) {
            mostrarErro(e);
        }
    }

    private void limparCampos() {
        tabela.clearSelection();
        idSelecionado = -1;
        campoNome.setText("");
        campoEmail.setText("");
        campoSenha.setText("");
    }

    private void mostrarErro(Exception e) {
        JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Affectus Lab - Aviso",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new TelaUsuarios().setVisible(true)
        );
    }
}

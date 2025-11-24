package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import service.AcademiaService;
import model.Instrutor;

public class TelaInstrutor extends JFrame {

    private AcademiaService service = new AcademiaService();


    private JTextField txtId, txtNome, txtEspecialidade;
    private JButton btnSalvar, btnAtualizar, btnExcluir, btnLimpar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaInstrutor() {
        setTitle("Gerenciar Instrutores");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        //PAINEL SUPERIOR
        JPanel painelSuperior = new JPanel(new BorderLayout(10, 10));
        painelSuperior.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(painelSuperior, BorderLayout.NORTH);

        //PAINEL DE CAMPOS
        JPanel painelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        painelSuperior.add(painelCampos, BorderLayout.CENTER);

        painelCampos.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        painelCampos.add(txtId);

        painelCampos.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painelCampos.add(txtNome);

        painelCampos.add(new JLabel("Especialidade:"));
        txtEspecialidade = new JTextField();
        painelCampos.add(txtEspecialidade);

        //PAINEL DE BOTÕES
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        painelSuperior.add(painelBotoes, BorderLayout.SOUTH);

        btnSalvar = new JButton("Salvar Novo");
        btnAtualizar = new JButton("Atualizar Selecionado");
        btnExcluir = new JButton("Excluir Selecionado");
        btnLimpar = new JButton("Limpar Campos");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        //PAINEL DA TABELA
        String[] colunas = {"ID", "Nome", "Especialidade"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(new EmptyBorder(0, 10, 10, 10));

        add(scrollPane, BorderLayout.CENTER);

        //AÇÕES
        listarInstrutores();

        btnSalvar.addActionListener(e -> salvarInstrutor());
        btnAtualizar.addActionListener(e -> atualizarInstrutor());
        btnExcluir.addActionListener(e -> excluirInstrutor());
        btnLimpar.addActionListener(e -> limparCampos());

        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linhaSelecionada = tabela.getSelectedRow();
                if (linhaSelecionada != -1) {
                    txtId.setText(modeloTabela.getValueAt(linhaSelecionada, 0).toString());
                    txtNome.setText(modeloTabela.getValueAt(linhaSelecionada, 1).toString());
                    txtEspecialidade.setText(modeloTabela.getValueAt(linhaSelecionada, 2).toString());
                }
            }
        });
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtEspecialidade.setText("");
        tabela.clearSelection();
    }

    private void listarInstrutores() {
        try {
            var lista = service.getInstrutores();
            modeloTabela.setRowCount(0);
            for (Instrutor i : lista) {
                modeloTabela.addRow(new Object[]{ i.getId(), i.getNome(), i.getEspecialidade() });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao ATUALIZAR lista de instrutores: " + e.getMessage());
        }
    }

    private void salvarInstrutor() {
        try {
            String nome = txtNome.getText();
            String esp = txtEspecialidade.getText();
            if (nome.isEmpty() || esp.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e Especialidade são obrigatórios.");
                return;
            }
            service.cadastrarInstrutor(nome, esp);
            JOptionPane.showMessageDialog(this, "Instrutor salvo com sucesso!");
            listarInstrutores();
            limparCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
        }
    }

    private void atualizarInstrutor() {
        try {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um instrutor na tabela para atualizar.");
                return;
            }
            int id = Integer.parseInt(txtId.getText());
            String nome = txtNome.getText();
            String esp = txtEspecialidade.getText();
            service.atualizarInstrutor(id, nome, esp);
            JOptionPane.showMessageDialog(this, "Instrutor atualizado com sucesso!");
            listarInstrutores();
            limparCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + e.getMessage());
        }
    }

    private void excluirInstrutor() {
        try {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um instrutor na tabela para excluir.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir este instrutor?",
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(txtId.getText());
                service.excluirInstrutor(id);
                JOptionPane.showMessageDialog(this, "Instrutor excluído com sucesso!");
                listarInstrutores();
                limparCampos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
        }
    }
}
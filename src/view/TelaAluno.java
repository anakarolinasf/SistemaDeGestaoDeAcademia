package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import service.AcademiaService;
import model.Aluno;

public class TelaAluno extends JFrame {

    private AcademiaService service = new AcademiaService();

    private JTextField txtId, txtNome, txtCpf, txtData;
    private JButton btnSalvar, btnAtualizar, btnExcluir, btnLimpar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaAluno() {
        setTitle("Gerenciar Alunos");
        setSize(650, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //PAINEL PRINCIPAL
        setLayout(new BorderLayout(10, 10));

        //PAINEL SUPERIOR
        JPanel painelSuperior = new JPanel(new BorderLayout(10, 10));
        painelSuperior.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(painelSuperior, BorderLayout.NORTH);

        //PAINEL DE CAMPOS
        JPanel painelCampos = new JPanel(new GridLayout(4, 2, 10, 10));
        painelSuperior.add(painelCampos, BorderLayout.CENTER);

        painelCampos.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        painelCampos.add(txtId);

        painelCampos.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painelCampos.add(txtNome);

        painelCampos.add(new JLabel("CPF:"));
        txtCpf = new JTextField();
        painelCampos.add(txtCpf);

        painelCampos.add(new JLabel("Data Ingresso (AAAA-MM-DD):"));
        txtData = new JTextField();
        painelCampos.add(txtData);

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


        String[] colunas = {"ID", "Nome", "CPF", "Data de Ingresso"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);

        scrollPane.setBorder(new EmptyBorder(0, 10, 10, 10));


        add(scrollPane, BorderLayout.CENTER);



        listarAlunos();

        btnSalvar.addActionListener(e -> salvarAluno());
        btnAtualizar.addActionListener(e -> atualizarAluno());
        btnExcluir.addActionListener(e -> excluirAluno());
        btnLimpar.addActionListener(e -> limparCampos());

        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linhaSelecionada = tabela.getSelectedRow();
                if (linhaSelecionada != -1) {
                    txtId.setText(modeloTabela.getValueAt(linhaSelecionada, 0).toString());
                    txtNome.setText(modeloTabela.getValueAt(linhaSelecionada, 1).toString());
                    txtCpf.setText(modeloTabela.getValueAt(linhaSelecionada, 2).toString());
                    txtData.setText(modeloTabela.getValueAt(linhaSelecionada, 3).toString());
                }
            }
        });
    }

    //MÉTODOS DE AÇÃO

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        txtData.setText("");
        tabela.clearSelection();
    }

    private void listarAlunos() {
        modeloTabela.setRowCount(0);
        try {
            var lista = service.getAlunos();
            for (Aluno a : lista) {
                modeloTabela.addRow(new Object[]{
                        a.getId(), a.getNome(), a.getCpf(), a.getDataIngresso()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar alunos: " + e.getMessage());
        }
    }

    private void salvarAluno() {
        try {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            Date data = Date.valueOf(txtData.getText());
            if (nome.isEmpty() || cpf.isEmpty() || txtData.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.");
                return;
            }
            service.cadastrarAluno(nome, cpf, data);
            JOptionPane.showMessageDialog(this, "Aluno salvo com sucesso!");
            listarAlunos();
            limparCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar (verifique a data AAAA-MM-DD): " + e.getMessage());
        }
    }

    private void atualizarAluno() {
        try {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um aluno na tabela para atualizar.");
                return;
            }
            int id = Integer.parseInt(txtId.getText());
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            Date data = Date.valueOf(txtData.getText());
            service.atualizarAluno(id, nome, cpf, data);
            JOptionPane.showMessageDialog(this, "Aluno atualizado com sucesso!");
            listarAlunos();
            limparCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar (verifique a data AAAA-MM-DD): " + e.getMessage());
        }
    }

    private void excluirAluno() {
        try {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um aluno na tabela para excluir.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir este aluno?",
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(txtId.getText());
                service.excluirAluno(id);
                JOptionPane.showMessageDialog(this, "Aluno excluído com sucesso!");
                listarAlunos();
                limparCampos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
        }
    }
}
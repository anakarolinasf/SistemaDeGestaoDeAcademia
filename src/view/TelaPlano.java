package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import service.AcademiaService;
import model.PlanoTreino;

public class TelaPlano extends JFrame {

    private AcademiaService service = new AcademiaService();

    private JTextField txtIdPlano, txtIdAluno, txtIdInstrutor, txtDescricao, txtDuracao;
    private JButton btnSalvar, btnAtualizar, btnExcluir, btnLimpar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaPlano() {
        setTitle("Gerenciar Planos de Treino");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        //PAINEL SUPERIOR
        JPanel painelSuperior = new JPanel(new BorderLayout(10, 10));
        painelSuperior.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(painelSuperior, BorderLayout.NORTH);

        //PAINEL DE CAMPOS
        JPanel painelCampos = new JPanel(new GridLayout(5, 2, 10, 10));
        painelSuperior.add(painelCampos, BorderLayout.CENTER);

        painelCampos.add(new JLabel("ID Plano:"));
        txtIdPlano = new JTextField();
        txtIdPlano.setEditable(false);
        painelCampos.add(txtIdPlano);

        painelCampos.add(new JLabel("ID Aluno:"));
        txtIdAluno = new JTextField();
        painelCampos.add(txtIdAluno);

        painelCampos.add(new JLabel("ID Instrutor:"));
        txtIdInstrutor = new JTextField();
        painelCampos.add(txtIdInstrutor);

        painelCampos.add(new JLabel("Descrição:"));
        txtDescricao = new JTextField();
        painelCampos.add(txtDescricao);

        painelCampos.add(new JLabel("Duração (semanas):"));
        txtDuracao = new JTextField();
        painelCampos.add(txtDuracao);

        // PAINEL DE BOTÕES
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
        String[] colunas = {"ID Plano", "ID Aluno", "ID Instrutor", "Descrição", "Semanas"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(new EmptyBorder(0, 10, 10, 10));

        add(scrollPane, BorderLayout.CENTER);


        listarPlanos();

        btnSalvar.addActionListener(e -> cadastrarPlano());
        btnAtualizar.addActionListener(e -> atualizarPlano());
        btnExcluir.addActionListener(e -> excluirPlano());
        btnLimpar.addActionListener(e -> limparCampos());

        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linhaSelecionada = tabela.getSelectedRow();
                if (linhaSelecionada != -1) {
                    txtIdPlano.setText(modeloTabela.getValueAt(linhaSelecionada, 0).toString());
                    txtIdAluno.setText(modeloTabela.getValueAt(linhaSelecionada, 1).toString());
                    txtIdInstrutor.setText(modeloTabela.getValueAt(linhaSelecionada, 2).toString());
                    txtDescricao.setText(modeloTabela.getValueAt(linhaSelecionada, 3).toString());
                    txtDuracao.setText(modeloTabela.getValueAt(linhaSelecionada, 4).toString());
                }
            }
        });
    }

    //MÉTODOS DE AÇÃO

    private void limparCampos() {
        txtIdPlano.setText("");
        txtIdAluno.setText("");
        txtIdInstrutor.setText("");
        txtDescricao.setText("");
        txtDuracao.setText("");
        tabela.clearSelection();
    }

    private void listarPlanos() {
        modeloTabela.setRowCount(0);
        try {
            var lista = service.getPlanos();
            for (PlanoTreino p : lista) {
                modeloTabela.addRow(new Object[]{
                        p.getId(), p.getIdAluno(), p.getIdInstrutor(), p.getDescricao(), p.getDuracaoSemanas()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar planos: " + e.getMessage());
        }
    }

    private void cadastrarPlano() {
        try {
            int idAluno = Integer.parseInt(txtIdAluno.getText());
            int idInstrutor = Integer.parseInt(txtIdInstrutor.getText());
            String descricao = txtDescricao.getText();
            int duracao = Integer.parseInt(txtDuracao.getText());
            service.cadastrarPlano(idAluno, idInstrutor, descricao, duracao);
            JOptionPane.showMessageDialog(this, "Plano salvo com sucesso!");
            listarPlanos();
            limparCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar. Verifique se os IDs de Aluno e Instrutor existem.");
        }
    }

    private void atualizarPlano() {
        try {
            if (txtIdPlano.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um plano na tabela para atualizar.");
                return;
            }
            int idPlano = Integer.parseInt(txtIdPlano.getText());
            int idAluno = Integer.parseInt(txtIdAluno.getText());
            int idInstrutor = Integer.parseInt(txtIdInstrutor.getText());
            String descricao = txtDescricao.getText();
            int duracao = Integer.parseInt(txtDuracao.getText());
            service.atualizarPlano(idPlano, idAluno, idInstrutor, descricao, duracao);
            JOptionPane.showMessageDialog(this, "Plano atualizado com sucesso!");
            listarPlanos();
            limparCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar. Verifique os IDs.");
        }
    }

    private void excluirPlano() {
        try {
            if (txtIdPlano.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um plano na tabela para excluir.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir este plano?",
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(txtIdPlano.getText());
                service.excluirPlano(id);
                JOptionPane.showMessageDialog(this, "Plano excluído com sucesso!");
                listarPlanos();
                limparCampos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
        }
    }
}
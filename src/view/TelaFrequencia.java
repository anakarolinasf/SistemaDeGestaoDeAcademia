package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import service.AcademiaService;
import model.Frequencia;

public class TelaFrequencia extends JFrame {

    private AcademiaService service = new AcademiaService();


    private JTextField txtIdFrequencia, txtIdAluno, txtData;
    private JCheckBox checkPresenca;
    private JButton btnSalvar, btnAtualizar, btnExcluir, btnLimpar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaFrequencia() {
        setTitle("Gerenciar Frequência");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));


        JPanel painelSuperior = new JPanel(new BorderLayout(10, 10));
        painelSuperior.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(painelSuperior, BorderLayout.NORTH);

        // PAINEL DE CAMPOS
        JPanel painelCampos = new JPanel(new GridLayout(4, 2, 10, 10));
        painelSuperior.add(painelCampos, BorderLayout.CENTER);

        painelCampos.add(new JLabel("ID Frequência:"));
        txtIdFrequencia = new JTextField();
        txtIdFrequencia.setEditable(false);
        painelCampos.add(txtIdFrequencia);

        painelCampos.add(new JLabel("ID Aluno:"));
        txtIdAluno = new JTextField();
        painelCampos.add(txtIdAluno);

        painelCampos.add(new JLabel("Data (AAAA-MM-DD):"));
        txtData = new JTextField();
        painelCampos.add(txtData);

        painelCampos.add(new JLabel("Presença:"));
        checkPresenca = new JCheckBox("Presente");
        painelCampos.add(checkPresenca);

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
        String[] colunas = {"ID Freq.", "ID Aluno", "Data", "Presença"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) return Boolean.class;
                return super.getColumnClass(columnIndex);
            }
        };
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(new EmptyBorder(0, 10, 10, 10));

        add(scrollPane, BorderLayout.CENTER);

        //AÇÕES
        listarFrequencias();

        btnSalvar.addActionListener(e -> registrarFrequencia());
        btnAtualizar.addActionListener(e -> atualizarFrequencia());
        btnExcluir.addActionListener(e -> excluirFrequencia());
        btnLimpar.addActionListener(e -> limparCampos());

        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linhaSelecionada = tabela.getSelectedRow();
                if (linhaSelecionada != -1) {
                    txtIdFrequencia.setText(modeloTabela.getValueAt(linhaSelecionada, 0).toString());
                    txtIdAluno.setText(modeloTabela.getValueAt(linhaSelecionada, 1).toString());
                    txtData.setText(modeloTabela.getValueAt(linhaSelecionada, 2).toString());
                    checkPresenca.setSelected((Boolean) modeloTabela.getValueAt(linhaSelecionada, 3));
                }
            }
        });
    }



    private void limparCampos() {
        txtIdFrequencia.setText("");
        txtIdAluno.setText("");
        txtData.setText("");
        checkPresenca.setSelected(false);
        tabela.clearSelection();
    }

    private void listarFrequencias() {
        modeloTabela.setRowCount(0);
        try {
            var lista = service.getFrequencias();
            for (Frequencia f : lista) {
                modeloTabela.addRow(new Object[]{
                        f.getId(), f.getIdAluno(), f.getData(), f.isPresenca()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar frequências: " + e.getMessage());
        }
    }

    private void registrarFrequencia() {
        try {
            int idAluno = Integer.parseInt(txtIdAluno.getText());
            Date data = Date.valueOf(txtData.getText());
            boolean presenca = checkPresenca.isSelected();
            service.registrarFrequencia(idAluno, data, presenca);
            JOptionPane.showMessageDialog(this, "Frequência registrada com sucesso!");
            listarFrequencias();
            limparCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar. Verifique o ID do Aluno e a Data (AAAA-MM-DD).");
        }
    }

    private void atualizarFrequencia() {
        try {
            if (txtIdFrequencia.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um registro na tabela para atualizar.");
                return;
            }
            int idFreq = Integer.parseInt(txtIdFrequencia.getText());
            int idAluno = Integer.parseInt(txtIdAluno.getText());
            Date data = Date.valueOf(txtData.getText());
            boolean presenca = checkPresenca.isSelected();
            service.atualizarFrequencia(idFreq, idAluno, data, presenca);
            JOptionPane.showMessageDialog(this, "Frequência atualizada com sucesso!");
            listarFrequencias();
            limparCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar. Verifique os dados.");
        }
    }

    private void excluirFrequencia() {
        try {
            if (txtIdFrequencia.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um registro na tabela para excluir.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir este registro?",
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(txtIdFrequencia.getText());
                service.excluirFrequencia(id);
                JOptionPane.showMessageDialog(this, "Registro excluído com sucesso!");
                listarFrequencias();
                limparCampos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
        }
    }
}
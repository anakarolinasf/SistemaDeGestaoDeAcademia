package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Sistema da Academia");
        setSize(450, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);


        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(new Color(245, 245, 245));


        JLabel titulo = new JLabel("Sistema da Academia", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(255, 20, 147));
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));


        JPanel painelBotoes = new JPanel(new GridLayout(5, 1, 15, 15));
        painelBotoes.setBackground(new Color(245, 245, 245));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 60, 30, 60));

        JButton btnAluno = criarBotao(" Gerenciar Alunos");
        JButton btnInstrutor = criarBotao("Gerenciar Instrutores");
        JButton btnPlano = criarBotao("Gerenciar Planos de Treino");
        JButton btnFrequencia = criarBotao("Gerenciar Frequência");
        JButton btnSair = criarBotao("Sair");

        painelBotoes.add(btnAluno);
        painelBotoes.add(btnInstrutor);
        painelBotoes.add(btnPlano);
        painelBotoes.add(btnFrequencia);
        painelBotoes.add(btnSair);

        painelPrincipal.add(titulo, BorderLayout.NORTH);
        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);

        add(painelPrincipal);


        btnAluno.addActionListener(e -> new TelaAluno().setVisible(true));
        btnInstrutor.addActionListener(e -> new TelaInstrutor().setVisible(true));
        btnPlano.addActionListener(e -> new TelaPlano().setVisible(true));
        btnFrequencia.addActionListener(e -> new TelaFrequencia().setVisible(true));
        btnSair.addActionListener(e -> System.exit(0));
    }


    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        botao.setFocusPainted(false);
        botao.setBackground(new Color(236, 136, 202));
        botao.setForeground(Color.WHITE);
        botao.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setOpaque(true);


        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(new Color(255, 182, 193));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(new Color(219, 105, 180));
            }
        });

        return botao;
    }
}

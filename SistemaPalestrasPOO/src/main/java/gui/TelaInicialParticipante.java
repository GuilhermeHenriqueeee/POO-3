package gui;

import javax.swing.*;
import java.awt.*;

public class TelaInicialParticipante extends JFrame {
    private JLabel lblBemVindo;
    private JButton btnMudarSenha, btnVisualizarPalestras, btnSair;

    public TelaInicialParticipante() { 
        setTitle("Menu Principal - Participante");
        setSize(400,300);
        setLayout(new GridLayout(5, 1, 10, 10)); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setResizable(false);

        lblBemVindo = new JLabel("Bem-vindo(a) Participante", JLabel.CENTER);
        lblBemVindo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblBemVindo);

        btnMudarSenha = new JButton("Mudar Senha");
        btnVisualizarPalestras = new JButton("Visualizar Minhas Palestras");
        btnSair = new JButton("Sair do Sistema");

        add(btnMudarSenha);
        add(btnVisualizarPalestras);
        add(btnSair);

        btnMudarSenha.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "teste1")
        );

        btnVisualizarPalestras.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "teste2")
        );

        btnSair.addActionListener(e -> {
            dispose();
        });
    }
}

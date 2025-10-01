package gui;

import javax.swing.*;
import java.awt.*;

public class TelaInformacoesParticipante extends JFrame {
    private JLabel lblNome, lblData, lblQtdParticipantes;
    private JButton btnCancelarInscricao, btnFechar;

    public TelaInformacoesParticipante() {
        setTitle("Informações da Palestra");
        setSize(400, 250);
        setLayout(new GridLayout(5, 1, 10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        lblNome = new JLabel("Palestra: Nome da Palestra", JLabel.CENTER);
        lblNome.setFont(new Font("Arial", Font.BOLD, 16));
        lblData = new JLabel("Data: 00/00/0000", JLabel.CENTER);
        lblQtdParticipantes = new JLabel("Participantes inscritos: 0", JLabel.CENTER);

        btnCancelarInscricao = new JButton("Cancelar Inscrição");
        btnFechar = new JButton("Fechar");

        add(lblNome);
        add(lblData);
        add(lblQtdParticipantes);
        add(btnCancelarInscricao);
        add(btnFechar);

        btnCancelarInscricao.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "teste1")
        );

        btnFechar.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "teste2") 
        );
    }
}

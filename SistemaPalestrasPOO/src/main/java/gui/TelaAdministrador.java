package gui;

import gui.TelaManterPalestrantes;
import gui.TelaManterPalestras;

import javax.swing.*;
import java.awt.*;
import main.EscolherEntrada;

public class TelaAdministrador extends JFrame {
    private JButton btnPalestrantes, btnPalestras, btnSair;

    public TelaAdministrador() {
        setTitle("Menu Principal - Administrador");
        setSize(400,250);
        setLayout(new GridLayout(4,1,10,10));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel lblTitulo = new JLabel("Bem-vindo(a), Administrador!", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo);

        btnPalestrantes = new JButton("Manter Palestrantes");
        btnPalestras = new JButton("Manter Palestras");
        btnSair = new JButton("Sair");

        add(btnPalestrantes);
        add(btnPalestras);
        add(btnSair);
        
        btnPalestrantes.addActionListener(e -> {
            new TelaManterPalestrantes().setVisible(true);
        });

        btnPalestras.addActionListener(e -> {
            new TelaManterPalestras().setVisible(true);
        });

        btnSair.addActionListener(e -> {
            EscolherEntrada escolherEntrada = new EscolherEntrada();
            this.dispose();
        });
    }
}

    
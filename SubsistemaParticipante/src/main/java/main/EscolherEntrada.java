package main;

import gui.TelaLoginAdministrador;
import main.EscolherForma; 
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class EscolherEntrada extends JFrame {
    private JButton escolherAdministrador;
    private JButton escolherParticipante;
    private JLabel escolherEntrada;
    
    public EscolherEntrada(){
        setTitle("ENTRAR COMO");
        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        escolherEntrada = new JLabel("ESCOLHA A FORMA DE FAZER LOGIN:", JLabel.CENTER);
        escolherEntrada.setFont(new Font("Arial", Font.BOLD, 18));
        
        escolherAdministrador = new JButton("ADMINISTRADOR");
        escolherAdministrador.setFont(new Font("Arial", Font.BOLD, 20));
        
        escolherParticipante = new JButton("PARTICIPANTE");
        escolherParticipante.setFont(new Font("Arial", Font.BOLD, 20));
        
        JPanel painelBotoes = new JPanel(new GridLayout(2, 1, 10, 10));
        painelBotoes.add(escolherAdministrador);
        painelBotoes.add(escolherParticipante);
        
        setLayout(new BorderLayout(20,20));
        add(escolherEntrada, BorderLayout.NORTH);
        add(painelBotoes, BorderLayout.CENTER);
        
        setLocationRelativeTo(null); 
        setVisible(true);

        escolherParticipante.addActionListener(e -> {
            EscolherForma telaEscolha = new EscolherForma();
            telaEscolha.setVisible(true);
            this.dispose(); 
        });
        
        escolherAdministrador.addActionListener(e -> {
            TelaLoginAdministrador telaLoginAdm = new TelaLoginAdministrador();
            telaLoginAdm.setVisible(true);
            this.dispose();
        });
    }
}
package gui;

import javax.swing.*;
import java.awt.*;
import model.Participante;
import repository.ParticipanteRepository;

public class TelaInicialParticipante extends JFrame {

    private JLabel lblBemVindo;
    private JButton btnMudarSenha, btnVisualizarPalestras, btnSair;
    private Participante participante;
    private ParticipanteRepository repo = new ParticipanteRepository();

    public TelaInicialParticipante(Participante participante) {
        this.participante = participante;

        setTitle("Menu Principal - Participante");
        setSize(400, 300);
        setLayout(new GridLayout(5, 1, 10, 10));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        lblBemVindo = new JLabel("Bem-vindo(a), " + participante.getNome(), JLabel.CENTER);
        lblBemVindo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblBemVindo);

        btnMudarSenha = new JButton("Mudar Senha");
        btnVisualizarPalestras = new JButton("Visualizar Minhas Palestras");
        btnSair = new JButton("Sair do Sistema");

        add(btnMudarSenha);
        add(btnVisualizarPalestras);
        add(btnSair);

        btnMudarSenha.addActionListener(e -> {
            String novaSenha = JOptionPane.showInputDialog(this, "Digite a nova senha (mínimo 8 caracteres):");
            if (novaSenha != null && novaSenha.length() >= 8) {
                this.participante.setSenha(novaSenha);
                repo.atualizar(this.participante);
                JOptionPane.showMessageDialog(this, "Senha alterada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Senha inválida!");
            }
        });

        // Visualizar palestras
        btnVisualizarPalestras.addActionListener(e -> {
            // Recarrega o participante do banco
            this.participante = repo.buscarPorId(this.participante.getId());

            // Abre a tela de palestras
            TelaParticipantePalestras telaPalestras = new TelaParticipantePalestras(this.participante);
            telaPalestras.setVisible(true);
        });

        // Sair do sistema
        btnSair.addActionListener(e -> dispose());
    }
}

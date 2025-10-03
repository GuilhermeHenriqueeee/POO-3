package gui;

import javax.swing.*;
import java.awt.*;
import model.Palestra;
import model.Participante;
import java.time.format.DateTimeFormatter; 

public class TelaInformacoesParticipante extends JFrame {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TelaInformacoesParticipante(Palestra palestra, Participante participante) {
        setTitle("Informações da Palestra");
        setSize(400, 350); 
        setLayout(new BorderLayout(10,10));
        setLocationRelativeTo(null);
        setResizable(false);

        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setFont(new Font("Arial", Font.PLAIN, 14)); 

        StringBuilder sb = new StringBuilder();
        
        sb.append("--- Detalhes da Palestra ---\n\n");
        sb.append("ID: ").append(palestra.getId()).append("\n");
        sb.append("Palestra: ").append(palestra.getNome()).append("\n");
        sb.append("Evento: ").append(palestra.getEvento()).append("\n");
        
        sb.append("Data: ").append(palestra.getData().format(formatter)).append("\n");
        
        if (palestra.getPalestrante() != null) {
             sb.append("Palestrante: ").append(palestra.getPalestrante().getNome()).append("\n\n");
        } else {
             sb.append("Palestrante: A ser definido\n\n");
        }
       
        int totalParticipantes = palestra.getParticipantes() != null ? palestra.getParticipantes().size() : 0;
        sb.append("Total de Participantes Inscritos: ").append(totalParticipantes).append("\n");
        
        // Exibe o status da inscrição para o participante logado (apenas como informação extra)
        boolean inscrito = palestra.getParticipantes() != null && palestra.getParticipantes().contains(participante);
        sb.append("Status de Inscrição: ").append(inscrito ? "Inscrito(a) ✅" : "Não Inscrito(a) ❌").append("\n");
        
        info.setText(sb.toString());
        add(new JScrollPane(info), BorderLayout.CENTER);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelRodape.add(btnFechar);
        add(painelRodape, BorderLayout.SOUTH);
    }
}
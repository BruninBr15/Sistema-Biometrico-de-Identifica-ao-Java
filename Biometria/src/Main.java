import java.awt.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BiometriaUI());
    }
}

class BiometriaUI extends JFrame {
    private JLabel statusLabel;
    private JButton capturarBtn, verificarBtn, registrarBtn;

    public BiometriaUI() {
        
        setTitle("Sistema de Autenticação Biométrica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
        JLabel titulo = new JLabel("Identificação Facial", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        
        JPanel botoesPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        capturarBtn = new JButton("Capturar");
        verificarBtn = new JButton("Verificar");
        registrarBtn = new JButton("Registrar");

        botoesPanel.add(capturarBtn);
        botoesPanel.add(verificarBtn);
        botoesPanel.add(registrarBtn);
        add(botoesPanel, BorderLayout.CENTER);

        
        statusLabel = new JLabel("Status: aguardando ação...", SwingConstants.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        
        capturarBtn.addActionListener(e -> atualizarStatus("Capturando imagem facial..."));
        verificarBtn.addActionListener(e -> atualizarStatus("Verificando identidade..."));
        registrarBtn.addActionListener(e -> atualizarStatus("Iniciando registro de novo usuário..."));

        
        setVisible(true);
    }

    private void atualizarStatus(String msg) {
        statusLabel.setText("Status: " + msg);
    }
}

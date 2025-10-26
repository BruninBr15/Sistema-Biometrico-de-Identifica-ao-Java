package br.com.suauniversidade.biometria.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import br.com.suauniversidade.biometria.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class BiometriaUI extends JFrame {
    private JLabel statusLabel;
    private JButton capturarBtn, verificarBtn, registrarBtn;
    private EntityManagerFactory emf;
    private EntityManager em;

    public BiometriaUI() {
        initializeJPA();
        initializeUI();
    }

    private void initializeJPA() {
        emf = Persistence.createEntityManagerFactory("biometria-pu");
        em = emf.createEntityManager();
    }

    private void initializeUI() {
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

        capturarBtn.addActionListener(e -> capturarImagem());
        verificarBtn.addActionListener(e -> verificarIdentidade());
        registrarBtn.addActionListener(e -> registrarNovoUsuario());

        setVisible(true);

        // Adiciona shutdown hook para fechar conexões JPA
        Runtime.getRuntime().addShutdownHook(new Thread(this::cleanup));
    }

    private void capturarImagem() {
        atualizarStatus("Capturando imagem facial...");
        // TODO: Implementar integração com captura de imagem
    }

    private void verificarIdentidade() {
        atualizarStatus("Verificando identidade...");
        // TODO: Implementar verificação biométrica
    }

    private void registrarNovoUsuario() {
        atualizarStatus("Iniciando registro de novo usuário...");
        String nome = JOptionPane.showInputDialog(this, "Nome do usuário:");
        if (nome != null && !nome.trim().isEmpty()) {
            try {
                em.getTransaction().begin();
                Usuario novoUsuario = new Usuario();
                novoUsuario.setNome(nome);
                novoUsuario.setNivelAcesso(1); // Nível básico por padrão
                novoUsuario.setImagem("placeholder"); // TODO: Integrar com captura real
                novoUsuario.setInformacoes("Registro via interface gráfica");
                
                em.persist(novoUsuario);
                em.getTransaction().commit();
                
                atualizarStatus("Usuário " + nome + " registrado com sucesso!");
            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                atualizarStatus("Erro ao registrar usuário: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void atualizarStatus(String msg) {
        statusLabel.setText("Status: " + msg);
    }

    private void cleanup() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }
}
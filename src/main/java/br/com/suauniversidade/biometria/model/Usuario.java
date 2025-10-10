package br.com.suauniversidade.biometria.model;

import jakarta.persistence.*;

@Entity
@Table(name= "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome_completo", length = 100, nullable = false)
    private String nome;

    @Column(name = "nivel_acesso", nullable = false)
    private int nivelAcesso;

    public Usuario() {}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNivelAcessoivelAcesso() {
        return nivelAcesso;
    }
    public void setNivelAcesso(int nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
}

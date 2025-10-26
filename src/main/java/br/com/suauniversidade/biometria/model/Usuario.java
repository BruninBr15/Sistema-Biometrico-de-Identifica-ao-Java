package br.com.suauniversidade.biometria.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "usuarios")
public class Usuario {
    //Variáveis do Banco de Dados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private int id;

    @Column(name = "name")
    private String nome;

    @Column(name = "nivel_acesso")
    private int nivelAcesso;

    @Column(name = "imagem")
    private String imagem;

    @Column(name = "informacoes")
    private String informacoes;

    //Método Construtor Vazio
    public Usuario() {}

    //Getters e Setters
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

    public int getNivelAcesso() {
        return nivelAcesso;
    }
    public void setNivelAcesso(int nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public String getImagem() {
        return imagem;
    }
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getInformacoes() {
        return informacoes;
    }
    public void setInformacoes(String informacoes) {
        this.informacoes = informacoes;
    }

    //Métodos do Usuario
    public Usuario(int id, String nome, int nivelAcesso, String imagem, String informacoes) {
      this.id = id;
      this.nome = nome;
      this.nivelAcesso = nivelAcesso;
      this.imagem = imagem;
      this.informacoes = informacoes;
    }

    @Override
    public String toString () {
      return "Usuário{"+
      "id="+id+
      ", Nome="+nome+
      ", Nível de acesso="+nivelAcesso+
      ", Imagem caminho="+imagem+
      ", Informações de agrotóxico="+informacoes;
    }
}

package dominio.entidades;

import java.util.List;

public class PerfilExibir {

    private String nome;
    private String genero;
    private int distancia;
    private int idade;
    private List<Foto> fotos;
    private String descricao;
    private String email;

    public PerfilExibir(String nome, String genero, int distancia, int idade, List<Foto> fotos, String descricao, String email) {
        this.nome = nome;
        this.genero = genero;
        this.distancia = distancia;
        this.idade = idade;
        this.fotos = fotos;
        this.descricao = descricao;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getGenero() {
        return genero;
    }

    public int getDistancia() {
        return distancia;
    }

    public int getIdade() {
        return idade;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "PerfilExibir{" +
                "nome='" + nome + '\'' +
                ", genero='" + genero + '\'' +
                ", distancia=" + distancia +
                ", idade=" + idade +
                ", fotos=" + fotos +
                ", descricao='" + descricao + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

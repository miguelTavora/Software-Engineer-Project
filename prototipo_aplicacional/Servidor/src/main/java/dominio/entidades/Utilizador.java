package dominio.entidades;

import java.util.Arrays;
import java.util.List;

public class Utilizador {

    //esta classe basicamente é a junção do perfil de exibir e identificador

    private String email;

    private String nome;
    private String genero;
    private int distancia;
    private int idade;
    private List<Foto> fotos;
    private String descricao;

    private int distMaxima;
    private List<Integer> idadesAceitaveis;
    private String generoInteresse;

    // nas idades aceitáveis tem de ser uma lista porque o firestore não deixa guardar arrays só lista
    public Utilizador(String email, String nome, String genero, int distancia, int idade, List<Foto> fotos,
                      String descricao, int distMaxima, List<Integer> idadesAceitaveis, String generoInteresse) {

        this.email = email;
        this.nome = nome;
        this.genero = genero;
        this.distancia = distancia;
        this.idade = idade;
        this.fotos = fotos;
        this.descricao = descricao;
        this.distMaxima = distMaxima;
        this.idadesAceitaveis = idadesAceitaveis;
        this.generoInteresse = generoInteresse;
    }

    public String getEmail() {
        return email;
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

    public int getDistMaxima() {
        return distMaxima;
    }

    public List<Integer> getIdadesAceitaveis() {
        return idadesAceitaveis;
    }

    public String getGeneroInteresse() {
        return generoInteresse;
    }

    @Override
    public String toString() {
        return "Utilizador{" +
                "email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", genero='" + genero + '\'' +
                ", distancia=" + distancia +
                ", idade=" + idade +
                ", fotos=" + fotos +
                ", descricao='" + descricao + '\'' +
                ", distMaxima=" + distMaxima +
                ", idadesAceitaveis=" + idadesAceitaveis +
                ", generoInteresse='" + generoInteresse + '\'' +
                '}';
    }
}

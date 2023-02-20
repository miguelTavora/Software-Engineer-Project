package dominio.entidades;

import java.util.Arrays;

public class Identificador {

    private String email;
    private int distMaxima;
    private int[] idadesAceitaveis;
    private String generoInteresse;

    public Identificador(String email, int distMaxima, int[] idadesAceitaveis, String generoInteresse) {
        this.email = email;
        this.distMaxima = distMaxima;
        this.idadesAceitaveis = idadesAceitaveis;
        this.generoInteresse = generoInteresse;
    }

    public String getEmail() {
        return email;
    }

    public int getDistMaxima() {
        return distMaxima;
    }

    public int[] getIdadesAceitaveis() {
        return idadesAceitaveis;
    }

    public String getGeneroInteresse() {
        return generoInteresse;
    }

    @Override
    public String toString() {
        return "Identificador{" +
                "email='" + email + '\'' +
                ", distMaxima=" + distMaxima +
                ", idadesAceitaveis=" + Arrays.toString(idadesAceitaveis) +
                ", generoInteresse='" + generoInteresse + '\'' +
                '}';
    }
}

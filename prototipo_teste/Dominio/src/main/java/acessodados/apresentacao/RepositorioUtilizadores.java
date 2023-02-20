package acessodados.apresentacao;

import dominio.entidades.Identificador;
import dominio.entidades.LocalizacaoUtilizador;
import dominio.entidades.PerfilExibir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepositorioUtilizadores {

    // estes atributos são de teste, a classe que guarda não devia ser Identificador e PerfilExibir mas sim Utilizador
    // contudo com o caso de utilizacao do login não foi feito assume-se esta classe
    private List<Identificador> perfis;
    private List<PerfilExibir> informacaoPerfil;
    private HashMap<String, Integer> indiceAleatorio;

    public RepositorioUtilizadores() {
        perfis = new ArrayList<Identificador>();
        informacaoPerfil = new ArrayList<PerfilExibir>();
        indiceAleatorio = new HashMap<>();
    }

    public Identificador obterInformacaoIdentificacao(LocalizacaoUtilizador locUtilizador) {
        for(Identificador perfil : perfis) {
            if(perfil.getEmail().equals(locUtilizador.getEmail())) {
                return perfil;
            }
        }
        return null;
    }

    public PerfilExibir obterPerfil(String emailUtilizador) {
        for(PerfilExibir perfil : informacaoPerfil) {
            if(perfil.getEmail().equals(emailUtilizador)) return perfil;
        }
        return null;
    }

    public PerfilExibir obterPerfilAleatorio(Identificador ident) {
        Integer indice = indiceAleatorio.get(ident.getEmail());
        if(indice ==  null) {
            indice = 0;
        }
        try {
            // se existem perfis para exibir, sao exibidos
            PerfilExibir perfil = informacaoPerfil.get(indice);
            indiceAleatorio.put(ident.getEmail(), indice+1);
            return perfil;
        } catch (Exception e) {
            return null;
        }
    }

    // este metodo deve ser depois modificado, serve somente de teste
    public boolean inserir(Identificador identUtl, PerfilExibir perfil) {
        boolean resultado1 = perfis.add(identUtl);
        boolean resultado2 = informacaoPerfil.add(perfil);
        return resultado1 && resultado2;
    }

    @Override
    public String toString() {
        String names = "\n";
        for(int i = 0; i < perfis.size(); i++) {
            names = names + perfis.get(i).toString()+", "+informacaoPerfil.get(i)+",\n";
        }

        return "\nRepositorioUtilizadores{" +
                "perfis && informacaoPerfil=" + names +
                "}\n";
    }
}

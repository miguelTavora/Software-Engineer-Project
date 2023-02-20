package acessodados.melhoresutilizadores;

import dominio.entidades.Identificador;
import dominio.recomendacao.Recomendacao;

import java.util.ArrayList;
import java.util.List;

public class RepositorioRecomendacoes {

    // atributo de teste
    // este atributo por cada utilizador guarda as recomendacoes obtidas
    // pelo algoritmo de aprendizagem automatica
    private ArrayList<Recomendacao> recomendacoes;

    public RepositorioRecomendacoes() {
        recomendacoes = new ArrayList<Recomendacao>();
    }

    public List<Recomendacao> obterRecomendacoes(Identificador ident) {
        List<Recomendacao> recomenUtl = new ArrayList<Recomendacao>();
        for(Recomendacao rec : recomendacoes) {
            if(rec.getEmailUtl1().equals(ident.getEmail())) recomenUtl.add(rec);
        }
        return recomenUtl;
    }

    public boolean inserirRecomendacoes(List<Recomendacao> recCalculadas) {
        return recomendacoes.addAll(recCalculadas);
    }

    @Override
    public String toString() {
        String recomendacoesNome = "\n";
        for(int i = 0; i < recomendacoes.size(); i++) {
            recomendacoesNome = recomendacoesNome + recomendacoes.get(i)+",\n";
        }

        return "RepositorioRecomendacoes{" +
                "recomendacoes=" + recomendacoesNome +
                "}\n";
    }
}

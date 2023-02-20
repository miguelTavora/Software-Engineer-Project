package acessodados.melhoresutilizadores;

import dominio.entidades.Classificacao;

import java.util.ArrayList;
import java.util.List;

public class RepositorioClassificacaoUtilizdor {

    private int indiceObtido = 0;
    // atributo de teste, teria de ser substituido pelo Firestore
    private List<Classificacao> classificacoes;

    public RepositorioClassificacaoUtilizdor() {
        classificacoes = new ArrayList<Classificacao>();
    }

    public boolean inserirClassificacao(Classificacao classificacao) {
        boolean existe = false;
        for(Classificacao classf : classificacoes) {
            if(classf.getEmailUtl().equals(classificacao.getEmailUtl()) &&
                    classf.getEmailUtlCorr().equals(classificacao.getEmailUtlCorr()))
                existe = true;
        }
        if(!existe)
            return classificacoes.add(classificacao);

        return false;
    }

    // obtem as classificacoes que ainda não foram obtidas previamente
    public List<Classificacao> obterNovasClassificacoes() {
        List<Classificacao> novasClassificacoes = new ArrayList<Classificacao>();
        int incremento = 0;
        for(int i = indiceObtido; i < classificacoes.size(); i++) {
            novasClassificacoes.add(classificacoes.get(i));
            incremento++;
        }
        indiceObtido = indiceObtido+incremento;
        return novasClassificacoes;
    }

    // obtem todas as classificacoes dadas por um utilizador
    public List<Classificacao> obterClassificacoesUtilizador(String emailUtl) {
        List<Classificacao> classificacoesUtl = new ArrayList<Classificacao>();
        for(Classificacao classf : classificacoes) {
            if(classf.getEmailUtl().equals(emailUtl)) classificacoesUtl.add(classf);
        }
        return classificacoesUtl;
    }

    @Override
    public String toString() {
        String classificacaoNome = "\n";
        for(int i = 0; i < classificacoes.size(); i++) {
            classificacaoNome = classificacaoNome+classificacoes.get(i)+",\n";
        }

        return "RepositorioClassificacaoUtilizdor{" +
                "classificacoes=" + classificacaoNome +
                '}';
    }
}

package dominio.servicos;

import acessodados.apresentacao.RepositorioSelecoes;
import acessodados.melhoresutilizadores.RepositorioClassificacaoUtilizdor;
import acessodados.melhoresutilizadores.RepositorioQuestionarios;
import acessodados.melhoresutilizadores.RepositorioRecomendacoes;
import dominio.entidades.Classificacao;
import dominio.entidades.RespostasQuestionario;
import dominio.entidades.Selecao;
import dominio.recomendacao.Recomendacao;

import java.util.ArrayList;
import java.util.List;

public class ServicoClassificacao {

    private RepositorioRecomendacoes repositorioRecom;
    private RepositorioSelecoes repositorioSel;
    private RepositorioQuestionarios repositorioQues;
    private RepositorioClassificacaoUtilizdor repositorioClass;

    private List<RespostasQuestionario> questionarios;
    private List<Classificacao> classificacoes;
    private List<Selecao> selecoes;

    public ServicoClassificacao(RepositorioRecomendacoes repositorioRecom, RepositorioSelecoes repositorioSel,
                                RepositorioQuestionarios repositorioQues, RepositorioClassificacaoUtilizdor repositorioClass) {
        this.repositorioRecom = repositorioRecom;
        this.repositorioSel = repositorioSel;
        this.repositorioQues = repositorioQues;
        this.repositorioClass = repositorioClass;
        questionarios = new ArrayList<>();
        classificacoes = new ArrayList<>();
        selecoes = new ArrayList<>();

    }

    public void calcularMelhoresUtilizadores() {
        List<Recomendacao> recomendacaos = calcularRecomendacoes();
        boolean resultado = repositorioRecom.inserirRecomendacoes(recomendacaos);
        System.out.println("Resultado inserir recomendacoes: "+resultado);
    }

    // metodo que deveria utilizar aprendizagem automatica para calcular as melhores
    // correspondencias, contudo esta implementacao seria muito demorada de implementar
    // e de testar e por isso é utilizado um algoritmo basico
    public List<Recomendacao> calcularRecomendacoes() {
        adicionarNovosQuestionarios();
        adicionarNovasClassificacoes();
        adicionarNovasSelecoes();

        // este algoritmo deveria ser substituido por um classificador
        // este algoritmo deveria utilizar tanto as seleções, como classificações atribuidas
        // a utilizadores e tambem os questionarios
        List<Recomendacao> recomendacaos = new ArrayList<>();
        for(Classificacao classificacao : classificacoes) {
            Recomendacao rec = new Recomendacao(classificacao.getEmailUtl(),
                    classificacao.getEmailUtlCorr(),classificacao.getClassificao());
            recomendacaos.add(rec);
        }

        return recomendacaos;
    }

    private void adicionarNovosQuestionarios() {
        List<RespostasQuestionario> novosQuestionarios = repositorioQues.obterNovosQuestionarios();
        questionarios.addAll(novosQuestionarios);
    }

    private void adicionarNovasClassificacoes() {
        List<Classificacao> novasClassificacoes = repositorioClass.obterNovasClassificacoes();
        classificacoes.addAll(novasClassificacoes);
    }

    private void adicionarNovasSelecoes() {
        List<Selecao> novasSelecoes = repositorioSel.obterNovasSelecoes();
        selecoes.addAll(novasSelecoes);
    }

}

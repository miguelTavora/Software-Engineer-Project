package teste;

import acessodados.apresentacao.RepositorioAnuncios;
import acessodados.apresentacao.RepositorioSelecoes;
import acessodados.apresentacao.RepositorioUtilizadores;
import acessodados.melhoresutilizadores.RepositorioClassificacaoUtilizdor;
import acessodados.melhoresutilizadores.RepositorioQuestionarios;
import acessodados.melhoresutilizadores.RepositorioRecomendacoes;
import dominio.servicos.ServicoApresentacao;
import dominio.servicos.ServicoClassificacao;
import dominio.servicos.ServicoClassificacaoPerfil;
import dominio.servicos.ServicoPreferencias;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class AplicacaoDominio {

    private static int PORTO_SERVICO_APRESENTACAO = 8000;
    private static int PORTO_SERVICO_CLASSIFICACAO_UTILIZADORES = 8001;
    private static int PORTO_SERVICO_PREFERENCIAS = 8002;


    private static RepositorioUtilizadores repUtilizadores;
    private static RepositorioSelecoes repSelecoes;
    private static RepositorioAnuncios repAnuncios;
    private static RepositorioRecomendacoes repRecomendacoes;
    private static RepositorioQuestionarios repQuestionarios;
    private static RepositorioClassificacaoUtilizdor repClassfUtl;

    private static ServicoApresentacao servApresentacao;
    private static ServicoClassificacao servClassificacao;
    private static ServicoClassificacaoPerfil servClassfPerfil;
    private static ServicoPreferencias servPreferencias;

    public static void main(String[] args) throws IOException, InterruptedException {
        // criacao dos repositorios
        repUtilizadores = new RepositorioUtilizadores();
        repSelecoes = new RepositorioSelecoes();
        repAnuncios = new RepositorioAnuncios();
        repRecomendacoes = new RepositorioRecomendacoes();
        repQuestionarios = new RepositorioQuestionarios();
        repClassfUtl = new RepositorioClassificacaoUtilizdor();

        // criação dos serviços
        servApresentacao = new ServicoApresentacao(repUtilizadores, repSelecoes, repAnuncios, repRecomendacoes);
        servClassificacao = new ServicoClassificacao(repRecomendacoes, repSelecoes, repQuestionarios, repClassfUtl);
        servClassfPerfil = new ServicoClassificacaoPerfil(repClassfUtl);
        servPreferencias = new ServicoPreferencias(repQuestionarios);

        // classe utilizada para teste das operacoes
        InserirDados dados = new InserirDados(repAnuncios, repSelecoes, repUtilizadores,
                repClassfUtl, repQuestionarios, repRecomendacoes);

        // inserir os dados de teste
        dados.inserirDadosUtilizadores();
        dados.inserirSelecoes();
        dados.inserirDadosAnuncio();
        dados.inserirRecomendacoes();
        dados.inserirQuestionario();
        dados.inserirClasificacaoUtilizadores();

        // mostrar os dados atuais
        /*System.out.println("###### UTILIZADORES ######\n"+repUtilizadores.toString());
        System.out.println("###### SELEÇÕES ######\n\n"+repSelecoes.toString());
        System.out.println("###### ANUNCIOS ######\n\n"+repAnuncios.toString());

        System.out.println("###### RECOMENDACOES ######\n\n"+repRecomendacoes.toString());
        System.out.println("###### QUESTIONARIS ######\n\n"+repQuestionarios.toString());
        System.out.println("###### CLASSIFICACAO UTILIZADORES ######\n\n"+repClassfUtl.toString());
        System.out.println("\n\n");*/

        System.out.println("###########   DADOS DE TESTE   ###########\n");

        System.out.println("###### UTILIZADORES ######\n"+repUtilizadores.toString());
        System.out.println("###### SELEÇÕES ######\n\n"+repSelecoes.toString());
        System.out.println("###### ANUNCIOS ######\n\n"+repAnuncios.toString());


        System.out.println("###### RECOMENDACOES ######\n\n"+repRecomendacoes.toString());
        System.out.println("###### QUESTIONARIOS ######\n\n"+repQuestionarios.toString());
        System.out.println("###### CLASSIFICACAO UTILIZADORES ######\n\n"+repClassfUtl.toString());
        System.out.println("########### FIM DADOS TESTE  ################\n\n\n");


        System.out.println("Caso de utilização (calcular melhores utilizadores): \n");
        System.out.println("###### RECOMENDACOES ANTES DE CALCULAR MELHORES UTILIZADORES ######\n"+repRecomendacoes.toString());
        servClassificacao.calcularMelhoresUtilizadores();
        System.out.println("\n###### RECOMENDACOES DEPOIS DE CALCULAR MELHORES UTILIZADORES ######\n"+repRecomendacoes.toString());


        // servidor do servico apresentacao
        final Server svc1 = ServerBuilder.forPort(PORTO_SERVICO_APRESENTACAO)
                .addService(servApresentacao).build().start();

        // servidor do servico classificacao utilizador
        final Server svc2 = ServerBuilder.forPort(PORTO_SERVICO_CLASSIFICACAO_UTILIZADORES)
                .addService(servClassfPerfil).build().start();


        final Server svc3 = ServerBuilder.forPort(PORTO_SERVICO_PREFERENCIAS)
                .addService(servPreferencias).build().start();



        // comeca uma nova thread para obter os pedidos dos novos clientes
        new Thread() {
            public void run() {
                try {
                    svc1.awaitTermination();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        new Thread() {
            public void run() {
                try {
                    svc2.awaitTermination();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        System.err.println("*** server await termination");
        svc3.awaitTermination();
    }
}

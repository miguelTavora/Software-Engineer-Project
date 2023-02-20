package servidor;

import acessodados.apresentacao.RepositorioAnuncios;
import acessodados.apresentacao.RepositorioSelecoes;
import acessodados.apresentacao.RepositorioUtilizadores;
import acessodados.melhoresutilizadores.RepositorioClassificacaoUtilizdor;
import acessodados.melhoresutilizadores.RepositorioQuestionarios;
import acessodados.melhoresutilizadores.RepositorioRecomendacoes;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import dominio.entidades.Classificacao;
import dominio.servicos.ServicoApresentacao;
import dominio.servicos.ServicoClassificacao;
import dominio.servicos.ServicoClassificacaoPerfil;
import dominio.servicos.ServicoPreferencias;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import teste.InserirDados;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Servidor {

    private static final int PORTO_SERVICO_APRESENTACAO = 8000;
    private static final int PORTO_SERVICO_CLASSIFICACAO_UTILIZADORES = 8001;
    private static final int PORTO_SERVICO_PREFERENCIAS = 8002;
    private static final String NOME_COLECAO = "ES";


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

    private static Firestore firestore;

    public static void main(String[] args) throws IOException, InterruptedException {

        //cria uma conexão do firestore com a google
        firestore = criarConexaoFirestore(args);

        // criacao dos repositorios
        repUtilizadores = new RepositorioUtilizadores(firestore);
        repSelecoes = new RepositorioSelecoes(firestore);
        repAnuncios = new RepositorioAnuncios(firestore);
        repRecomendacoes = new RepositorioRecomendacoes(firestore);
        repQuestionarios = new RepositorioQuestionarios(firestore);
        repClassfUtl = new RepositorioClassificacaoUtilizdor(firestore);

        // criação dos serviços
        servApresentacao = new ServicoApresentacao(repUtilizadores, repSelecoes, repAnuncios, repRecomendacoes);
        servClassificacao = new ServicoClassificacao(repRecomendacoes, repSelecoes, repQuestionarios, repClassfUtl);
        servClassfPerfil = new ServicoClassificacaoPerfil(repClassfUtl);
        servPreferencias = new ServicoPreferencias(repQuestionarios);


        // classe utilizada para teste das operacoes
        InserirDados dados = new InserirDados(repAnuncios, repSelecoes, repUtilizadores,
                repClassfUtl, repQuestionarios, repRecomendacoes);
        //dados.inserirDadosAnuncio();
        //System.out.println("###### ANUNCIOS ######\n\n"+repAnuncios.toString());


        //dados.inserirSelecoes();
        //System.out.println("###### SELEÇÕES ######\n\n"+repSelecoes.toString());
        /*var x = repSelecoes.obterSelecoesUtilizador("sara@isel.pt");
        System.out.println(x);*/


        //dados.inserirDadosUtilizadores();
        //System.out.println("###### UTILIZADORES ######\n"+repUtilizadores.toString());



        //dados.inserirClasificacaoUtilizadores();
        /* System.out.println("###### CLASSIFICACAO UTILIZADORES ######\n\n"+repClassfUtl.toString());
        List<Classificacao> x = repClassfUtl.obterClassificacoesUtilizador("joao@isel.pt");
        System.out.println(x);*/


        //dados.inserirRecomendacoes();
        //System.out.println("###### RECOMENDACOES ######\n\n"+repRecomendacoes.toString());

        System.out.println("Caso de utilização (calcular melhores utilizadores): ");
        servClassificacao.calcularMelhoresUtilizadores();
        System.out.println("###### RECOMENDACOES ######\n\n"+repRecomendacoes.toString());


        //dados.inserirQuestionario();
        /*System.out.println("###### QUESTIONARIOS ######\n\n"+repQuestionarios.toString());
        var x = repQuestionarios.obterQuestionarioUtilizador("ricardo@isel.pt");
        System.out.println(x);
        var x2 = repQuestionarios.obterQuestionarioUtilizador("luis@isel.pt");
        System.out.println(x2);*/
        /*

        System.out.println("###### RECOMENDACOES ######\n\n"+repRecomendacoes.toString());

        System.out.println("");*/


        /*System.out.println("Caso de utilização (calcular melhores utilizadores): ");
        servClassificacao.calcularMelhoresUtilizadores();
        System.out.println("###### RECOMENDACOES ######\n\n"+repRecomendacoes.toString());*/



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

    // cria uma conexão com o firestore com uma key do firestore
    private static Firestore criarConexaoFirestore(String[] args) throws IOException {
        // path hardcoded
        String pathFileKeyJson = "cd2122d-g10-c30a18543a8e.json";
        GoogleCredentials credentials = null;
        if (args.length > 0) {
            InputStream serviceAccount = new FileInputStream(args[0]);
            credentials = GoogleCredentials.fromStream(serviceAccount);
        } else {
            InputStream serviceAccount = new FileInputStream(pathFileKeyJson);
            credentials = GoogleCredentials.fromStream(serviceAccount);
            // utilizar GOOGLE_APPLICATION_CREDENTIALS variavel de ambiente e descomentar codigo abaixo
            //credentials = GoogleCredentials.getApplicationDefault();
        }
        FirestoreOptions options = FirestoreOptions
                .newBuilder().setCredentials(credentials).build();
        return options.getService();
    }

}

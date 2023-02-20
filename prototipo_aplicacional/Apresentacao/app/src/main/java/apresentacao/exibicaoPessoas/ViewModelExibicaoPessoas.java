package apresentacao.exibicaoPessoas;

import android.util.Log;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import servicoApresentacao.*;

import java.util.List;

public class ViewModelExibicaoPessoas {

    private ManagedChannel canal = null;
    private ApresentacaoGrpc.ApresentacaoStub noBlockingStub;
    private ApresentacaoGrpc.ApresentacaoBlockingStub blockingStub;


    public ViewModelExibicaoPessoas(String enderecoIP, int porto) {
        try {
            // criação dos canais para falar com o servidor
            canal = ManagedChannelBuilder.forAddress(enderecoIP, porto)
                    .usePlaintext().build();

            noBlockingStub = ApresentacaoGrpc.newStub(canal);
            blockingStub = ApresentacaoGrpc.newBlockingStub(canal);


        } catch (Exception ex) {
            System.err.println("Erro na criação do canal com o servidor apresentação"+ex.getMessage());
            //System.exit(1);
        }
    }

    public Identificador enviarLocalizacao(LocalizacaoUtilizador locUtilizador) {
        Identificador ident = blockingStub.enviarLocalizacaoUtilizador(locUtilizador);
        return ident;
    }

    public List<PerfilExibir> pedirUtilizadores(Identificador identificador) {
        ExibicaoPessoasObservador observador = new ExibicaoPessoasObservador();

        noBlockingStub.pedirUtilizadores(identificador,observador);
        // espera até obter todos os perfis
        while (!observador.getCompleto()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!observador.getErro()) {
            return  observador.getPerfis();
        }
        return null;
    }

    public boolean enviarSelecao(Selecao selecao) {
        Resultado res = blockingStub.enviarSelecao(selecao);
        return res.getResultado();
    }

    public Anuncio pedirAnuncio(Identificador identificador) {
        Anuncio anuncio = blockingStub.pedirAnuncio(identificador);
        return anuncio;
    }



}

package dominio.servicos;

import acessodados.melhoresutilizadores.RepositorioClassificacaoUtilizdor;
import classificacaoPerfil.Classificacao;
import classificacaoPerfil.ClassificacaoPerfilGrpc;
import classificacaoPerfil.Resultado;
import io.grpc.stub.StreamObserver;

public class ServicoClassificacaoPerfil extends ClassificacaoPerfilGrpc.ClassificacaoPerfilImplBase{

    private RepositorioClassificacaoUtilizdor repositorio;

    public ServicoClassificacaoPerfil(RepositorioClassificacaoUtilizdor repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void enviarClassificao(Classificacao request, StreamObserver<Resultado> responseObserver) {
        // transorma o objeto Classificacao utilizado no gRPC para classe da entidade
        String emailUtl = request.getEmailUtl();
        String emailUtlCorr = request.getEmailUtlCorr();
        int classificacao = request.getClassificao();
        dominio.entidades.Classificacao entidadeClassf =
                new dominio.entidades.Classificacao(emailUtl,emailUtlCorr, classificacao);

        // guarda o resultado e obtem o resultado
        boolean resultado = repositorio.inserirClassificacao(entidadeClassf);
        Resultado res = Resultado.newBuilder().setResultado(resultado).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}

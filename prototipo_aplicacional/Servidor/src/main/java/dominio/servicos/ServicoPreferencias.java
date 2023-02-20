package dominio.servicos;

import acessodados.melhoresutilizadores.RepositorioQuestionarios;
import io.grpc.stub.StreamObserver;
import preferencias.PreferenciasGrpc;
import preferencias.RespostasQuestionario;
import preferencias.Resultado;

public class ServicoPreferencias extends PreferenciasGrpc.PreferenciasImplBase {

    private RepositorioQuestionarios repositorio;

    public ServicoPreferencias(RepositorioQuestionarios repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void enviarQuestionario(RespostasQuestionario request, StreamObserver<Resultado> responseObserver) {

        // construir a classe das entidadas com o objeto gRPC
        dominio.entidades.RespostasQuestionario questionario =
                new dominio.entidades.RespostasQuestionario(request.getEmailUtl(), request.getRespostasList());

        boolean resultadoInserir = repositorio.inserir(questionario);
        Resultado resultado = Resultado.newBuilder().setResultado(resultadoInserir).build();
        // enviar resultado
        responseObserver.onNext(resultado);
        responseObserver.onCompleted();
    }
}

package testeClassificacaoUtilizador;

import classificacaoPerfil.Classificacao;
import classificacaoPerfil.ClassificacaoPerfilGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import servicoApresentacao.ApresentacaoGrpc;
import servicoApresentacao.EmailUtlCorr;
import servicoApresentacao.PerfilExibir;

public class ViewModelPerfilCorrespondencia {

    private ManagedChannel channelApresentacao = null;
    private ApresentacaoGrpc.ApresentacaoBlockingStub blockingStubApresentacao;


    private ManagedChannel channelClassPerfil = null;
    private ClassificacaoPerfilGrpc.ClassificacaoPerfilBlockingStub blockingStubClassPerfil;

    public ViewModelPerfilCorrespondencia(String enderecoIPApresentacao, int portoApresentacao,
                                          String endereçoIPClassPerfil, int portoClassPerfil) {
        try {
            channelApresentacao = ManagedChannelBuilder.forAddress(enderecoIPApresentacao, portoApresentacao)
                    .usePlaintext().build();
            blockingStubApresentacao = ApresentacaoGrpc.newBlockingStub(channelApresentacao);

        } catch (Exception ex) {
            System.err.println("Erro na criação do canal com o servidor apresentação");
            System.exit(1);
        }


        try {
            channelClassPerfil = ManagedChannelBuilder.forAddress(endereçoIPClassPerfil, portoClassPerfil)
                    .usePlaintext().build();
            blockingStubClassPerfil = ClassificacaoPerfilGrpc.newBlockingStub(channelClassPerfil);

        } catch (Exception ex) {
            System.err.println("Erro na criação do canal com o servidor classificação de perfil");
            System.exit(1);
        }

    }

    public PerfilExibir pedirInformacaoPerfil(String emailUtl) {
        EmailUtlCorr email = EmailUtlCorr.newBuilder().setEmail(emailUtl).build();
        PerfilExibir perfil = blockingStubApresentacao.pedirInformacaoPerfil(email);
        return perfil;
    }

    public boolean enviarClassificacao(Classificacao classificacao) {
        classificacaoPerfil.Resultado resultado = blockingStubClassPerfil.enviarClassificao(classificacao);
        return resultado.getResultado();
    }
}

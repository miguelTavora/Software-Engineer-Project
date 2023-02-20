package testeResponderQuestionario;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import preferencias.PreferenciasGrpc;
import preferencias.RespostasQuestionario;
import preferencias.Resultado;

import java.util.ArrayList;
import java.util.List;

public class ViewModelQuestionario {

    private ManagedChannel canal = null;
    private PreferenciasGrpc.PreferenciasBlockingStub blockingStub;
    private List<Integer> respostas;

    public ViewModelQuestionario(String enderecoIP, int porto) {
        respostas = new ArrayList<>();

        try {
            canal = ManagedChannelBuilder.forAddress(enderecoIP, porto)
                    .usePlaintext().build();
            blockingStub = PreferenciasGrpc.newBlockingStub(canal);

        } catch (Exception ex) {
            System.err.println("Erro na criação do canal com o servidor apresentação");
            System.exit(1);
        }
    }

    public void submeterResposta(int resposta) {
        respostas.add(resposta);
    }

    public boolean enviarQuestionario(String emailUtl) {
        RespostasQuestionario questionario = RespostasQuestionario.newBuilder()
                .addAllRespostas(respostas).setEmailUtl(emailUtl).build();

        System.out.println("Questionario: "+questionario.toString().replace("\n", ", "));
        Resultado resultado = blockingStub.enviarQuestionario(questionario);
        return resultado.getResultado();
    }
}

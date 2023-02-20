package apresentacao.exibicaoPessoas;

import io.grpc.stub.StreamObserver;
import servicoApresentacao.PerfilExibir;

import java.util.ArrayList;
import java.util.List;

public class ExibicaoPessoasObservador implements StreamObserver<PerfilExibir> {

    private List<PerfilExibir> perfis = new ArrayList<>();
    private boolean completo = false;
    private boolean erro = false;


    @Override
    public void onNext(PerfilExibir perfilExibir) {
        perfis.add(perfilExibir);
    }

    @Override
    public void onError(Throwable throwable) {
        erro = true;
        completo = true;
        System.err.println("Erro recebido: "+throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        completo = true;
    }

    public boolean getCompleto() {
        return completo;
    }

    public boolean getErro() {
        return this.erro;
    }

    public List<PerfilExibir> getPerfis() {
        return this.perfis;
    }
}

package testeClassificacaoUtilizador;

import classificacaoPerfil.Classificacao;
import servicoApresentacao.PerfilExibir;

public class TesteAtribuirClassificacaoUtilizador {

    private static final String ENDERECO_IP_SERVIDOR_APRESENTACAO = "localhost";
    private static final int PORTO_SERVER_APRESENTACAO = 8000;
    private static final String ENDERECO_IP_SERVIDOR_CLASSIFICACAO_UTILIZADORES = "localhost";
    private static final int PORTO_SERVICO_CLASSIFICACAO_UTILIZADORES = 8001;

    private static final String emailExemplo = "miguel@isel.pt";
    private static final String emailExemploCorres = "sara@isel.pt";

    private static ViewModelPerfilCorrespondencia viewModelPerfilCorrs;

    public static void main(String[] args) {
        viewModelPerfilCorrs = new ViewModelPerfilCorrespondencia(ENDERECO_IP_SERVIDOR_APRESENTACAO,
                PORTO_SERVER_APRESENTACAO, ENDERECO_IP_SERVIDOR_CLASSIFICACAO_UTILIZADORES, PORTO_SERVICO_CLASSIFICACAO_UTILIZADORES);

        //PerfilExibir
        // {nome='Sara',
        // genero='femenino',
        // distancia=10,
        // idade=23,
        // fotos=[Foto{texto='Foto: 1'},
        // Foto{texto='Foto: 2'}],
        // descricao='nao gosto de ananas',
        // email='sara@isel.pt'},
        System.out.println("#### Teste ao domínio -> Atribuir classificação ao utilizador ####");
        PerfilExibir perfil = viewModelPerfilCorrs.pedirInformacaoPerfil(emailExemploCorres);
        System.out.println("Pedir Informação de perfil(resultado): ");
        System.out.println(perfil+"\n");

        // resultado deve ser -> true
        // (no caso do prototipo aplicacional) corrido 2x vai dar false
        Classificacao classificacao = Classificacao.newBuilder().setEmailUtl(emailExemplo)
                .setEmailUtlCorr(emailExemploCorres).setClassificao(90).build();
        System.out.println("Classificação: "+classificacao.toString().replace("\n", ", "));
        boolean resultado = viewModelPerfilCorrs.enviarClassificacao(classificacao);
        System.out.println("Enviar classificação(resultado): "+resultado+"\n");

        // resultado deve ser -> false
        Classificacao classificacao2 = Classificacao.newBuilder().setEmailUtl(emailExemplo)
                .setEmailUtlCorr(emailExemploCorres).setClassificao(90).build();
        boolean resultado2 = viewModelPerfilCorrs.enviarClassificacao(classificacao2);
        System.out.println("Enviar classificação(resultado): "+resultado2);
    }
}

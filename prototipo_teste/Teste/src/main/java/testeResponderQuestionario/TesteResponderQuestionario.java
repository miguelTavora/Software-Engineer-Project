package testeResponderQuestionario;

public class TesteResponderQuestionario {

    private static final String ENDERECO_IP_SERVIDOR_PREFERENCIAS = "localhost";
    private static final int PORTO_SERVER_PREFERENCIAS = 8002;

    private static String emailExemplo = "joao@isel.pt";

    private static ViewModelQuestionario viewModelQuestionario;

    public static void main(String[] args) {
        viewModelQuestionario = new ViewModelQuestionario(ENDERECO_IP_SERVIDOR_PREFERENCIAS, PORTO_SERVER_PREFERENCIAS);

        System.out.println("#### Teste ao domínio -> Obter preferencias do utilizador ####");

        // adicionar respostas
        viewModelQuestionario.submeterResposta(3);
        viewModelQuestionario.submeterResposta(2);
        viewModelQuestionario.submeterResposta(1);
        viewModelQuestionario.submeterResposta(3);
        viewModelQuestionario.submeterResposta(3);
        viewModelQuestionario.submeterResposta(3);



        // enviar questionario
        // primeiro teste -> deve dar true
        // (no caso do prototipo aplicacional) se correr 2x vão dar os 2 false
        boolean resultado = viewModelQuestionario.enviarQuestionario(emailExemplo);
        System.out.println("Enviar questionario(resultado): "+resultado+"\n\n");



        // segundo teste -> deve dar falso
        boolean resultado2 = viewModelQuestionario.enviarQuestionario(emailExemplo);
        System.out.println("Enviar questionario(resultado): "+resultado2);
    }
}

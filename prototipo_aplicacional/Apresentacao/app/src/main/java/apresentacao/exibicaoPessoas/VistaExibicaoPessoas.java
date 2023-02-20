package apresentacao.exibicaoPessoas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import apresentacao.R;
import servicoApresentacao.Anuncio;
import servicoApresentacao.Identificador;
import servicoApresentacao.LocalizacaoUtilizador;
import servicoApresentacao.PerfilExibir;
import servicoApresentacao.Selecao;

public class VistaExibicaoPessoas extends AppCompatActivity {

    private final String LOCALIZACAO = "Lisboa";

    private String enderecoIP;
    private int porto;
    private String email;
    private String nome;
    private ViewModelExibicaoPessoas viewModel;
    private int contador = 0;

    private Identificador ident;
    private List<PerfilExibir> perfis;
    private PerfilExibir perfilRetroceder;
    private boolean retrocedeu = false;
    private Anuncio anuncio;
    private boolean primeiraVez = true;

    private ImageView imagem;
    private Button gostoBt;
    private Button retrocederBt;
    private Button naoGostoBt;
    private TextView nomeTxt;
    private TextView descricaoTxt;

    private ImageView imagemGosto;
    private ImageView imagemNaoGosto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_exibicao_pessoas);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            enderecoIP = extras.getString("enderecoIP");
            porto = extras.getInt("porto");
            email = extras.getString("email");
            nome = extras.getString("nome");
        }

        imagem = findViewById(R.id.foto);
        gostoBt = findViewById(R.id.gosto);
        retrocederBt = findViewById(R.id.retroceder);
        naoGostoBt = findViewById(R.id.naogosto);
        nomeTxt = findViewById(R.id.nome);
        descricaoTxt = findViewById(R.id.descricao);
        imagemGosto = findViewById(R.id.imgosto);
        imagemNaoGosto = findViewById(R.id.imnaogosto);

        imagem.setImageResource(R.drawable.avatar);


        viewModel = new ViewModelExibicaoPessoas(enderecoIP, porto);

        // TODO se os jars funcionassem era adicionar isto
        /*enviarLocalizacaoUtilizador();

        pedirUtilizadores();

        exibirUtilizadores(perfis);

        selecionarGosto();
        selecionarNaoGosto();
        selecionarRetroceder();*/

    }

    public void enviarLocalizacaoUtilizador() {
        LocalizacaoUtilizador loc = LocalizacaoUtilizador.newBuilder()
                .setNome(nome).setEmail(email).setLocalizacao(obterLocalizacao()).build();

        Identificador ident = viewModel.enviarLocalizacao(loc);
        this.ident = ident;
    }

    // metodo que deveria de facto obter a localização
    private String obterLocalizacao() {
        return LOCALIZACAO;
    }

    public void pedirUtilizadores() {
        List<PerfilExibir> perfis = viewModel.pedirUtilizadores(ident);
        this.perfis = perfis;
    }

    // TODO falta foto
    public void exibirUtilizadores(List<PerfilExibir> utls) {
        PerfilExibir perfil = utls.get(contador);
        String texto = perfil.getNome()+", "+perfil.getIdade()+", "+perfil.getDistancia();
        nomeTxt.setText(texto);
        descricaoTxt.setText(perfil.getDescricao());

    }

    public boolean enviarSelecao(boolean gosto) {
        Selecao selecao = Selecao.newBuilder().setGosto(gosto).setEmailUtilizador(email)
                .setEmailPerfilApresentado(perfis.get(contador).getEmail()).build();
        boolean resultado = viewModel.enviarSelecao(selecao);
        return resultado;
    }

    public void exibicaoResultadoAtualizarPerfil(boolean gosto) {
        if(gosto) {
            imagemGosto.setVisibility(View.VISIBLE);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            imagemGosto.setVisibility(View.GONE);
        }
        else {
            imagemNaoGosto.setVisibility(View.VISIBLE);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            imagemNaoGosto.setVisibility(View.GONE);
        }
    }

    public void pedirAnuncio() {
        Anuncio anuncio = viewModel.pedirAnuncio(ident);
        this.anuncio = anuncio;
    }

    public void exibirAnuncio() {
        String texto = anuncio.getTexto();
        nomeTxt.setText(texto);
        descricaoTxt.setText("");
        imagem.setImageResource(R.drawable.anuncio);
    }

    private void voidGostoNaoGosto(boolean selecao) {
        if(contador == 10) {
            perfilRetroceder = perfis.get(contador-1);
            pedirAnuncio();
            exibirAnuncio();
            Log.d("ANUNCIO", "ANUNCIOOOO");
            contador = -1;
        }
        else if(contador == 0) {
            imagem.setImageResource(R.drawable.avatar);
            enviarSelecao(selecao);
            if(!primeiraVez) {
                pedirUtilizadores();
                exibirUtilizadores(perfis);
            }
            primeiraVez = false;
        }
        else if(contador > -1){
            enviarSelecao(selecao);
            perfilRetroceder = perfis.get(contador-1);
            exibirUtilizadores(perfis);
        }
        contador++;
        retrocedeu = false;
    }

    //TODO
    // meter uma imagem quando se da like ou não
    private void selecionarGosto() {
        gostoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exibicaoResultadoAtualizarPerfil(true);
                voidGostoNaoGosto(true);
            }
        });
    }

    private void selecionarNaoGosto() {
        naoGostoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exibicaoResultadoAtualizarPerfil(false);
                voidGostoNaoGosto(false);
            }
        });
    }

    private void selecionarRetroceder() {
        retrocederBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(perfilRetroceder != null && !retrocedeu) {
                    contador--;
                    exibirUtilizadores(perfis);
                    retrocedeu = true;
                }
            }
        });
    }

}
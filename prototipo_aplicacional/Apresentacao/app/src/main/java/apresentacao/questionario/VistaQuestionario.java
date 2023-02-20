package apresentacao.questionario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import apresentacao.R;

public class VistaQuestionario extends AppCompatActivity {

    private ViewModelQuestionario viewModel;
    private String enderecoIP;
    private int porto;
    private String email;

    private TextView pergunta;
    private Button bt1;
    private Button bt2;
    private Button bt3;
    private TextView resultado;
    private int estado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_questionario);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            enderecoIP = extras.getString("enderecoIP");
            porto = extras.getInt("porto");
            email = extras.getString("email");
        }

        pergunta = findViewById(R.id.pergunta);
        bt1 = findViewById(R.id.botao1);
        bt2 = findViewById(R.id.botao2);
        bt3 = findViewById(R.id.botao3);
        resultado = findViewById(R.id.resultado);

        pergunta.setText("Tu costumas fazer amigos regularmente?");

        bt1.setText("Muito frequentemente");
        bt2.setText("Nem muito nem pouco");
        bt3.setText("Muito pouco");

        viewModel = new ViewModelQuestionario(enderecoIP, porto);

        selecionarMuito();
        selecionarNemMuitoNemPouco();
        selecionarPouco();
    }

    private void selecionarMuito() {
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.submeterResposta(3);
                if(estado == 0) {
                    apresentarPergunta2();
                }
                else if(estado == 1) {
                    apresentarPergunta3();
                }
                else if(estado == 2) {
                    exibirResultadoQuestionario();
                }
                else {
                    //viewModel.enviarQuestionario(email);
                    mudarVistaPrincipal();
                }
                estado++;
            }
        });
    }

    private void selecionarNemMuitoNemPouco() {
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.submeterResposta(2);
                if(estado == 0) {
                    apresentarPergunta2();
                }
                else if(estado == 1) {
                    apresentarPergunta3();
                }
                else if(estado == 2) {
                    exibirResultadoQuestionario();
                }
                estado++;
            }
        });
    }

    private void selecionarPouco() {
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.submeterResposta(1);
                if(estado == 0) {
                    apresentarPergunta2();
                }
                else if(estado == 1) {
                    apresentarPergunta3();
                }
                else if(estado == 2) {
                    exibirResultadoQuestionario();
                }
                estado++;
            }
        });
    }

    private void apresentarPergunta2() {
        pergunta.setText("Costumas geralmente manter-te calmo?");
        bt1.setText("Muito frequentemente");
        bt2.setText("Nem muito nem pouco");
        bt3.setText("Muito pouco");
    }

    private void apresentarPergunta3() {
        pergunta.setText("És muito sentimental?");
        bt1.setText("Muito");
        bt2.setText("Nem muito nem pouco");
        bt3.setText("Pouco");
    }

    public void exibirResultadoQuestionario() {
        pergunta.setVisibility(View.GONE);
        bt3.setVisibility(View.GONE);
        bt2.setVisibility(View.GONE);
        String resultadoTxt = "Respostas: "+viewModel.getRespostas();
        resultado.setText(resultadoTxt);
        resultado.setVisibility(View.VISIBLE);
        bt1.setText("Retornar");
    }

    public void mudarVistaPrincipal() {
        finish();
    }

}
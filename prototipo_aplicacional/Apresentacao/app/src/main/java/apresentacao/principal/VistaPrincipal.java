package apresentacao.principal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import apresentacao.R;
import apresentacao.exibicaoPessoas.VistaExibicaoPessoas;
import apresentacao.perfilCorrespondencia.VistaPerfiCorrespondencia;
import apresentacao.questionario.VistaQuestionario;

public class VistaPrincipal extends AppCompatActivity {

    // todo estes atributos deveriam ser substituidos por
    // a parte quando é feito o login
    private final String EMAIL_EXEMPLO = "joao@isel.pt";
    private final String EMAIL_CORRS_EXEMPLO = "sara@isel.pt";
    private final String ENDERECO_IP = "localhost";
    private final int PORTO_APRESENTACAO = 8000;
    private final int PORTO_CLASSIFICACAO = 8001;
    private final int PORTO_PREFERENCIAS = 8002;
    private final String NOME = "João";

    private Button editarBtn;
    private Button gostosBtn;
    private Button corrsBtn;
    private Button questionarioBtn;
    private Button definicoesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_principal);

        editarBtn = findViewById(R.id.editar);
        gostosBtn = findViewById(R.id.gostos);
        corrsBtn = findViewById(R.id.corrs);
        questionarioBtn = findViewById(R.id.questionario);
        definicoesBtn = findViewById(R.id.definicoes);


        mudarVistaEditarPerfil();
        mudarVistaExibicaoPessoas();

        alterarVistaPerfilCorrespondencia(EMAIL_CORRS_EXEMPLO);

        mudarVistaQuestionario();

        mudarVistaDefinicoes();
    }

    public void mudarVistaEditarPerfil() {
        editarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editarBtn.getText().equals("Editar perfil")) {
                    editarBtn.setText("Não implementado");
                }
                else {
                    editarBtn.setText("Editar perfil");
                }
            }
        });
    }

    public void mudarVistaExibicaoPessoas() {
        gostosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VistaPrincipal.this, VistaExibicaoPessoas.class);
                intent.putExtra("email", EMAIL_EXEMPLO);
                intent.putExtra("enderecoIP", ENDERECO_IP);
                intent.putExtra("porto", PORTO_APRESENTACAO);
                intent.putExtra("nome", NOME);
                startActivity(intent);
            }

        });
    }

    public void alterarVistaPerfilCorrespondencia(String emailPerfil) {
        corrsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VistaPrincipal.this, VistaPerfiCorrespondencia.class);
                intent.putExtra("enderecoIP", ENDERECO_IP);
                intent.putExtra("porto1", PORTO_APRESENTACAO);
                intent.putExtra("porto2", PORTO_CLASSIFICACAO);
                intent.putExtra("email", emailPerfil);
                intent.putExtra("emailCorrs", EMAIL_CORRS_EXEMPLO);
                startActivity(intent);
            }

        });
    }

    public void mudarVistaQuestionario() {
        questionarioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VistaPrincipal.this, VistaQuestionario.class);
                intent.putExtra("email", EMAIL_EXEMPLO);
                intent.putExtra("enderecoIP", ENDERECO_IP);
                intent.putExtra("porto", PORTO_PREFERENCIAS);
                startActivity(intent);
            }
        });
    }

    public void mudarVistaDefinicoes() {
        definicoesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(definicoesBtn.getText().equals("Definições")) {
                    definicoesBtn.setText("Não implementado");
                }
                else {
                    definicoesBtn.setText("Definições");
                }
            }
        });
    }

}
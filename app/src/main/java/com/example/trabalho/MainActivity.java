package com.example.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase bancoDados;

    EditText nome, altura, peso;
    Button botaoAdd, botaoCad, botaoDel, botaoAvaliar;
    ListView listView;
    RatingBar rtb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        nome = (EditText) findViewById(R.id.nome);
        altura = (EditText) findViewById(R.id.altura);
        peso = (EditText) findViewById(R.id.peso);

        botaoAdd = (Button) findViewById(R.id.btnAdd);
        botaoCad = (Button) findViewById(R.id.btnCad);
        botaoDel = (Button) findViewById(R.id.btnDel);






        criarBancoDados();
       // listarDados();
       // inserirDados();
        //apagarDados();


        botaoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarMsg();
                cadastrarPaciente();
            }
        });

        botaoDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apagarDados();
            }
        });


        botaoCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarPacientes();

            }
        });
    }
    public void criarBancoDados() {
        try {
            bancoDados = openOrCreateDatabase("hospital", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS paciente(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ", nome VARCHAR" +
                    ", peso FLOAT" +
                    ", altura FLOAT" +
                    ", SEXO VARCHAR)");
            bancoDados.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }



    public void enviarMsg(){
            Toast.makeText(this, "Paciente Cadastrado!", Toast.LENGTH_SHORT).show();
    }

    public void mostrarPacientes(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }




    public void cadastrarPaciente(){
        if(!TextUtils.isEmpty(nome.getText().toString())){
            try{
                bancoDados = openOrCreateDatabase("hospital", MODE_PRIVATE, null);
                String sql = "INSERT INTO paciente (nome, peso, altura) VALUES (?, ?,?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1,nome.getText().toString());
                stmt.bindString(2,peso.getText().toString());
                stmt.bindString(3,altura.getText().toString());
                stmt.executeInsert();
                bancoDados.close();
                finish();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void inserirDados(){
        bancoDados = openOrCreateDatabase("hospital", MODE_PRIVATE, null);
        String sql = "INSERT INTO paciente (nome, peso, altura) VALUES (?, ?,?)";
        SQLiteStatement stmt = bancoDados.compileStatement(sql);
        stmt.bindString(1,"Jeferson");
        stmt.bindString(2,"72");
        stmt.bindString(3,"1.75");
        stmt.executeInsert();

    }
    public void apagarDados(){
        bancoDados = openOrCreateDatabase("hospital", MODE_PRIVATE, null);
        String sql = "DELETE FROM paciente";
        SQLiteStatement stmt = bancoDados.compileStatement(sql);

        stmt.executeUpdateDelete();

    }

}
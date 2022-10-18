package com.example.trabalho;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {


    ListView lista;

    SQLiteDatabase bancoDados;
    public ArrayList<Integer> arrayIds;
    public Integer idSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        lista = (ListView) findViewById(R.id.listView);
        Button btnVoltar;
        btnVoltar = (Button) findViewById(R.id.btnVoltar);

        listarDados();

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltar();
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                idSelecionado = arrayIds.get(i);
                confirmaExcluir();
                return true;
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                idSelecionado = arrayIds.get(i);
                abrirTelaAlterar();
            }
        });
    }
        public void excluir(){

            try {
                bancoDados = openOrCreateDatabase("hospital", MODE_PRIVATE, null);
                String sql = "DELETE FROM paciente WHERE id = ?";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindLong(1, idSelecionado);
                stmt.executeUpdateDelete();
                listarDados();
                bancoDados.close();
                Toast.makeText(this, "O paciente selecionado foi excluído!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void confirmaExcluir(){
            AlertDialog.Builder msgBox = new AlertDialog.Builder(MainActivity2.this);
            msgBox.setTitle("Excluir");
            msgBox.setIcon(android.R.drawable.ic_menu_delete);
            msgBox.setMessage("Você quer excluir esse registro?");
            msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    excluir();
                    listarDados();
                }
            });
            msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            msgBox.show();
        }

        public void voltar () {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        public void listarDados(){
            try {
                arrayIds = new ArrayList<>();
                bancoDados = openOrCreateDatabase("hospital", MODE_PRIVATE, null);
                Cursor meuCursor = bancoDados.rawQuery("SELECT id, nome FROM paciente", null);
                ArrayList<String> linhas = new ArrayList<String>();
                ArrayAdapter meuAdapter = new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        linhas
                );
                lista.setAdapter(meuAdapter);
                meuCursor.moveToFirst();
                while (meuCursor != null) {
                    linhas.add(meuCursor.getString(1));
                    arrayIds.add(meuCursor.getInt(0));
                    meuCursor.moveToNext();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        public void abrirTelaAlterar(){
            Intent intent = new Intent(this, MainActivity3.class);
            intent.putExtra("id",idSelecionado);
            startActivity(intent);
    }
    }

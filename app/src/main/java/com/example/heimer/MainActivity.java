package com.example.heimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.heimer.Modelo.Evento;
import com.example.heimer.Modelo.Local;
import com.example.heimer.database.EventoDAO;
import com.example.heimer.database.LocalDAO;
import com.example.heimer.database.entity.EventoEntity;
import com.example.heimer.database.entity.LocalEntity;

public class MainActivity extends AppCompatActivity {

    private ListView lvEventos;
    private ArrayAdapter<Evento> adapterEvento;
    private String ascDesc = "ASC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Próximos Eventos");
        lvEventos = findViewById(R.id.lvEvento);

        definirOnclickListenerListView();
    }

    private void definirOnclickListenerListView() {
        lvEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evento eventoClicado = adapterEvento.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastroEvento.class);
                intent.putExtra("eventoEdicao", eventoClicado);
                intent.putExtra("eventoExcluir", eventoClicado);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        EditText editTextBuscarNome = findViewById(R.id.editText_buscarNome);
        EditText editTextBuscarCidade = findViewById(R.id.editText_buscarCidade);

        super.onResume();
        EventoDAO eventoDAO = new EventoDAO(getBaseContext());
        eventoDAO.setSQL_LISTAR_TODOS("SELECT evento._id, nome, data, idlocal, endereco, bairro, cidade, capacidade FROM " +
                EventoEntity.TABLE_NAME + " INNER JOIN " + LocalEntity.TABLE_NAME + " ON " +
                EventoEntity.COLUMN_NAME_ID_LOCAL +
                " = " + LocalEntity.TABLE_NAME + "." + LocalEntity._ID + " WHERE nome LIKE \'%" + editTextBuscarNome.getText().toString() + "%\' " +
                "AND cidade LIKE \'%" + editTextBuscarCidade.getText().toString() + "%\' ORDER BY nome " + ascDesc);
        adapterEvento = new ArrayAdapter<Evento>(MainActivity.this, android.R.layout.simple_list_item_1, eventoDAO.listar());
        lvEventos.setAdapter(adapterEvento);
    }

    public void onClickCadastrarEvento(View v) {
        Intent intent = new Intent(MainActivity.this, CadastroEvento.class);
        startActivity(intent);
    }

    public void onClickListarLocais(View v) {
        Intent intent = new Intent(MainActivity.this, ListarLocal.class);
        startActivity(intent);
        finish();
    }

    public void onClickPesquisarNome(View v) {
        recreate();
    }

    public void onClickAscDesc(View v) {
        if (ascDesc.equals("ASC")) {
            ascDesc = "DESC";
        } else {
            ascDesc = "ASC";
        }
        onResume();
    }

}
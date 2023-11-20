package com.udesc.beermanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.*;

public class CreateBeerActivity extends AppCompatActivity {
    private CreateBeerDTO beer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_beer);

        //Necessário para acessar a API REST Spring que está rodando no localhost
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Remove a barra de navegação e a barra de notificação do android
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        String[] opcoes={"Sim","Não"};
        Spinner spin = (Spinner) findViewById(R.id.input_disponibilidade);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,opcoes);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
    }

    //Função para voltar
    public void take_me_back(View v) {
        finish();
    };

    public void create(View v) {
        beer = new CreateBeerDTO();
        EditText input_name = findViewById(R.id.input_name);
        beer.setName(input_name.getText().toString());
        EditText input_desc = findViewById(R.id.input_desc);
        beer.setDescription(input_desc.getText().toString());
        EditText input_alcool = findViewById(R.id.input_alcool);
        beer.setAlcoholContent(Double.valueOf(input_alcool.getText().toString()));
        EditText input_tipo = findViewById(R.id.input_tipo);
        beer.setType(input_tipo.getText().toString());
        EditText input_origem = findViewById(R.id.input_origem);
        beer.setOrigin(input_origem.getText().toString());
        EditText input_cervejaria = findViewById(R.id.input_cervejaria);
        beer.setBrewery(input_cervejaria.getText().toString());
        EditText input_preco = findViewById(R.id.input_preco);
        beer.setPrice(Double.valueOf(input_preco.getText().toString()));

        Spinner input_disponibilidade = findViewById(R.id.input_disponibilidade);
        if (input_disponibilidade.getSelectedItemId() == 0){
            beer.setAvailable(true);
        }else{
            beer.setAvailable(false);
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://192.168.0.111:8080/beer/")
                .post(
                        RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(beer))
                )
                .build();
        Call call = client.newCall(request);
        Response response = null;

        try {
            response = call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finish();
    };
}
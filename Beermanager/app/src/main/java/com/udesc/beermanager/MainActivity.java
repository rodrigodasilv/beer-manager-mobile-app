package com.udesc.beermanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.*;

public class MainActivity extends AppCompatActivity {
    private ListView LV;
    Gson gson;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> List;
    private ArrayList<BeerDTO> ListObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    @Override
    public void onResume(){
        super.onResume();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.0.111:8080/beer/")
                .build();
        Call call = client.newCall(request);
        Response response = null;

        try {
            response = call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        gson = new Gson();
        ArrayList<BeerDTO> cervejas;
        try {
            //Gambiarra pra pegar o tipo de array list de BeerDTOs
            Type beerListType = new TypeToken<List<BeerDTO>>() {}.getType();
            //Converte de JSON para array list de BeerDTOs
            cervejas = gson.fromJson(response.body().string(), beerListType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //LV é o listview
        LV = findViewById(R.id.bebidas);

        //Carrega o list com apenas os nomes
        List = new ArrayList<>();
        for (BeerDTO beer : cervejas) {
            List.add(beer.getName());
        }

        //Cria adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, List);
        //Seta adapter
        LV.setAdapter(adapter);

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent editBeer = new Intent(view.getContext(), BeerActivity.class);
                editBeer.putExtra("beer_data",gson.toJson(findBeerByName(cervejas,List.get(position))));
                startActivity(editBeer);

            }
        });
    };

    public static BeerDTO findBeerByName(ArrayList<BeerDTO> list, String targetName) {
        for (BeerDTO item : list) {
            if (item.getName().equals(targetName)) {
                return item;
            }
        }
        return null;
    }
    public void createNewBeer(View view){
        Intent createBeer = new Intent(view.getContext(), CreateBeerActivity.class);
        startActivity(createBeer);
    }
}
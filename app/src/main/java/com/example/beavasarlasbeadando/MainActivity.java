package com.example.beavasarlasbeadando;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText nev;
    private EditText egysegrar;
    private EditText mennyiseg;
    private EditText mertekegyseg;
    private Button hozzaadButton;
    private Button listaButton;
    private boolean isAllChecked;
    private List<Termekek> termekek;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

        hozzaadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllChecked = checkAll();

                if (isAllChecked){
                    Termekek newTermek = new Termekek(nev.getText().toString(),Integer.valueOf(egysegrar.getText().toString()), Float.valueOf(String.valueOf(mennyiseg.getText())),mertekegyseg.getText().toString());
                    termekek.add(newTermek);
                    createTermek(newTermek);
                }
            }
        });

        listaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean checkAll() {
        if (nev.length() == 0 || egysegrar.length() == 0 || mennyiseg.length() == 0 || mertekegyseg.length() == 0){
            Toast.makeText(this, "Ki kell tölteni minden mezőt", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void createTermek(Termekek termek) {
        RetrofitApiService apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);

        Call<Termekek> call = apiService.createTermek(termek);
        call.enqueue(new Callback<Termekek>() {
            @Override
            public void onResponse(Call<Termekek> call, Response<Termekek> response) {
                if (response.isSuccessful()) {
                    Termekek createdItem = response.body();

                    Log.d("API siker", "Létrehozva: " + createdItem.getNev());
                } else {
                    Log.e("API Error", "Nem sikerült. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Termekek> call, Throwable t) {
                Log.e("API Failure", t.getMessage());
            }
        });
    }


    private void init(){
        nev = findViewById(R.id.nevEditText);
        egysegrar = findViewById(R.id.egysegarEditText);
        mennyiseg = findViewById(R.id.mennyisegEditText);
        mertekegyseg = findViewById(R.id.mertekegysegEditText);
        hozzaadButton = findViewById(R.id.buttonHozzaad);
        listaButton = findViewById(R.id.buttonLista);
        termekek = new ArrayList<Termekek>();
    }
}
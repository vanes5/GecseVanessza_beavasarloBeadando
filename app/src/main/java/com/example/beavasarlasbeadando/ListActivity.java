package com.example.beavasarlasbeadando;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    private Button backButton;
    private RetrofitApiService apiService;
    private TermekekAdapter adapter;
    private ArrayList<Termekek> termekek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

        fetchProducts();

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ListActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Termekek clicked = termekek.get(i);
                Intent intent = new Intent(ListActivity.this, TermekActivity.class);
                intent.putExtra("productId", clicked.getId());
                startActivity(intent);
                finish();
            }
        });
    }

    private void init(){
        listView = findViewById(R.id.listView);
        backButton = findViewById(R.id.backButton);
        apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);
        termekek = new ArrayList<Termekek>();
        adapter = new TermekekAdapter(termekek,ListActivity.this);
        listView.setAdapter(adapter);
        apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);
    }

    private void fetchProducts() {
        apiService.getAllTermek().enqueue(new Callback<List<Termekek>>() {
            @Override
            public void onResponse(Call<List<Termekek>> call, Response<List<Termekek>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Termekek> productList = response.body();
                    listView.setAdapter(adapter);
                    Log.d("ListActivity", "Products fetched: " + productList.size());
                    termekek.clear();
                    termekek.addAll(productList);
                    adapter.notifyDataSetChanged();
                    Log.d("ListActivity", "Products added to adapter: " + termekek.size());
                } else {
                    Toast.makeText(ListActivity.this, "Failed to fetch products!", Toast.LENGTH_SHORT).show();
                    Log.e("ListActivity", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Termekek>> call, Throwable t) {
                Toast.makeText(ListActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                Log.e("ListActivity", "Error: " + t.getMessage());
            }
        });
    }
}

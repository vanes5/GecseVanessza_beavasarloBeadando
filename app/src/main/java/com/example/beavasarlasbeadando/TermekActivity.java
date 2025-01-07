package com.example.beavasarlasbeadando;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermekActivity extends AppCompatActivity {
    private EditText nevEditText;
    private EditText egysegArEditText;
    private EditText mennyisegEditText;
    private EditText mertekegysegEditText;
    private Button updateButton;
    private Button deleteButton;
    private Button backButton;
    private int productId;
    private RetrofitApiService apiService;
    private Termekek clickedTermek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_termek);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

        if (productId != -1) {
            fetchProductDetails(productId);
        }

        updateButton.setOnClickListener(v -> updateProduct());
        deleteButton.setOnClickListener(v -> deleteProduct());
        backButton.setOnClickListener(v -> goBack());
    }

    private void goBack() {
        Intent intent = new Intent(TermekActivity.this, ListActivity.class);
        startActivity(intent);
        finish();
    }

    private void deleteProduct() {
        new AlertDialog.Builder(TermekActivity.this)
                .setMessage("Biztosan törölni szeretné?")
                .setCancelable(false)
                .setPositiveButton("Igen", (dialog, id) -> {
                    apiService.deleteTermek(productId).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(TermekActivity.this, "Törölve", Toast.LENGTH_SHORT).show();
                                goBack();
                            } else {
                                Toast.makeText(TermekActivity.this, "Nem sikerült a törlés", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(TermekActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Nem", (dialog, id) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    private boolean checkAll() {
        if (nevEditText.getText().length() == 0 || egysegArEditText.getText().length() == 0 || mennyisegEditText.getText().length() == 0 || mertekegysegEditText.getText().length() == 0) {
            Toast.makeText(this, "Ki kell tölteni minden mezőt", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void updateProduct() {
        if (!checkAll()) {
            return; // Don't proceed if fields are empty
        }
        String nev = nevEditText.getText().toString();
        String mertekegyseg = mertekegysegEditText.getText().toString();
        int egysegAr;
        float mennyiseg;
        try {
            egysegAr = Integer.parseInt(egysegArEditText.getText().toString());
            mennyiseg = Float.parseFloat(mennyisegEditText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(TermekActivity.this, "Hibás számformátum", Toast.LENGTH_SHORT).show();
            return;
        }

        Termekek updatedTermek = new Termekek(nev, egysegAr, mennyiseg, mertekegyseg);
        updatedTermek.setId(productId);
        apiService.updateTermek(productId, updatedTermek).enqueue(new Callback<Termekek>() {
            @Override
            public void onResponse(Call<Termekek> call, Response<Termekek> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TermekActivity.this, "Törölve", Toast.LENGTH_SHORT).show();
                    goBack();
                } else {
                    Toast.makeText(TermekActivity.this, "Nem sikerült a törlés", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Termekek> call, Throwable t) {
                Toast.makeText(TermekActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

private void fetchProductDetails(int productId) {
    // Ensure apiService is initialized properly
    if (apiService != null) {
        apiService.getTermekById(productId).enqueue(new Callback<Termekek>() {
            @Override
            public void onResponse(Call<Termekek> call, Response<Termekek> response) {
                Log.d("API", "Request URL: " + call.request().url().toString());
                if (response.isSuccessful()) {
                    // Handle the successful response
                    Termekek termek = response.body();
                    if (termek != null) {
                        // Process the product
                        Log.d("API", "Product fetched successfully: " + termek.getNev());
                        // Use the data as required (e.g., update the UI, save data, etc.)
                    } else {
                        Log.e("API", "Response body is null");
                        Toast.makeText(getApplicationContext(), "No product data received", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle unsuccessful response
                    Log.e("API", "Request failed with status code: " + response.code());
                    try {
                        // Log the error body if possible
                        Log.e("API", "Error body: " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.e("API", "Failed to parse error body", e);
                    }
                    Toast.makeText(getApplicationContext(), "Failed to fetch product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Termekek> call, Throwable t) {
                // Handle failure of the network request
                Log.e("API", "Network request failed: " + t.getMessage(), t);
                Toast.makeText(getApplicationContext(), "Network error!", Toast.LENGTH_SHORT).show();
            }
        });
    } else {
        // Log if apiService is null
        Log.e("API", "API service is null. Check Retrofit setup.");
        Toast.makeText(getApplicationContext(), "API service is not initialized", Toast.LENGTH_SHORT).show();
    }
}
private void init(){
    nevEditText = findViewById(R.id.nevEditText);
    egysegArEditText = findViewById(R.id.egysegArEditText);
    mennyisegEditText = findViewById(R.id.mennyisegEditText);
    mertekegysegEditText = findViewById(R.id.mertekegysegEditText);
    updateButton = findViewById(R.id.updateButton);
    deleteButton = findViewById(R.id.deleteButton);
    backButton = findViewById(R.id.backButton);
    apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);
    productId = getIntent().getIntExtra("productId", -1);
}
}
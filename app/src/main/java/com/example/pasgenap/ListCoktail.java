package com.example.pasgenap;

import static com.example.pasgenap.MainActivity.EMAIL_KEY;
import static com.example.pasgenap.MainActivity.PASSWORD_KEY;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListCoktail extends AppCompatActivity implements ContactsAdapter.ContactsAdapterListener {
    RecyclerView rvdrinks;
    ArrayList<CoktailModel> listDataDrink;
    private ContactsAdapter adapterListMinuman;
    ProgressBar pbloading;
    private SharedPreferences sharedpreferences;

    public void getDrinksOnline() {
        String url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?f=a";
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("Success", "onResponse: " + jsonObject.toString());
                        try {
                            listDataDrink = new ArrayList<>();
                            JSONArray jsonArrayDrinks = jsonObject.getJSONArray("drinks");
                            for (int i = 0; i < jsonArrayDrinks.length(); i++) {
                                CoktailModel drink = new CoktailModel();
                                JSONObject jsonDrink = jsonArrayDrinks.getJSONObject(i);
                                drink.setDrinkName(jsonDrink.getString("strDrink"));
                                drink.setCategory(jsonDrink.getString("strCategory"));
                                drink.setStrDrinkThumb(jsonDrink.getString("strDrinkThumb"));
                                drink.setAlcoholic(jsonDrink.getString("strAlcoholic"));
                                drink.setGlass(jsonDrink.getString("strGlass"));
                                drink.setIngredient1(jsonDrink.getString("strIngredient1"));
                                drink.setIngredient2(jsonDrink.getString("strIngredient2"));
                                drink.setIngredient3(jsonDrink.getString("strIngredient3"));
                                drink.setIngredient4(jsonDrink.getString("strIngredient4"));
                                drink.setMeasure1(jsonDrink.getString("strMeasure1"));
                                drink.setMeasure2(jsonDrink.getString("strMeasure2"));
                                drink.setMeasure3(jsonDrink.getString("strMeasure3"));
                                drink.setMeasure4(jsonDrink.getString("strMeasure4"));
                                drink.setInstruction(jsonDrink.getString("strInstructions"));
                                listDataDrink.add(drink);
                            }
                            rvdrinks = findViewById(R.id.rvdrinks);
                            adapterListMinuman = new ContactsAdapter(getApplicationContext(), listDataDrink, ListCoktail.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rvdrinks.setLayoutManager(mLayoutManager);
                            rvdrinks.setAdapter(adapterListMinuman);

                            pbloading.setVisibility(View.GONE);
                            rvdrinks.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Failed", "onError: " + anError.toString());
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_coktail_layout);
        listDataDrink = new ArrayList<>();
        pbloading = findViewById(R.id.pbloading);
        getDrinksOnline();
    }


    @Override
    public void onItemLongClick(int position) {
        // Tampilkan dialog konfirmasi penghapusan
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi")
                .setMessage("Apakah kamu yakin ingin menghapusnya?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Hapus item dari RecyclerView
                        listDataDrink.remove(position);
                        adapterListMinuman.notifyItemRemoved(position);
                    }
                })
                .setNegativeButton("Tidak", null)
                .show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            logoutUser(); // Panggil method untuk proses logout
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        // Tambahkan kode untuk proses logout di sini
        // Misalnya, menghapus data sesi pengguna, menghapus token akses, atau mengarahkan pengguna kembali ke halaman login
        // Contoh:
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(EMAIL_KEY);
        editor.remove(PASSWORD_KEY);
        editor.apply();

        Intent intent = new Intent(ListCoktail.this, MainActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    public void onContactSelected(CoktailModel drink) {
        Intent intent = new Intent(ListCoktail.this, DetailCoktailPage.class);
        intent.putExtra("drink", drink);
        startActivity(intent);
    }
}

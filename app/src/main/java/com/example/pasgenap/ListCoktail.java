package com.example.pasgenap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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
    RecyclerView rvdrink;
    ArrayList<CoktailModel> listDataDrink;
    private ContactsAdapter adapterListMinuman;
    ProgressBar pbloadingteam;

    public void getEPLOnline() {
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
                            listDataDrink = new ArrayList<>(); // Inisialisasi objek listDataDrink
                            JSONArray jsonArrayEPLTeam = jsonObject.getJSONArray("drinks"); // Ubah "drink" menjadi "drinks"
                            for (int i = 0; i < jsonArrayEPLTeam.length(); i++) {
                                CoktailModel myDrink = new CoktailModel();
                                JSONObject jsonTeam = jsonArrayEPLTeam.getJSONObject(i);
                                myDrink.setDrinkName(jsonTeam.getString("strDrink"));
                                myDrink.setCategory(jsonTeam.getString("strCategory"));
                                myDrink.setStrDrinkThumb(jsonTeam.getString("strDrinkThumb"));
                                listDataDrink.add(myDrink);
                            }
                            rvdrink = findViewById(R.id.rvkontakname);
                            adapterListMinuman = new ContactsAdapter(getApplicationContext(), listDataDrink, ListCoktail.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rvdrink.setHasFixedSize(true);
                            rvdrink.setLayoutManager(mLayoutManager);
                            rvdrink.setAdapter(adapterListMinuman);
                            // untuk loading bar
                            pbloadingteam = findViewById(R.id.pbloading);
                            pbloadingteam.setVisibility(View.GONE);
                            rvdrink.setVisibility(View.VISIBLE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("failed ", "onError: " + anError.toString());
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_coktail_layout);
        listDataDrink = new ArrayList<>();
        getEPLOnline();
    }

    @Override
    public void onContactSelected(CoktailModel drink) {
        // Pindah ke halaman DetailPage dengan mengirimkan data minuman
        Intent intent = new Intent(ListCoktail.this, DetailCoktailPage.class);
        intent.putExtra("drink", (Parcelable) drink);
        startActivity(intent);
    }
}


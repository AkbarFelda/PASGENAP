package com.example.pasgenap;

import android.os.Bundle;
import android.util.Log;

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

public class ListCoktail extends AppCompatActivity implements ContactAdapter.ContactAdapterListener {
    RecyclerView rvdrink;
    ArrayList<CoktailModel> listDrink;
    private ContactAdapter adapterListKontak;

    public void getEPLOnline() {
        String url = "www.thecocktaildb.com/api/json/v1/1/search.php?f=a";
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("Success", "onResponse: " + jsonObject.toString());
                        try {
                            JSONArray jsonArrayEPLTeam = jsonObject.getJSONArray("teams");
                            for (int i = 0; i < jsonArrayEPLTeam.length(); i++) {
                                CoktailModel myDrink = new CoktailModel();
                                JSONObject jsonTeam = jsonArrayEPLTeam.getJSONObject(i);
                                myDrink.setDrinkName(jsonTeam.getString("strDrink"));
                                myDrink.setCategory(jsonTeam.getString("strCategory"));
                                myDrink.setGlass(jsonTeam.getString("strGlass"));
                                myDrink.setAlcoholic(jsonTeam.getString("strAlcoholic"));
                                myDrink.setStrDrinkThumb(jsonTeam.getString("strDrinkThumb"));
                                myDrink.setIngredient1(jsonTeam.getString("strIngredient1"));
                                myDrink.setInstruction(jsonTeam.getString("strInstruction"));
                                myDrink.setMeasure1(jsonTeam.getString("strMeasure1"));
                                listDrink.add(myDrink);
                            }
                            rvdrink = findViewById(R.id.rvdrink);
                            adapterListKontak = new ContactAdapter(getApplicationContext(), listDrink, ListCoktail.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rvdrink.setHasFixedSize(true);
                            rvdrink.setLayoutManager(mLayoutManager);
                            rvdrink.setAdapter(adapterListKontak);
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
        listDrink = new ArrayList<>();
        getEPLOnline();
    }

    @Override
    public void onContactSelected(CoktailModel contact) {
        // Implement your code when a contact is selected
    }
}

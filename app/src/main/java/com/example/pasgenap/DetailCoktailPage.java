package com.example.pasgenap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailCoktailPage extends AppCompatActivity {
    Intent i;
    CoktailModel coktailModel;
    TextView tvDrinkName, tvCategory, tvAlcoholic, tvGlass, tvInstructions, tvIngredient1, tvIngredient2,
            tvIngredient3, tvIngredient4, tvMeasure1, tvMeasure2, tvMeasure3, tvMeasure4;
    ImageView ivDrink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_coktail_page);

        i = getIntent();
        coktailModel = (CoktailModel) i.getParcelableExtra("drink");

        tvDrinkName = findViewById(R.id.tvDrinkName);
        tvCategory = findViewById(R.id.tvCategory);
        tvAlcoholic = findViewById(R.id.tvAlcoholic);
        tvGlass = findViewById(R.id.tvGlass);
        tvInstructions = findViewById(R.id.tvInstructions);
        ivDrink = findViewById(R.id.ivDrink);
        tvIngredient1 = findViewById(R.id.tvIngredient1);
        tvIngredient2 = findViewById(R.id.tvIngredient2);
        tvIngredient3 = findViewById(R.id.tvIngredient3);
        tvIngredient4 = findViewById(R.id.tvIngredient4);
        tvMeasure1 = findViewById(R.id.tvMeasure1);
        tvMeasure2 = findViewById(R.id.tvMeasure2);
        tvMeasure3 = findViewById(R.id.tvMeasure3);
        tvMeasure4 = findViewById(R.id.tvMeasure4);



        // Set the data to the views
        tvDrinkName.setText(coktailModel.getDrinkName());
        tvCategory.setText(coktailModel.getCategory());
        tvAlcoholic.setText(coktailModel.getAlcoholic());
        tvGlass.setText(coktailModel.getGlass());
        tvInstructions.setText(coktailModel.getInstruction());
        tvIngredient1.setText(coktailModel.getIngredient1());
        tvIngredient2.setText(coktailModel.getIngredient2());
        tvIngredient3.setText(coktailModel.getIngredient3());
        tvIngredient4.setText(coktailModel.getIngredient4());
        tvMeasure1.setText(coktailModel.getMeasure1());
        tvMeasure2.setText(coktailModel.getMeasure2());
        tvMeasure3.setText(coktailModel.getMeasure3());
        tvMeasure4.setText(coktailModel.getMeasure4());


        // Load the image using a library like Glide or Picasso
        // Here, assuming you are using Glide library
        Glide.with(this)
                .load(coktailModel.getStrDrinkThumb())
                .into(ivDrink);
    }
}

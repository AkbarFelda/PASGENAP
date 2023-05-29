package com.example.pasgenap;

import android.os.Parcel;
import android.os.Parcelable;

public class CoktailModel implements Parcelable {
    private String drinkName;
    private String category;
    private String strDrinkThumb;
    private String alcoholic;
    private String glass;
    private String instruction;
    private String ingredient1;
    private String ingredient2;
    private String ingredient3;
    private String ingredient4;
    private String measure1;
    private String measure2;
    private String measure3;
    private String measure4;

    public CoktailModel() {
        // Default constructor required for Parcelable
    }

    protected CoktailModel(Parcel in) {
        drinkName = in.readString();
        category = in.readString();
        strDrinkThumb = in.readString();
        alcoholic = in.readString();
        glass = in.readString();
        instruction = in.readString();
        ingredient1 = in.readString();
        ingredient2 = in.readString();
        ingredient3 = in.readString();
        ingredient4 = in.readString();
        measure1 = in.readString();
        measure2 = in.readString();
        measure3 = in.readString();
        measure4 = in.readString();
    }

    public static final Creator<CoktailModel> CREATOR = new Creator<CoktailModel>() {
        @Override
        public CoktailModel createFromParcel(Parcel in) {
            return new CoktailModel(in);
        }

        @Override
        public CoktailModel[] newArray(int size) {
            return new CoktailModel[size];
        }
    };

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStrDrinkThumb() {
        return strDrinkThumb;
    }

    public void setStrDrinkThumb(String strDrinkThumb) {
        this.strDrinkThumb = strDrinkThumb;
    }

    public String getAlcoholic() {
        return alcoholic;
    }

    public void setAlcoholic(String alcoholic) {
        this.alcoholic = alcoholic;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getIngredient1() {
        return ingredient1;
    }

    public void setIngredient1(String ingredient1) {
        this.ingredient1 = ingredient1;
    }

    public String getIngredient2() {
        return ingredient2;
    }

    public void setIngredient2(String ingredient2) {
        this.ingredient2 = ingredient2;
    }

    public String getIngredient3() {
        return ingredient3;
    }

    public void setIngredient3(String ingredient3) {
        this.ingredient3 = ingredient3;
    }

    public String getIngredient4() {
        return ingredient4;
    }

    public void setIngredient4(String ingredient4) {
        this.ingredient4 = ingredient4;
    }

    public String getMeasure1() {
        return measure1;
    }

    public void setMeasure1(String measure1) {
        this.measure1 = measure1;
    }

    public String getMeasure2() {
        return measure2;
    }

    public void setMeasure2(String measure2) {
        this.measure2 = measure2;
    }

    public String getMeasure3() {
        return measure3;
    }

    public void setMeasure3(String measure3) {
        this.measure3 = measure3;
    }

    public String getMeasure4() {
        return measure4;
    }

    public void setMeasure4(String measure4) {
        this.measure4 = measure4;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(drinkName);
        parcel.writeString(category);
        parcel.writeString(strDrinkThumb);
        parcel.writeString(alcoholic);
        parcel.writeString(glass);
        parcel.writeString(instruction);
        parcel.writeString(ingredient1);
        parcel.writeString(ingredient2);
        parcel.writeString(ingredient3);
        parcel.writeString(ingredient4);
        parcel.writeString(measure1);
        parcel.writeString(measure2);
        parcel.writeString(measure3);
        parcel.writeString(measure4);
    }
}

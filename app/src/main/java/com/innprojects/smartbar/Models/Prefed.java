package com.innprojects.smartbar.Models;

import java.io.Serializable;

/**
 * Created by simransarin on 29/10/17.
 */

public class Prefed implements Serializable {

    String name;
    String ingredient_name_1;
    String bottle_name_1;
    String quantity_1;
    String cost_per_ml_1;
    String ingredient_name_2;
    String bottle_name_2;
    String quantity_2;
    String cost_per_ml_2;
    String ingredient_name_3;
    String bottle_name_3;
    String quantity_3;
    String cost_per_ml_3;
    String ingredient_name_4;
    String bottle_name_4;
    String quantity_4;
    String cost_per_ml_4;
    String ingredient_name_5;
    String bottle_name_5;
    String quantity_5;
    String cost_per_ml_5;
    String ingredient_name_6;
    String bottle_name_6;
    String quantity_6;
    String cost_per_ml_6;
    String ingredient_name_7;
    String bottle_name_7;
    String quantity_7;
    String cost_per_ml_7;
    String ingredient_name_8;
    String bottle_name_8;
    String quantity_8;
    String cost_per_ml_8;
    String colour;
    String encoded_string;

    public Prefed(String name, String colour, String encoded_string) {
        this.name = name;
        this.colour = colour;
        this.encoded_string = encoded_string;
    }

    public Prefed(String name, String ingredient_name_1, String bottle_name_1, String quantity_1, String cost_per_ml_1, String ingredient_name_2, String bottle_name_2, String quantity_2, String cost_per_ml_2, String ingredient_name_3, String bottle_name_3, String quantity_3, String cost_per_ml_3, String ingredient_name_4, String bottle_name_4, String quantity_4, String cost_per_ml_4, String ingredient_name_5, String bottle_name_5, String quantity_5, String cost_per_ml_5, String ingredient_name_6, String bottle_name_6, String quantity_6, String cost_per_ml_6, String ingredient_name_7, String bottle_name_7, String quantity_7, String cost_per_ml_7, String ingredient_name_8, String bottle_name_8, String quantity_8, String cost_per_ml_8, String colour, String encoded_string) {
        this.name = name;
        this.ingredient_name_1 = ingredient_name_1;
        this.bottle_name_1 = bottle_name_1;
        this.quantity_1 = quantity_1;
        this.cost_per_ml_1 = cost_per_ml_1;
        this.ingredient_name_2 = ingredient_name_2;
        this.bottle_name_2 = bottle_name_2;
        this.quantity_2 = quantity_2;
        this.cost_per_ml_2 = cost_per_ml_2;
        this.ingredient_name_3 = ingredient_name_3;
        this.bottle_name_3 = bottle_name_3;
        this.quantity_3 = quantity_3;
        this.cost_per_ml_3 = cost_per_ml_3;
        this.ingredient_name_4 = ingredient_name_4;
        this.bottle_name_4 = bottle_name_4;
        this.quantity_4 = quantity_4;
        this.cost_per_ml_4 = cost_per_ml_4;
        this.ingredient_name_5 = ingredient_name_5;
        this.bottle_name_5 = bottle_name_5;
        this.quantity_5 = quantity_5;
        this.cost_per_ml_5 = cost_per_ml_5;
        this.ingredient_name_6 = ingredient_name_6;
        this.bottle_name_6 = bottle_name_6;
        this.quantity_6 = quantity_6;
        this.cost_per_ml_6 = cost_per_ml_6;
        this.ingredient_name_7 = ingredient_name_7;
        this.bottle_name_7 = bottle_name_7;
        this.quantity_7 = quantity_7;
        this.cost_per_ml_7 = cost_per_ml_7;
        this.ingredient_name_8 = ingredient_name_8;
        this.bottle_name_8 = bottle_name_8;
        this.quantity_8 = quantity_8;
        this.cost_per_ml_8 = cost_per_ml_8;
        this.colour = colour;
        this.encoded_string = encoded_string;
    }

    public String getCost_per_ml_1() {
        return cost_per_ml_1;
    }

    public void setCost_per_ml_1(String cost_per_ml_1) {
        this.cost_per_ml_1 = cost_per_ml_1;
    }

    public String getCost_per_ml_2() {
        return cost_per_ml_2;
    }

    public void setCost_per_ml_2(String cost_per_ml_2) {
        this.cost_per_ml_2 = cost_per_ml_2;
    }

    public String getCost_per_ml_3() {
        return cost_per_ml_3;
    }

    public void setCost_per_ml_3(String cost_per_ml_3) {
        this.cost_per_ml_3 = cost_per_ml_3;
    }

    public String getCost_per_ml_4() {
        return cost_per_ml_4;
    }

    public void setCost_per_ml_4(String cost_per_ml_4) {
        this.cost_per_ml_4 = cost_per_ml_4;
    }

    public String getCost_per_ml_5() {
        return cost_per_ml_5;
    }

    public void setCost_per_ml_5(String cost_per_ml_5) {
        this.cost_per_ml_5 = cost_per_ml_5;
    }

    public String getCost_per_ml_6() {
        return cost_per_ml_6;
    }

    public void setCost_per_ml_6(String cost_per_ml_6) {
        this.cost_per_ml_6 = cost_per_ml_6;
    }

    public String getCost_per_ml_7() {
        return cost_per_ml_7;
    }

    public void setCost_per_ml_7(String cost_per_ml_7) {
        this.cost_per_ml_7 = cost_per_ml_7;
    }

    public String getCost_per_ml_8() {
        return cost_per_ml_8;
    }

    public void setCost_per_ml_8(String cost_per_ml_8) {
        this.cost_per_ml_8 = cost_per_ml_8;
    }

    public String getEncoded_string() {
        return encoded_string;
    }

    public void setEncoded_string(String encoded_string) {
        this.encoded_string = encoded_string;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredient_name_1() {
        return ingredient_name_1;
    }

    public void setIngredient_name_1(String ingredient_name_1) {
        this.ingredient_name_1 = ingredient_name_1;
    }

    public String getBottle_name_1() {
        return bottle_name_1;
    }

    public void setBottle_name_1(String bottle_name_1) {
        this.bottle_name_1 = bottle_name_1;
    }

    public String getQuantity_1() {
        return quantity_1;
    }

    public void setQuantity_1(String quantity_1) {
        this.quantity_1 = quantity_1;
    }

    public String getIngredient_name_2() {
        return ingredient_name_2;
    }

    public void setIngredient_name_2(String ingredient_name_2) {
        this.ingredient_name_2 = ingredient_name_2;
    }

    public String getBottle_name_2() {
        return bottle_name_2;
    }

    public void setBottle_name_2(String bottle_name_2) {
        this.bottle_name_2 = bottle_name_2;
    }

    public String getQuantity_2() {
        return quantity_2;
    }

    public void setQuantity_2(String quantity_2) {
        this.quantity_2 = quantity_2;
    }

    public String getIngredient_name_3() {
        return ingredient_name_3;
    }

    public void setIngredient_name_3(String ingredient_name_3) {
        this.ingredient_name_3 = ingredient_name_3;
    }

    public String getBottle_name_3() {
        return bottle_name_3;
    }

    public void setBottle_name_3(String bottle_name_3) {
        this.bottle_name_3 = bottle_name_3;
    }

    public String getQuantity_3() {
        return quantity_3;
    }

    public void setQuantity_3(String quantity_3) {
        this.quantity_3 = quantity_3;
    }

    public String getIngredient_name_4() {
        return ingredient_name_4;
    }

    public void setIngredient_name_4(String ingredient_name_4) {
        this.ingredient_name_4 = ingredient_name_4;
    }

    public String getBottle_name_4() {
        return bottle_name_4;
    }

    public void setBottle_name_4(String bottle_name_4) {
        this.bottle_name_4 = bottle_name_4;
    }

    public String getQuantity_4() {
        return quantity_4;
    }

    public void setQuantity_4(String quantity_4) {
        this.quantity_4 = quantity_4;
    }

    public String getIngredient_name_5() {
        return ingredient_name_5;
    }

    public void setIngredient_name_5(String ingredient_name_5) {
        this.ingredient_name_5 = ingredient_name_5;
    }

    public String getBottle_name_5() {
        return bottle_name_5;
    }

    public void setBottle_name_5(String bottle_name_5) {
        this.bottle_name_5 = bottle_name_5;
    }

    public String getQuantity_5() {
        return quantity_5;
    }

    public void setQuantity_5(String quantity_5) {
        this.quantity_5 = quantity_5;
    }

    public String getIngredient_name_6() {
        return ingredient_name_6;
    }

    public void setIngredient_name_6(String ingredient_name_6) {
        this.ingredient_name_6 = ingredient_name_6;
    }

    public String getBottle_name_6() {
        return bottle_name_6;
    }

    public void setBottle_name_6(String bottle_name_6) {
        this.bottle_name_6 = bottle_name_6;
    }

    public String getQuantity_6() {
        return quantity_6;
    }

    public void setQuantity_6(String quantity_6) {
        this.quantity_6 = quantity_6;
    }

    public String getIngredient_name_7() {
        return ingredient_name_7;
    }

    public void setIngredient_name_7(String ingredient_name_7) {
        this.ingredient_name_7 = ingredient_name_7;
    }

    public String getBottle_name_7() {
        return bottle_name_7;
    }

    public void setBottle_name_7(String bottle_name_7) {
        this.bottle_name_7 = bottle_name_7;
    }

    public String getQuantity_7() {
        return quantity_7;
    }

    public void setQuantity_7(String quantity_7) {
        this.quantity_7 = quantity_7;
    }

    public String getIngredient_name_8() {
        return ingredient_name_8;
    }

    public void setIngredient_name_8(String ingredient_name_8) {
        this.ingredient_name_8 = ingredient_name_8;
    }

    public String getBottle_name_8() {
        return bottle_name_8;
    }

    public void setBottle_name_8(String bottle_name_8) {
        this.bottle_name_8 = bottle_name_8;
    }

    public String getQuantity_8() {
        return quantity_8;
    }

    public void setQuantity_8(String quantity_8) {
        this.quantity_8 = quantity_8;
    }
}

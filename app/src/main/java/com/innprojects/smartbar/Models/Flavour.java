package com.innprojects.smartbar.Models;

import java.io.Serializable;

/**
 * Created by simransarin on 05/10/17.
 */

public class Flavour implements Serializable {
    String ingredient_name;
    String quantity;
    String id;
    String machineId;
    String cost;
    String bottle;
    String quantity_selected;

    public Flavour(String ingredient_name, String quantity, String id, String machineId, String cost, String bottle, String quantity_selected) {
        this.ingredient_name = ingredient_name;
        this.quantity = quantity;
        this.id = id;
        this.machineId = machineId;
        this.cost = cost;
        this.bottle = bottle;
        this.quantity_selected = quantity_selected;
    }

    public String getQuantity_selected() {
        return quantity_selected;
    }

    public void setQuantity_selected(String quantity_selected) {
        this.quantity_selected = quantity_selected;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getBottle() {
        return bottle;
    }

    public void setBottle(String bottle) {
        this.bottle = bottle;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public void setIngredient_name(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

}

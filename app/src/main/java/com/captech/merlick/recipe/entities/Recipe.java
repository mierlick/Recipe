package com.captech.merlick.recipe.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@SuppressWarnings("serial")
@DatabaseTable(tableName = "recipes")
public class Recipe implements Serializable {

    @DatabaseField(columnName = "_id", canBeNull = false, generatedId = true)
    private int id;

    @DatabaseField(columnName = "name", canBeNull = true)
    private String name;

    @DatabaseField(columnName = "minutes", canBeNull = true)
    private int minutes;

    @DatabaseField(columnName = "category", canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Category category;

    @DatabaseField(columnName = "ingredients", canBeNull = true)
    private String ingredients;

    @DatabaseField(columnName = "instructions", canBeNull = true)
    private String instructions;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return name;
    }

    public String toFormattedString() {
        return "id: " + id +
                "\nname: " + name +
                "\nminutes: " + minutes +
                "\ncategory: " + category +
                "\ningredients: " + ingredients +
                "\ninstructions: " + instructions;
    }
}

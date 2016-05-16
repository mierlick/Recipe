package com.captech.merlick.recipe.entities;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@SuppressWarnings("serial")
@DatabaseTable(tableName = "Categories")
public class Category implements Serializable {
	
	@DatabaseField(columnName = "_id", canBeNull = false, generatedId = true)
	private int id;
	
	@DatabaseField(columnName = "category", canBeNull = false)
	private String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

	public String toFormattedString () {
		return id + ": " + category;
	}

    @Override
    public String toString() {
        return category;
    }
}

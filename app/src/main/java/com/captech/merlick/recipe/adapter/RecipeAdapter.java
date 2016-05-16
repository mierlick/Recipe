package com.captech.merlick.recipe.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.captech.merlick.recipe.R;
import com.captech.merlick.recipe.entities.Recipe;

import java.util.List;

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    private Context context;
    private int layoutResourceId;
    private List<Recipe> recipes;

    public RecipeAdapter(Context context, int layoutResourceId, List<Recipe> recipes) {
        super(context, layoutResourceId, recipes);

        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.recipes = recipes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecipeHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecipeHolder();
            holder.nameTextView = (TextView)row.findViewById(R.id.itemName);
            holder.timeTextView = (TextView)row.findViewById(R.id.itemTime);

            row.setTag(holder);
        }
        else
        {
            holder = (RecipeHolder)row.getTag();
        }

        Recipe recipe = recipes.get(position);
        holder.nameTextView.setText(recipe.getName());
        holder.timeTextView.setText(recipe.getMinutes() + " Minutes");

        return row;
    }



    public static class RecipeHolder {
        TextView nameTextView;
        TextView timeTextView;
    }
}

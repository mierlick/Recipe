package com.captech.merlick.recipe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.captech.merlick.recipe.db.CategoryDAO;
import com.captech.merlick.recipe.db.RecipeDAO;
import com.captech.merlick.recipe.entities.Category;
import com.captech.merlick.recipe.entities.Recipe;

import java.util.ArrayList;
import java.util.List;

public class CreateRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);

        }

        setContentView(R.layout.create_recipe);
        populateCategoryList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();

//        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_create) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void populateCategoryList() {
        Spinner categorySpinner = (Spinner) findViewById(R.id.editCategory);

        CategoryDAO categoryDAO = new CategoryDAO(this);

        Category[] results = categoryDAO.categorySearch();

        ArrayAdapter<Category> adapter =
                new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1, android.R.id.text1, results);
        categorySpinner.setAdapter(adapter);
    }

    public void cancel(View view) {
        finish();
    }

    public void createRecipe(View view) {
        EditText nameEditText = (EditText)findViewById(R.id.editName);
        Spinner categorySpinner = (Spinner)findViewById(R.id.editCategory);
        EditText totalTimeEditText = (EditText)findViewById(R.id.editTotalTime);
        EditText ingredientsEditText = (EditText)findViewById(R.id.editIngredients);
        EditText instructionsEditText = (EditText)findViewById(R.id.editInstructions);

        List<String> invalidFields = new ArrayList<String>();

        Recipe recipe = new Recipe();

        if (nameEditText.getText() != null && !nameEditText.getText().toString().isEmpty()) {
            recipe.setName(nameEditText.getText().toString());
        } else {
            invalidFields.add("Name");
        }

        Category category = (Category)categorySpinner.getSelectedItem();
        recipe.setCategory(category);

        if (totalTimeEditText.getText() != null && !totalTimeEditText.getText().toString().isEmpty()) {
            int totalTime = Integer.parseInt(totalTimeEditText.getText().toString());
            recipe.setMinutes(totalTime);
        } else {
            invalidFields.add("Minutes");
        }

        if (ingredientsEditText.getText() != null && !ingredientsEditText.getText().toString().isEmpty()) {
            recipe.setIngredients(ingredientsEditText.getText().toString());
        } else {
            invalidFields.add("Ingredients");
        }

        if (instructionsEditText.getText() != null && !instructionsEditText.getText().toString().isEmpty()) {
            recipe.setInstructions(instructionsEditText.getText().toString());
        } else {
            invalidFields.add("Instructions");
        }

        if (invalidFields.isEmpty()) {
            RecipeDAO recipeDAO = new RecipeDAO(this);

            recipeDAO.createRecipe(recipe);

            Intent intent = new Intent(this, RecipeViewActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("recipe", recipe);
            startActivity(intent);
        } else {
            showAlert(invalidFields.toString());
        }

    }

    private void showAlert(String fieldName) {
        new AlertDialog.Builder(this)
                .setTitle("Invaild Recipe")
                .setMessage(String.format("Required Field(s) %s is(are) null or empty.", fieldName))
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}

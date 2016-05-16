package com.captech.merlick.recipe;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.captech.merlick.recipe.db.RecipeDAO;
import com.captech.merlick.recipe.entities.Recipe;

public class RecipeViewActivity extends BaseRecipeActivity implements RecipeDialogFragment.RecipeFragmentListener {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        } else {

        }

        setContentView(R.layout.recipe);
        Recipe recipe = (Recipe) getIntent().getExtras().getSerializable("recipe");
        populateSearchResults(recipe);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.recipe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                navigateUp();
                return true;
            case R.id.action_delete:
                deleteRecipe(recipe);
                break;
            case R.id.action_create:
                createRecord();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void dialogButtonClick(int buttonId) {
        switch (buttonId){
            case R.id.yesButton:
                RecipeDAO recipeDAO = new RecipeDAO(this);
                recipeDAO.deleteRecipe(recipe);
                navigateUp();
                break;
            case R.id.noButton:
                break;
        }

    }

    private void populateSearchResults(Recipe recipe) {
        this.recipe = recipe;

        TextView nameTextView = (TextView) findViewById(R.id.name);
        nameTextView.setText(recipe.getName());

        TextView categoryTextView = (TextView) findViewById(R.id.category);
        categoryTextView.setText(recipe.getCategory().toString());

        TextView totalTimeTextView = (TextView) findViewById(R.id.totalTime);
        totalTimeTextView.setText(Integer.toString(recipe.getMinutes()));

        TextView ingredientsTextView = (TextView) findViewById(R.id.ingredients);
        ingredientsTextView.setText(recipe.getIngredients());

        TextView instructionTextView = (TextView) findViewById(R.id.instructions);
        instructionTextView.setText(recipe.getInstructions());
    }

    private void deleteRecipe(final Recipe recipe) {
        if (recipe != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeDialogFragment recipeDialogFragment = new RecipeDialogFragment();
            recipeDialogFragment.show(fragmentManager, "Confirm");
        }
    }

    private void navigateUp() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        upIntent.putExtra("category", recipe.getCategory());
        TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent)
                .startActivities();
    }
}

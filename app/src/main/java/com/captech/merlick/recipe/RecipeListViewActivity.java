package com.captech.merlick.recipe;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.captech.merlick.recipe.adapter.RecipeAdapter;
import com.captech.merlick.recipe.db.RecipeDAO;
import com.captech.merlick.recipe.entities.Category;
import com.captech.merlick.recipe.entities.Recipe;
import com.captech.merlick.recipe.intent.RecipeViewIntent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeListViewActivity extends BaseRecipeActivity {

    private List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recipe_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        } else {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Category category = (Category) getIntent().getExtras().getSerializable("category");
        populateSearchResults(category);

        ListView listView = (ListView) findViewById(R.id.recipeList);
        listView.setOnItemClickListener(new RecipeViewIntent());

        registerForContextMenu(listView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_create:
                createRecord();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(menu, view, contextMenuInfo);

        getMenuInflater().inflate(R.menu.recipe_list_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_delete:
                if (recipes != null && !recipes.isEmpty()) {
                    Category category = recipes.get(info.position).getCategory();
                    deleteRecipe(recipes.get(info.position));
                    populateSearchResults(category);
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void populateSearchResults(Category category) {
        ListView listView = (ListView) findViewById(R.id.recipeList);

        RecipeDAO recipeDAO = new RecipeDAO(this);

        recipes = recipeDAO.recipeSearchByCategoryId(category.getId());

        ArrayList<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for (Recipe recipe : recipes) {
            Map<String,Object> item = new HashMap<String, Object>();
            item.put("name", recipe.getName());
            item.put("time", "Total Time: " + recipe.getMinutes());
            item.put("recipe", recipe);
            list.add(item);
        }
        RecipeAdapter recipeAdapter = new RecipeAdapter(this, R.layout.recipe_item, recipes);

        listView.setAdapter(recipeAdapter);

        toggleEmptyView(R.id.recipeList, R.id.no_results);

    }

    private void deleteRecipe(final Recipe recipe) {
        if (recipe != null) {
            RecipeDAO recipeDAO = new RecipeDAO(this);
            recipeDAO.deleteRecipe(recipe);
        }
    }
}
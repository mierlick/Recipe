package com.captech.merlick.recipe;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.captech.merlick.recipe.db.CategoryDAO;
import com.captech.merlick.recipe.entities.Category;
import com.captech.merlick.recipe.intent.RecipeListViewIntent;


public class CategoryListViewActivity extends BaseRecipeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);

        }

        setContentView(R.layout.category_list);
        populateSearchResults();

        ListView listView = (ListView) findViewById(R.id.categoryList);
        listView.setOnItemClickListener(new RecipeListViewIntent());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();

        getMenuInflater().inflate(R.menu.category_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:
                createRecord();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void populateSearchResults() {
        ListView listView = (ListView) findViewById(R.id.categoryList);

        CategoryDAO categoryDAO = new CategoryDAO(this);

        Category[] results = categoryDAO.categorySearch();

        ArrayAdapter<Category> adapter =
                new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1, android.R.id.text1, results);
        listView.setAdapter(adapter);

        toggleEmptyView(R.id.categoryList, R.id.no_results);

    }
}

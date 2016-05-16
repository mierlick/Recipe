package com.captech.merlick.recipe.db;

import android.content.Context;

import com.captech.merlick.recipe.entities.Recipe;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RecipeDAO {
    private MySQLiteHelper dbHelper;

    public RecipeDAO (Context context) {
        dbHelper = new MySQLiteHelper(context);
        try {
            dbHelper.createDatabase();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public List<Recipe> recipeSearchByCategoryId(int categoryId) {
        List<Recipe> recipes = null;

        try {
            open();

            Dao<Recipe, Integer> recipeDAO = dbHelper.getRecipeDAO();
            QueryBuilder<Recipe, Integer> queryBuilder = recipeDAO.queryBuilder();

            queryBuilder.where().eq("category", categoryId);

            PreparedQuery<Recipe> preparedQuery = queryBuilder.prepare();

            recipes = recipeDAO.query(preparedQuery);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            close();
        }

        return recipes;
    }

    public Recipe getRecipeById(int recipeId) {
        Recipe recipe = null;

        try {
            open();

            Dao<Recipe, Integer> recipeDAO = dbHelper.getRecipeDAO();

            recipe = recipeDAO.queryForId(recipeId);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            close();
        }

        return recipe;
    }

    public void deleteRecipe(Recipe recipe) {
        try {
            open();

            Dao<Recipe, Integer> recipeDao = dbHelper.getRecipeDAO();

            recipeDao.delete(recipe);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void createRecipe(Recipe recipe) {
        try {
            open();

            Dao<Recipe, Integer> recipeDao = dbHelper.getRecipeDAO();

            recipeDao.create(recipe);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }finally {
            close();
        }
    }

    /**
     * Open Database
     */
    public void open() {
        dbHelper.getWritableDatabase();
    }

    /**
     * Close Database
     */
    public void close() {
        dbHelper.close();
    }
}

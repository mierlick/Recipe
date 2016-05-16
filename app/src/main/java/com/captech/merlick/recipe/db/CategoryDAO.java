package com.captech.merlick.recipe.db;

import android.content.Context;

import com.captech.merlick.recipe.entities.Category;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CategoryDAO {
    private MySQLiteHelper dbHelper;

    public CategoryDAO(Context context) {
        dbHelper = new MySQLiteHelper(context);
        try {
            dbHelper.createDatabase();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public Category[] categorySearch() {
        List<Category> categories = null;

        try {
            open();
            Dao<Category, Integer> categoryDAO = dbHelper.getCategoryDAO();
            QueryBuilder<Category, Integer> queryBuilder = categoryDAO.queryBuilder();

            PreparedQuery<Category> preparedQuery = queryBuilder.prepare();

            categories = categoryDAO.query(preparedQuery);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            close();
        }

        Category[] array = null;

        if (categories != null) {
            array = new Category[categories.size()];
            categories.toArray(array);
        }

        return array;
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

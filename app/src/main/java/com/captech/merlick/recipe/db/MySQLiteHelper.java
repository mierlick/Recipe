package com.captech.merlick.recipe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.captech.merlick.recipe.entities.Category;
import com.captech.merlick.recipe.entities.Recipe;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class MySQLiteHelper extends OrmLiteSqliteOpenHelper {

	private static String DB_PATH = "/data/data/com.captech.merlick.recipe/databases/";
	private static final String DATABASE_NAME = "recipe.db";
	private static final int DATABASE_VERSION = 1;
    private final Context myContext;
    private ConnectionSource connectionSource;
	
    private Dao<Category, Integer> categoryDAO;
	private Dao<Recipe, Integer> recipeDAO;
    
	public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
		myContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		this.connectionSource = connectionSource;
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		this.connectionSource = connectionSource;
	}
	
	public void createDatabase () throws FileNotFoundException, IOException {
		if(checkDataBase()){
    		//do nothing - database already exist
    	} else {
    		this.getReadableDatabase();
        	try { 
        		copyDataBase();
        	} catch (FileNotFoundException fnfe) {
        		try {
					TableUtils.createTable(connectionSource, Recipe.class);
					TableUtils.createTable(connectionSource, Category.class);
				} catch (SQLException e) {
					
				}
        	}
    	}
	}
	
	/**
	 * Checks to see if the Database already exists on the device.
	 * @return
	 */
	private boolean checkDataBase(){
    	SQLiteDatabase checkDB = null;
    	try{
    		String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS|SQLiteDatabase.OPEN_READONLY);
    	}catch(SQLiteException e){
    		//database does't exist yet.
    	}

    	if(checkDB != null){
    		checkDB.close();
    	}
    	return checkDB != null ? true : false;
    }

    public Dao<Category, Integer> getCategoryDAO() throws SQLException {
        if (categoryDAO == null) {
            categoryDAO = getDao(Category.class);
        }
        return categoryDAO;
    }

    public Dao<Recipe, Integer> getRecipeDAO() throws SQLException {
        if(recipeDAO == null) {
            recipeDAO = getDao(Recipe.class);
        }
        return recipeDAO;
    }

    /**
	 * Copies the database from the APK to the device.
	 * @throws IOException
	 */
	private void copyDataBase() throws FileNotFoundException, IOException {
		
		String outFileName = DB_PATH + DATABASE_NAME;
    	InputStream inputStream = null;
    	OutputStream outputStream = null;
    	
		try {
			inputStream = myContext.getAssets().open(DATABASE_NAME);
			outputStream = new FileOutputStream(outFileName);
			byte[] buffer = new byte[1024];
	    	int length;
	    	while ((length = inputStream.read(buffer))>0){
	    		outputStream.write(buffer, 0, length);
	    	}
			
		} finally {
			if (outputStream != null ) {
				outputStream.flush();
				outputStream.close();
			}

			if (inputStream != null ) {
				inputStream.close();
			}
		}
    }

}
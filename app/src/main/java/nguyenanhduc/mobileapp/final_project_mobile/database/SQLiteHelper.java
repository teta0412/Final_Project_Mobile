package nguyenanhduc.mobileapp.final_project_mobile.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import nguyenanhduc.mobileapp.final_project_mobile.dao.CommunityChestCardDao;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "monopoly.db";
    private static final int DATABASE_VERSION = 1;

    private static SQLiteHelper instance;

    private SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized SQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SQLiteHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Community Chest table
        db.execSQL(CommunityChestCardDao.CREATE_TABLE);

        // Initialize with default data
        CommunityChestCardDao.initializeDefaultCards(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CommunityChestCardDao.TABLE_NAME);
        onCreate(db);
    }
}
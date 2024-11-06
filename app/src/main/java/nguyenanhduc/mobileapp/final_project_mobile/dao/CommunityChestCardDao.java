package nguyenanhduc.mobileapp.final_project_mobile.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import nguyenanhduc.mobileapp.final_project_mobile.database.SQLiteHelper;
import nguyenanhduc.mobileapp.final_project_mobile.model.CommunityChestCard;
import nguyenanhduc.mobileapp.final_project_mobile.model.CommunityChest;

public class CommunityChestCardDao {
    public static final String TABLE_NAME = "community_chest_cards";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DETAILS = "details";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_DETAILS + " TEXT NOT NULL" +
                    ")";

   private SQLiteHelper dbHelper;

    public CommunityChestCardDao(SQLiteHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Initialize default cards
    public static void initializeDefaultCards(SQLiteDatabase db) {
        String[][] cardsData = {
                {"0", "From Sale of Stock.You get $50"},
                {"1", "Pay your Insurance Premium $50"},
                {"2", "Bank Error in your Favour.Collect $200"},
                {"3", "Pay a $10 Fine or Take a Chance Card"},
                {"4", "Go Back to Old Kent Road"},
                {"5", "Go to Jail.Move Directly to Jail.\nDo not Pass 'GO'.Do not Collect $200"},
                {"6", "Recieve Interest on 7% Preference Shares $25"},
                {"7", "Pay Hospital $100"},
                {"8", "Income Tax Refund.Collect $20"},
                {"9", "Advance to 'Go'.Collect $200"},
                {"10", "It is your Birthday.Collect $10 from Each Player"},
                {"11", "Annuity Matures.Collect $100"},
                {"12", "You inherit $100"},
                {"13", "Doctor's Fee.Pay $50"},
                {"14", "You have won Second Prize in a Beauty Contest.\nCollect $10"}
        };

        for (String[] card : cardsData) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, Integer.parseInt(card[0]));
            values.put(COLUMN_DETAILS, card[1]);
            db.insert(TABLE_NAME, null, values);
        }
    }

    // Get all cards
    public List<CommunityChestCard> getAllCards(CommunityChest communityChest) {
        List<CommunityChestCard> cards = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_ID + " ASC"
        );

        try {
            while (cursor.moveToNext()) {
                cards.add(fromCursor(cursor, communityChest));
            }
        } finally {
            cursor.close();
        }

        return cards;
    }

    // Get card by ID
    public CommunityChestCard getCardById(int id, CommunityChest communityChest) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        CommunityChestCard card = null;
        try {
            if (cursor.moveToFirst()) {
                card = fromCursor(cursor, communityChest);
            }
        } finally {
            cursor.close();
        }

        return card;
    }

    // Convert cursor to CommunityChestCard object
    private CommunityChestCard fromCursor(Cursor cursor, CommunityChest communityChest) {
        int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        String details = cursor.getString(cursor.getColumnIndex(COLUMN_DETAILS));
        return new CommunityChestCard(communityChest, id, details);
    }
}
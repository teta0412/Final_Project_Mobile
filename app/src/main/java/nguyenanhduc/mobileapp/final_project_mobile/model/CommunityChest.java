package nguyenanhduc.mobileapp.final_project_mobile.model;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;

import nguyenanhduc.mobileapp.final_project_mobile.R;
import nguyenanhduc.mobileapp.final_project_mobile.config.SizeDisplay;
import nguyenanhduc.mobileapp.final_project_mobile.activity.PlayActivity;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import java.util.List;
import java.util.Collections;
import java.util.Random;

import nguyenanhduc.mobileapp.final_project_mobile.database.DatabaseHelper;
import nguyenanhduc.mobileapp.final_project_mobile.dao.CommunityChestCardDao;

public class CommunityChest extends City {
    private static List<CommunityChestCard> cards;
    private static int iter = 0;
    private static Player visitor;
    private CommunityChestCardDao cardDao;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CommunityChest(int position, String cityName, Context context) {
        super(position, cityName, 0);
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(context);
        cardDao = new CommunityChestCardDao(dbHelper);
        loadCardsFromDatabase();
    }

    private void loadCardsFromDatabase() {
        cards = cardDao.getAllCards(this);
        // Maintain the same shuffling logic
        Collections.shuffle(cards, new Random(100));
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void createImage() {
        Typeface monopolyBold = ResourcesCompat.getFont(PlayActivity.getInstance(), R.font.monopolybold);
        this.bitmap = Bitmap.createBitmap(this.width,this.height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTypeface(monopolyBold);

        //border
        Rect rect = new Rect(0,0,this.width,this.height);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(SizeDisplay.getPhoneDensity());
        paint.setColor(Color.BLACK);
        canvas.drawRect(rect,paint);

        //chance type
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(Color.parseColor("#8B4513"));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(SizeDisplay.CITY_TEXT_SIZE*SizeDisplay.getPhoneDensity());
        canvas.drawText("Community",this.width/2, SizeDisplay.COMMUNITY_TEXT_HEIGHT*SizeDisplay.getPhoneDensity(),paint);
        canvas.drawText("Chest",this.width/2,SizeDisplay.CHEST_TEXT_HEIGHT*SizeDisplay.getPhoneDensity(),paint);

        Resources res = Board.getContext().getResources();
        Bitmap roughBitmap = BitmapFactory.decodeResource(res,R.drawable.chest);
        Bitmap treasureBitmap = Bitmap.createScaledBitmap(roughBitmap,(int)(SizeDisplay.CHANCE_BITMAP_WIDTH*SizeDisplay.getPhoneDensity()),(int)(SizeDisplay.CHANCE_BITMAP_HEIGHT*SizeDisplay.getPhoneDensity()),true);
        canvas.drawBitmap(treasureBitmap,SizeDisplay.CITY_TEXT_SIZE*SizeDisplay.getPhoneDensity(),(int)(SizeDisplay.CHANCE_BITMAP_WIDTH*SizeDisplay.getPhoneDensity()),paint);

        generatedBitmaps.putIfAbsent(0,this.bitmap);
        this.generateBitmaps();
    }

    public static Player getVisitor() {
        return visitor;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void visit(Player player) {
        this.playerInCity(player);
        visitor = player;
        System.out.println(player.getName()+" is now at "+this.getCityName());
        final CommunityChestCard card = cards.get(iter++);
        PlayActivity instance = (PlayActivity)PlayActivity.getInstance();
        instance.showCard(visitor, "Community Chest", card.getDetails(), new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {System.out.println(card);
                try {
                    card.callFunction();
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(Chance.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Chance.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(Chance.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(Chance.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(iter == 15)
                    iter = 0;
            }
        });

    }

    public static void printCards()
    {
        for( CommunityChestCard card : cards)
            System.out.println(card);
    }
}


package nguyenanhduc.mobileapp.final_project_mobile.model;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

import androidx.annotation.RequiresApi;


import nguyenanhduc.mobileapp.final_project_mobile.adapter.PlayerAdapter;
import nguyenanhduc.mobileapp.final_project_mobile.R;
import nguyenanhduc.mobileapp.final_project_mobile.config.SizeDisplay;
import nguyenanhduc.mobileapp.final_project_mobile.activity.PlayActivity;

public class Board  {
    private static PlayActivity playActivity;
    private static Map<Integer,City> cities;
    private static Map<Integer,Player> players;
    private static int turn;
    private Scanner scan;
    private static Jail jail;
    private Start start;
    private static CityGroup citygroup;
    private static Context context;
    private static Map<Integer, Bitmap> pieces;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Board(Context context) {
        Board.context = context;
        scan = new Scanner(System.in);
        cities = new HashMap<Integer,City>();
        players = new HashMap<Integer,Player>();
        turn = 1;
        citygroup = new CityGroup();


        playActivity = (PlayActivity)PlayActivity.getInstance();

        pieces = new HashMap<Integer,Bitmap>();
        Resources resources = Board.getContext().getResources();

        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.bluepiece);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,(int)(SizeDisplay.PLAYER_PIECE*SizeDisplay.getPhoneDensity()),(int)(SizeDisplay.PLAYER_PIECE*SizeDisplay.getPhoneDensity()),true);
        pieces.put(1,scaledBitmap);

        bitmap = BitmapFactory.decodeResource(resources,R.drawable.redpiece);
        scaledBitmap = Bitmap.createScaledBitmap(bitmap,(int)(SizeDisplay.PLAYER_PIECE*SizeDisplay.getPhoneDensity()),(int)(SizeDisplay.PLAYER_PIECE*SizeDisplay.getPhoneDensity()),true);
        pieces.put(2,scaledBitmap);

        bitmap = BitmapFactory.decodeResource(resources,R.drawable.yellowpiece);
        scaledBitmap = Bitmap.createScaledBitmap(bitmap,(int)(SizeDisplay.PLAYER_PIECE*SizeDisplay.getPhoneDensity()),(int)(SizeDisplay.PLAYER_PIECE*SizeDisplay.getPhoneDensity()),true);
        pieces.put(3,scaledBitmap);

        bitmap = BitmapFactory.decodeResource(resources,R.drawable.greenpiece);
        scaledBitmap = Bitmap.createScaledBitmap(bitmap,(int)(SizeDisplay.PLAYER_PIECE*SizeDisplay.getPhoneDensity()),(int)(SizeDisplay.PLAYER_PIECE*SizeDisplay.getPhoneDensity()),true);
        pieces.put(4,scaledBitmap);

        InputStream fileStream = playActivity.getResources().openRawResource(R.raw.cities);
        try(BufferedReader file = new BufferedReader(new InputStreamReader(fileStream)))
        {
            String line = null;
            while((line = file.readLine()) != null)
            {
                String[] details = line.split(",");
                String className = details[0];
                int position = Integer.parseInt(details[1]);
                String cityName = details[2];
                int colorId = Integer.parseInt(details[3]);
                switch(className)
                {
                    case "monopoly.Start":
                    {
                        start = new Start(cityName);
                        cities.put(start.getPosition(),start);
                        break;
                    }
                    case "monopoly.Property":
                    {
                        int price = Integer.parseInt(details[4]);
                        int rent = Integer.parseInt(details[5]);
                        int mortgage = Integer.parseInt(details[6]);
                        int house1Rent = Integer.parseInt(details[7]);
                        int house2Rent = Integer.parseInt(details[8]);
                        int house3Rent = Integer.parseInt(details[9]);
                        int hotelRent = Integer.parseInt(details[10]);
                        Property property = new Property(position,cityName,price,rent,mortgage,house1Rent,house2Rent,house3Rent,hotelRent,colorId);
                        cities.put(property.getPosition(),property);
                        break;
                    }
                    case "monopoly.CommunityChest":
                    {
                        cities.put(position,new CommunityChest(position,cityName,context));
                        break;
                    }
                    case "monopoly.Others":
                    {
                        int rent = Integer.parseInt(details[4]);
                        cities.put(position,new Others(position,cityName,rent));
                        break;
                    }
                    case "monopoly.Utilities":
                    {
                        cities.put(position,new Utilities(position,cityName));
                        break;
                    }
                    case "monopoly.Jail":
                    {
                        cities.put(position,new Jail());
                        break;
                    }
                }
            }
        }
        catch(IOException e)
        {
            System.out.println(e);
        }

        citygroup.grouping(cities);

    }

    public static Map<Integer, City> getCities() {
        return Collections.unmodifiableMap(cities);
    }

    public static Map<Integer,Player> getPlayers() {
        return Collections.unmodifiableMap(players);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void start() {
        for(Map.Entry<Integer,Player> entry : players.entrySet())
        {
            entry.getValue().atStart();
        }
        playerToPlay();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void playerToPlay()
    {
            final Player currentPlayer = players.get(turn);
            currentPlayer.setDiceToken(true);
            playActivity.showPlayers(currentPlayer);
            turn ++;
            if(turn == players.size()+1)
            {
                turn = 1;
            }
            if(currentPlayer instanceof Computer)
            {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playActivity.computerPlay((Computer) currentPlayer);
                    }
                },2000);
            }
    }

    public void addPlayers(HashMap<Integer,String> names)
    {
        for(Map.Entry<Integer,String > entry : names.entrySet())
        {
            players.put(entry.getKey(),new Player(entry.getKey(),entry.getValue()));
        }
        players.get(1).setDiceToken(true);
    }

    public static Map<Integer, Bitmap> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }

    public static Context getContext()
    {
        return context;
    }

    public void finishGame() {
        LinkedList<Player> orderWinning = new LinkedList<Player>();

        for(Map.Entry<Integer,Player> entry : players.entrySet())
        {
            orderWinning.add(entry.getValue());
        }

        Collections.sort(orderWinning, new Comparator<Player>() {
            @Override
            public int compare(Player player1, Player player2) {
                return player2.getTotalValue() - player1.getTotalValue();
            }
        });
        final Dialog dialog = new Dialog(playActivity);
        dialog.setContentView(R.layout.playerlist);

        ListView listView = dialog.findViewById(R.id.listview);
        PlayerAdapter playerAdapter = new PlayerAdapter(context,orderWinning);
        listView.setAdapter(playerAdapter);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                playActivity.finish();
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();


    }

    public void computerMode() {
        players.put(1,new Player(1,"You"));
        players.put(2,new Computer());

    }
}


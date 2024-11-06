package nguyenanhduc.mobileapp.final_project_mobile.model;


import android.content.DialogInterface;
import android.os.Build;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import androidx.annotation.RequiresApi;

import nguyenanhduc.mobileapp.final_project_mobile.activity.PlayActivity;

public class CommunityChestCard {
    private int id;
    private String details;
    private Player visitor;
    private CommunityChest communityChest;
    int choice;

    public CommunityChestCard(CommunityChest communityChest, int id, String details) {
        this.communityChest = communityChest;
        this.id = id;
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }

    public void callFunction() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        visitor = CommunityChest.getVisitor();
        String name = String.valueOf(this.id) ;
        Method method = CommunityChestCard.class.getMethod("CommunityChestCard"+name);
        method.invoke(this);
    }

    public void CommunityChestCard0()
    {
        Bank.payPlayer(visitor, 50);
        PlayActivity.onCompleteListener.OnComplete(visitor);
    }

    public void CommunityChestCard1()
    {
        visitor.payBank(50);
        PlayActivity.onCompleteListener.OnComplete(visitor);
    }

    public void CommunityChestCard2()
    {
        Bank.payPlayer(visitor, 200);
        PlayActivity.onCompleteListener.OnComplete(visitor);
    }

    public void CommunityChestCard3()
    {
        visitor.payBank(10);
        PlayActivity.onCompleteListener.OnComplete(visitor);

    }

    public void CommunityChestCard4()
    {
        Property oldKentRoad = (Property)Board.getCities().get(1);
        visitor.movedtoCity(oldKentRoad);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void CommunityChestCard5()
    {
        visitor.jailed();
    }

    public void CommunityChestCard6()
    {
        Bank.payPlayer(visitor, 25);
        PlayActivity.onCompleteListener.OnComplete(visitor);
    }

    public void CommunityChestCard7()
    {
        visitor.payBank(100);
        PlayActivity.onCompleteListener.OnComplete(visitor);
    }

    public void CommunityChestCard8()
    {
        Bank.payPlayer(visitor, 20);
        PlayActivity.onCompleteListener.OnComplete(visitor);
    }

    public void CommunityChestCard9()
    {
        Start start = (Start)Board.getCities().get(0);
        visitor.movedtoCity(start);
    }

    public void CommunityChestCard10()
    {
        Map<Integer,Player> players = Board.getPlayers();
        for(Map.Entry<Integer,Player> entry : players.entrySet())
        {
            Player player = entry.getValue();
            if(!player.equals(visitor))
            {
                player.birthdayTreat(visitor);
            }
        }
        PlayActivity.onCompleteListener.OnComplete(visitor);
    }

    public void CommunityChestCard11()
    {
        Bank.payPlayer(visitor, 100);
        PlayActivity.onCompleteListener.OnComplete(visitor);
    }

    public void CommunityChestCard12()
    {
        Bank.payPlayer(visitor, 100);
        PlayActivity.onCompleteListener.OnComplete(visitor);
    }

    public void CommunityChestCard13()
    {
        visitor.payBank(50);
        PlayActivity.onCompleteListener.OnComplete(visitor);
    }

    public void CommunityChestCard14()
    {
        Bank.payPlayer(visitor, 10);
        PlayActivity.onCompleteListener.OnComplete(visitor);
    }

    @Override
    public String toString() {
        return "CommunityChestCard " + "id= " + id + ", details = " + details;
    }
}

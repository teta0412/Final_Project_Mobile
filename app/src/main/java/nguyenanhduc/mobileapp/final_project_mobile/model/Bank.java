package nguyenanhduc.mobileapp.final_project_mobile.model;

import nguyenanhduc.mobileapp.final_project_mobile.activity.PlayActivity;

public class Bank{
    private static int money=3000;
    private static PlayActivity playActivity = (PlayActivity)PlayActivity.getInstance();
    public static int getMoney() {
        return money;
    }

    public static void debitMoney(Player player,int amount)
    {
        playActivity.showNotification(player,player.getName()+" pays Bank $"+amount);
        money += amount;
    }

    public static void payPlayer(Player player,int amount)
    {
        playActivity.showNotification(player,"Bank pays "+player.getName()+" $"+amount);
        money -= amount;
        player.getRent(amount);
    }

}


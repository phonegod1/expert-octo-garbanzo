package me.ac.gui;

import me.ac.Main;
import me.ac.playerbets;
import me.ac.util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class ConvPrompt extends NumericPrompt{
    Main plugin;util z;Double x;playerbets.Color c;
    public ConvPrompt(Main plugin, playerbets.Color c){this.plugin=plugin;z= new util(plugin);this.c=c;}
    /*@Override
    protected boolean isNumberValid​(ConversationContext con, Number input){

        x=input.doubleValue();
        if(x<=0){return false;}
        Player p=(Player)con.getForWhom();
        if(x>z.getBalance(p.getUniqueId())){return false;}

        return true;
    }*/
    @Override
    protected boolean isInputValid(ConversationContext con, String in)
    {

        /*if(!Main.isRoll&&Main.time!=-1){
            if(isDouble(in)){
                x=Double.parseDouble(in);
                if(x<=0){return false;}
                Player p=(Player)con.getForWhom();
                String a = new StringBuilder().append(ChatColor.GREEN).append(ChatColor.BOLD).append("Cant place a bet while rolling.").toString();
                p.sendMessage(a);
                if(x>z.getBalance(p.getUniqueId())){return false;}

                return true;
            } else{
                return false;
            }
        }*/
        if(isDouble(in)){
            x=Double.parseDouble(in);
            if(x<=0){return false;}
            Player p=(Player)con.getForWhom();
            //String a = new StringBuilder().append(ChatColor.GREEN).append(ChatColor.BOLD).append("Cant place a bet while rolling.").toString();
            //p.sendMessage(a);
            if(x>z.getBalance(p.getUniqueId())){return false;}

            return true;
        } else{
            return false;
        }
        //Bukkit.getLogger().info("hit input valid for TestPrompt");
    }


    @Override
    protected Prompt acceptValidatedInput(ConversationContext con, Number number) {

        Player p=(Player)con.getForWhom();
        Double x=number.doubleValue();
        if(!Main.isRoll) {
            z.removeFromAccount(p.getUniqueId(), number.doubleValue());
            Main.pb.add(new playerbets(p.getUniqueId(), number.doubleValue(), c));
        } else {
            z.removeFromAccount(p.getUniqueId(), number.doubleValue());
            Main.pb2.add(new playerbets(p.getUniqueId(), number.doubleValue(), c));
        }
        //String a = new StringBuilder().append(ChatColor.GREEN).append(ChatColor.BOLD).append("Bet Successfully Placed!").toString();
        if(Main.mainData.wagers.containsKey(p.getName())){
            Main.mainData.wagers.replace(p.getName(),Main.mainData.wagers.get(p.getName())+x);

        } else{
            //Main.mainData.playerReports.put(bannedPlayer.getName(), reasonandproof);
            Main.mainData.wagers.put(p.getName(),x);
        }
        if(Main.mainData.wintotalratio.containsKey(p.getUniqueId().toString())) {
            String[] constu = Main.mainData.wintotalratio.get(p.getUniqueId().toString()).split("/");
            int t = Integer.parseInt(constu[1]) + 1;
            Main.mainData.wintotalratio.replace(p.getUniqueId().toString(), constu[0] + "/" + t);
        } else {
            Main.mainData.wintotalratio.put(p.getUniqueId().toString(), "0/1");
        }
        //p.sendMessage(a);
        return Prompt.END_OF_CONVERSATION;
    }

    @NotNull
    @Override
    public String getPromptText(ConversationContext con) {
        /*if(!Main.isRoll&&Main.time!=-1) {
            Player p = (Player) con.getForWhom();
            String a = new StringBuilder().append(ChatColor.DARK_AQUA).append(ChatColor.BOLD).append("Enter Amount To Bet. ").toString();
            String d = new StringBuilder().append(ChatColor.GRAY).append("Enter cancel to exit.").toString();
            return a+d;
        } else {
            String a = new StringBuilder().append(ChatColor.RED).append(ChatColor.BOLD).append("Cant place a bet while rolling.").toString();
            String d = new StringBuilder().append(ChatColor.GRAY).append("Enter cancel to exit.").toString();
            return a+d;
        }*/
        //Player p = (Player) con.getForWhom();
        String a = new StringBuilder().append(ChatColor.DARK_AQUA).append(ChatColor.BOLD).append("Enter Amount To Bet. ").toString();
        String d = new StringBuilder().append(ChatColor.GRAY).append("Enter cancel to exit.").toString();
        return a+d;
    }

    public boolean isDouble(String s){
        try
        {
            Double.parseDouble(s);
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
            //not a double
        }
    }
}

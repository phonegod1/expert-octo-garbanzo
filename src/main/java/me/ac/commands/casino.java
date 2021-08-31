package me.ac.commands;

import me.ac.Data;
import me.ac.Main;
import me.ac.util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class casino implements CommandExecutor{
    private Player p;
    public static ArrayList<UUID> setwheel;
    Main plugin;
    public casino(Main plugin){
        this.plugin=plugin;

    }
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getServer().getLogger().warning(plugin.getConfig().getString("NoConsole"));
            return true;
        }
        setwheel=new ArrayList<>();
        p = (Player) sender;
        /*if (!p.hasPermission("AdvancedCasino.op")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("NoPermission")));
            return true;
        }*/
        if(args.length==0){
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("InvalidUsageCasino")));
            return true;
        }
        if ("help".equals(args[0])) {
            if(p.hasPermission("AdvancedCasino.help")) {
                sendhelp(p);
            } else {nopermission();}
        }
        if ("getwalletaddress".equals(args[0])) {
            if(p.hasPermission("AdvancedCasino.getwalletaddress")) {
                p.sendMessage(Main.WS.getWalletAddress());
            } else {nopermission();}
        }
        if ("stoptransfer".equals(args[0])) {
            if(p.hasPermission("AdvancedCasino.stoptransfer")) {
                Main.allowtransfer = false;
            } else{nopermission();}
        }
        if ("starttransfer".equals(args[0])) {
            if(p.hasPermission("AdvancedCasino.starttransfer")) {
            Main.allowtransfer = true;} else {nopermission();}
        }
        if ("withdraw".equals(args[0])) {
            if(p.hasPermission("AdvancedCasino.adminwithdraw")){

            if (args.length == 3) withdraw(args[2], args[1]);

            }
        }
        if ("balance".equals(args[0])) {
            if(p.hasPermission("AdvancedCasino.balance")) {
                if (args.length == 1) {
                    p.sendMessage(Main.WS.getBalance());
                } else if (args.length == 2) {
                    util z = new util(plugin);
                    p.sendMessage(String.format("%.8f",z.getBalance(Bukkit.getPlayer(args[1]).getUniqueId())));
                }
            } else {nopermission();}
        }
        if("give".equals(args[0])){
            if(p.hasPermission("AdvancedCasino.give")) {
                Player player = Bukkit.getPlayer(args[1]);
                util z = new util(plugin);
                z.addToAccount(Objects.requireNonNull(player).getUniqueId(), Double.parseDouble(args[2]));
            } else {nopermission();}
        }
        if("set".equals(args[0])){
            if(p.hasPermission("AdvancedCasino.set")) {
                Player player = Bukkit.getPlayer(args[1]);
                util z = new util(plugin);
                z.setBalance(Objects.requireNonNull(player).getUniqueId(),Double.parseDouble(args[2]));
            } else {nopermission();}
        }
        if ("setwheel".equals(args[0])) {
            if(p.hasPermission("AdvancedCasino.setwheel")) {
                //ENABLE SETHWELL MODE
                UUID u = p.getUniqueId();
                for (int i = 0; i < setwheel.size(); i++) {
                    if (setwheel.get(i).equals(u)) {
                        //System.out.println("removing");
                        setwheel.remove(i);
                        return true;
                    }
                }
                setwheel.add(u);
            } else {nopermission();}
            //set a listener that checks for right clicking item
            //make recursive algorithm that finds all blocks for item
            //add item to array in Data
            //add timer that iterates throug harray
            //Main.mainData= new Data(blocks);
        }
        if("resettop10".equals(args[0])){
            if(p.hasPermission("AdvancedCasino.resettop10")) {
                Main.mainData.wagers=new HashMap<>();
            }


        }
        if("resetwheel".equals(args[0])){
            if(p.hasPermission("AdvancedCasino.resetwheel")) {
                Main.mainData.b=new ArrayList<>();
            }


        }

        return true;
    }
    private void sendhelp(Player p){

        p.sendMessage(ChatColor.DARK_AQUA+"---Advanced Casino Help---");
        p.sendMessage(ChatColor.AQUA+ "/casino "+"help"+" Shows the help page.");
        p.sendMessage(ChatColor.AQUA+ "/casino "+"getwalletaddress"+" Sets the main wallet of the server.");
        p.sendMessage(ChatColor.AQUA+ "/casino "+"stoptransfer"+" Disables /deposit and /withdraw until stated otherwise");
        p.sendMessage(ChatColor.AQUA+ "/casino "+"starttransfer"+" Enables /deposit and /withdraw until stated otherwise");
        p.sendMessage(ChatColor.AQUA+ "/casino "+"withdraw <amount> <address>"+" Sets the main wallet of the server.");
        p.sendMessage(ChatColor.AQUA+ "/casino "+"balance"+" Gives the balance of the wallet.");
        p.sendMessage(ChatColor.AQUA+ "/casino "+"balance <player>"+" Gets the balance of a player.");
        p.sendMessage(ChatColor.AQUA+ "/casino "+"setwheel"+" Break each portion of the wheel to be rotated to setup the wheel.");
        p.sendMessage(ChatColor.AQUA+ "/casino "+"give <player> <amount>"+" Adds a specified amount to the players balance.");
        p.sendMessage(ChatColor.AQUA+ "/casino "+"set <player> <amount>"+" Sets the balance of the player.");
        p.sendMessage(ChatColor.AQUA+ "/casino "+"resettop10"+" Reset the top10 billboard.");

    }
    private void withdraw(String address, String amount){
        try
        {
            Double.parseDouble(amount);
        }
        catch(NumberFormatException e)
        {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("InvalidUsageWithdrawNoNum")));
            //not a double
        }
        long a = (long)Double.parseDouble(amount)*100000000;
        p.sendMessage("Sending "+a+" to wallet: "+ address);
        Main.WS.withdraw(address,a,p);
    }

    private void nopermission(){
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("NoPermission")));
    }



}

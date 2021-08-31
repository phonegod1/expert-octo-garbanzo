package me.ac.commands;

import me.ac.Main;
import me.ac.gui.gui;
import me.ac.playerbets;
import me.ac.util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class bet implements CommandExecutor {
    private Main plugin;
    Player p;
    public bet(Main plugin){
        this.plugin=plugin;
    }
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getServer().getLogger().warning(plugin.getConfig().getString("NoConsole"));
            return true;
        }
        p = (Player) sender;
        if (!p.hasPermission("AdvancedCasino.bet")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("NoPermission")));
            return true;
        }

        /*try {
            Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("InvalidUsageWithdrawNoNum")));
            return true;
            //not a double
        }
        double amount = Double.parseDouble(args[0]);
        */
        // /bet color amount
        if(args.length==2){
            Double bets;
            try {
                bets = Double.parseDouble(args[1]);
            } catch (NumberFormatException e) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("InvalidUsageWithdrawNoNum")));
                return true;
                //not a double
            }
            if(!Main.isRoll) {


                if (args[0].equalsIgnoreCase("red")) {
                    placebet(bets, playerbets.Color.RED);
                }
                if (args[0].equalsIgnoreCase("black")) {
                    placebet(bets, playerbets.Color.BLACK);
                }
                if (args[0].equalsIgnoreCase("green")) {
                    placebet(bets, playerbets.Color.GREEN);
                }
            } else {

                if (args[0].equalsIgnoreCase("red")) {
                    placebet2(bets, playerbets.Color.RED);
                }
                if (args[0].equalsIgnoreCase("black")) {
                    placebet2(bets, playerbets.Color.BLACK);
                }
                if (args[0].equalsIgnoreCase("green")) {
                    placebet2(bets, playerbets.Color.GREEN);
                }
            }
        }
        if(args.length==0) {
            gui x = new gui(plugin);
            x.openInventory(p);
        }



        return true;
    }
    private Double getdouble(){
        try {
            //Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("InvalidUsageWithdrawNoNum")));
            //not a double
        }
        return null;
    }
    private void placebet(double number, playerbets.Color c){
        util z =new util(plugin);
        if(number>z.getBalance(p.getUniqueId())){p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("InvalidUsageWithdrawIns")));return;}
        z.removeFromAccount(p.getUniqueId(),number);
        playerbets pb=new playerbets(p.getUniqueId(),number,c);
        Main.pb.add(pb);
        String a = new StringBuilder().append(ChatColor.GREEN).append(ChatColor.BOLD).append("Bet Successfully Placed!").toString();
        if(Main.mainData.wagers.containsKey(p.getName())){
            Main.mainData.wagers.replace(p.getName(),Main.mainData.wagers.get(p.getName())+number);


        } else{
            //Main.mainData.playerReports.put(bannedPlayer.getName(), reasonandproof);
            Main.mainData.wagers.put(p.getName(),number);
        }

        if(Main.mainData.wintotalratio.containsKey(p.getUniqueId().toString())) {
            String[] constu = Main.mainData.wintotalratio.get(pb.getplayer().toString()).split("/");
            int t = Integer.parseInt(constu[1]) + 1;
            Main.mainData.wintotalratio.replace(pb.getplayer().toString(), constu[0] + "/" + t);
            p.sendMessage(a);
        } else {
            Main.mainData.wintotalratio.put(pb.getplayer().toString(), "0/1");
            p.sendMessage(a);
        }
    }
    private void placebet2(double number, playerbets.Color c){
        util z =new util(plugin);
        if(number>z.getBalance(p.getUniqueId())){p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("InvalidUsageWithdrawIns")));return;}
        z.removeFromAccount(p.getUniqueId(),number);
        playerbets pb=new playerbets(p.getUniqueId(),number,c);
        Main.pb2.add(pb);
        //String a = new StringBuilder().append(ChatColor.GREEN).append(ChatColor.BOLD).append("Bet Successfully Placed!").toString();
        String a = new StringBuilder().append(net.md_5.bungee.api.ChatColor.GREEN).append(net.md_5.bungee.api.ChatColor.BOLD).append("Successfully placed bet for the next roll!").toString();
        if(Main.mainData.wagers.containsKey(p.getName())){
            Main.mainData.wagers.replace(p.getName(),Main.mainData.wagers.get(p.getName())+number);


        } else{
            //Main.mainData.playerReports.put(bannedPlayer.getName(), reasonandproof);
            Main.mainData.wagers.put(p.getName(),number);
        }
        if(Main.mainData.wintotalratio.containsKey(p.getUniqueId().toString())) {
            String[] constu = Main.mainData.wintotalratio.get(pb.getplayer().toString()).split("/");
            int t = Integer.parseInt(constu[1]) + 1;
            Main.mainData.wintotalratio.replace(pb.getplayer().toString(), constu[0] + "/" + t);
            p.sendMessage(a);
        } else {
            Main.mainData.wintotalratio.put(pb.getplayer().toString(), "0/1");
            p.sendMessage(a);
        }
    }

}

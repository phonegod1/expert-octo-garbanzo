package me.ac.timers;

import me.ac.Main;
import me.ac.blocksets;
import me.ac.playerbets;
import me.ac.util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class roll extends BukkitRunnable {
    private int counter;
    private Main plugin;
    private ArrayList<Material> mats;
    public roll(int x,Main plugin){
        this.counter=x+1;
        this.plugin=plugin;
        //rotate the wheel using a random number generator rotate 80 times

    }

    @Override
    public void run() {
        //System.out.print("ROLL function");
        if(counter==0){
            //System.out.print("Ended roll");
            //run all bets
            bets z= new bets(plugin.getConfig().getInt("betsduration"),plugin);
            z.runTaskTimer(plugin,5,20);

            //Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin,z,0,20);
            /*int random= new Random().nextInt(15);
            random++;
            if(random<=7){
                //reward black
                reward(playerbets.Color.BLACK);
            } else if(random<=14){
                //reward red
                reward(playerbets.Color.RED);
            } else {
                //reward green
                reward(playerbets.Color.GREEN);
            }*/
            if(Main.blocks.size()!=0) {
                Material matty = Main.blocks.get(0).getmat();
                if (matty == Material.BLACK_WOOL) {
                    Main.LastWonColor= playerbets.Color.BLACK;
                    reward(playerbets.Color.BLACK);
                } else if (matty == Material.RED_WOOL) {
                    Main.LastWonColor= playerbets.Color.RED;
                    reward(playerbets.Color.RED);
                } else {
                    Main.LastWonColor= playerbets.Color.GREEN;
                    reward(playerbets.Color.GREEN);
                }
            }


            Main.isRoll=false;
            Bukkit.getScheduler().cancelTask(this.getTaskId());
            this.cancel();
        }
        if(counter==1){
            Main.isRoll=false;
        }
        counter--;
        //System.out.print("Counter: "+ counter);

    }
    private void reward(playerbets.Color c){
        for(playerbets x:Main.pb){
            if(x.getcolor()== playerbets.Color.GREEN){
                if(x.getcolor()==c){
                    util z= new util(plugin);
                    z.addToAccount(x.getplayer(),x.getamount()*14);
                }
            }
            else if(x.getcolor()==c){
                util z= new util(plugin);
                z.addToAccount(x.getplayer(),x.getamount()*2);
            }
        }
        Main.pb=new ArrayList<>();
    }

}

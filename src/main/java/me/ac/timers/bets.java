package me.ac.timers;

import me.ac.Main;
import me.ac.blocksets;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class bets extends BukkitRunnable{
    private int counter;
    private Main plugin;
    //private int stat;
    public bets(int i, Main plugin) {
        this.counter=i+1;
        this.plugin=plugin;
        Main.time=i;


    }


    @Override
    public void run() {
        //System.out.print("BET FUNCTION");
        if(counter==0){
            //System.out.print("Ended bet function");
            roll y= new roll(plugin.getConfig().getInt("rollduration"),plugin);
            y.runTaskTimer(plugin,5,20);
            randomspin();
            Main.isRoll=true;
            Bukkit.getScheduler().cancelTask(this.getTaskId());
            //this.cancel();

        }
        if(counter>0) {
            counter--;
            Main.time--;
            //System.out.print("Counter: "+ counter);
        }


    }


    private void randomspin(){
        ArrayList<Material> mats=new ArrayList<>();ArrayList<Material> mats2;
        int random= new Random().nextInt(150);
        ArrayList<blocksets> b=Main.blocks;
        for(int p=0;p<random;p++) {
            if(p==0) {
                for (int i = 0; i < b.size(); i++) {
                    if (i == 0) {
                        mats.add(b.get(b.size() - 1).getmat());

                    } else {
                        mats.add(b.get(i - 1).getmat());
                    }
                }
            } else {

                mats2 = mats;
                mats = new ArrayList<>();
                for (int i = 0; i < b.size(); i++) {
                    if (i == 0) {
                        //mats.add(b.get(b.size() - 1).getmat());
                        mats.add(mats2.get(b.size() - 1));

                    } else {
                        //mats.add(b.get(i - 1).getmat());
                        mats.add(mats2.get(i - 1));
                    }
                }
            }

        }
        for (int i = 0; i < b.size(); i++) {
            b.get(i).setmat(mats.get(i));
        }
    }
}

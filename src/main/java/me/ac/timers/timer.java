package me.ac.timers;

import me.ac.Main;
import me.ac.blocksets;
import me.ac.playerbets;
import me.ac.util;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class timer extends BukkitRunnable {
    private int rollduration,betduration,counter;

    private Main plugin;
    public timer(Main plugin){
        this.plugin=plugin;
        rollduration=plugin.getConfig().getInt("rollduration");
        betduration=plugin.getConfig().getInt("betsduration");
        counter=betduration;
    }
    public int getcounter(){
        return counter;
    }
    @Override
    public void run() {


        if(counter==betduration){

        } else if(counter==0){
            randomspin();
            Main.isRoll=true;
            //begin roll set roll to true
        } else {
            if(counter==1-rollduration){
                Main.isRoll=false;
            }
            if(counter==-rollduration){
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
                counter=betduration;
            }
        }
        counter--;
    }


    private void reward(playerbets.Color c){
        for(playerbets x:Main.pb){
            if(x.getcolor()== playerbets.Color.GREEN){
                if(x.getcolor()==c){
                    if(Main.mainData.wintotalratio.containsKey(x.getplayer().toString())) {
                        String[] constu = Main.mainData.wintotalratio.get(x.getplayer().toString()).split("/");
                        int t = Integer.parseInt(constu[0]) + 1;
                        Main.mainData.wintotalratio.replace(x.getplayer().toString(), t + "/" + constu[1]);
                    } else{
                        Main.mainData.wintotalratio.put(x.getplayer().toString(), "1/1");
                    }

                    util z= new util(plugin);
                    z.addToAccount(x.getplayer(),x.getamount()*14);
                }
            }
            else if(x.getcolor()==c){
                if(Main.mainData.wintotalratio.containsKey(x.getplayer().toString())) {
                    String[] constu = Main.mainData.wintotalratio.get(x.getplayer().toString()).split("/");
                    int t = Integer.parseInt(constu[0]) + 1;
                    Main.mainData.wintotalratio.replace(x.getplayer().toString(), t + "/" + constu[1]);
                } else{
                    Main.mainData.wintotalratio.put(x.getplayer().toString(), "1/1");
                }

                util z= new util(plugin);
                z.addToAccount(x.getplayer(),x.getamount()*2);
            }
        }
        Main.pb=Main.pb2;
        Main.pb2=new ArrayList<>();
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

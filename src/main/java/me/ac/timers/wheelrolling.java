package me.ac.timers;

import me.ac.Main;
import me.ac.blocksets;

import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

import static me.ac.Main.isRoll;

public class wheelrolling extends BukkitRunnable {
    ArrayList<blocksets> b;
    private ArrayList<Material> mats;
    public wheelrolling() {
       b = Main.blocks;
    }
    @Override
    public void run() {
        if (isRoll) {

            if (b != null) {
                mats = new ArrayList<>();
                if (b.size() != 0) {
                    //System.out.print("B SIZE" + b.size());

                    for (int i = 0; i < b.size(); i++) {
                        if (i == 0) {
                            mats.add(b.get(b.size() - 1).getmat());

                        } else {
                            mats.add(b.get(i - 1).getmat());
                        }

                    }
                    //System.out.print("MATS SIZE: "+ mats.size());
                    for (int i = 0; i < b.size(); i++) {
                        b.get(i).setmat(mats.get(i));
                    }
                }
            }
        }
    }
}

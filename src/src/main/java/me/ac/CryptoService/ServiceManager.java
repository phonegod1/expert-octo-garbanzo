package me.ac.CryptoService;

import com.google.protobuf.Enum;
import com.sun.org.apache.xpath.internal.operations.Bool;
import me.ac.Main;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.MainNetParams;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class ServiceManager extends Thread{
    protected static NetworkParameters params;
    private String filePrefix;
    private File file;
    protected static WalletAppKit kit;
    protected Main plugin;
    public ServiceManager(Main plugin){
        this.plugin=plugin;
    }

    public void run(){
        if(Main.walletset) {
            file = new File("plugins" + System.getProperty("file.separator") + "AdvancedCasino" + System.getProperty("file.separator") + "Wallet");
            params = MainNetParams.get();
            filePrefix = "forwarding-service";

            kit = new WalletAppKit(params, file, filePrefix);
            kit.setAutoSave(true);
            //System.out.println("start async");
            kit.startAsync();
            //System.out.println("aRUNNING");
            kit.awaitRunning();
            //System.out.println("RUNNING");
            Main.walletset=false;
        }
    }

    public WalletAppKit getWallet(){
        return kit;
    }




}

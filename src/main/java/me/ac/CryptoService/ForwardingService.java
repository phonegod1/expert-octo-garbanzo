package me.ac.CryptoService;




import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;
import me.ac.Main;
import me.ac.util;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.crypto.KeyCrypterException;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.File;

import static com.google.common.base.Preconditions.checkNotNull;


public class ForwardingService extends Thread {
    private NetworkParameters params;
    private String filePrefix;
    private static Address forwardingAddress;
    private static WalletAppKit kit;
    private Player p;
    private Main plugin;
    ConsoleCommandSender console;
    public ForwardingService(Player p,Main plugin){
        console = Bukkit.getServer().getConsoleSender();
        console.sendMessage(" ");

        this.plugin=plugin;
        console.sendMessage(" ");
        this.p=p;
        console.sendMessage("Setting params");

        params = MainNetParams.get();
        filePrefix = "forwarding-service";
        console.sendMessage("run");


    }

    public void run() {
        // This line makes the log output more compact and easily read, especially when using the JDK log adapter.
        //BriefLogFormatter.init();
        //if (args.length < 1) {
        //    System.err.println("Usage: address-to-send-back-to [regtest|testnet]");
        //    return;
        //}

        // Figure out which network we should connect to. Each one gets its own set of files.
        /*if (args.length > 1 && args[1].equals("testnet")) {
            params = TestNet3Params.get();
            filePrefix = "forwarding-service-testnet";
        } else if (args.length > 1 && args[1].equals("regtest")) {
            params = RegTestParams.get();
            filePrefix = "forwarding-service-regtest";
        } else {
            params = MainNetParams.get();
            filePrefix = "forwarding-service";
        }*/
        // Parse the address given as the first parameter.
        forwardingAddress = LegacyAddress.fromBase58(params,plugin.getConfig().getString("ReceivingAddress"));

        System.out.println("Network: " + params.getId());
        System.out.println("Forwarding address: " + forwardingAddress);

        // Start up a basic app using a class that automates some boilerplate.
        console.sendMessage("this");

        kit = new WalletAppKit(params, new File("."), filePrefix);

        console.sendMessage("that");

        /*if (params == RegTestParams.get()) {
            // Regression test mode is designed for testing and development only, so there's no public network for it.
            // If you pick this mode, you're expected to be running a local "bitcoind -regtest" instance.
            kit.connectToLocalHost();
        }*/

        // Download the block chain and wait until it's done.

        kit.startAsync();
        kit.awaitRunning();

        //kit.wallet().addWatchedAddress()

        // We want to know when we receive money.
        Address ad= kit.wallet().freshReceiveAddress();

        kit.wallet().addCoinsReceivedEventListener(new WalletCoinsReceivedEventListener() {
            @Override
            public void onCoinsReceived(Wallet w, final Transaction tx, Coin prevBalance, Coin newBalance) {
                // Runs in the dedicated "user thread" (see bitcoinj docs for more info on this).
                //
                // The transaction "tx" can either be pending, or included into a block (we didn't see the broadcast).

                final Coin value = tx.getValueSentToMe(w);
                System.out.println("Received tx for " + value.toFriendlyString() + ": " + tx);
                System.out.println("Transaction will be forwarded after it confirms.");
                // Wait until it's made it into the block chain (may run immediately if it's already there).
                //
                // For this dummy app of course, we could just forward the unconfirmed transaction. If it were
                // to be double spent, no harm done. Wallet.allowSpendingUnconfirmedTransactions() would have to
                // be called in onSetupCompleted() above. But we don't do that here to demonstrate the more common
                // case of waiting for a block.

                Futures.addCallback(tx.getConfidence().getDepthFuture(1), new FutureCallback<TransactionConfidence>() {
                    @Override
                    public void onSuccess(TransactionConfidence result) {

                        System.out.println("Confirmation received.");
                        p.sendMessage("BTC received, deposited ");
                        //ADD PLAYER BALANCE HERE
                        util z = new util(plugin);
                        z.addToAccount(p.getUniqueId(),(double)value.getValue());
                        forwardCoins(tx);

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        // This kind of future can't fail, just rethrow in case something weird happens.
                        throw new RuntimeException(t);
                    }
                }, MoreExecutors.directExecutor());
            }
        });

        Address sendToAddress = LegacyAddress.fromKey(params, kit.wallet().currentReceiveKey());
        System.out.println("Send coins to: " + sendToAddress);


        p.sendMessage(ChatColor.AQUA+"Send bitcoin to address: "+ ChatColor.BOLD+ChatColor.DARK_AQUA+sendToAddress);
        p.sendMessage(ChatColor.AQUA+"Send bitcoin to address: "+ ChatColor.BOLD+ChatColor.DARK_AQUA+kit.wallet().currentReceiveAddress());
        //sendMessage(p,"Click Here To Copy The Address",sendToAddress.toString());
        System.out.println("Waiting for coins to arrive. Press Ctrl-C to quit.");

        try {
            Thread.sleep(Long.MAX_VALUE);

        } catch (InterruptedException ignored) {}
    }
    public Address getaddress(){
        Address sendToAddress = LegacyAddress.fromKey(params, kit.wallet().currentReceiveKey());
        return sendToAddress;
    }

    private static void forwardCoins(Transaction tx) {
        try {
            // Now send the coins onwards.
            SendRequest sendRequest = SendRequest.emptyWallet(forwardingAddress);
            final Wallet.SendResult sendResult = kit.wallet().sendCoins(sendRequest);
            checkNotNull(sendResult);  // We should never try to send more coins than we have!
            System.out.println("Sending ...");
            // Register a callback that is invoked when the transaction has propagated across the network.
            // This shows a second style of registering ListenableFuture callbacks, it works when you don't
            // need access to the object the future returns.
            sendResult.broadcastComplete.addListener(new Runnable() {
                @Override
                public void run() {
                    // The wallet has changed now, it'll get auto saved shortly or when the app shuts down.
                    System.out.println("Sent coins onwards! Transaction hash is " + sendResult.tx.getTxId());
                }
            }, MoreExecutors.directExecutor());
        } catch (KeyCrypterException | InsufficientMoneyException e) {
            // We don't use encrypted wallets in this example - can never happen.
            throw new RuntimeException(e);
        }
    }
    private void sendMessage(Player player, String message, String url) {
        Bukkit.getServer().dispatchCommand(
                Bukkit.getConsoleSender(),
                "/tellraw " + player.getName() +
                        " {text:\"" + message + "\",clickEvent:{action:open_url,value:\"" +
                        url + "\"}}");
    }
}

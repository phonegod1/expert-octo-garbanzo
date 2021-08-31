package me.ac.CryptoService;

import com.google.common.util.concurrent.MoreExecutors;
import me.ac.Main;
import org.bitcoinj.core.*;
import org.bitcoinj.crypto.KeyCrypterException;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.File;

import static com.google.common.base.Preconditions.checkNotNull;

public class WithdrawService extends ServiceManager{
    private LegacyAddress forwardingAddress;
    //public static Wallet x= Wallet.loadFromFile()
    public WithdrawService(Main plugin){
        super(plugin);


    }

    public String getWalletAddress(){
        return kit.wallet().freshReceiveAddress().toString();
    }
    public String getBalance(){
        return kit.wallet().getBalance(Wallet.BalanceType.AVAILABLE).toFriendlyString();
    }
    public void withdraw(String address,long amount,Player p){
        Coin value = Coin.valueOf(amount);
        try {
            // Now send the coins onwards.
            //SendRequest sendRequest = SendRequest.emptyWallet(forwardingAddress);
            //SendRequest sendRequest = SendRequest.to();
            //forwardingAddress = LegacyAddress.fromBase58(params, address);
            forwardingAddress = LegacyAddress.fromBase58(params, address);

            //final Wallet.SendResult sendResult = kit.wallet().sendCoins(kit.peerGroup(),forwardingAddress,value);
            //Generate the request.
            SendRequest request = SendRequest.to( forwardingAddress, value );

            //Update the fee.
            request.feePerKb = Transaction.REFERENCE_DEFAULT_MIN_TX_FEE;

            //Generate the result.
            Wallet.SendResult sendResult = kit.wallet().sendCoins(kit.peerGroup(), request);

            //Broadcast.
            //Transaction transaction = result.broadcastComplete.get();

            checkNotNull(sendResult);  // We should never try to send more coins than we have!
            p.sendMessage(ChatColor.AQUA+"BTC sent successfully!");
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


}

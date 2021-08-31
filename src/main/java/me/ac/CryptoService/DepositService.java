package me.ac.CryptoService;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;
import me.ac.Main;
import me.ac.util;
import org.bitcoinj.core.*;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptChunk;
import org.bitcoinj.script.ScriptException;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DepositService extends ServiceManager{
    private final static HashMap<String, UUID> depo=new HashMap<>();

    private Player p;
    public DepositService(Main plugin,Player p) {
        super(plugin);

        this.p=p;
        if(Main.ls&&!Main.walletset){
            initializeListener();Main.ls=false;
        }
        if(!Main.walletset){
            addtransaction();
        }
    }
    private void addtransaction(){
        String w=kit.wallet().freshReceiveAddress().toString();
        p.sendMessage(ChatColor.AQUA+"Send bitcoin to address: "+ ChatColor.BOLD+ChatColor.DARK_AQUA+w);
        sendMessage(p,"Click Here To Copy The Address",w);
        depo.put(w,p.getUniqueId());
    }
    public void initializeListener() {

        kit.wallet().addCoinsReceivedEventListener(new WalletCoinsReceivedEventListener() {
            @Override
            public void onCoinsReceived(Wallet w, final Transaction tx, Coin prevBalance, org.bitcoinj.core.Coin
                    newBalance) {
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
                        p.sendMessage(ChatColor.AQUA+"BTC received, deposited ");
                        //ADD PLAYER BALANCE HERE
                        util z = new util(plugin);
                        //z.addToAccount(p.getUniqueId(), (double) value.getValue());
                        //forwardCoins(tx);
                        //String toAddress=tx.getOutput(0).getAddressFromP2PKHScript(params).toString();
                        /*List<TransactionOutput> outputs = tx.getOutputs();
                        String toAddress= Objects.requireNonNull(outputs.get(0).getAddressFromP2PKHScript(params)).toString();
                        String toAddress1= Objects.requireNonNull(outputs.get(0).getAddressFromP2SH(params)).toString();
                        if(toAddress==null){
                            toAddress=toAddress1;
                        }
                        System.out.println("Address: "+toAddress);*/
                        /*String toAddress=getWalletAddressOfSender(tx).toString();
                        System.out.println(toAddress);
                        if(depo.containsKey(toAddress)){
                            System.out.println("Adding to account.");
                            z.addToAccount(depo.get(toAddress),(double) value.getValue());
                        } else{System.out.println("Panic");}*/

                        //find from which player did the transaction belong to
                        //add to the players account

                        /*for (final TransactionOutput ti : tx.getOutputs()) {

                            try {

                                String toAddress= Objects.requireNonNull(ti.getAddressFromP2PKHScript(params)).toString();//NULLL NOT GOOD
                                String toAddress1= Objects.requireNonNull(ti.getAddressFromP2SH(params)).toString();
                                if(depo.containsKey(toAddress)){
                                    System.out.println("Adding to account.");
                                    z.addToAccount(depo.get(toAddress),(double) value.getValue());
                                } else  if(depo.containsKey(toAddress1)){
                                    z.addToAccount(depo.get(toAddress),(double) value.getValue());
                                }
                                //fromAddress=new LegacyAddress(MainNetParams.get(), pubKeyHash);

                            } catch (final ScriptException x) {
                                System.out.println(x.getMessage());
                            }
                        }*/
                        for (final TransactionOutput ti : tx.getOutputs()) {

                            //TransactionOutput ti = tx.getOutput(0);
                            String toAddress = ti.getScriptPubKey().getToAddress(params).toString();

                            System.out.println(toAddress);
                            if (depo.containsKey(toAddress)) {
                                DecimalFormat df = new DecimalFormat("##.########");
                                System.out.println("Adding to account.");
                                z.addToAccount(depo.get(toAddress), Double.parseDouble(df.format((double) ti.getValue().getValue()/100000000)));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        // This kind of future can't fail, just rethrow in case something weird happens.
                        throw new RuntimeException(t);
                    }
                }, MoreExecutors.directExecutor());
            }
        });

    }
    private void sendMessage(Player player, String message, String url) {
        Bukkit.getServer().dispatchCommand(
                Bukkit.getConsoleSender(),
                "tellraw " + player.getName() +
                        " {\"text\":\""+ " "+message +"\",\"bold\":true,\"italic\":true,\"underlined\":true,\"color\":\"dark_aqua\",\"clickEvent\":{\"action\":\"copy_to_clipboard\",\"value\":\""+url+"\"}}");
    }

    public Address getWalletAddressOfSender(final Transaction tx) {

        return null;
    }


}

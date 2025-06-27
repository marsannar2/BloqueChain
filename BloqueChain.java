import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.GsonBuilder;


import block.Block;
import transaction.Transaction;
import transaction.TransactionOutput;
import utils.BlockUtils;
import wallet.Wallet;

public class BloqueChain {

    public static List<Block> blockchain = new ArrayList<>();
    public static Map<String,TransactionOutput> UnspentTransactionOutputs = new HashMap<>();

    public static int difficulty = 5;

    public static void main(String[] args) throws Exception {


        //El hash previo será 0 debido a que es el primer bloque de la cadena
        Block firstBlock = new Block(4000,"0");
        firstBlock.mineBlock(difficulty);
        System.out.println("Hash del primer bloque: " + firstBlock .getHash());

        Block secondBlock = new Block(5000,firstBlock.getHash());
        secondBlock.mineBlock(difficulty);
        System.out.println("Hash del segundo bloque: " + secondBlock.getHash());

        Block thirdBlock = new Block(5500,secondBlock.getHash());
        thirdBlock.mineBlock(difficulty);
        System.out.println("Hash del tercer bloque: " + thirdBlock.getHash());

        //comprobamos que al modificar la información de algún bloques, la cadena de bloques deja de ser válida
        //firstBlock.setData(4500);
        //System.out.println("El hash del bloque modificado coincide con el nuevo hash? : " + BlockUtils.checkCalcutatedHash(firstBlock));

        blockchain = List.of(firstBlock, secondBlock, thirdBlock);
        System.out.println("Es la cadena de bloques válida? : " + BlockUtils.isChainValid(blockchain,difficulty)); 

        //Convertimos la cadena de bloques a formato JSON
        GsonBuilder gson = new GsonBuilder();
        String blockChainToJson = gson.setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockChainToJson);

        
        Wallet firstWallet = new Wallet();
		Wallet secondWallet = new Wallet();
        System.out.println(firstWallet.getPublicKey());
        System.out.println(secondWallet.getPublicKey());

        Transaction transaction = new Transaction(firstWallet.getPublicKey(), secondWallet.getPublicKey(), 4.0, null);
        
        transaction.createSignature(firstWallet.getPrivateKey());
        System.out.println("La transacción ha sido verificada? : " + transaction.verifySignature());

    }
    
    
}

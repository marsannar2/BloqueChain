package utils;
import java.util.ArrayList;
import java.util.List;

import block.Block;
import transaction.Transaction;

public class BlockUtils {

    public static Boolean checkCalcutatedHash(Block currentBlock){

        String currentHash = currentBlock.getHash();
        String newHash = currentBlock.createHash();

        return currentHash.equals(newHash);
    }

    public static Boolean checkPreviousHash(String currentHash,String previousHash){
        return currentHash.equals(previousHash);
    }

    public static Boolean isHashSolved(Block block,int difficulty){
        String target = "0".repeat(difficulty);
        
        return block.getHash().startsWith(target);
        
    }

    public static boolean isChainValid(List<Block> blockchain,int difficulty){
        Block currentBlock;
        Block previousBlock;

        for(int actual = 1;actual < blockchain.size();actual++){
            currentBlock = blockchain.get(actual);
            previousBlock = blockchain.get(actual-1);

            if(checkCalcutatedHash(previousBlock).equals(false)){
                System.out.println("Previous hash of block " + actual + " is not consistent with the new one");
                return false;
            }else if(checkCalcutatedHash(currentBlock).equals(false)){
                System.out.println("Current hash of block " + actual + " is not consistent with the new one");
                return false;
            }else if(checkPreviousHash(currentBlock.getPreviousHash(),previousBlock.getHash()).equals(false)){
                System.out.println("The previous block hash saved in block " + actual + " is not consistent with the new previous  block hash");
                return false;
            }else if(isHashSolved(currentBlock,difficulty).equals(false)){
                System.out.println("The block " + actual + " hasnt been mined yet");
                return false;
            }

        }

        return true;

    }

    public static String getMerkleRoot(List<Transaction> transactions){
        //partimos de momento de un árbol de merkle no binario
        List<String> previousTreeLayer = transactions.stream().map(transaction -> transaction.getHash()).toList();
        List<String> actualTreeLayer = new ArrayList<>();
        Integer count = previousTreeLayer.size();
        String sumHash;

        while(count > 1){
            for(int i = 1 ; i < previousTreeLayer.size() ; i++){
                sumHash = StringUtils.applySHA224(previousTreeLayer.get(i-1) + previousTreeLayer.get(i));
                actualTreeLayer.add(sumHash);
            }
            previousTreeLayer = actualTreeLayer;
            count = previousTreeLayer.size();
        }

        //obtenemos el hash de la raíz del árbol merkle
        String merkleRoot = actualTreeLayer.size() == 1? actualTreeLayer.get(0):"";
        return merkleRoot;
    }
    
    
}

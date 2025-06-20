package utils;
import java.util.List;

import block.Block;

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
    
    
}

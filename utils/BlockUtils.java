package utils;
import java.util.List;

import block.Block;

public class BlockUtils {

    public static Boolean checkCalcutatedHash(Block currentBlock){

        String currentHash = currentBlock.getHash();
        String newHash = currentBlock.createHash();

        return currentHash.equals(newHash)?true:false;
    }

    public static Boolean checkPreviousHash(String currentHash,String previousHash){
        return currentHash.equals(previousHash)?true:false;
    }

    public static boolean isChainValid(List<Block> blockchain){
        Block currentBlock;
        Block previousBlock;

        for(int actual = 1;actual < blockchain.size();actual++){
            currentBlock = blockchain.get(actual);
            previousBlock = blockchain.get(actual-1);
                
            if(checkCalcutatedHash(currentBlock).equals(false) || checkCalcutatedHash(previousBlock).equals(false)) return false;
            else if(checkPreviousHash(currentBlock.getPreviousHash(),previousBlock.getHash()).equals(false)) return false;
        }

        return true;

    }
    
    
}

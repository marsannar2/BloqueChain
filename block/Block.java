package block;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import transaction.Transaction;
import utils.BlockUtils;
import utils.StringUtils;
public class Block{

    private String hash;

    private String previousHash;

    // La información que contendrán serán enteros de momento;
    private List<Transaction> transactions;
    // Usaremos el timestamp para la generación del hash de cada bloque
    private long timeStamp;

    // Representa los fragmentos de cada transacción en un único hash, hace más facil la verificación del bloque
    private String merkleRoot;

    private int nonce;

    public Block(String previousHash){
        long timeStamp = new Date().getTime();
        this.transactions = new ArrayList<>();
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        this.hash = createHash();
    }
    
    public String createHash(){
        String hash = StringUtils.applySHA224( 
			previousHash +
			Long.toString(timeStamp) +
            Integer.toString(nonce) +
			merkleRoot 
			);
        return hash;
    }

    // la dificultad representa el número de ceros necesarios para crear un hash válido para un bloque
    public void mineBlock(int difficulty){
        String target = "0".repeat(difficulty);
        String tempHash = "";
        // forzamos a que el hash empiece con un número de ceros exactos
        while(!tempHash.startsWith(target)){
            nonce ++;
            tempHash = createHash();
        }

        this.merkleRoot = BlockUtils.getMerkleRoot(transactions);
        this.hash = tempHash;
    }

    public String getHash(){
        return hash;
    }

    public String getPreviousHash(){
        return previousHash;
    }

    public List<Transaction> getTransactions(){
        return transactions;
    }

    public long getTimeStamp(){
        return timeStamp;
    }

    

}
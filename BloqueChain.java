import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BloqueChain {

    public static void main(String[] args) {
    
        //El hash previo será 0 debido a que es el primer bloque de la cadena
        Block firstBlock = new Block(4000,"0");
        System.out.println("Hash del primer bloque: " + firstBlock .getHash());

        Block secondBlock = new Block(5000,firstBlock.getHash());
        System.out.println("Hash del segundo bloque: " + secondBlock.getHash());

        Block thirdBlock = new Block(5000,secondBlock.getHash());
        System.out.println("Hash del segundo bloque: " + thirdBlock.getHash());

        List<Block> blockchain = List.of(firstBlock,secondBlock,thirdBlock);

        //Convertimos la cadena de bloques a formato JSON
        GsonBuilder gson = new GsonBuilder();
        String blockChainToJson = gson.setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockChainToJson);



    }
    
}

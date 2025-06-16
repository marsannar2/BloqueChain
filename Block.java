import java.util.Date;

public class Block{

    private String hash;

    private String previousHash;

    // La información que contendrán serán enteros de momento;
    private Integer data;
    // Usaremos el timestamp para la generación del hash de cada bloque
    private long timeStamp;

    public Block(Integer data, String previousHash){
        long timeStamp = new Date().getTime();
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
    }

}
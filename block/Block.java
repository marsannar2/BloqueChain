package block;
import java.util.Date;
import utils.StringUtils;
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
        this.hash = createHash();
    }
    
    public String createHash(){
        String hash = StringUtils.applySHA224( 
			previousHash +
			Long.toString(timeStamp) +
			data 
			);
        return hash;
    }

    public String getHash(){
        return this.hash;
    }

    public String getPreviousHash(){
        return this.previousHash;
    }

    public Integer getData(){
        return this.data;
    }

    public long getTimeStamp(){
        return this.timeStamp;
    }

    public void setData(Integer data){
        this.data = data;
    }

    

}
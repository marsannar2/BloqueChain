package utils;
import java.security.MessageDigest;

public class StringUtils {
    
    public static String applySHA224(String input){
        try{
            //
            MessageDigest md = MessageDigest.getInstance("SHA-224");
            md.update(input.getBytes("UTF-8"));

            byte[] hash = md.digest();
            StringBuffer hexStr = new StringBuffer();

            for (int i = 0;i<hash.length;i++){
                String hex = Integer.toHexString(0xFF & hash[i]);
                // Asegura que el hash sea siempre de longitud fija
                if(hex.length() == 1) hexStr.append('0');
                hexStr.append(hex);
            } 

            return hexStr.toString();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        
    }
}

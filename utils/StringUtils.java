package utils;
import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.util.Base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

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

    public static byte[] applyECDSASignature(PrivateKey privateKey, String input) throws Exception{
        Security.addProvider(new BouncyCastleProvider());
        Signature dsa;
        byte [] signature = new byte[0];
        try{
            dsa = Signature.getInstance("ECDSA","BC");
            dsa.initSign(privateKey);
            byte[] strBytes = input.getBytes();
            dsa.update(strBytes);
            signature = dsa.sign();

        }catch(Exception e){
            throw new Exception("The signature could not be applied",e);
        }
        return signature;
    } 

    public static boolean verifyECDSASignature(byte[] signature, PublicKey publicKey, String data) throws Exception{
        Security.addProvider(new BouncyCastleProvider());
        try{
            Signature dsa = Signature.getInstance("ECDSA","BC");
            dsa.initVerify(publicKey);
            dsa.update(data.getBytes());
            return dsa.verify(signature);


        }catch(Exception e){
            throw new Exception("Could not verify signature",e);
        }
    }

    public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
}

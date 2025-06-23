package wallet;

import java.security.PublicKey;
import java.security.SecureRandom;

import java.security.spec.ECGenParameterSpec;


import java.security.PrivateKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;


public class Wallet {
    
    // dirección donde se reciben las transacciones
    private PublicKey publicKey;
    
    // firma digital de la cartera que controla el acceso a sus transacciones
    private PrivateKey privateKey;

    public Wallet() throws Exception{
        createKeyPair();
    }

    public void createKeyPair() throws Exception{
        try{
            //Generador de firmas con el algoritmo de curvas elípticas y proveedor BounceyCastle 
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("ECDSA","BC");
            SecureRandom random = SecureRandom.getInstance("NativePRNG");
            // Parámetros de curva elíptica inicializados con una configuración recomendada
            ECGenParameterSpec specs = new ECGenParameterSpec("sect233k1");

            kpg.initialize(specs, random);
            KeyPair keyPair = kpg.generateKeyPair();

            this.publicKey = keyPair.getPublic();
            this.privateKey = keyPair.getPrivate();

        }catch(Exception e){
            throw new Exception("No se han podido generar las claves correctamente", e);
        }
        
    }



}

package wallet;

import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.BloqueChain;
import transaction.Transaction;
import transaction.TransactionInput;
import transaction.TransactionOutput;


import java.security.PrivateKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class Wallet {
    
    // dirección donde se reciben las transacciones
    private PublicKey publicKey;
    
    // firma digital de la cartera que controla el acceso a sus transacciones
    private PrivateKey privateKey;

    private Map<String,TransactionOutput> UnspentTransactionsOutputs = new HashMap<>();

    public Wallet() throws Exception{
        createKeyPair();
    }

    public void createKeyPair() throws Exception{
        try{
            Security.addProvider(new BouncyCastleProvider());
            //Generador de firmas con el algoritmo de curvas elípticas y proveedor BounceyCastle 
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("ECDSA","BC");
            SecureRandom random = new SecureRandom();
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

    public Map<String,TransactionOutput> setUnspentTransactions(){

       Map<String,TransactionOutput> transactions = BloqueChain.UnspentTransactionOutputs;
       for(Map.Entry<String,TransactionOutput> transaction : transactions.entrySet()){
            TransactionOutput output = transaction.getValue();
            if(output.verifyOwnership(publicKey)){
                UnspentTransactionsOutputs.put(output.getId(),output);
            }
       }
       return UnspentTransactionsOutputs;
    }

    public Double getBalance(){
        Double balance = 0.0;
        this.UnspentTransactionsOutputs = setUnspentTransactions();
        for(TransactionOutput transactionOutput: UnspentTransactionsOutputs.values()){
            balance += transactionOutput.getAmount();
        }
        return balance;
        
    }

    public Transaction sendFunds(PublicKey receiver, Double value) throws Exception{
        //Se espera que el remitente tenga los fondos suficientes para realizar la transacción
        if(getBalance() < value){
            throw new Exception("Not enough funds to send transaction");
        }

        List<TransactionInput> inputs = new ArrayList<>();
        Double total = 0.0;
        for(Map.Entry<String,TransactionOutput> transaction : BloqueChain.UnspentTransactionOutputs.entrySet()){
            TransactionOutput transactionOutput = transaction.getValue();
            total += transactionOutput.getAmount();
            inputs.add(new TransactionInput(transactionOutput.getId(),transactionOutput));
            // se detiene cuando alcanza la cantidad necesaria a enviar
            if(total >= value) break;
        }

        Transaction newTransaction = new Transaction(publicKey, receiver, value, inputs);
        newTransaction.createSignature(privateKey);
        

        for(TransactionInput input:inputs) UnspentTransactionsOutputs.remove(input.getTransactionOutputId());
        
        return newTransaction;

    }

    public PublicKey getPublicKey(){
        return publicKey;
    }

    public PrivateKey getPrivateKey(){
        return privateKey;
    }

    public Map<String,TransactionOutput> getUnspentTransactions(){
        return UnspentTransactionsOutputs;
    }

}

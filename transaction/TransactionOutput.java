package transaction;

import java.security.PublicKey;

import utils.StringUtils;

public class TransactionOutput {

    private String id;

    private Double amount;

    // el propietario de esta cantidad de monedas
    private PublicKey owner; 

    // La transacción a la que está vinculada
    public String parentTransactionId;

    public TransactionOutput(String parentTransactionId, Double amount, PublicKey owner){
        this.id = StringUtils.applySHA224(StringUtils.getStringFromKey(owner) + 
            parentTransactionId +
            Double.toString(amount));
        this.parentTransactionId = parentTransactionId;
        this.amount = amount;
        this.owner = owner;
    }
    
    public PublicKey getPublicKey(){
        return owner;
    }

    public Double getAmount(){
        return amount;
    }

    public String getParentTransactionid(){
        return parentTransactionId;
    }

    public String getId(){
        return id;
    }

    public boolean verifyOwnership(PublicKey publickey){
        return getPublicKey().equals(owner);
    }



}

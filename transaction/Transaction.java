package transaction;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

import utils.StringUtils;

public class Transaction {

    public String hash;

    private PublicKey senderKey;

    private PublicKey receiverKey;

    private Double value;

    private byte [] signature;

    private ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();

	private ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    //variable que evita la existencia de transacciones con hashes iguales
    private static int sequence = 0;

    public Transaction(PublicKey senderKey,PublicKey receiverKey,Double value,ArrayList<TransactionInput> inputs){

        this.senderKey = senderKey;
        this.receiverKey = receiverKey;
        this.value = value;
        this.inputs = inputs;

    }

    private String createHash(){
        sequence++;
        return StringUtils.applySHA224(
            senderKey.toString() +
            receiverKey.toString() +
            value.toString() +
            Double.toString(sequence));
    }

    public void createSignature(PrivateKey privateKey) throws Exception{
        //Incluye toda la información que no debe ser manipulada
        String data = StringUtils.getStringFromKey(this.receiverKey) + StringUtils.getStringFromKey(this.senderKey) + Double.toString(value);
        this.signature = StringUtils.applyECDSASignature(privateKey, data);
    }

    public boolean verifySignature() throws Exception{
        String data = StringUtils.getStringFromKey(receiverKey) + StringUtils.getStringFromKey(senderKey) + Double.toString(value);
	    return StringUtils.verifyECDSASignature(signature,senderKey,data);
    }

    




    
}

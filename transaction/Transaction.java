package transaction;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import utils.StringUtils;
import main.BloqueChain;

public class Transaction {

    public String hash;

    private PublicKey senderKey;

    private PublicKey receiverKey;

    private Double value;

    private byte [] signature;

    private List<TransactionInput> inputs = new ArrayList<TransactionInput>();

	private List<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    //variable que evita la existencia de transacciones con hashes iguales
    private static int sequence = 0;

    public Transaction(PublicKey senderKey,PublicKey receiverKey,Double value,List<TransactionInput> inputs){

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

    public void processTransaction() throws Exception{
        if(verifySignature() == false){
            throw new Exception("The transaction signature does not match with the one expected");
        }

        // recuperamos las entradas de transacciones que todavía no han sido gastadas 
        for(TransactionInput input: inputs){
            input.setUTXO(BloqueChain.UnspentTransactionOutputs.get(input.getUTXO().getId()));;
        }

        Double remaining = getInputsValue() - value;
        this.hash = createHash();
        // se envía la cantidad establecida al receptor y se registra ese cambio en el remitente
        outputs.add(new TransactionOutput(hash, value, this.receiverKey));
        outputs.add(new TransactionOutput(hash, remaining, this.senderKey));

        // se registran estas salidas en la red de bloques
        for(TransactionOutput output: outputs){
            if(!BloqueChain.UnspentTransactionOutputs.containsKey(output.getId())){
                BloqueChain.UnspentTransactionOutputs.put(output.getId(),output);
            } 
        }

        //vaciamos la memoria de las salidas sin gastar de la red de bloques
        for(TransactionInput input : inputs){
            if(input.getUTXO() == null) continue;
            BloqueChain.UnspentTransactionOutputs.remove(input.getUTXO().getId());
        }

    }

    public Double getInputsValue(){
        Double inputsValue = 0.0;
        for(TransactionInput input : inputs){
            if(input.getUTXO() == null) continue;
            inputsValue += input.getUTXO().getAmount();
        }
        return inputsValue;
    }

    public Double getOutputsValue(){
        Double OutputsValue = 0.0;
        for(TransactionOutput output : outputs){
            //Solo escogemos 
            OutputsValue += output.getAmount();
        }
        return OutputsValue;
    }

    public List<TransactionInput> getInputs(){
        return inputs;
    }

    public List<TransactionOutput> getOutputs(){
        return outputs;
    }

    public Double getValue(){
        return value;
    }

    public String getHash(){
        return hash;
    }

    




    
}

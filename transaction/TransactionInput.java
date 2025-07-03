package transaction;

public class TransactionInput {

    private String transactionOutputId;

    // contiene la transacción que todavía no ha sido gastada 
    private TransactionOutput UTXO;

    public TransactionInput(String transactionOutputId,TransactionOutput UTXO){
        this.transactionOutputId = transactionOutputId;
        this.UTXO = UTXO;
    }

    public String getTransactionOutputId(){
        return transactionOutputId;
    }

    public TransactionOutput getUTXO(){
        return UTXO;
    }

    public void setUTXO(TransactionOutput UTXO){
        this.UTXO = UTXO;
    }

}

package main.com.dao;

import java.util.List;
import java.util.Optional;

import main.com.model.TransactionData;

/**
 * This is the definition of the methods that the DAO layer will expose in order
 * to fulfill the given requirements.
 * 
 * @author Miguel Isasmendi
 *
 */
public interface TransactionsDAO {
	/**
	 * Adds a transaction data to the system. It validates that at least has to
	 * have an amount and a timestamp.
	 * 
	 * @param transactionData
	 * @return optional holding the transaction that was added to the system
	 *         successfully. Else, it's an empty optional.
	 */
	public Optional<TransactionData> addTransaction(TransactionData transactionData);

	/**
	 * Retrieves all the transactions received from the client.
	 * 
	 * @return List of the transactions held in the application.
	 */
	public List<TransactionData> getTransactions();

}

package main.com.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.com.model.TransactionData;

/**
 * This is the definition of the methods that the resource layer will expose in
 * order to fulfill the requirements given.
 * 
 * @author Miguel Isasmendi
 *
 */
@RestController
@RequestMapping(path = "/transactions", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
public interface TransactionsResource {
	/**
	 * Adds new transaction data. The request body should contain all the
	 * necessary data.
	 * 
	 * @return ResponseEntity with the proper status code according to the
	 *         validation results and the operation done.
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addTransactionData(@RequestBody TransactionData transactionData);

	/**
	 * Retrieves all the transactions received from the client.
	 * 
	 * @return ResponseEntity object containing the list of the transactions
	 *         held in the application.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getTransactions();
}

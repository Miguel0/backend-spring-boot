package main.com.resource.impl;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import main.com.model.TransactionData;
import main.com.resource.TransactionsResource;
import main.com.service.TransactionsService;

/**
 * This is the implementation of the resource layer that communicates with the
 * service layer to execute certain behavior that's required according to the
 * business logic necessities.
 * 
 * @author Miguel
 *
 */
@RestController
public class TransactionsImpl implements TransactionsResource {

	@Autowired
	private TransactionsService transactionsService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<?> addTransactionData(@RequestBody TransactionData transactionData) {
		ResponseEntity<?> response = null;

		if (!transactionData.validate()) {
			return ResponseEntity.badRequest().build();
		}

		Optional<TransactionData> transactionOptional = this.transactionsService.addTransaction(transactionData);

		if (transactionOptional.isPresent()) {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(transactionOptional.get().getId()).toUri();

			response = ResponseEntity.created(location).build();
		} else {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<?> getTransactions() {
		return ResponseEntity.ok(this.transactionsService.getTransactions());
	}

}

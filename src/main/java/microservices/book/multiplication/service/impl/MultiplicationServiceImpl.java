/**
 * 
 */
package microservices.book.multiplication.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.service.MultiplicationService;
import microservices.book.multiplication.service.RandomGeneratorService;

/**
 * @author biya-bi
 *
 */
@Service
public class MultiplicationServiceImpl implements MultiplicationService {
	private RandomGeneratorService randomGeneratorService;

	@Autowired
	public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService) {
		this.randomGeneratorService = randomGeneratorService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see microservices.book.multiplication.service.MultiplicationService#
	 * createRandomMultiplication()
	 */
	@Override
	public Multiplication createRandomMultiplication() {
		int factorA = randomGeneratorService.generateRandomFactor();
		int factorB = randomGeneratorService.generateRandomFactor();
		return new Multiplication(factorA, factorB);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * microservices.book.multiplication.service.MultiplicationService#checkAttempt(
	 * microservices.book.multiplication.domain.MultiplicationResultAttempt)
	 */
	@Override
	public boolean checkAttempt(MultiplicationResultAttempt resultAttempt) {
		// Checks if it's correct
		boolean correct = resultAttempt.getResultAttempt() == resultAttempt.getMultiplication().getFactorA()
				* resultAttempt.getMultiplication().getFactorB();

		// Avoids 'hack' attempts
		Assert.isTrue(!resultAttempt.isCorrect(), "You can't send an attempt marked as correct!!");

		// Creates a copy, now setting the 'correct' field accordingly
		MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(resultAttempt.getUser(),
				resultAttempt.getMultiplication(), resultAttempt.getResultAttempt(), correct);
		
		// Returns the result
		return correct;
	}

}

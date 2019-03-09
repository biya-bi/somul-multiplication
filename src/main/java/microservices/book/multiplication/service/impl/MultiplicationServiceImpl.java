/**
 * 
 */
package microservices.book.multiplication.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.repository.MultiplicationRepository;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repository.UserRepository;
import microservices.book.multiplication.service.MultiplicationService;
import microservices.book.multiplication.service.RandomGeneratorService;

/**
 * @author biya-bi
 *
 */
@Service
public class MultiplicationServiceImpl implements MultiplicationService {
	private RandomGeneratorService randomGeneratorService;
	private MultiplicationResultAttemptRepository attemptRepository;
	private UserRepository userRepository;
	private MultiplicationRepository multiplicationRepository;

	@Autowired
	public MultiplicationServiceImpl(final RandomGeneratorService randomGeneratorService,
			final MultiplicationResultAttemptRepository attemptRepository, final UserRepository userRepository,
			MultiplicationRepository multiplicationRepository) {
		this.randomGeneratorService = randomGeneratorService;
		this.attemptRepository = attemptRepository;
		this.userRepository = userRepository;
		this.multiplicationRepository = multiplicationRepository;
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
	@Transactional
	@Override
	public boolean checkAttempt(MultiplicationResultAttempt resultAttempt) {
		// Check if the user already exists for that alias
		Optional<User> userOptional = userRepository.findByAlias(resultAttempt.getUser().getAlias());
		// Checks if it's correct
		boolean correct = resultAttempt.getResultAttempt() == resultAttempt.getMultiplication().getFactorA()
				* resultAttempt.getMultiplication().getFactorB();

		// Avoids 'hack' attempts
		Assert.isTrue(!resultAttempt.isCorrect(), "You can't send an attempt marked as correct!!");

		Optional<Multiplication> multiplicationOptional = multiplicationRepository.findByFactorAAndFactorB(
				resultAttempt.getMultiplication().getFactorA(), resultAttempt.getMultiplication().getFactorB());

		// Creates a copy, now setting the 'correct' field accordingly
		MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(
				userOptional.orElse(resultAttempt.getUser()), multiplicationOptional.orElse(resultAttempt.getMultiplication()),
				resultAttempt.getResultAttempt(), correct);

		// Stores the attempt
		attemptRepository.save(checkedAttempt);

		// Returns the result
		return correct;
	}

}

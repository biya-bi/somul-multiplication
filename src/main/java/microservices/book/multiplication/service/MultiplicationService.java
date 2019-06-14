/**
 * 
 */
package microservices.book.multiplication.service;

import java.util.List;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationAttemptCheckResult;
import microservices.book.multiplication.domain.MultiplicationAttempt;

public interface MultiplicationService {
	/**
	 * Generates a random {@link Multiplication} object.
	 *
	 * @return a multiplication of randomly generated numbers
	 */
	Multiplication createRandomMultiplication();

	/**
	 * @return a {@link MultiplicationAttemptCheckResult} representing the
	 *         correctness of a {@link MultiplicationAttempt}. A
	 *         {@link MultiplicationAttempt} is correct if the product of the
	 *         factors of its multiplication property is equal to its result
	 *         property.
	 */
	MultiplicationAttemptCheckResult checkAttempt(final MultiplicationAttempt resultAttempt);

	/**
	 * Returns the statistics about multiplication results attempted by a given
	 * user.
	 * 
	 * @param userAlias the alias of the user for whom to retrieve the statistics
	 * @return the list of {@link MultiplicationAttempt}s for the user with
	 *         the supplied alias
	 */
	List<MultiplicationAttempt> getStatsForUser(String userAlias);

	/**
	 * Returns the {@link MultiplicationAttempt} corresponding to the given
	 * ID.
	 * 
	 * @param attemptId the ID of the {@link MultiplicationAttempt} to
	 *                  retrieve.
	 * @return the {@link MultiplicationAttempt} corresponding to the given
	 *         ID.
	 */
	MultiplicationAttempt getMultiplicationResultAttempt(Long attemptId);
}
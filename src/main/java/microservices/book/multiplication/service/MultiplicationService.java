/**
 * 
 */
package microservices.book.multiplication.service;

import java.util.List;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;

public interface MultiplicationService {
	/**
	 * Generates a random {@link Multiplication} object.
	 *
	 * @return a multiplication of randomly generated numbers
	 */
	Multiplication createRandomMultiplication();

	/**
	 * @return true if the attempt matches the result of the
	 * 
	 *         multiplication, false otherwise.
	 */
	boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);

	/**
	 * Returns the statistics about multiplication results attempted by a given
	 * user.
	 * 
	 * @param userAlias the alias of the user for whom to retrieve the statistics
	 * @return the list of {@link MultiplicationResultAttempt}s for the user with
	 *         the supplied alias
	 */
	List<MultiplicationResultAttempt> getStatsForUser(String userAlias);
}
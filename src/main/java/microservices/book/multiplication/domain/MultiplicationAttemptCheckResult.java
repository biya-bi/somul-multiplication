/**
 * 
 */
package microservices.book.multiplication.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * This class represents the result of a check of the correctness of a
 * multiplication attempt.
 * 
 * @author biya-bi
 *
 */
@Getter
@RequiredArgsConstructor
public class MultiplicationAttemptCheckResult {
	private final Long attemptId;
	private final boolean correct;

	@SuppressWarnings("unused")
	private MultiplicationAttemptCheckResult() {
		this(null, false);
	}
}

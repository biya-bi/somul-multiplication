/**
 * 
 */
package microservices.book.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Identifies the attempt from a {@link User} to solve a {@link Multiplication}.
 * 
 * @author biya-bi
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class MultiplicationResultAttempt {
	private final User user;
	private final Multiplication multiplication;
	private final int resultAttempt;
	private final boolean correct;

	// Empty constructor for JSON (de)serialization
	MultiplicationResultAttempt() {
		this(null, null, -1, false);
	}
}
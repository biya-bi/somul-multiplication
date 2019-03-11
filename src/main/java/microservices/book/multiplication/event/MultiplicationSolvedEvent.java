/**
 * 
 */
package microservices.book.multiplication.event;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Event that models the fact that a
 * {@link microservices.book.multiplication.domain.Multiplication} has been
 * solved in the system. Provides some context information about the
 * multiplication.
 * 
 * @author biya-bi
 *
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class MultiplicationSolvedEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5653629641114806956L;
	private final Long multiplicationResultAttemptId;
	private final Long userId;
	private final boolean correct;
}

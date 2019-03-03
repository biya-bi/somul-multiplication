/**
 * 
 */
package microservices.book.multiplication.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author biya-bi
 *
 */
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
public final class ResultResponse {
	private final boolean correct;
}
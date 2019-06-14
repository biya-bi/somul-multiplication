/**
 * 
 */
package microservices.book.multiplication.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import microservices.book.multiplication.domain.MultiplicationAttempt;

/**
 * This interface allow us to store and retrieve {@link MultiplicationAttempt}s.
 * 
 * @author biya-bi
 *
 */
public interface MultiplicationAttemptRepository extends CrudRepository<MultiplicationAttempt, Long> {
	/**
	 * @return the latest 5 attempts for a given user, identified by their alias.
	 */
	List<MultiplicationAttempt> findTop5ByUserAliasOrderByIdDesc(String userAlias);
}

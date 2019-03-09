/**
 * 
 */
package microservices.book.multiplication.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import microservices.book.multiplication.domain.User;

/**
 * This interface allows us to save and retrieve {@link User}s.
 * 
 * @author biya-bi
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByAlias(final String alias);
}

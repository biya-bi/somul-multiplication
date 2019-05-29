/**
 * 
 */
package microservices.book.multiplication.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.repository.UserRepository;

/**
 * @author biya-bi
 *
 */
@RestController
@RequestMapping("/users")
@Slf4j
class UserController {

	private UserRepository userRepository;

	@Autowired
	UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/{alias}")
	ResponseEntity<User> getUser(@PathVariable("alias") String alias) {
		log.debug("Finding user with alias {}.", alias);
		Optional<User> user = userRepository.findByAlias(alias);
		if (!user.isPresent()) {
			log.debug("User with alias {} not found.", alias);
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get());
	}

}

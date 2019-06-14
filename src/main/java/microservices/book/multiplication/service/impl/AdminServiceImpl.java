/**
 * 
 */
package microservices.book.multiplication.service.impl;

import microservices.book.multiplication.repository.MultiplicationRepository;
import microservices.book.multiplication.repository.MultiplicationAttemptRepository;
import microservices.book.multiplication.repository.UserRepository;
import microservices.book.multiplication.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * @author @author biya-bi
 */
@Profile("test")
@Service
class AdminServiceImpl implements AdminService {

	private MultiplicationRepository multiplicationRepository;
	private MultiplicationAttemptRepository attemptRepository;
	private UserRepository userRepository;

	@Autowired
	AdminServiceImpl(final MultiplicationRepository multiplicationRepository, final UserRepository userRepository,
			final MultiplicationAttemptRepository attemptRepository) {
		this.multiplicationRepository = multiplicationRepository;
		this.userRepository = userRepository;
		this.attemptRepository = attemptRepository;
	}

	@Override
	public void deleteDatabaseContents() {
		attemptRepository.deleteAll();
		multiplicationRepository.deleteAll();
		userRepository.deleteAll();
	}
}
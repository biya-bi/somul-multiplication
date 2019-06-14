/**
 * 
 */
package microservices.book.multiplication.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationAttemptCheckResult;
import microservices.book.multiplication.domain.MultiplicationAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.event.EventDispatcher;
import microservices.book.multiplication.event.MultiplicationSolvedEvent;
import microservices.book.multiplication.repository.MultiplicationRepository;
import microservices.book.multiplication.repository.MultiplicationAttemptRepository;
import microservices.book.multiplication.repository.UserRepository;
import microservices.book.multiplication.service.RandomGeneratorService;

/**
 * @author biya-bi
 *
 */
public class MultiplicationServiceImplTest {

	private MultiplicationServiceImpl multiplicationService;

	@Mock
	private RandomGeneratorService randomGeneratorService;

	@Mock
	private MultiplicationAttemptRepository attemptRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private MultiplicationRepository multiplicationRepository;

	@Mock
	private EventDispatcher eventDispatcher;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		multiplicationService = new MultiplicationServiceImpl(randomGeneratorService, attemptRepository, userRepository,
				multiplicationRepository, eventDispatcher);
	}

	@Test
	public void createRandomMultiplicationTest() {
		// given (our mocked Random Generator service will return first 50, then 30)
		given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);
		// when
		Multiplication multiplication = multiplicationService.createRandomMultiplication();
		// then
		assertThat(multiplication.getFactorA()).isEqualTo(50);
		assertThat(multiplication.getFactorB()).isEqualTo(30);
		assertThat(multiplication.getResult()).isEqualTo(1500);
	}

	@Test
	public void checkCorrectAttemptTest() {
		// given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john_doe");
		MultiplicationAttempt attempt = new MultiplicationAttempt(user, multiplication, 3000, false);
		MultiplicationAttempt verifiedAttempt = new MultiplicationAttempt(user, multiplication, 3000, true);
		given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());
		given(multiplicationRepository.findByFactorAAndFactorB(multiplication.getFactorA(),
				multiplication.getFactorB())).willReturn(Optional.<Multiplication>of(multiplication));
		// when
		MultiplicationAttemptCheckResult checkResult = multiplicationService.checkAttempt(attempt);
		// then
		assertThat(checkResult.isCorrect()).isTrue();
		verify(attemptRepository).save(verifiedAttempt);
		verify(multiplicationRepository).findByFactorAAndFactorB(multiplication.getFactorA(),
				multiplication.getFactorB());
		verify(eventDispatcher).send(
				new MultiplicationSolvedEvent(verifiedAttempt.getId(), user.getId(), verifiedAttempt.isCorrect()));
	}

	@Test
	public void checkWrongAttemptTest() {
		// given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john_doe");
		MultiplicationAttempt attempt = new MultiplicationAttempt(user, multiplication, 3010, false);
		given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());
		given(multiplicationRepository.findByFactorAAndFactorB(multiplication.getFactorA(),
				multiplication.getFactorB())).willReturn(Optional.<Multiplication>of(multiplication));
		// when
		MultiplicationAttemptCheckResult checkResult = multiplicationService.checkAttempt(attempt);
		// then
		assertThat(checkResult.isCorrect()).isFalse();
		verify(attemptRepository).save(attempt);
		verify(multiplicationRepository).findByFactorAAndFactorB(multiplication.getFactorA(),
				multiplication.getFactorB());
		verify(eventDispatcher).send(new MultiplicationSolvedEvent(attempt.getId(), user.getId(), attempt.isCorrect()));
	}

	@Test
	public void retrieveStatsTest() {
		// given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john_doe");
		MultiplicationAttempt attempt1 = new MultiplicationAttempt(user, multiplication, 3010, false);
		MultiplicationAttempt attempt2 = new MultiplicationAttempt(user, multiplication, 3051, false);
		List<MultiplicationAttempt> latestAttempts = Lists.newArrayList(attempt1, attempt2);
		given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());
		given(attemptRepository.findTop5ByUserAliasOrderByIdDesc("john_doe")).willReturn(latestAttempts);
		// when
		List<MultiplicationAttempt> latestAttemptsResult = multiplicationService.getStatsForUser("john_doe");
		// then
		assertThat(latestAttemptsResult).isEqualTo(latestAttempts);
	}

	@Test
	public void getMultiplicationResultAttempt_MultiplicationResultAttemptWithProvidedIDExists_ReturnMultiplicationResultAttempt() {
		// Given
		long attemptId = 1;
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john_doe");
		MultiplicationAttempt expected = new MultiplicationAttempt(user, multiplication, 3000, true);

		given(attemptRepository.findOne(attemptId)).willReturn(expected);

		// When
		MultiplicationAttempt actual = multiplicationService.getMultiplicationResultAttempt(attemptId);

		// Then
		assertThat(actual).isEqualTo(expected);
	}
}

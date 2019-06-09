/**
 * 
 */
package microservices.book.multiplication.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationAttemptCheckResult;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.service.MultiplicationService;

/**
 * @author biya-bi
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MultiplicationResultAttemptController.class)
public class MultiplicationResultAttemptControllerTest {
	@MockBean
	private MultiplicationService multiplicationService;
	@Autowired
	private MockMvc mvc;
	// These objects will be magically initialized by the initFields method below.
	private JacksonTester<MultiplicationResultAttempt> jsonResult;
	private JacksonTester<List<MultiplicationResultAttempt>> jsonResultAttemptList;
	private JacksonTester<MultiplicationAttemptCheckResult> attemptCheckResultTester;

	@Before
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void postResultReturnCorrect() throws Exception {
		genericParameterizedTest(true);
	}

	@Test
	public void postResultReturnNotCorrect() throws Exception {
		genericParameterizedTest(false);
	}

	void genericParameterizedTest(final boolean correct) throws Exception {
		MultiplicationAttemptCheckResult result = new MultiplicationAttemptCheckResult(1L, correct);
		// given (remember we're not testing here the service itself)
		given(multiplicationService.checkAttempt(any(MultiplicationResultAttempt.class))).willReturn(result);
		User user = new User("john");
		Multiplication multiplication = new Multiplication(50, 70);
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, correct);
		// when
		MockHttpServletResponse response = mvc.perform(
				post("/results").contentType(MediaType.APPLICATION_JSON).content(jsonResult.write(attempt).getJson()))
				.andReturn().getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(attemptCheckResultTester.write(result).getJson());
	}

	@Test
	public void getUserStats() throws Exception {
		// given
		User user = new User("john_doe");
		Multiplication multiplication = new Multiplication(50, 70);
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, true);
		List<MultiplicationResultAttempt> recentAttempts = Lists.newArrayList(attempt, attempt);
		given(multiplicationService.getStatsForUser("john_doe")).willReturn(recentAttempts);
		// when
		MockHttpServletResponse response = mvc.perform(get("/results").param("alias", "john_doe")).andReturn()
				.getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonResultAttemptList.write(recentAttempts).getJson());
	}

	@Test
	public void getResultById_ResultIdProvided_ReturnMultiplicationResultAttempt() throws Exception {
		boolean correct = true;
		// Given
		long attemptId = 1;
		User user = new User("john_doe");
		Multiplication multiplication = new Multiplication(50, 70);
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, correct);

		given(multiplicationService.getMultiplicationResultAttempt(attemptId)).willReturn(attempt);

		// When
		MockHttpServletResponse response = mvc.perform(get("/results/" + attemptId).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonResult.write(attempt).getJson());
	}
}
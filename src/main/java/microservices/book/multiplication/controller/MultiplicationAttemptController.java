/**
 * 
 */
package microservices.book.multiplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import microservices.book.multiplication.domain.MultiplicationAttemptCheckResult;
import microservices.book.multiplication.domain.MultiplicationAttempt;
import microservices.book.multiplication.service.MultiplicationService;

/**
 * @author biya-bi
 *
 */
@RestController
@RequestMapping("/attempts")
@Slf4j
class MultiplicationAttemptController {

	private final MultiplicationService multiplicationService;
	private final int serverPort;

	@Autowired
	MultiplicationAttemptController(final MultiplicationService multiplicationService,
			final @Value("${server.port}") int serverPort) {
		this.multiplicationService = multiplicationService;
		this.serverPort = serverPort;
	}

	@PostMapping
	ResponseEntity<MultiplicationAttemptCheckResult> postResult(
			@RequestBody MultiplicationAttempt multiplicationAttempt) {
		return ResponseEntity.ok(multiplicationService.checkAttempt(multiplicationAttempt));
	}

	@GetMapping
	ResponseEntity<List<MultiplicationAttempt>> getStatistics(@RequestParam("alias") String alias) {
		return ResponseEntity.ok(multiplicationService.getStatsForUser(alias));
	}

	@GetMapping("/{attemptId}")
	ResponseEntity<MultiplicationAttempt> getResultById(@PathVariable("attemptId") Long attemptId) {
		log.info("Retrieving result {} from the server running on port {}", attemptId, serverPort);
		return ResponseEntity.ok(multiplicationService.getMultiplicationResultAttempt(attemptId));
	}
}

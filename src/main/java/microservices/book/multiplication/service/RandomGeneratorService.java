/**
 * 
 */
package microservices.book.multiplication.service;

/**
 * @author biya-bi
 *
 */
public interface RandomGeneratorService {
	/**
	 * @return a randomly-generated factor. It's always a number between 11 and 99.
	 */
	int generateRandomFactor();
}

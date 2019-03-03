/**
 * 
 */
package microservices.book.multiplication.service.impl;

import java.util.Random;

import org.springframework.stereotype.Service;

import microservices.book.multiplication.service.RandomGeneratorService;

/**
 * @author biya-bi
 *
 */
@Service
public class RandomGeneratorServiceImpl implements RandomGeneratorService {
	final static int MINIMUM_FACTOR = 11;
	final static int MAXIMUM_FACTOR = 99;

	/*
	 * (non-Javadoc)
	 * 
	 * @see microservices.book.multiplication.service.RandomGeneratorService#
	 * generateRandomFactor()
	 */
	@Override
	public int generateRandomFactor() {
		return new Random().nextInt((MAXIMUM_FACTOR - MINIMUM_FACTOR) + 1) + MINIMUM_FACTOR;
	}

}

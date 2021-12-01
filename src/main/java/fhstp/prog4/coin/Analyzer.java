package fhstp.prog4.coin;

/**
 * @author Berger Stefan, Oberndorfer Patrick, Toesch Bernhard
 * Analyzer Class
 */

import java.util.Set;

public class Analyzer {

	public static void sortByPrice(Set<Coin> coinList) {
		
		if (coinList == null) {
			throw new IllegalArgumentException("invalid CoinList");
		}
		
		System.out.println("SORTED BY PRICE");
		coinList.stream()
		.sorted((c1, c2) -> Double.compare(c2.getPrice(), c1.getPrice()))
		.forEach(c1 -> System.out.println(c1));
	}

	
	public static void sortByPercentageMove(Set<Coin> coinList) {
		
		if (coinList == null) {
			throw new IllegalArgumentException("invalid CoinList");
		}
		
		System.out.println("\nSORTED BY TOP MOVER");
		coinList.stream()
		.sorted((c1, c2) -> Double.compare(c2.getPercentage(), c1.getPercentage()))
		.limit(5)
		.forEach(c1 -> System.out.println(c1));
	}
	
	
}

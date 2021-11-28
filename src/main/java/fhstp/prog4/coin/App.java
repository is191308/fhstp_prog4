package fhstp.prog4.coin;

public class App {
    
    public static void main(String[] args) {
    	CoinList cl = new CoinList("ed40901f-9d05-4879-a2a7-b29fd7047d02");
    	if (cl.refreshCoinList()) {
    		System.out.println("All coins (sorted by price):");
    		cl.getCoinList().stream()
			.sorted((c1, c2) -> Double.compare(c2.getPriceUSD(), c1.getPriceUSD()))
			.forEach(c1 -> System.out.println(c1));
    		
    		System.out.println("\nTop movers (24h):");
    		cl.getCoinList().stream()
			.sorted((c1, c2) -> Double.compare(c2.getPercentChange24(), c1.getPercentChange24()))
			.limit(5)
			.forEach(c1 -> System.out.println(c1));
    	} else {
    		System.out.println("Failed to refresh coinList!");
    	}
    	
    }
    
}

package fhstp.prog4.coin;

public class App {
    
    public static void main(String[] args) {
    	CoinList cl = new CoinList("ed40901f-9d05-4879-a2a7-b29fd7047d02");
    	
    	if (cl.updateCoinList()) {
    		System.out.println("All coins (sorted by price):");
    		cl.getCoinListSortedByPrice().stream().forEach(System.out::println);
    		System.out.println("\nTop movers (24h):");
    		cl.getCoinListTopMover().stream().forEach(System.out::println);
    	} else {
    		System.out.println("Failed to refresh coinList!");
    	}
    	
    }
}

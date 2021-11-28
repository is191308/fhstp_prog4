package fhstp.prog4.coin;

// Coin
public class Coin {
	private String name;
	private String symbol;
	private double price_usd;
	private double percent_change_24h;
	
	private Coin(CoinBuilder coinBuilder) {
		this.name = coinBuilder.name;
        this.symbol = coinBuilder.symbol;
        this.price_usd = coinBuilder.price_usd;
        this.percent_change_24h = coinBuilder.percent_change_24h;
	}
	
	public String getName() {
        return name;
    }
    public String getSymbol() {
        return symbol;
    }
    public double getPriceUSD() {
        return price_usd;
    }
    public double getPercentChange24() {
        return percent_change_24h;
    }
 
    @Override
    public String toString() {
        return "" + this.name + " (" + this.symbol + "): " + String.format("%.2f", this.price_usd) + " USD (" + String.format("%.2f", this.percent_change_24h) +" % since 24h)";
    }
    
    @Override
    public int hashCode() {
    	return this.name.hashCode() + this.symbol.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj != null && obj.getClass() == Coin.class) {
    		if (this.name.equals(((Coin)obj).name) && this.symbol.equals(((Coin)obj).symbol) && this.price_usd == ((Coin)obj).price_usd && this.percent_change_24h == ((Coin)obj).percent_change_24h) {
    			return true;
    		}
    	}
    	return false;
    }
	
    // Builder for Coin class (static inner class)
	public static class CoinBuilder {
		private final String name;
        private final String symbol;
        private double price_usd;
    	private double percent_change_24h;
    	
        public CoinBuilder(String name, String symbol) {
        	if (name == null || name.isEmpty() || name.trim().isEmpty()) {
        		throw new IllegalArgumentException("name is empty");
        	}
        	if (symbol == null || symbol.isEmpty() || symbol.trim().isEmpty()) {
        		throw new IllegalArgumentException("symbol is empty");
        	}
            this.name = name;
            this.symbol = symbol;
        }
        public CoinBuilder priceUSD(double price_usd) {
            this.price_usd = price_usd;
            return this;
        }
        public CoinBuilder percentChange24(double percent_change_24h) {
            this.percent_change_24h = percent_change_24h;
            return this;
        }

        public Coin build() {
        	Coin coin =  new Coin(this);
        	validateCoin(coin);
            return coin;
        }
        
        private void validateCoin(Coin user) {
            if (price_usd < 0) {
            	throw new IllegalArgumentException("price_usd less than zero");
            }
        }
	}
}

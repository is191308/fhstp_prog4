package fhstp.prog4.coin;


public class Coin {

	String symbol;
	double price;
	double percentage;
	
	
	public Coin(CoinBuilder coinBuilder) {
		this.symbol = coinBuilder.symbol;
		this.price = coinBuilder.price;
		this.percentage = coinBuilder.percentage;
	}

	@Override
	public String toString() {
		return "" + this.symbol + ": " + String.format("%.6f", this.price) + " USD (" + String.format("%.2f", this.percentage) +" % since 24h)";
	}


	public String getSymbol() {
		return this.symbol;
	}


	public double getPrice() {
		return this.price;
	}


	public double getPercentage() {
		return this.percentage;
	}
	
	
	
	
// Builder for Coin class (static inner class)

public static class CoinBuilder {
	
	private String symbol;
	private double price;
	private double percentage;
	
	
	public CoinBuilder(String symbol) {
		this.symbol = symbol;
	}
	
	public CoinBuilder priceUSD(double price) {
		this.price = price;
		return this;
	}
	
	public CoinBuilder percentage(double percentage) {
		this.percentage = percentage;
		return this;
	}
	
	public Coin build() {
		Coin coin = new Coin(this);
		//validate
		return coin;
	}
	
	
	}

}


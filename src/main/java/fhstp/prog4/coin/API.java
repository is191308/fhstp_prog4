package fhstp.prog4.coin;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import fhstp.prog4.coin.Coin.CoinBuilder;


public class API {

	public static final String apiBaseURL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency";
	public static final String uri = apiBaseURL + "/listings/latest";
	private String apiKey;
	 
	public API(String apiKey) {
		if (apiKey == null || apiKey.length() != 36) {
			throw new IllegalArgumentException("Invalid apiKey");
		}
		this.apiKey = apiKey;
	}
	
	public String getApiKey() {
		return this.apiKey;
	}
	
	public  JSONArray receiveJSON() {
		
		try {
			HttpClient client = HttpClient.newBuilder().build();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).header("X-CMC_PRO_API_KEY", apiKey).build();

			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			//System.out.println(response.body().substring(0, 3000));
			
			JSONObject jsonObject = new JSONObject(response.body());
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			
			return jsonArray;
							
		} catch (IOException | InterruptedException e) {
			System.err.println("Failed to retrive data from " + uri + ": " + e.getMessage());
		}
		
		//will not be executed
		return new JSONArray();
		
		
	}
	
	
	
	
    public static Set<Coin> convertJSONToCoin(JSONArray jsonArray) {
    	Set<Coin> coinList = new HashSet<Coin>();
    	    	
  	    
    	for (Object o : jsonArray) {
    		String name = ((JSONObject) o).getString("name");
    		String symbol = ((JSONObject) o).getString("symbol");
    		double price = ((JSONObject) o).getJSONObject("quote").getJSONObject("USD").getDouble("price");
    		double percentage = ((JSONObject) o).getJSONObject("quote").getJSONObject("USD").getDouble("percent_change_24h");
    		  		
    		//init & remember
    		Coin c = new CoinBuilder(symbol, name)
    				.priceUSD(price)
    				.percentage(percentage)
    				.build();
    		
    		
    		coinList.add(c);
    		
    	}
    	
    	return coinList;
    }
	
	
	
}

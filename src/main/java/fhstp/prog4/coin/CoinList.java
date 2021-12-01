package fhstp.prog4.coin;

import fhstp.prog4.coin.Coin.CoinBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.IOException;
import org.json.*;



/**
 * @author Stefan
 * CoinList class
 */
public class CoinList {	
	public static final String apiBaseURL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency";
	private String apiKey;
	private Set<Coin> coinList;
	
	/**
	 * creates a new CoinList instance
	 * @param apiKey coinmarketcap api key
	 */
	public CoinList(String apiKey) {
		if (apiKey == null || apiKey.isEmpty()) {
			throw new IllegalArgumentException("apiKey: null or empty not allowed");
		}
		this.apiKey = apiKey;
	}

	// coinList Getter
	public Set<Coin> getCoinList() {
		return this.coinList;
	}
	
	// ApiKey Getter
	public String getApiKey() {
		return this.apiKey;
	}
	
	
	/**
	 * updates the coinList object via coinmarketcap api
	 * @return true if update was successfully, else false
	 */
	public boolean updateCoinList() {
		final String uri = apiBaseURL + "/listings/latest";

		try {
			HttpClient client = HttpClient.newBuilder().version(Version.HTTP_1_1).followRedirects(Redirect.NORMAL)
					.connectTimeout(Duration.ofSeconds(10)).build();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).header("X-CMC_PRO_API_KEY", this.apiKey)
					.build();

			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			if (response.statusCode() != 200) {
				System.err.println("Failed to retrive data from " + uri + ": " + response.statusCode());
				return false;
			}

			this.coinList = convertJsonToCoin(response.body());
			return true;
		} catch (IOException | InterruptedException e) {
			System.err.println("Failed to retrive data from " + uri + ": " + e.getMessage());
			return false;
		} catch (IllegalArgumentException iex) {
			System.err.println(iex.getMessage());
			return false;
		}
	}

	
	/**
	 * Helper method to convert jsonString to Set<Coin>
	 * @param jsonString to convert
	 * @return jsonString converted to Set<Coin>
	 */
	private static Set<Coin> convertJsonToCoin(String jsonString) {
		Set<Coin> coinl = new HashSet<Coin>();
		JSONArray coins = null;
		try {
			JSONObject jObj = new JSONObject(jsonString);
			coins = jObj.getJSONArray("data");
		} catch (JSONException jex) {
			throw new IllegalArgumentException("failed to parse string as json");
		}
		for (Object o : coins) {
			if (o instanceof JSONObject) {
				try {
					Coin c = new CoinBuilder(
							((JSONObject) o).getString("name"), 
							((JSONObject) o).getString("symbol"))
							.priceUSD(((JSONObject) o).getJSONObject("quote").getJSONObject("USD").getDouble("price"))
							.percentChange24(((JSONObject) o).getJSONObject("quote").getJSONObject("USD").getDouble("percent_change_24h"))
							.build();
					
					coinl.add(c);
					
				} catch (JSONException | IllegalArgumentException jex) {
					System.out.println("Skip invalid object!");
				}
			}
		}
		return coinl;
	}
	
	
	/**
	 * @return list of coins sorted by price (asc)
	 */
	public List<Coin> getCoinListSortedByPrice() {
		if (this.coinList == null) {
			return null;
		}
		return this.coinList.stream()
				.sorted((c1, c2) -> Double.compare(c2.getPriceUSD(), c1.getPriceUSD()))
				.toList(); // req. Java 16
	}
	
	/**
	 * @return top five movers as list of coins
	 */
	public List<Coin> getCoinListTopMover() {
		if (this.coinList == null) {
			return null;
		}
		return this.coinList.stream()
				.sorted((c1, c2) -> Double.compare(c2.getPercentChange24(), c1.getPercentChange24()))
				.limit(5)
				.toList(); // req. Java 16
	}
}
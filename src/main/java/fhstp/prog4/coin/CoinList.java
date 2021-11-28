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
import java.util.Set;
import java.io.IOException;
import org.json.*;

public class CoinList {
	public static final String apiBaseURL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency";
	private String apiKey;
	private Set<Coin> coinList;

	public CoinList(String apiKey) {
		if (apiKey == null) {
			throw new IllegalArgumentException("apiKey: null not allowed");
		}
		this.apiKey = apiKey;
	}

	public Set<Coin> getCoinList() {
		return this.coinList;
	}
	
	
	public boolean refreshCoinList() {
		final String uri = apiBaseURL + "/listings/latest";

		try {
			HttpClient client = HttpClient.newBuilder().version(Version.HTTP_1_1).followRedirects(Redirect.NORMAL)
					.connectTimeout(Duration.ofSeconds(10)).build();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).header("X-CMC_PRO_API_KEY", this.apiKey)
					.build();

			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			if (response.statusCode() != 200) {
				return false;
			}

			this.coinList = convertJsonToCoin(response.body());
			return true;
		} catch (IOException | InterruptedException e) {
			System.err.println("Failed to retrive data from " + uri + ": " + e.getMessage());
			return false;
		}
	}

	private Set<Coin> convertJsonToCoin(String jsonString) {
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
					Coin c = new CoinBuilder(((JSONObject) o).getString("name"), ((JSONObject) o).getString("symbol"))
							.priceUSD(((JSONObject) o).getJSONObject("quote").getJSONObject("USD").getDouble("price"))
							.percentChange24(((JSONObject) o).getJSONObject("quote").getJSONObject("USD").getDouble("percent_change_24h"))
							.build();
					coinl.add(c);
				} catch (JSONException | IllegalArgumentException jex) {
					jex.printStackTrace();
					System.out.println("Skip invalid object!");
				}
			}
		}
		return coinl;
	}
}
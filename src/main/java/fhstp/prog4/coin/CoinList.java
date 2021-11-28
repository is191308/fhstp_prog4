package fhstp.prog4.coin;


import java.io.IOException;
//import java.net.Authenticator;
//import java.net.HttpRetryException;
//import java.net.InetSocketAddress;
//import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
//import java.util.Map;
//import java.util.HashMap;
//import java.util.stream.*;

public class CoinList {

	
   public static void test() {

		final String apiKey = "31a76587-bd71-4b4c-968c-d8641a46b85f";
		String uri =  "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
		
	
		//Map<String, String>  authHeader = new HashMap<>();
		//authHeader.put("X-CMC_PRO_API_KEY", apiKey);
	
		try {
			HttpClient client = HttpClient.newBuilder()
				.version(Version.HTTP_1_1)
				.followRedirects(Redirect.NORMAL)
				.connectTimeout(Duration.ofSeconds(10))
				//.proxy(ProxySelector.of(new InetSocketAddress("proxy.example.com", 80)))
				//.authenticator(Authenticator.getDefault())
				.build();
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri))
				.header("X-CMC_PRO_API_KEY", apiKey)
				.build();	
	
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			System.out.println(response.statusCode());
			System.out.println(response.body());
		
		
		}
		catch ( IOException | InterruptedException e ){
			System.err.println("HTTP Exception: "+ e.getMessage());
			e.printStackTrace();
		
			}
		}
	}

	
	/*
	    catch(IOException er) {
	    	System.err.println("IOException: "+ er.getMessage());
	    	e.printStackTrace();
	    	
	    } catch (InterruptedException err) {
	    	System.err.println("InterruptedException:: "+ err.getMessage());
			e.printStackTrace();
		}
	    */  
			
		   // finally {
		    //	client.releaseConnection();
		    //}



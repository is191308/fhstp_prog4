package fhstp.prog4.coin;


import java.util.Set;
import org.json.JSONArray;


public class App {
	
    public static void main(String[] args) {
    
    	//init
    	API api = new API();
    	
    	//http & json parse
    	JSONArray jsonArray = api.receiveJSON();
    	Set<Coin> coinList = API.convertJSONToCoin(jsonArray);
    	
    	//sort
    	Analyzer.sortByPrice(coinList);
    	Analyzer.sortByPercentageMove(coinList);
    	   
    }

    
}

package fhstp.prog4.coin;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)

public class CoinTest {
	
	//JSON Object init - beinhaltet bereits "data" Teil
	private static JSONArray defaultCoinFromJSON = new JSONArray("[{\"id\":1,\"name\":\"Bitcoin\",\"symbol\":\"BTC\",\"slug\":\"bitcoin\",\"num_market_pairs\":8344,\"date_added\":\"2013-04-28T00:00:00.000Z\",\"tags\":[\"mineable\",\"pow\",\"sha-256\",\"store-of-value\",\"state-channel\",\"coinbase-ventures-portfolio\",\"three-arrows-capital-portfolio\",\"polychain-capital-portfolio\",\"binance-labs-portfolio\",\"blockchain-capital-portfolio\",\"boostvc-portfolio\",\"cms-holdings-portfolio\",\"dcg-portfolio\",\"dragonfly-capital-portfolio\",\"electric-capital-portfolio\",\"fabric-ventures-portfolio\",\"framework-ventures-portfolio\",\"galaxy-digital-portfolio\",\"huobi-capital-portfolio\",\"alameda-research-portfolio\",\"a16z-portfolio\",\"1confirmation-portfolio\",\"winklevoss-capital-portfolio\",\"usv-portfolio\",\"placeholder-ventures-portfolio\",\"pantera-capital-portfolio\",\"multicoin-capital-portfolio\",\"paradigm-portfolio\"],\"max_supply\":21000000,\"circulating_supply\":18885400,\"total_supply\":18885400,\"platform\":null,\"cmc_rank\":1,\"last_updated\":\"2021-11-28T16:08:02.000Z\",\"quote\":{\"USD\":{\"price\":54367.67567610061,\"volume_24h\":23992130960.407303,\"volume_change_24h\":-23.593,\"percent_change_1h\":-0.04389891,\"percent_change_24h\":-1.1108174,\"percent_change_7d\":-8.54946554,\"percent_change_30d\":-13.40118972,\"percent_change_60d\":30.26539625,\"percent_change_90d\":13.12273436,\"market_cap\":1026755302213.4304,\"market_cap_dominance\":41.8878,\"fully_diluted_market_cap\":1141721189198.11,\"last_updated\":\"2021-11-28T16:08:02.000Z\"}}}]}");

	@Mock
	CoinList coinListMock;
	/*
	@Mock
	Analyzer analyzerMock;
*/	
    
    @Test
    public void testInValidApiKey() {
    	assertThrows(IllegalArgumentException.class, () -> new CoinList(null));
    	assertThrows(IllegalArgumentException.class, () -> new CoinList(""));
        System.out.println("TEST PASSED: Key is invalid");
    }
    
    @Test
    public void testValidApiKey() {
    	assertDoesNotThrow(() -> new CoinList("1234-56789-123123-123132"));
        System.out.println("TEST PASSED: Key is valid");
    }
    
    @Test // with Hamcrest
    public void testApikey() {
    	assertThat(new CoinList("1234-56789-123123-123132").getApiKey(), is("1234-56789-123123-123132"));
    }

    /*//Mockito
    @Test
    public void testApiShouldReturnSameClassAsJSONArray() {
    	//prep
		when(apiMock.receiveJSON()).thenReturn(defaultCoinFromJSON);

		//test
    	assertEquals(apiMock.receiveJSON().getClass(), defaultCoinFromJSON.getClass());
    	verify(apiMock, times(1)).receiveJSON();
    	
    	System.out.println("TEST PASSED: receiveJSON() returned JSONArray Object");
     }
    
    @Test
    public void testCoinShouldBouldCorrect() {
    	//prep
    	Set<Coin> coinList = API.convertJSONToCoin(defaultCoinFromJSON);
    	
    	//test List Size
    	assertEquals(1, coinList.size());
      	
    	//test CoinBuild
    	
    	for(Coin coin : coinList) {
    		assertEquals("BTC", coin.getSymbol());
    	  	assertEquals(54367.67567610061, coin.getPrice());
    	  	assertEquals(-1.1108174, coin.getPercentage());		
     	}
    	
    	System.out.println("TEST PASSED: Coins have been built correct");
    }*/
   
    
    //demo only - no Test because methods do not return anything. (void)
    /*@Test
    public void testStream() {
    	//prep
    	Set<Coin> coinList = API.convertJSONToCoin(defaultCoinFromJSON);	
    
    	//test
    	Analyzer.sortByPrice(coinList);
    	
    }*/
    


    
}

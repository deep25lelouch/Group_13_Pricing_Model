/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TheBusiness.MarketModel;

import TheBusiness.MarketModel.Market;
import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class MarketCatalog {
// Holds all markets created in the catalog
    ArrayList<Market> markets;
    // constructor  MarketCatalog 

    public MarketCatalog() {

        markets = new ArrayList();

    }
// Creates a new market with the given name and adds it to the catalog
    public Market newMarket(String m) {

        Market market = new Market(m);
        markets.add(market);
        return market;
    }

}

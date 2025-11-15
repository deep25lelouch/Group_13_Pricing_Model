/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TheBusiness.MarketModel;

import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class ChannelCatalog {
    // Maintains a list of all channels available in the business
    ArrayList<Channel> channellist ;

    // Constructor initializes the channel list when the catalog is created
    public ChannelCatalog(){
    channellist = new ArrayList();
    }
    
    // Creates a new channel with the given type and adds it to the catalog
    public Channel newChannel(String type){
        
        Channel c = new Channel(type);
        channellist.add(c);
        return c;
    }
    
    public Channel findChannel(String type){
        
        for( Channel c: channellist ){
            
            if (c.getChannelType().equalsIgnoreCase(type)) return c;
            
        }
        return null; //not found
    }
    
    
}

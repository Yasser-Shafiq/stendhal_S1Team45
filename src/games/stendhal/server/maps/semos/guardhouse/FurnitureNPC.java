package games.stendhal.server.maps.semos.guardhouse;

import java.util.Map;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.ShopList;

public class FurnitureNPC implements ZoneConfigurator {
	
	private final ShopList shops = SingletonRepository.getShopList();
	
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildNPC(zone);
	}
	
	 private void buildNPC(final StendhalRPZone zone) {
		 		
         }

}

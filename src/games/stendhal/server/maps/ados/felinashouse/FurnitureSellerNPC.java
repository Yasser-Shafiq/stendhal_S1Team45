package games.stendhal.server.maps.ados.felinashouse;

import games.stendhal.server.entity.npc.SpeakerNPC;
import java.util.Map;
import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.ShopList;
public class FurnitureSellerNPC implements ZoneConfigurator {
			
		private final ShopList shops = SingletonRepository.getShopList();
	
		public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
			buildNPC(zone);
		}
		
		 private void buildNPC(final StendhalRPZone zone) {
	    	    final SpeakerNPC npc = new SpeakerNPC("FurnitureSeller") {
	    	    protected void creates() {
	    	    	setPath(null);
	            }
	    	   
	    	   protected void createDialog() {
	                // Lets the NPC reply with "Hallo" when a player greets him. But we could have set a custom greeting inside the ()
	                addGreeting();
	                // Lets the NPC reply when a player says "job"
	                addJob("I have a lot of furniture items for salae such as: chairs tables or pianos");
	                // Lets the NPC reply when a player asks for help
	                addHelp("Ask me #offer and I will show you what furniture I have on sale.");
	                // sell furniture
	                addReply("furniture","Please ask for my #offer.");
	                // standard goodbye
	                addGoodbye();
	            }
	    	   
	    	    };
	    	    npc.setEntityClass("welcomernpc");
	    	    npc.setDescription("That is the FurnitureSeller, he looks always have one of the best tables on sale");
	    	    npc.setPosition(6, 7);
	    	    npc.initHP(100);
	    	    zone.add(npc);
		 }
	
}

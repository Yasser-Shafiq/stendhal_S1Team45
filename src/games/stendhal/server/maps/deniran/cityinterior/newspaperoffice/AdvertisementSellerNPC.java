package games.stendhal.server.maps.deniran.cityinterior.newspaperoffice;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.ShopList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.behaviour.adder.SellerAdder;
import games.stendhal.server.entity.npc.behaviour.impl.SellerBehaviour;

import java.util.HashMap;
//import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AdvertisementSellerNPC implements ZoneConfigurator {
	private final ShopList shops = SingletonRepository.getShopList();

	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	
	@Override	
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildHaydenNPC(zone);
	}
	
	private void buildHaydenNPC(final StendhalRPZone zone){
		final SpeakerNPC Hayden = new SpeakerNPC("Hayden"){
			
			/*@Override
			protected void Path() {
				
			}*/
			
			@Override
			protected void createPath() {
				final List<Node> nodes = new LinkedList<Node>();
				setPath(new FixedPath(nodes,true));
			}
			
			@Override
			protected void createDialog() {
				addGreeting();
				addReply("advertisement!", "if you want to buy an advertisement just tell me:#buy #advertisement");
				//addQuest("Oh Sorry, i have nothing to do for you.");
				addHelp("Feel free to ask if you need any #help!");
				addJob("i am an advertisement seller in deniran city!");
				addGoodbye();
			}
		};
		
		//make NPC appeared
        final Map<String, Integer> pricesSell = new HashMap<String, Integer>();
        new SellerAdder().addSeller(Hayden, new SellerBehaviour(pricesSell));
		
        //NPC settings.
		Hayden.setEntityClass("boynpc");
		Hayden.setDescription("i guess you can't wait to buy an adveritisement!");
		Hayden.setPosition(15, 11);
		Hayden.initHP(250);
		zone.add(Hayden);
	}
}	
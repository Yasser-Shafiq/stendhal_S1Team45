package games.stendhal.server.maps.deniran.cityoutside;

import games.stendhal.common.grammar.ItemParserResult;
import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.ShopList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.behaviour.adder.ProducerAdder;
import games.stendhal.server.entity.npc.behaviour.adder.SellerAdder;
import games.stendhal.server.entity.npc.behaviour.impl.ProducerBehaviour;
import games.stendhal.server.entity.npc.behaviour.impl.SellerBehaviour;
import games.stendhal.server.entity.player.Player;

import java.util.*;


public class NewspaperNPC  implements ZoneConfigurator {
    private final ShopList shops = SingletonRepository.getShopList();


    public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
        buildNPC(zone);
    }

    private void buildNPC(final StendhalRPZone zone) {
        final SpeakerNPC npc = new SpeakerNPC("news boy") {
            @Override
            protected void createDialog() {


                addGreeting();
                addOffer("You can buy a newspaper here");
                addHelp("Ask me to buy a latest newspaper, if you need one, just tell me #buy #newspaper");
                addReply("potions","Please ask for my #offer.");
                addGoodbye();

            }
            protected void createPath() {
                setPath(null);
            }
        };

        // sells
        final Map<String, Integer> pricesSell = new HashMap<String, Integer>();
        pricesSell.put("mithril shield", 1);
        pricesSell.put("golden blade", 1);
        pricesSell.put("black book",1);
        pricesSell.put("obsidian",1);
        pricesSell.put("royal legs",1);
        pricesSell.put("newspaper",1);
        pricesSell.put("bestiary",1);
        new SellerAdder().addSeller(npc, new SellerBehaviour(pricesSell));



        // This determines how the NPC will look like. welcomernpc.png is a picture in data/sprites/npc/
        npc.setEntityClass("barmannpc");
        // set a description for when a player does 'Look'
        npc.setDescription("You see a newspaper man, you can buy a latest newspaper here.");
        npc.setPosition(18,108);
        npc.initHP(10000);
        npc.setLevel(100);
        zone.add(npc);
    }


}
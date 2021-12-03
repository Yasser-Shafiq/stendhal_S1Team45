package games.stendhal.server.maps.deniran.cityinterior.newspaperoffice;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.deniran.cityinterior.newspaperoffice.AdvertisementSellerNPC;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.RPClass.ItemTestHelper;

public class AdvertisementSellerNPCTest {
    private Player player = null;
    private SpeakerNPC npc = null;
    private Engine en = null;

    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ItemTestHelper.generateRPClasses();
        QuestHelper.setUpBeforeClass();
    }

    @Before
    public void setUp() {
        StendhalRPZone zone = new StendhalRPZone("admin_test");
        new AdvertisementSellerNPC().configureZone(zone, null);

    }    
	@Test
	public void test() {
        player = PlayerTestHelper.createPlayer("testNPC");
        npc = SingletonRepository.getNPCList().get("Hayden");
        en = npc.getEngine();

        en.step(player, "hi");
        assertEquals("Greetings! How may I help you?", getReply(npc));

        en.step(player, "buy advertisement");
        assertEquals("Sorry, I don't sell advertisements."  , getReply(npc));
	}

}

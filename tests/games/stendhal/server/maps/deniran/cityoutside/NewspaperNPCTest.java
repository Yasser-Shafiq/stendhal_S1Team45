package games.stendhal.server.maps.deniran.cityoutside;
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
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.RPClass.ItemTestHelper;



public class NewspaperNPCTest {



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
        new NewspaperNPC().configureZone(zone, null);

    }
    @Test
    public void testNPC() {
        player = PlayerTestHelper.createPlayer("bob");
        npc = SingletonRepository.getNPCList().get("newspaper seller");
        en = npc.getEngine();


        assertEquals(0, player.getNumberOfEquipped("newspaper"));
        en.step(player, "hi");
        assertEquals("Greetings! How may I help you?", getReply(npc));

        en.step(player, "buy newspaper");
        assertEquals("A newspaper will cost 10. Do you want to buy it?", getReply(npc));
       en.step(player, "no");
       assertEquals(getReply(npc),"Ok, how else may I help you?");

    }
    }


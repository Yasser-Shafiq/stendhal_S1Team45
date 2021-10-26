package games.stendhal.server.maps.quests;
import static org.junit.Assert.assertEquals;
import static utilities.SpeakerNPCTestHelper.getReply;

import java.awt.Point;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.maps.nalwor.forest.Dojo;
import games.stendhal.server.maps.nalwor.forest.TrainingArea;
import games.stendhal.server.maps.nalwor.forest.TrainingArea.TrainerNPC;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import utilities.PlayerTestHelper;

public class OmuraFeeTest {
	
    private Player player = null;
    private SpeakerNPC npc = null;
    private Engine en = null;
    private static TrainingArea dojoArea;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	MockStendlRPWorld.get();
		Log4J.init();
    }
    
    @Before
    public void setUp() {
		final StendhalRPZone zone = new StendhalRPZone("admin_test");
		new Dojo().configureZone(zone, null);
        player = PlayerTestHelper.createPlayer("Jim");
        npc = SingletonRepository.getNPCList().get("Omura Sumitada");
        dojoArea = new TrainingArea("dojo", zone, 5, 52, 35, 20, new TrainerNPC("","",""), new Point(22, 74), new Point(22, 72), Direction.DOWN);
        en = npc.getEngine();
    }
    
    @Test
    public void testQuest() {
        en.step(player, "hi");
        assertEquals("This is the assassins' dojo.", getReply(npc));
        player.setAtk(10);
        player.setLevel(0);
        en.step(player, "fee");
        assertEquals("At your level of experience, your attack strength is too high to train here at this time.", getReply(npc));
        player.setAtk(10);
        player.setLevel(11);
        en.step(player, "fee");
        assertEquals("The fee to #train for your skill level is " + dojoArea.calculateFee(player.getAtk()) + " money.", getReply(npc));
        en.step(player,"bye");
        assertEquals("Bye.",getReply(npc));

    }
}

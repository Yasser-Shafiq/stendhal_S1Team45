package games.stendhal.server.maps.quests;
import static org.junit.Assert.assertEquals;
import static utilities.SpeakerNPCTestHelper.getReply;

import games.stendhal.server.maps.semos.library.HistorianGeographerNPC;
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

public class MeetZynnTest {

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
        new HistorianGeographerNPC().configureZone(zone, null);
    }

    @Test
    public void testQuest() {
        player = PlayerTestHelper.createPlayer("bob");
        npc = SingletonRepository.getNPCList().get("Zynn Iwuhos");
        en = npc.getEngine();
        AbstractQuest quest = new MeetZynn();
        quest.addToWorld();
        en.step(player, "hi");
        assertEquals("Hi, potential reader! Here you can find records of the history of Semos, and lots of interesting facts about this island of Faiumoni. If you like, I can give you a quick introduction to its #geography and #history! I also keep up with the #news, so feel free to ask me about that.",getReply(npc));
        assertEquals(0, player.getXP());
        en.step(player, "help");
        assertEquals("I can best help you by sharing my knowledge of Faiumoni's #geography and #history, as well as the latest #news.",getReply(npc));
        en.step(player,"news");
        assertEquals(5,player.getXP());
        player.setLevel(100);
        assertEquals(100,player.getLevel());
        en.step(player,"hi");
        en.step(player,"bye");
        assertEquals("Bye. Hey, you should consider getting a library card, you know.",getReply(npc));

    }
}

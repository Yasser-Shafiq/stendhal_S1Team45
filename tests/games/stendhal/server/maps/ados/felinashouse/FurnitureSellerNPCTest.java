package games.stendhal.server.maps.ados.felinashouse;

import utilities.ZonePlayerAndNPCTestImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.RPClass.CatTestHelper;
public class FurnitureSellerNPCTest extends ZonePlayerAndNPCTestImpl {
	
	
	private static final String ZONE_NAME = "int_ados_felinas_house";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		CatTestHelper.generateRPClasses();
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}
	
	//testing
	public FurnitureSellerNPCTest() {
		setNpcNames("FurnitureSeller");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new FurnitureSellerNPC(), ZONE_NAME);
	}
	//another test
	
	@Test
	public void testSellFurniture() {
		final SpeakerNPC npc = getNPC("FurnitureSeller");
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "hi"));
		assertEquals("Greetings! How may I help you?", getReply(npc));

	}
	//finished test
	

}
package games.stendhal.server.entity.item;

//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import utilities.PlayerTestHelper;
import utilities.RPClass.ItemTestHelper;

public class SleepingBagTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Log4J.init();
		MockStendlRPWorld.get();
		ItemTestHelper.generateRPClasses();
	}
	
	/**
	 * Test for adding sleeping bag to inventory
	 */
	@Test
	public void testAddToSlotUnmarked() {
		final Player bill = PlayerTestHelper.createPlayer("bill");
		final SleepingBag sleepingBag = (SleepingBag) SingletonRepository.getEntityManager().getItem("sleeping bag");

		bill.equip("bag", sleepingBag);

		assertNotNull(bill.getTotalNumberOf("sleeping bag"));
	}
	
	/**
	 * Test for onUsed 
	 */
	@Test
	public void testOnUsed() {
		String name = "sleeping bag";
		String clazz = "";
		String subclass = "";
		Map<String, String> attributes = new HashMap<String, String>();
		Player testPlayer = PlayerTestHelper.createPlayer("bob");
		final SleepingBag bag = new SleepingBag(name, clazz, subclass, attributes);
		testPlayer.equip("bag", bag);

		assertTrue(bag.onUsed(testPlayer));

	}
		
}

package games.stendhal.server.entity.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
		final WeddingRing sleepingBag = (WeddingRing) SingletonRepository.getEntityManager().getItem("sleeping bag");

		bill.equip("bag", sleepingBag);

		assertNotNull(bill.getAllEquipped("sleeping bag"));
		assertEquals(bill.getAllEquipped("sleeping bag").size(), 1);
	}
	
	/**
	 * Test for onUsed 
	 */
	@Test
	public void testOnUsed() {
		final Player testPlayer = PlayerTestHelper.createPlayer("bob");
		final SleepingBag testSBag = (SleepingBag) SingletonRepository.getEntityManager().getItem("sleeping bag");
		testPlayer.equip("bag", testSBag);
		
		assertTrue(testSBag.onUsed(testPlayer));
	}
}

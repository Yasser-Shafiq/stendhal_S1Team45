package games.stendhal.server.entity.item;

import static org.junit.Assert.assertNotNull;


import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import utilities.PlayerTestHelper;
import utilities.RPClass.ItemTestHelper;

public class ChairTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Log4J.init();
		MockStendlRPWorld.get();
		ItemTestHelper.generateRPClasses();
	}
	
	@Test
	public void testAddToSlotUnmarked() {
		final Player bill = PlayerTestHelper.createPlayer("bill");
		final Item chair = SingletonRepository.getEntityManager().getItem("chair");

		bill.equip("bag", chair);
		
		assertNotNull(bill.getTotalNumberOf("chair"));
	}
	
}

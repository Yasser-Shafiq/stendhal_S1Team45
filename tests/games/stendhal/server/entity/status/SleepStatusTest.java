package games.stendhal.server.entity.status;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;
import games.stendhal.server.entity.player.Player;

public class SleepStatusTest {

	@BeforeClass
	public static void setUpBeforeClass() {
		MockStendlRPWorld.get();
	}
	
	/**
	 * Tests for sleeping status on player
	 */
	@Test
	public void testSleeping() {
		final Player testPlayer = PlayerTestHelper.createPlayer("bob");
		SleepStatus status = new SleepStatus();
		SleepStatusHandler statusHandler = new SleepStatusHandler();
		
		statusHandler.inflict(status, testPlayer.getStatusList(), null);
		
		assertTrue(testPlayer.hasStatus(StatusType.SLEEPING));
	}

	/**
	 * Test for removing sleep status
	 */
	@Test
	public void testRemove() {
		
		final Player testPlayer = PlayerTestHelper.createPlayer("bob");
		SleepStatus status = new SleepStatus();
		SleepStatusHandler statusHandler = new SleepStatusHandler();
		statusHandler.inflict(status, testPlayer.getStatusList(), null);
		
		statusHandler.remove(status, testPlayer.getStatusList());
		
		assertFalse(testPlayer.hasStatus(StatusType.SLEEPING));
		
	}
}

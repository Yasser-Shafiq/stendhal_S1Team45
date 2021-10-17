package games.stendhal.server.events;

import org.junit.Before;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import games.stendhal.server.entity.player.Player;
import utilities.PlayerTestHelper;

public class BestiaryEventTest {
	
	Player player = null;
	
	@Before
	public void setUp() throws Exception {
		player = PlayerTestHelper.createPlayer("player");
	}
	
	@Test
	public void init() {
		testBestiaryFluff();
	}
	
	private void testBestiaryFluff() {
		
		// Arrange
		boolean unknownShown;
		player.setKeyedSlot("!kills", "solo.deer", "1001");
		
		// Act
		BestiaryEvent testE = new BestiaryEvent(player);
		String bestiaryString = testE.get("enemies");
		if (bestiaryString.startsWith("???")) {
			unknownShown = true;
		}
		else {
			unknownShown = false;
		}
		
		// Test
		/* bestiaryString should not start with ??? 
		 * ??? entries are clogging Bestiary UI
		 */
		assertFalse(unknownShown);
		
	}
}

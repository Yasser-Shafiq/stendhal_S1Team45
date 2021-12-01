package games.stendhal.server.entity.item;

import java.util.Map;

import games.stendhal.server.entity.RPEntity;

/**
 * A sleeping bag that allows players to sleep, making them unable to move.
 * 
 * Heals the player while being used.
 *
 */

public class SleepingBag extends Item {
	
	public SleepingBag(final String name, final String clazz, final String subclass, final Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
		setMenu("Sleep");
	}
	
	@Override
	public boolean onUsed(final RPEntity user) {
		return false;
	}
}

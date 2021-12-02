package games.stendhal.server.entity.item;

import java.util.Map;

import games.stendhal.server.entity.RPEntity;
import java.util.concurrent.TimeUnit;

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
	
	public SleepingBag(final SleepingBag item) {
		super(item);
	}
	
	@Override
	public boolean onUsed(final RPEntity user) {
		int currentHP = user.getHP();
		int maxHP  = user.getBaseHP();
		
		while (currentHP < maxHP) {
			user.heal(maxHP/100);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
}

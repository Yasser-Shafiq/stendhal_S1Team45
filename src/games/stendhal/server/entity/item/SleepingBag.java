package games.stendhal.server.entity.item;

import java.util.Map;
import java.util.concurrent.TimeUnit;

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
		setMenu("Use");
	}
	
	public SleepingBag(final SleepingBag item) {
		super(item);
	}
	
	@Override
	public boolean onUsed(final RPEntity user) {
		
		int maxHp = user.getBaseHP();
		int currentHp  = user.getHP();
		int healAmount = 1/100 * maxHp;
		
		while(currentHp < maxHp) {
			user.heal(healAmount);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
}

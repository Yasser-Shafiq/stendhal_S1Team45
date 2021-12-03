package games.stendhal.server.entity.item;

import java.util.Map;

//import games.stendhal.server.core.engine.SingletonRepository;
//import games.stendhal.server.core.events.TurnNotifier;
import games.stendhal.server.entity.RPEntity;
//import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.status.SleepStatus;
import games.stendhal.server.entity.status.StatusType;

/**
 * A sleeping bag that allows players to sleep, making them unable to move.
 * 
 * Heals the player while being used.
 *
 */

public class SleepingBag extends Item {
	
	/**
	 * Creates a new sleeping abg
	 *
	 * @param name
	 * @param clazz
	 * @param subclass
	 * @param attributes
	 */
	public SleepingBag(final String name, final String clazz, final String subclass, final Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
		setMenu("Use");
	}
	
	public SleepingBag(final SleepingBag item) {
		super(item);
	}
	
	/**
	 * Heals the player in increments of 3
	 * 
	 * @param user The player
	 * 
	 * @return true
	 */
	@Override
	public boolean onUsed(final RPEntity user) {
		
		if (user.getStatusList().hasStatus(StatusType.SLEEPING)) {
			user.getStatusList().removeAll(SleepStatus.class);
		} else {
			SleepStatus sleep = new SleepStatus(1000, 10, 3);
			user.getStatusList().inflictStatus(sleep, null);
		}
		
		return true;
	}
}

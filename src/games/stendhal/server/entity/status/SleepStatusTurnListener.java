package games.stendhal.server.entity.status;

import java.util.Collections;
import java.util.List;

import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.core.events.TurnNotifier;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;

public class SleepStatusTurnListener implements TurnListener {
	private StatusList statusList;
	private static final String ATTRIBUTE_NAME = "status_sleep";
	
	/**
	 * SleepStatusTurnListener
	 *
	 * @param statusList StatusList
	 */
	public SleepStatusTurnListener(StatusList statusList) {
		this.statusList = statusList;
	}
	
	@Override
	public void onTurnReached(int turn) {
		RPEntity entity = statusList.getEntity();
		List<SleepStatus> toConsume = statusList.getAllStatusByClass(SleepStatus.class);
		SleepStatus status = statusList.getFirstStatusByClass(SleepStatus.class);

		
		// check that the entity exists
		if (entity == null || status == null) {
			return;
		}
		
		// cleanup status
		if (toConsume.isEmpty()) {
			if (entity.has(ATTRIBUTE_NAME)) {
				entity.remove(ATTRIBUTE_NAME);
			}
			entity.notifyWorldAboutChanges();
			return;
		}
		
		Collections.sort(toConsume);
		final ConsumableStatus sleep = toConsume.get(0);
		
		if (turn % sleep.getFrecuency() == 0) {
			final int amount = sleep.consume();
			entity.put(ATTRIBUTE_NAME, amount);
			entity.notifyWorldAboutChanges();
			
			// is full hp?
			if (entity.heal(amount, true) == 0) {
				if (entity instanceof Player) {
					statusList.removeAll(SleepStatus.class);
				}
			}
			
			if (entity.getBaseHP() == entity.getHP()) {
				statusList.removeAll(SleepStatus.class);
			}
		}
		
		// Stop the player's movement
		int stepsTaken = entity.getStepsTaken();
		if (stepsTaken >= 0) {
			if (entity instanceof Player) {
				((Player) entity).forceStop();
			} else {
				entity.stop();
			}
			entity.clearPath();
		}
		
		TurnNotifier.get().notifyInTurns(0, this);
		
	}
}

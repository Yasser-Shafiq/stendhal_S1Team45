package games.stendhal.server.entity.status;

import games.stendhal.common.NotificationType;
import games.stendhal.server.core.events.TurnNotifier;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.RPEntity;

public class SleepStatusHandler implements StatusHandler<SleepStatus> {
	
	private StatusRemover remover;
	
	/**
	 * Inflicts a status
	 * 
	 * @param status Status to inflict
	 * @param statusList StatusList to add the status onto
	 * @param attacker Entity inflicting status (can be null)
	 */
	@Override
	public void inflict(SleepStatus status, StatusList statusList, Entity attacker) {
		if (!statusList.hasStatus(status.getStatusType())) {
			RPEntity entity = statusList.getEntity();
			if (entity != null) {
				if (attacker == null) {
					entity.sendPrivateText(NotificationType.SCENE_SETTING, "You are now sleeping.");
				} else {
					entity.sendPrivateText(NotificationType.SCENE_SETTING, "You have been put to sleep by " + attacker.getName() + ".");
				}
				statusList.addInternal(status);
				statusList.activateStatusAttribute("status_" + status.getName());
			}
		}
		
	}
	
	/**
	 * Removes a status
	 * 
	 * @param status Status to remove
	 * @param statusList StatusList to remove status from
	 */
	@Override
	public void remove(SleepStatus status, StatusList statusList) {
		statusList.removeInternal(status);
		
		final RPEntity entity = statusList.getEntity();
		if (entity == null) {
			return;
		}

		entity.sendPrivateText(NotificationType.SCENE_SETTING, "You are no longer sleeping.");
		entity.remove("status_" + status.getName());

		// disable pending notifications
		if (remover != null) {
			TurnNotifier.get().dontNotify(remover);
			remover = null;
		}
	}
}

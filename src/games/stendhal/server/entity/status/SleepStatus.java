package games.stendhal.server.entity.status;

/**
 * A status effect that causes entity to sleep
 */

public class SleepStatus extends Status{
	
	public SleepStatus() {
		super("sleep");
	}
	
	@Override
	public StatusType getStatusType() {
		return null;
	}
}

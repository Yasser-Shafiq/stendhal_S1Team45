package games.stendhal.server.entity.status;

/**
 * A status effect that causes entity to sleep
 */

public class SleepStatus extends ConsumableStatus{
	
	
	/** 
	 * Creates sleep status
	 */
	public SleepStatus(int amount, int frequency, int regen) {
		super("sleep", amount, frequency, regen);
	}
	
	/**
	 * Returns status type (SLEEPING)
	 * 
	 * @return StatusType
	 */
	@Override
	public StatusType getStatusType() {
		return StatusType.SLEEPING;
	}
}

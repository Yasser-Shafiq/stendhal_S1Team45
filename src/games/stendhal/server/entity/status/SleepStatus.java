package games.stendhal.server.entity.status;

/**
 * A status effect that causes entity to sleep
 */

public class SleepStatus extends Status{
	
	
	/** 
	 * Creates sleep status
	 */
	public SleepStatus() {
		super("sleep");
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

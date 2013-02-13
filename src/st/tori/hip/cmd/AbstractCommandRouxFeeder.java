package st.tori.hip.cmd;

import st.tori.hip.cmd.exception.CommandExecException;

public abstract class AbstractCommandRouxFeeder extends AbstractHipServerCommand {

	public static final int ACTION_WATER_STATUS	 = 1;
	public static final int ACTION_WATER_ADD	 = 2;
	public static final int ACTION_PUMP_STATUS	 = 3;
	public static final int ACTION_PUMP_TURNON	 = 4;
	public static final int ACTION_PUMP_TURNOFF	 = 5;
	public static final int ACTION_DRY_STATUS	 = 6;
	public static final int ACTION_DRY_ADD		 = 7;
	public static final int ACTION_WET_STATUS	 = 8;
	public static final int ACTION_WET_ADD		 = 9;
	public static final int ACTION_TEMPERATURE	 = 10;
	public static final int ACTION_SERVO		 = 11;
	
	protected abstract int getPatternIndex();
	
	@Override
	protected int getCommandId() {
		return COMMAND_ID_ROUX_FEEDER;
	}
	
	@Override
	protected String getCommandValue(String keyword) throws CommandExecException {
		return Integer.toString(getPatternIndex());
	}

}

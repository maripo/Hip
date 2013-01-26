package st.tori.hip.cmd;

import st.tori.hip.cmd.exception.CommandExecException;

public abstract class AbstractCommandEmitIr extends AbstractHipServerCommand {

	public static final int PATTERN_INDEX_1	 = 1;
	public static final int PATTERN_INDEX_2	 = 2;
	public static final int PATTERN_INDEX_3	 = 3;
	public static final int PATTERN_INDEX_4	 = 4;
	public static final int PATTERN_INDEX_5	 = 5;
	public static final int PATTERN_INDEX_6	 = 6;
	
	protected abstract int getPatternIndex();
	
	@Override
	protected int getCommandId() {
		return COMMAND_ID_EMIT_IR;
	}
	
	@Override
	protected String getCommandValue(String keyword) throws CommandExecException {
		return Integer.toString(getPatternIndex());
	}

}

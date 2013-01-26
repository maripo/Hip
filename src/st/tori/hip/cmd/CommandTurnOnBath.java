package st.tori.hip.cmd;

import st.tori.hip.cmd.exception.CommandExecException;

public class CommandTurnOnBath extends AbstractHipServerCommand {

	@Override
	protected int getCommandId() {
		return COMMAND_ID_TURN_ON_BATH;
	}
	
	@Override
	public boolean isMyKeyword(String keyword) {
		return (keyword.startsWith("サーボ")||keyword.startsWith("風呂")||keyword.startsWith("bath"));
	}

	@Override
	protected String getCommandValue(String keyword) throws CommandExecException {
		return null;
	}

}

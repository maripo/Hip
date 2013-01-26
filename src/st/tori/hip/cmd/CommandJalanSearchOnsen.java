package st.tori.hip.cmd;

import st.tori.hip.cmd.exception.CommandExecException;

public class CommandJalanSearchOnsen extends AbstractHipServerCommand {

	@Override
	protected int getCommandId() {
		return COMMAND_ID_JALAN_SEARCH_ONSEN;
	}
	
	@Override
	public boolean isMyKeyword(String keyword) {
		return (keyword.indexOf("温泉")>=0||keyword.indexOf("hotspring")>=0);
	}

	@Override
	protected String getCommandValue(String keyword) throws CommandExecException {
		return null;
	}

}

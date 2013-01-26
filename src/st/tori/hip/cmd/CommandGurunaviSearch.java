package st.tori.hip.cmd;

import st.tori.hip.cmd.exception.CommandExecException;

public class CommandGurunaviSearch extends AbstractHipServerCommand {

	@Override
	protected int getCommandId() {
		return COMMAND_ID_GURUNAVI_SEARCH;
	}
	
	@Override
	public boolean isMyKeyword(String keyword) {
		return (keyword.indexOf("お店")>=0||keyword.indexOf("shop")>=0);
	}

	@Override
	protected String getCommandValue(String keyword) throws CommandExecException {
		return null;
	}

}

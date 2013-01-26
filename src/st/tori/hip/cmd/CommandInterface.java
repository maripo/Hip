package st.tori.hip.cmd;

import st.tori.hip.cmd.exception.CommandExecException;

public interface CommandInterface {

	boolean isMyKeyword(String keyword);
	
	CommandResultInterface exec(String keyword) throws CommandExecException;
	
}

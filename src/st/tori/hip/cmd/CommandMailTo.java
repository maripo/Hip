package st.tori.hip.cmd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import st.tori.hip.cmd.exception.CommandExecException;

public class CommandMailTo extends AbstractHipServerCommand {

	@Override
	protected int getCommandId() {
		return COMMAND_ID_MAIL_TO;
	}
	
	@Override
	public boolean isMyKeyword(String keyword) {
		return (keyword.endsWith("にメール")||keyword.startsWith("mail to "));
	}

	private static final Pattern PATTERN_NI_MAIL	 = Pattern.compile("^(.*)にメール");
	private static final Pattern PATTERN_MAIL_TO	 = Pattern.compile("mail to (.*)$");
	@Override
	protected String getCommandValue(String keyword) throws CommandExecException {
		Matcher m;
		m = PATTERN_NI_MAIL.matcher(keyword);
		if(m.find())
			return m.group(1);
		m = PATTERN_MAIL_TO.matcher(keyword);
		if(m.find())
			return m.group(1);
		throw new CommandExecException("No target found");
	}

}

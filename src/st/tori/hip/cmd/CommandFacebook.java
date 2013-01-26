package st.tori.hip.cmd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import st.tori.hip.cmd.exception.CommandExecException;

public class CommandFacebook extends AbstractHipServerCommand {

	@Override
	protected int getCommandId() {
		return COMMAND_ID_FACEBOOK;
	}
	
	@Override
	public boolean isMyKeyword(String keyword) {
		return (keyword.startsWith("フェイスブック")||keyword.startsWith("facebook "));
	}

	private static final Pattern PATTERN_JP	 = Pattern.compile("^フェイスブック *(.*)$");
	private static final Pattern PATTERN_EN	 = Pattern.compile("^facebook (.*)$");
	@Override
	protected String getCommandValue(String keyword) throws CommandExecException {
		Matcher m;
		m = PATTERN_JP.matcher(keyword);
		if(m.find())
			return m.group(1);
		m = PATTERN_EN.matcher(keyword);
		if(m.find())
			return m.group(1);
		throw new CommandExecException("No target found");
	}

}

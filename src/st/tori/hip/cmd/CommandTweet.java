package st.tori.hip.cmd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import st.tori.hip.cmd.exception.CommandExecException;

public class CommandTweet extends AbstractHipServerCommand {

	@Override
	protected int getCommandId() {
		return COMMAND_ID_TWEET;
	}
	
	@Override
	public boolean isMyKeyword(String keyword) {
		return (keyword.startsWith("ツイート")||keyword.startsWith("tweet"));
	}

	private static final Pattern PATTERN_JP	 = Pattern.compile("^ツイート *(.*)$");
	private static final Pattern PATTERN_EN	 = Pattern.compile("^tweet *(.*)$");
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

package st.tori.hip.cmd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import st.tori.hip.cmd.exception.CommandExecException;

public class CommandCreateEvernoteMemo extends AbstractHipServerCommand {

	@Override
	protected int getCommandId() {
		return COMMAND_ID_CREATE_EVERNOTE_MEMO;
	}
	
	@Override
	public boolean isMyKeyword(String keyword) {
		return (keyword.startsWith("メモ")||keyword.startsWith("memo "));
	}

	private static final Pattern PATTERN_JP	 = Pattern.compile("^メモ *(.*)$");
	private static final Pattern PATTERN_EN	 = Pattern.compile("^memo (.*)$");
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

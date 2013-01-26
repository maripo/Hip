package st.tori.hip.cmd;

import st.tori.hip.cmd.exception.CommandExecException;

public class CommandSpeachStub implements CommandInterface {

	@Override
	public boolean isMyKeyword(String keyword) {
		return true;
	}

	@Override
	public CommandResultInterface exec(String keyword)
			throws CommandExecException {
		return new CommandResultTextToSpeechInterface() {
			@Override
			public String getSpeechText() {
				return "ぬるぽぬるぽ";
			}
		};
	}

}

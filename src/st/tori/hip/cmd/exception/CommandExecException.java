package st.tori.hip.cmd.exception;

public class CommandExecException extends Exception {

	private static final long serialVersionUID = -5972626996951942639L;

	private String reason;
	
	public CommandExecException(String reason) {
		this.reason = reason;
	}
	
	@Override
	public String getMessage() {
		return reason;
	}
	
}

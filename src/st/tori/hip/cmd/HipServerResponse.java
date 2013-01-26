package st.tori.hip.cmd;

public class HipServerResponse implements CommandResultTextToSpeechInterface {

	private String speechText = null;
	
	public HipServerResponse(String body) {
		if(body==null)return;
		speechText = body.replaceAll("\n.*", "");
	}
	
	@Override
	public String getSpeechText() {
		return speechText;
	}
}

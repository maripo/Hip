package st.tori.hip.cmd;

public class CommandEmitIrPattern4 extends AbstractCommandEmitIr {

	@Override
	protected int getPatternIndex() {
		return PATTERN_INDEX_4;
	}
	
	@Override
	public boolean isMyKeyword(String keyword) {
		return (keyword.indexOf("電気つけて")>=0||keyword.indexOf("turn on the light")>=0);
	}


}

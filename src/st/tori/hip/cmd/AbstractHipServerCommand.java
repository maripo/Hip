package st.tori.hip.cmd;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import st.tori.hip.cmd.exception.CommandExecException;

public abstract class AbstractHipServerCommand implements CommandInterface {

	public static final String PARAM_NAME_COMMAND_ID	 = "cid";
	public static final String PARAM_NAME_COMMAND_VALUE	 = "val";
	
	public static final int COMMAND_ID_CREATE_EVERNOTE_MEMO = 100;
	public static final int COMMAND_ID_MAIL_TO = 200;
	public static final int COMMAND_ID_TWEET = 300;
	public static final int COMMAND_ID_FACEBOOK = 400;
	public static final int COMMAND_ID_JALAN_SEARCH_ONSEN = 500;
	public static final int COMMAND_ID_GURUNAVI_SEARCH = 600;
	public static final int COMMAND_ID_TURN_ON_BATH = 700;
	public static final int COMMAND_ID_EMIT_IR_PATTERN1 = 800;
	public static final int COMMAND_ID_EMIT_IR_PATTERN2 = 900;
	public static final int COMMAND_ID_EMIT_IR_PATTERN3 = 1000;
	public static final int COMMAND_ID_EMIT_IR_PATTERN4 = 1100;
	public static final int COMMAND_ID_EMIT_IR_PATTERN5 = 1200;
	public static final int COMMAND_ID_EMIT_IR_PATTERN6 = 1300;

	protected abstract int getCommandId();

	protected abstract String getCommandValue(String keyword)
			throws CommandExecException;

	@Override
	public CommandResultInterface exec(String keyword)
			throws CommandExecException {
		String value = getCommandValue(keyword);
		try {
			String url = "http://dev.tori.st/maven.websample/hip?"
					+PARAM_NAME_COMMAND_ID+"="+getCommandId()
					+"&"+PARAM_NAME_COMMAND_VALUE+"="+URLEncoder.encode(value,"UTF-8");
			return new HipServerResponse(url);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String doGet(String url) {
		try {
			HttpGet method = new HttpGet(url);
			DefaultHttpClient client = new DefaultHttpClient();
			method.setHeader("Connection", "Keep-Alive");
			HttpResponse response = client.execute(method);
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK)
				return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

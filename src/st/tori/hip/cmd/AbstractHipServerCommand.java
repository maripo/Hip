package st.tori.hip.cmd;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import st.tori.hip.cmd.exception.CommandExecException;

public abstract class AbstractHipServerCommand implements CommandInterface {

	public static final String PARAM_NAME_COMMAND_ID = "cid";
	public static final String PARAM_NAME_COMMAND_VALUE = "val";

	public static final int COMMAND_ID_CREATE_EVERNOTE_MEMO = 1;
	public static final int COMMAND_ID_MAIL_TO = 2;
	public static final int COMMAND_ID_TWEET = 3;
	public static final int COMMAND_ID_FACEBOOK = 4;
	public static final int COMMAND_ID_JALAN_SEARCH_ONSEN = 5;
	public static final int COMMAND_ID_GURUNAVI_SEARCH = 6;
	public static final int COMMAND_ID_TURN_ON_BATH = 7;
	public static final int COMMAND_ID_EMIT_IR = 8;

	protected abstract int getCommandId();

	protected abstract String getCommandValue(String keyword)
			throws CommandExecException;

	@Override
	public CommandResultInterface exec(String keyword)
			throws CommandExecException {
		String value = getCommandValue(keyword);
		try {
			String urlStr = "http://dev.tori.st/maven.websample/hip?"
					+ PARAM_NAME_COMMAND_ID + "=" + getCommandId();
			if(value!=null&&value.length()>=0) {
				urlStr += "&"
						+ PARAM_NAME_COMMAND_VALUE + "="
						+ URLEncoder.encode(value, "UTF-8");
			}
			return new HipServerResponse(doGet(urlStr));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String doGet(String urlStr) {
		System.out.println("Request:"+urlStr);
		try {
			URL url = new URL(urlStr);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("GET");
			http.connect();
			InputStream in = http.getInputStream();
			byte b[] = new byte[1024];
			in.read(b);
			in.close();
			http.disconnect();
			String body = new String(b);
			System.out.println("Response:"+body);
			return body;
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * try { HttpGet method = new HttpGet(url); DefaultHttpClient client =
		 * new DefaultHttpClient(); method.setHeader("Connection",
		 * "Keep-Alive"); HttpResponse response = client.execute(method); int
		 * status = response.getStatusLine().getStatusCode(); if (status ==
		 * HttpStatus.SC_OK) return EntityUtils.toString(response.getEntity(),
		 * "UTF-8"); } catch (Exception e) { e.printStackTrace(); }
		 */
		return null;
	}

}

package st.tori.hip.android.activity;

import java.util.Locale;

import st.tori.hip.android.activity.CommandExecutor.CommandListener;
import st.tori.hip.cmd.CommandCreateEvernoteMemo;
import st.tori.hip.cmd.CommandEmitIrPattern1;
import st.tori.hip.cmd.CommandEmitIrPattern2;
import st.tori.hip.cmd.CommandEmitIrPattern3;
import st.tori.hip.cmd.CommandEmitIrPattern4;
import st.tori.hip.cmd.CommandEmitIrPattern5;
import st.tori.hip.cmd.CommandEmitIrPattern6;
import st.tori.hip.cmd.CommandFacebook;
import st.tori.hip.cmd.CommandGurunaviSearch;
import st.tori.hip.cmd.CommandInterface;
import st.tori.hip.cmd.CommandJalanSearchOnsen;
import st.tori.hip.cmd.CommandMailTo;
import st.tori.hip.cmd.CommandResultInterface;
import st.tori.hip.cmd.CommandResultTextToSpeechInterface;
import st.tori.hip.cmd.CommandTurnOnBath;
import st.tori.hip.cmd.CommandTweet;
import st.tori.hip.cmd.exception.CommandExecException;
import android.content.Context;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

public class CommandExecutor implements
		TextToSpeech.OnInitListener {

	Context context;
	
	static {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.permitAll().build());
	}

	public static final String PARAM_NAME_KEYWORD = "KEYWORD";

	public interface CommandListener {
		
	}
	
	private static CommandInterface[] COMMAND_ARRAY = new CommandInterface[] {
		new CommandMailTo(),
		new CommandCreateEvernoteMemo(),
		new CommandEmitIrPattern1(),
		new CommandEmitIrPattern2(),
		new CommandEmitIrPattern3(),
		new CommandEmitIrPattern4(),
		new CommandEmitIrPattern5(),
		new CommandEmitIrPattern6(),
		new CommandFacebook(),
		new CommandGurunaviSearch(),
		new CommandJalanSearchOnsen(),
		new CommandTurnOnBath(),
		new CommandTweet(),
		//CommandSpeachStub(),
	};

	private CommandListener listener;
	
	public CommandExecutor (Context context, CommandListener listener) {
		this.context = context;
		this.listener = listener;
	}

	public String exec(String keyword) {
		if (keyword == null || keyword.length() <= 0) {
			Toast.makeText(context, "No Keyword", Toast.LENGTH_LONG).show();
			return "なんですって?";
		} else {
			boolean found = false;
			for (CommandInterface command: COMMAND_ARRAY) {
				if (!command.isMyKeyword(keyword))
					continue;
				try {
					CommandResultInterface result = command.exec(keyword);
					if (result!=null && result instanceof CommandResultTextToSpeechInterface) {
						String speechText = ((CommandResultTextToSpeechInterface) result)
								.getSpeechText();
						System.out.println("ExecActivity.onCreate:speechText="
								+ speechText);
						return speechText;
					}
					found = true;
					break;
				} catch (CommandExecException e) {
					// e.printStackTrace();
					Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
							.show();
				}
			}
		}
		return "なんですって?";
	}


	private boolean isTTSReady = false;

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			
		}
	}
}

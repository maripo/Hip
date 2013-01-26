package st.tori.hip.android.activity;

import java.util.Locale;

import st.tori.hip.cmd.CommandInterface;
import st.tori.hip.cmd.CommandMailTo;
import st.tori.hip.cmd.CommandResultInterface;
import st.tori.hip.cmd.CommandResultTextToSpeechInterface;
import st.tori.hip.cmd.CommandSpeachStub;
import st.tori.hip.cmd.exception.CommandExecException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class CommandExecutor implements
		TextToSpeech.OnInitListener {

	Context context;
	
	static {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.permitAll().build());
	}

	public static final String PARAM_NAME_KEYWORD = "KEYWORD";

	private static CommandInterface[] COMMAND_ARRAY = new CommandInterface[] { new CommandMailTo(), new CommandSpeachStub(), };

	private TextToSpeech mTts;
	
	public CommandExecutor (Context context) {
		this.context = context;
		mTts = new TextToSpeech(context, this);
	}

	public void exec(String keyword) {
		if (keyword == null || keyword.length() <= 0) {
			Toast.makeText(context, "Illegal Keyword", Toast.LENGTH_LONG).show();
		} else {
			for (int i = 0; i < COMMAND_ARRAY.length; i++) {
				if (!COMMAND_ARRAY[i].isMyKeyword(keyword))
					continue;
				try {
					CommandResultInterface result = COMMAND_ARRAY[i]
							.exec(keyword);
					if (result!=null && result instanceof CommandResultTextToSpeechInterface) {
						String speechText = ((CommandResultTextToSpeechInterface) result)
								.getSpeechText();
						System.out.println("ExecActivity.onCreate:speechText="
								+ speechText);
						if (speechText != null && speechText.length() > 0) {
							mTts.speak(speechText, TextToSpeech.QUEUE_FLUSH,
									null);
						}
					}
					break;
				} catch (CommandExecException e) {
					// e.printStackTrace();
					Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
							.show();
				}
			}
		}
	}


	public void dispose() {
		// shutdown() を忘れてはならない
		if (mTts != null) {
			mTts.stop();
			mTts.shutdown();
		}
	}

	private boolean isTTSReady = false;

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			int result = mTts.setLanguage(Locale.JAPAN);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				System.out.println("ExecActivity.onInit:1");
				Toast.makeText(context, "Japanese not available",
						Toast.LENGTH_LONG).show();
			} else {
				System.out.println("ExecActivity.onInit:2");
				isTTSReady = true;
				// Toast.makeText(this, "TTS initialized",
				// Toast.LENGTH_LONG).show();
			}
		} else {
			System.out.println("ExecActivity.onInit:3");
			Toast.makeText(context, "TTS failed to initialize", Toast.LENGTH_LONG)
					.show();
		}
	}
}

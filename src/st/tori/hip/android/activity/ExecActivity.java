package st.tori.hip.android.activity;

import java.util.Locale;

import st.tori.hip.cmd.CommandInterface;
import st.tori.hip.cmd.CommandMailTo;
import st.tori.hip.cmd.CommandResultInterface;
import st.tori.hip.cmd.CommandResultTextToSpeechInterface;
import st.tori.hip.cmd.exception.CommandExecException;
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class ExecActivity extends Activity implements
		TextToSpeech.OnInitListener {

	public static final String PARAM_NAME_KEYWORD = "KEYWORD";

	private static CommandInterface[] COMMAND_ARRAY = new CommandInterface[] {
		new CommandMailTo(), };

	private TextView textKeyword;
	private TextToSpeech mTts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exec);

		textKeyword = (TextView) findViewById(R.id.textKeyword);
		mTts = new TextToSpeech(this, this);

		String keyword = getIntent().getStringExtra(PARAM_NAME_KEYWORD);
		if (keyword == null || keyword.length() <= 0) {
			Toast.makeText(this, "Illegal Keyword", Toast.LENGTH_LONG).show();
			finish();
		} else {
			textKeyword.setText(keyword);
			for (int i = 0; i < COMMAND_ARRAY.length; i++) {
				if (!COMMAND_ARRAY[i].isMyKeyword(keyword))
					continue;
				try {
					CommandResultInterface result = COMMAND_ARRAY[i]
							.exec(keyword);
					if (result == null)
						continue;
					if (result instanceof CommandResultTextToSpeechInterface) {
						String speechText = ((CommandResultTextToSpeechInterface) result)
								.getSpeechText();
						if (speechText != null && speechText.length() > 0) {
							mTts.speak(speechText, TextToSpeech.QUEUE_FLUSH, null);
						}
					}
				} catch (CommandExecException e) {
					// e.printStackTrace();
					Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG)
							.show();
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_exec, menu);
		return true;
	}

	@Override
	public void onDestroy() {
		// shutdown() を忘れてはならない
		if (mTts != null) {
			mTts.stop();
			mTts.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			int result = mTts.setLanguage(Locale.JAPAN);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Toast.makeText(this, "Japanese not available", Toast.LENGTH_LONG).show();
			} else {
				//Toast.makeText(this, "TTS initialized", Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, "TTS failed to initialize", Toast.LENGTH_LONG).show();
		}
	}
}

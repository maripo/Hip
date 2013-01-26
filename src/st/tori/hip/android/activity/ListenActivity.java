package st.tori.hip.android.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ListenActivity extends Activity {

	Button mButtonStartListening;
	TextView mTextRecognitionResult;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listen);
		mButtonStartListening = (Button) findViewById(R.id.button_start_listening);
		mTextRecognitionResult = (TextView) findViewById(R.id.text_recognition_result);
		mButtonStartListening.setOnClickListener(new ButtonStartListener());
	}
	
	class ButtonStartListener implements OnClickListener {

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
					.getPackage().getName());
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
					RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
			startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
		}
		
	}

	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE) {
			if(resultCode == RESULT_OK) {
				ArrayList<String> textMatchList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				if (!textMatchList.isEmpty()) {
					mTextRecognitionResult.setText(textMatchList.get(0));
				}
			}
			else {
				mTextRecognitionResult.setText("Voice recognition failed.");
			}
		}
	}
}

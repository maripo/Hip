package st.tori.hip.android.activity;

import java.util.ArrayList;

import st.tori.hip.android.widget.SoundMonitor;
import st.tori.hip.android.widget.SoundMonitor.SoundMonitorListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListenActivity extends Activity implements SoundMonitorListener {

	Button mButtonStartListening;
	TextView mTextRecognitionResult;
	Button mButtonCheckSoundLevel;
	ProgressBar mProgressBarSoundLevel;
	private static final int MAX_SOUND_LEVEL = 30000;
	
	SoundMonitor mSoundMonitor;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listen);
		
		mButtonStartListening = (Button) findViewById(R.id.button_start_listening);
		mTextRecognitionResult = (TextView) findViewById(R.id.text_recognition_result);
		mButtonCheckSoundLevel = (Button) findViewById(R.id.button_check_sound_level);
		mProgressBarSoundLevel = (ProgressBar) findViewById(R.id.progress_bar_sound_level);
		mProgressBarSoundLevel.setMax(MAX_SOUND_LEVEL);
		
		mSoundMonitor = new SoundMonitor();
		mSoundMonitor.setListener(this);
		
		mButtonStartListening.setOnClickListener(new ButtonStartListeningListener());
		mButtonCheckSoundLevel.setOnClickListener(new ButtonCheckSoundListener());
		
	}
	
	class ButtonStartListeningListener implements OnClickListener {

		@Override
		public void onClick(View v)
		{
			startVoiceRecognition();
		}
		
	}
	
	private void startVoiceRecognition () {

		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
				.getPackage().getName());
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	}
	
	class ButtonCheckSoundListener implements OnClickListener {

		@Override
		public void onClick(View v)
		{
			mSoundMonitor.start();
			
		}
		
	}

	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE) {
			if(resultCode == RESULT_OK) {
				ArrayList<String> textMatchList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				if (!textMatchList.isEmpty()) {
					String message = textMatchList.get(0);
					mTextRecognitionResult.setText(message);
					Intent i = new Intent(getApplicationContext(), ExecActivity.class);
					i.putExtra(ExecActivity.PARAM_NAME_KEYWORD, message);
					startActivity(i);
				}
			}
			else {
				mTextRecognitionResult.setText("Voice recognition failed.");
			}
		}
		if (mSoundMonitor!=null) {
			mSoundMonitor.resume();
		}
	}

	@Override
	public void onDestroy ()
	{
		super.onDestroy();
		if (mSoundMonitor!=null)
			mSoundMonitor.stop();
	}

	@Override
	public void displayMaxAmplitude(int maxAmplitude)
	{
		maxAmplitude = Math.min(MAX_SOUND_LEVEL, maxAmplitude);
		mProgressBarSoundLevel.setProgress(maxAmplitude);
	}

	@Override
	public void onLargeSoundDetected()
	{
		startVoiceRecognition();
	}

}

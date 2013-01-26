package st.tori.hip.android.activity;

import java.util.ArrayList;

import st.tori.hip.android.activity.CommandExecutor.CommandListener;
import st.tori.hip.android.widget.SoundMonitor;
import st.tori.hip.android.widget.SoundMonitor.SoundMonitorListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListenActivity extends Activity implements SoundMonitorListener, OnInitListener, CommandListener {

	public static final String TAG = "Hip";
	
	Button mButtonStartListening;
	TextView mTextRecognitionResult;
	Button mButtonCheckSoundLevel;
	ProgressBar mProgressBarSoundLevel;
	private static final int MAX_SOUND_LEVEL = 30000;
	CommandExecutor mCommandExecutor;
	
	SoundMonitor mSoundMonitor;
	
	TextToSpeech mSpeech;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listen);
		
		mCommandExecutor = new CommandExecutor(this, this);
		
		mButtonStartListening = (Button) findViewById(R.id.button_start_listening);
		mTextRecognitionResult = (TextView) findViewById(R.id.text_recognition_result);
		mButtonCheckSoundLevel = (Button) findViewById(R.id.button_check_sound_level);
		mProgressBarSoundLevel = (ProgressBar) findViewById(R.id.progress_bar_sound_level);
		mProgressBarSoundLevel.setMax(MAX_SOUND_LEVEL);

		mButtonStartListening.setVisibility(View.INVISIBLE);
		mButtonCheckSoundLevel.setVisibility(View.INVISIBLE);
		
		mSoundMonitor = new SoundMonitor();
		mSoundMonitor.setListener(this);
		
		mButtonStartListening.setOnClickListener(new ButtonStartListeningListener());
		startVoiceRecognition();
		
		mSpeech = new TextToSpeech(this, this);
		
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
					mCommandExecutor.exec(message);
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
		mCommandExecutor.dispose();
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

	@Override
	public void onInit(int status)
	{
		if (status == TextToSpeech.SUCCESS) 
		{
			mSpeech.speak("こんにちは。音声のテストです。", TextToSpeech.QUEUE_FLUSH, null);
		} else {
			//
			
		}
	}

}

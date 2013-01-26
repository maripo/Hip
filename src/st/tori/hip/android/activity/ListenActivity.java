package st.tori.hip.android.activity;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListenActivity extends Activity {

	Button mButtonStartListening;
	TextView mTextRecognitionResult;
	Button mButtonCheckSoundLevel;
	ProgressBar mProgressBarSoundLevel;
	private static final int MAX_SOUND_LEVEL = 30000;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listen);
		
		mButtonStartListening = (Button) findViewById(R.id.button_start_listening);
		mTextRecognitionResult = (TextView) findViewById(R.id.text_recognition_result);
		mButtonCheckSoundLevel = (Button) findViewById(R.id.button_check_sound_level);
		mProgressBarSoundLevel = (ProgressBar) findViewById(R.id.progress_bar_sound_level);
		mProgressBarSoundLevel.setMax(MAX_SOUND_LEVEL);
		
		mButtonStartListening.setOnClickListener(new ButtonStartListener());
		mButtonCheckSoundLevel.setOnClickListener(new ButtonCheckSoundListener());
		
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
	MediaRecorder recorder;
	Handler handler = new Handler ();
	int INTERVAL= 100;
	int recordCount = 0;
	int MAX_RECORD_COUNT = 50;
	
	
	class ButtonCheckSoundListener implements OnClickListener {

		@Override
		public void onClick(View v)
		{
			recorder = getRecorder();

			try
			{
				recorder.prepare();
				recorder.start();
				Timer timer = new Timer();
				timer.scheduleAtFixedRate(new RecorderTask(), INTERVAL, INTERVAL);
			} catch (IllegalStateException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}

			
		}
		
	}
	class RecorderTask extends TimerTask {

		public void run() {
			handler.post(new Runnable(){

				@Override
				public void run()
				{
					displayMaxAmplitude(recorder.getMaxAmplitude());
					if (recordCount == MAX_RECORD_COUNT)
					{
						recorder.stop();
						recordCount = 0;
						
						//recorder.release();
						try
						{
							recorder = getRecorder();
							recorder.prepare();
							recorder.start();
						} catch (IllegalStateException e)
						{
							e.printStackTrace();
						} catch (IOException e)
						{
							e.printStackTrace();
						}
					} 
					else {
						recordCount ++;
					}
				}

				
			});
		}
	}

	private void displayMaxAmplitude(int maxAmplitude)
	{
		maxAmplitude = Math.min(MAX_SOUND_LEVEL, maxAmplitude);
		mProgressBarSoundLevel.setProgress(maxAmplitude);
		
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

	MediaRecorder getRecorder()
	{
		MediaRecorder recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		Uri uri = Uri.fromFile(new File("/sdcard/hip_sample_sound"));
		recorder.setOutputFile(uri.getPath());
		return recorder;
	}
}

package st.tori.hip.android.widget;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Handler;


public class SoundMonitor
{
	private SoundMonitorListener listener;
	private MediaRecorder recorder;
	private Handler handler = new Handler ();
	private static final int INTERVAL= 100;
	private int recordCount = 0;
	private static final int MAX_RECORD_COUNT = 50;
	private static final String TMP_SOUND_PATH = "/sdcard/hip_sample_sound";
	private static final int LARGE_SOUND_THRESHOLD = 10000;
	Timer timer;
	
	public void start () {
		recorder = getRecorder();
		try
		{
			recorder.prepare();
			recorder.start();
			timer = new Timer();
			startTimer();
		} catch (IllegalStateException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public void pause () {
		timer.cancel();
		recorder.stop();
		recorder.release();
	}
	public void resume () {
		this.start();
	}
	public void stop() {
		
	}
	
	private void startTimer () {
		timer.scheduleAtFixedRate(new RecorderTask(), INTERVAL, INTERVAL);
	}
	private void resetMediaRecorder()
	{
		recorder.stop();
		recordCount = 0;
	
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
	public void setListener(SoundMonitorListener listener)
	{
		this.listener = listener;
	}
	MediaRecorder getRecorder()
	{
		MediaRecorder recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		Uri uri = Uri.fromFile(new File(TMP_SOUND_PATH));
		recorder.setOutputFile(uri.getPath());
		return recorder;
	}

	public interface SoundMonitorListener {
		void displayMaxAmplitude(int maxAmplitude);
		void onLargeSoundDetected();
		
	}

	class RecorderTask extends TimerTask {
	
		public void run() {
			handler.post(new Runnable(){
	
				@Override
				public void run()
				{
					int maxAmplitude = recorder.getMaxAmplitude();
					listener.displayMaxAmplitude(maxAmplitude);
					if (maxAmplitude > LARGE_SOUND_THRESHOLD)
					{
						pause();
						System.out.println("Detected large sound.");
						listener.onLargeSoundDetected();
						
					}
					if (recordCount == MAX_RECORD_COUNT)
					{
						resetMediaRecorder();
					} 
					else {
						recordCount ++;
					}
				}
			});
		}
	}
}

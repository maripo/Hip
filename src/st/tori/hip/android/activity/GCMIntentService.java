package st.tori.hip.android.activity;

import java.net.URLDecoder;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService
{
	private static final String TAG = "Hip";

	protected GCMIntentService(String senderId)
	{
		super(senderId);
	}
	public GCMIntentService() {
		super(MainActivity.GCM_SENDER_ID);
	}

	@Override
	protected void onError(Context arg0, String arg1)
	{
		Log.d(TAG, "GCMIntentService.onError");
		
	}

	public static final String PREF_KEY_DATE = "message_date";
	public static final String PREF_KEY_CONTENT = "message_content";
	
	@Override
	protected void onMessage(Context context, Intent intent)
	{
		Log.d(TAG, "GCMIntentService.onMessage");
		String message = URLDecoder.decode(intent.getExtras().getString("message"));
		Log.d(TAG, "Message=" + message);
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = pref.edit();
		editor.putLong(PREF_KEY_DATE,new Date().getTime());
		editor.putString(PREF_KEY_CONTENT, message);
		editor.commit();
	}

	@Override
	protected void onRegistered(Context arg0, String arg1)
	{
		Log.d(TAG, "GCMIntentService.onRegistered");
		
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1)
	{
		Log.d(TAG, "GCMIntentService.onUnregistered");
	}

}

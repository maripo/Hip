package st.tori.hip.android.activity;

import android.content.Context;
import android.content.Intent;
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

	@Override
	protected void onMessage(Context arg0, Intent arg1)
	{
		Log.d(TAG, "GCMIntentService.onMessage");
		
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

package st.tori.hip.android.activity;

import com.google.android.gcm.GCMRegistrar;

import st.tori.hip.android.activity.CommandExecutor.CommandListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements CommandListener {

	private Button buttonListen;
	private Button buttonExec;
	private CommandExecutor mCommandExecutor;
	private TextView debugText;
	
	public static String GCM_SENDER_ID = "506944099595";
	
	//Server Key AIzaSyCIQnfyzBHKr7lLbQJFWR4n3uw6Qu7Eyu0

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mCommandExecutor = new CommandExecutor(this, this);
		Button buttonListen = (Button) findViewById(R.id.buttonListen);
		debugText = (TextView) findViewById(R.id.debug_text);
		buttonListen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), ListenActivity.class));
			}
		});
		Button buttonExec = (Button) findViewById(R.id.buttonExec);
		buttonExec.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommandExecutor.exec("まりぽにメール");
			}
		});
		
		//GCM
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			Log.v("Hip", "GCM: not registered");
			debugText.setText("GCM: not registered");
			GCMRegistrar.register(this, GCM_SENDER_ID);
		} else {
			Log.v("Hip", "GCM: already registered. REG_ID=" + regId);
			debugText.setText("GCM: already registered. REG_ID=" + regId);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}

package st.tori.hip.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button buttonListen;
	private Button buttonExec;
	private CommandExecutor mCommandExecutor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mCommandExecutor = new CommandExecutor(this);
		Button buttonListen = (Button) findViewById(R.id.buttonListen);
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}

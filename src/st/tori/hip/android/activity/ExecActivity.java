package st.tori.hip.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ExecActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exec);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_exec, menu);
		return true;
	}
}

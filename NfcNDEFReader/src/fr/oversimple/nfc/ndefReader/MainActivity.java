package fr.oversimple.nfc.ndefReader;

import fr.oversimple.nfc.urlReader.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements NfcNdefReaderListener{

	 private TextView demoTextView;
	 private NfcNdefReader nfcUrlReader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		demoTextView = (TextView) findViewById(R.id.demoTextview);
		
        nfcUrlReader = new NfcNdefReader(this);
        
		if(!nfcUrlReader.isNfcAvailableOnDevice()) {
			Toast.makeText(this, "Nfc is not available on this device", Toast.LENGTH_SHORT).show();
		}
		
		if(!nfcUrlReader.isNfcActivated()) {
			Toast.makeText(this, "Nfc is not activated, please activate it", Toast.LENGTH_SHORT).show();
		}
		nfcUrlReader.setNfcUrlReaderListener(this);
		nfcUrlReader.handleIntent(getIntent());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		nfcUrlReader.setupForegroundDispatch(this);
	}
	
	@Override
	protected void onPause() {
		nfcUrlReader.stopForegroundDispatch(this);
		super.onPause();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		nfcUrlReader.handleIntent(intent);
	}

	@Override
	public void ndefDataRead(String ndefData) {
		demoTextView.setText(ndefData);
	}
}

package com.example.surtidointentimpl;


import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.core.app.ActivityCompat;

import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

	    Button btn1 = findViewById(R.id.button1);
	    Button btn2 = findViewById(R.id.button2);
	    Button btn3 = findViewById(R.id.button3);
	    Button btn4 = findViewById(R.id.button4);
	    Button btn5 = findViewById(R.id.button5);
	    Button btn6 = findViewById(R.id.button6);
	    Button btnMarcar = findViewById(R.id.buttonMarcar);
	    Button btnSms = findViewById(R.id.buttonsms);
	    Button btEmail = findViewById(R.id.buttonemail);
	    Button btGallery = findViewById(R.id.buttonGallery);

	    btn1.setOnClickListener(this);
	    btn2.setOnClickListener(this);
	    btn3.setOnClickListener(this);
	    btn4.setOnClickListener(this);
	    btn5.setOnClickListener(this);
	    btn6.setOnClickListener(this);
	    btnMarcar.setOnClickListener(this);
	    btnSms.setOnClickListener(this);
	    btEmail.setOnClickListener(this);
	    btGallery.setOnClickListener(this);

		if (Build.VERSION.SDK_INT >= 23)
			if (! ckeckPermissions())
			  requestPermissions();
	}

	@Override
	public void onClick (View v) {
		Intent in;
		final String lat = getString(R.string.lat);
		final String lon = getString(R.string.lon);
		final String url = getString(R.string.url);
		final String address = getString(R.string.direccion);
		final String textToSearch = getString(R.string.textoABuscar);

		switch (v.getId()) {
			case R.id.button1:
				Toast.makeText(this, getString(R.string.opcion1), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + lat + ',' + lon));
				startActivity(in);
				break;
			case R.id.button2:
				Toast.makeText(this, getString(R.string.opcion2), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + address));
				//in = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + textToSearch));
				startActivity(in);
				break;
			case R.id.button3:
				Toast.makeText(this, getString(R.string.opcion3), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(in);
				break;
			case R.id.button4:
				Toast.makeText(this, getString(R.string.opcion4), Toast.LENGTH_LONG).show();
				//in = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q=" + textToSearch));
				in = new Intent(Intent.ACTION_WEB_SEARCH);
				in.putExtra(SearchManager.QUERY, textToSearch);
				startActivity(in);
				break;
			case R.id.button5:
                callPhone();
				break;
			case R.id.buttonMarcar:
				dialNumber();
				break;
			case R.id.button6:
				accessContacts();
				break;
			case R.id.buttonsms:
				sendSms();
				break;
			case R.id.buttonemail:
				sendEmail();
				break;
			case R.id.buttonGallery:
				enterGallery();
				break;
			}
	}


	@Override
	protected void onResume() {
		super.onResume();
		if (Build.VERSION.SDK_INT >= 23)
			if (! ckeckPermissions())
				requestPermissions();
	}

	private boolean ckeckPermissions() {
		if (Build.VERSION.SDK_INT >= 23) {
			return ckeckPermissionsCallPhone() && ckeckPermissionsReadContacts();
		    }
		else
			return true;
	}

	private boolean ckeckPermissionsCallPhone() {
		return ActivityCompat.checkSelfPermission(getApplicationContext(),
				Manifest.permission.CALL_PHONE) ==
				PackageManager.PERMISSION_GRANTED;
	}

	private boolean ckeckPermissionsReadContacts() {
		return ActivityCompat.checkSelfPermission(getApplicationContext(),
				Manifest.permission.READ_CONTACTS) ==
				PackageManager.PERMISSION_GRANTED;
	}

	private void requestPermissions() {
		ActivityCompat.requestPermissions(MainActivity.this,
				new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE},
				0);
	}

	private void requestPermissionsCallPhone() {
		ActivityCompat.requestPermissions(MainActivity.this,
				new String[]{Manifest.permission.CALL_PHONE},
				0);
	}

	private void requestPermissionsReadContacts() {
		ActivityCompat.requestPermissions(MainActivity.this,
				new String[]{Manifest.permission.READ_CONTACTS},
				0);
	}

	private void accessContacts() {

		if (Build.VERSION.SDK_INT >= 23) {
			if (ckeckPermissionsReadContacts()) {
				accessContactsAction();
			} else {
				requestPermissionsReadContacts();
			}
		}
		else {
			accessContactsAction();
		}
	}

	private void accessContactsAction() {
		Intent in;

		Toast.makeText(this, getString(R.string.opcion6), Toast.LENGTH_LONG).show();
		in = new Intent(Intent.ACTION_VIEW);
		in.setData(ContactsContract.Contacts.CONTENT_URI);
		startActivity(in);
	}

	private void dialNumber(){
		Intent in;

		if (Build.VERSION.SDK_INT >= 23) {
			if (ckeckPermissionsCallPhone()) {
				Toast.makeText(this, getString(R.string.opcioMarcar), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getText(R.string.telef)));
				startActivity(in);
			} else {
				requestPermissionsCallPhone();
			}
		}else {
			Toast.makeText(this, getString(R.string.opcioMarcar), Toast.LENGTH_LONG).show();
			in = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getText(R.string.telef)));
			startActivity(in);
		}
	}

	private void callPhone() {
		Intent in;

		if (Build.VERSION.SDK_INT >= 23) {
		if (ckeckPermissionsCallPhone()) {
			Toast.makeText(this, getString(R.string.opcion5), Toast.LENGTH_LONG).show();
			in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getText(R.string.telef)));
			startActivity(in);
		} else {
			    requestPermissionsCallPhone();
		}
		} else {
			Toast.makeText(this, getString(R.string.opcion5), Toast.LENGTH_LONG).show();
			in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getText(R.string.telef)));
			startActivity(in);
		}
	}

	private void sendSms(){
		Intent in;

		Toast.makeText(this, getString(R.string.opcionSms), Toast.LENGTH_LONG).show();
		in = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", String.valueOf(getText(R.string.telef)), null));
		in.putExtra("sms_body", "Buenas tardes Amigo");
		startActivity(in);
	}

	private void sendEmail(){
		Intent in;

		Toast.makeText(this, getString(R.string.opcionEmail), Toast.LENGTH_LONG).show();
		in = new Intent(Intent.ACTION_SEND);
		in.setType("text/plain");
		in.putExtra(Intent.EXTRA_EMAIL, new String[] { "palomitas88tucasa@gmail.com"});
		in.putExtra(Intent.EXTRA_SUBJECT, "Informacion de la situacion Médica del país");
		in.putExtra(Intent.EXTRA_TEXT, "La cosa no va bien");

		startActivity(in);
	}

	private void enterGallery(){
		Intent in;

		Toast.makeText(this, getString(R.string.opcionGallery), Toast.LENGTH_LONG).show();
		in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

		startActivityForResult(in, 1);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data){

		if(resultCode == RESULT_OK){
			if(requestCode == 1){
				Uri selectedImage = data.getData();
				ImageView imageGallery = findViewById(R.id.imageView3);
				imageGallery.setImageURI(selectedImage);
			}
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

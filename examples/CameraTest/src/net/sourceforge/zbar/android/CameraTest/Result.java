package net.sourceforge.zbar.android.CameraTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.JSONfunctions;
import adapters.ResultAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

public class Result extends Activity {
	ListView reslist;

	public ArrayList<HashMap<String, String>> arraylist;
	public JSONObject jsonobject;
	public JSONArray jsonarray, jsonarray2;
	public ResultAdapter adapter;
	String id;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		reslist = (ListView) findViewById(R.id.listofalternates);
		try {
			id = getIntent().getStringExtra("data");
			Log.i("", "inside try with id=" + id);

		} catch (NullPointerException e) {
			Log.i("", "inside exception");
			id = "1";
		}
		new DownloadJSON().execute();

	}

	private class NetCheck extends AsyncTask<String, String, Boolean> {

		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			pDialog = new ProgressDialog(Result.this);
			pDialog.setMessage("Checking Connection");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Boolean doInBackground(String... args) {
			Log.d("msg", "NetChecking-doinback");
			/**
			 * Gets current device state and checks for working internet
			 * connection by trying Google.
			 **/

			ConnectivityManager cm = (ConnectivityManager) Result.this
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnected()) {
				try {

					URL url = new URL("http://www.google.co.in/");
					HttpURLConnection urlc = (HttpURLConnection) url
							.openConnection();

					urlc.setConnectTimeout(3000);
					urlc.connect();

					if (urlc.getResponseCode() == 200) {
						return true;
					}
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return false;

		}

		@Override
		protected void onPostExecute(Boolean th) {

			Log.d("msg", "NetChecking-postexec");
			if (th == true) {
				pDialog.dismiss();

				new DownloadJSON().execute();
			} else {
				pDialog.dismiss();

				Toast.makeText(getApplicationContext(),
						"Check Internet Connection.", Toast.LENGTH_SHORT)
						.show();

			}
		}
	}

	private class DownloadJSON extends AsyncTask<Void, Integer, Integer> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(Result.this);
			pDialog.setMessage("Loading details. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Integer doInBackground(Void... params) {

			// Create an array

			arraylist = new ArrayList<HashMap<String, String>>();
			// Retrieve JSON Objects from the given URL address
			jsonobject = JSONfunctions
					.getJSONfromURL("http://vesitpraxis.tk/products.php?barcode_id="
							+ id);

			try {

				jsonarray = jsonobject.getJSONArray("products");

				for (int i = 0; i < jsonarray.length(); i++) {

					HashMap<String, String> map = new HashMap<String, String>();

					jsonobject = jsonarray.getJSONObject(i);
					if (i == 0)
						map.put("producttype", "Actual Product");
					else
						map.put("producttype", "Alternate Product");
					map.put("barcode_id", jsonobject.getString("barcode_id"));
					map.put("pname", jsonobject.getString("pname"));
					map.put("price", jsonobject.getString("price"));
					map.put("type", jsonobject.getString("type"));
					map.put("harmful_q", jsonobject.getString("harmful_q"));
					map.put("images", jsonobject.getString("images"));
					map.put("ingredients", jsonobject.getString("ingredients"));
					arraylist.add(map);

				}

			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPostExecute(final Integer allCommon) {

			if (arraylist.size() != 0) {

				adapter = new ResultAdapter(getApplicationContext(), arraylist);

				SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
						adapter);

				swingBottomInAnimationAdapter.setAbsListView(reslist);
				reslist.setAdapter(swingBottomInAnimationAdapter);
			} else {
				Toast.makeText(getApplicationContext(),
						"Looks like there's no products", Toast.LENGTH_LONG);

			}

			pDialog.dismiss();

		}
	}

}
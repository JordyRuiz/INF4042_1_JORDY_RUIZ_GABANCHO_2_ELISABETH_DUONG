package org.esiea.jordy_ruiz_elisabeth_duong.footballclub;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ListActivity {

    private ProgressDialog pDialog;
    // URL to get clubs JSON
    private static String url = "https://jordyruiz.herokuapp.com/clubs.json";

    // JSON Node names
    private static final String TAG_TOUTLESCLUBS = "toutlesclubs";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "NomClub";
    private static final String TAG_LIGUE = "Ligue";
    private static final String TAG_DISTRICT = "District";
    private static final String TAG_NOMBREEQUIPE = "NombreEquipe";
    private static final String TAG_CORRESPONDANT = "Correspondant";
    private static final String TAG_ADRESSE = "Adresse";
    private static final String TAG_EMAIL = "Email";
    private static final String TAG_TELEPHONE = "Telephone";
    private static final String TAG_LATITUDE = "Latitude";
    private static final String TAG_LONGITUDE = "Longitude";
    private static final String TAG_URL = "url";

    // clubs JSONArray
    JSONArray clubs = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> clubList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clubList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();

        // Listview on item click listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String name = ((TextView) view.findViewById(R.id.name))
                        .getText().toString();
                String ligue = ((TextView) view.findViewById(R.id.ligue))
                        .getText().toString();
                String district = ((TextView) view.findViewById(R.id.district))
                        .getText().toString();
                String nombreEquipe = ((TextView) view.findViewById(R.id.nombreEquipe))
                        .getText().toString();
                String correspondant = ((TextView) view.findViewById(R.id.correspondant))
                        .getText().toString();
                String adresse = ((TextView) view.findViewById(R.id.adresse))
                        .getText().toString();
                String email = ((TextView) view.findViewById(R.id.email))
                        .getText().toString();
                String telephone = ((TextView) view.findViewById(R.id.telephone))
                        .getText().toString();

                // Starting single club activity
                Intent in = new Intent(getApplicationContext(),
                        SingleclubActivity.class);
                in.putExtra(TAG_NAME, name);
                in.putExtra(TAG_LIGUE, ligue);
                in.putExtra(TAG_DISTRICT, district);
                in.putExtra(TAG_NOMBREEQUIPE,nombreEquipe);
                in.putExtra(TAG_CORRESPONDANT,correspondant);
                in.putExtra(TAG_ADRESSE,adresse);
                in.putExtra(TAG_EMAIL,email);
                in.putExtra(TAG_TELEPHONE,telephone);

                startActivity(in);
                Toast.makeText(MainActivity.this, name + " sélectionné",
                        Toast.LENGTH_SHORT).show();

            }
        }
        );

        // Calling async task to get json
        new GetClubs().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetClubs  extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Chargement...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    clubs = jsonObj.getJSONArray(TAG_TOUTLESCLUBS);

                    // looping through All clubs
                    for (int i = 0; i < clubs.length(); i++) {
                        JSONObject c = clubs.getJSONObject(i);

                        String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NAME);
                        String ligue = c.getString(TAG_LIGUE);
                        String disctrict = c.getString(TAG_DISTRICT);
                        String nombreEquipe = c.getString(TAG_NOMBREEQUIPE);
                        String correspondant = c.getString(TAG_CORRESPONDANT);
                        String adresse = c.getString(TAG_ADRESSE);
                        String email = c.getString(TAG_EMAIL);
                        String telephone = c.getString(TAG_TELEPHONE);
                        String latitude = c.getString(TAG_LATITUDE);
                        String longitude = c.getString(TAG_LONGITUDE);
                        String url = c.getString(TAG_URL);

                        // tmp hashmap for single club
                        HashMap<String, String> club = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        club.put(TAG_NAME, name);
                        club.put(TAG_LIGUE, ligue);
                        club.put(TAG_DISTRICT, disctrict);
                        club.put(TAG_NOMBREEQUIPE, nombreEquipe);
                        club.put(TAG_CORRESPONDANT, correspondant);
                        club.put(TAG_ADRESSE, adresse);
                        club.put(TAG_EMAIL, email);
                        club.put(TAG_TELEPHONE, telephone);
                        club.put(TAG_LATITUDE, latitude);
                        club.put(TAG_LONGITUDE, longitude);
                        club.put(TAG_URL, url);

                        // adding club to club list
                        clubList.add(club);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, clubList,
                    R.layout.list_item, new String[] { TAG_NAME, TAG_LIGUE,TAG_DISTRICT
                    ,TAG_NOMBREEQUIPE,TAG_CORRESPONDANT,TAG_ADRESSE,TAG_EMAIL,TAG_TELEPHONE
                     }, new int[] { R.id.name,R.id.ligue,R.id.district,R.id.nombreEquipe
                                    ,R.id.correspondant,R.id.adresse,R.id.email,R.id.telephone});
            setListAdapter(adapter);
        }

    }
}

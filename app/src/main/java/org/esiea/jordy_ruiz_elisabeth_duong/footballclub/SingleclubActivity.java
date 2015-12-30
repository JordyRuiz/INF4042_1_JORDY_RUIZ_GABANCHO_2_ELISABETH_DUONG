package org.esiea.jordy_ruiz_elisabeth_duong.footballclub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleclubActivity  extends Activity {
	
	// JSON node keys
	private static final String TAG_NAME = "NomClub";
	private static final String TAG_LIGUE = "Ligue";
	private static final String TAG_DISTRICT = "District";
    private static final String TAG_NOMBREEQUIPE = "NombreEquipe";
    private static final String TAG_CORRESPONDANT = "Correspondant";
    private static final String TAG_ADRESSE = "Adresse";
    private static final String TAG_EMAIL = "Email";
    private static final String TAG_TELEPHONE = "Telephone";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_club);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        String name = in.getStringExtra(TAG_NAME);
        String ligue = in.getStringExtra(TAG_LIGUE);
        String district = in.getStringExtra(TAG_DISTRICT);
        String nombreEquipe = in.getStringExtra(TAG_NOMBREEQUIPE);
        String correspondant = in.getStringExtra(TAG_CORRESPONDANT);
        String adresse = in.getStringExtra(TAG_ADRESSE);
        String email = in.getStringExtra(TAG_EMAIL);
        String telephone = in.getStringExtra(TAG_TELEPHONE);

        // Displaying all values on the screen
        TextView lblName = (TextView) findViewById(R.id.name_label);
        TextView lblLigue = (TextView) findViewById(R.id.ligue_label);
        TextView lblDistrict = (TextView) findViewById(R.id.disctrict_label);
        TextView lblNombreEquipe = (TextView) findViewById(R.id.nombreEquipe_label);
        TextView lblCorrespondant = (TextView) findViewById(R.id.correspondant_label);
        TextView lblAdresse = (TextView) findViewById(R.id.adresse_label);
        TextView lblEmail = (TextView) findViewById(R.id.email_label);
        TextView lblTelephone = (TextView) findViewById(R.id.telephone_label);


        lblName.setText(name);
        lblLigue.setText(ligue);
        lblDistrict.setText(district);
        lblNombreEquipe.setText(nombreEquipe);
        lblCorrespondant.setText(correspondant);
        lblAdresse.setText(adresse);
        lblEmail.setText(email);
        lblTelephone.setText(telephone);
    }
}

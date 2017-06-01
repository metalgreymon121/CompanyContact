package sample.com.companycontact;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class CompanyDetailsActivity extends AppCompatActivity {

    public static final String TAG_pos = "position";

    private int position = 0;

    ImageView logo;
    TextView detailsAddress, detailsPhone, name, city, state, zip, longitude, latitude, storeId;
    Button directions, callStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        name = (TextView) findViewById(R.id.Company_name);
        city = (TextView) findViewById(R.id.city);
        state = (TextView) findViewById(R.id.state);
        zip = (TextView) findViewById(R.id.zip_Code);
        longitude = (TextView) findViewById(R.id.longitude);
        latitude = (TextView) findViewById(R.id.latitude);
        storeId = (TextView) findViewById(R.id.store_id);
        detailsAddress = (TextView) findViewById(R.id.details_address);
        detailsPhone = (TextView) findViewById(R.id.details_phone);

        directions = (Button) findViewById(R.id.directions);
        callStore = (Button) findViewById(R.id.call_store);

        position = (int) getIntent().getSerializableExtra(TAG_pos);

        String logoURL = "";
        String nameData = "";
        String addressData = "";
        final String phoneData;

        String cityData = "";
        String stateData = "";
        String zipData = "";

        final String longitudeData;
        final String latitudeData;
        String storeIDData = "";

        try {
            String formatJsonString = "{\"stores\":" + readJsonData() + "}";
            JSONObject jsonRootObject = new JSONObject(formatJsonString);
            JSONArray jsonArray = jsonRootObject.optJSONArray("stores");
//            for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(position);

//            logoURL = jsonObject.getString("storeLogoURL");
            nameData = jsonObject.getString("name");
            addressData = jsonObject.getString("address");
            phoneData = jsonObject.getString("phone");

            cityData = jsonObject.getString("city");
            stateData = jsonObject.getString("state");
            zipData = jsonObject.getString("zipcode");

            longitudeData = jsonObject.getString("longitude");
            latitudeData = jsonObject.getString("latitude");
            storeIDData = jsonObject.getString("storeID");

            name.setText("Store Name: " + nameData);
            detailsPhone.setText("Phone: " + phoneData);
            city.setText("City: " + cityData);
            detailsAddress.setText("Address: " + addressData);

            state.setText("State: " + stateData);
            zip.setText("Zip: " + zipData);

            longitude.setText("Longitutde: " + longitudeData);
            latitude.setText("Latitude: " + latitudeData);
            storeId.setText("Store ID: " + storeIDData);

            directions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uri = "https://www.google.com/maps?saddr=My+Location&daddr" + latitudeData + "," + longitudeData
                            + "&daddr=" + latitudeData + "," + longitudeData;
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);
                }
            });
            callStore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + phoneData));
                        startActivity(callIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please allow call permission", Toast.LENGTH_LONG).show();
                    }

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String readJsonData() {
        try {
            File f = new File("/data/data/" + getPackageName() + "/" + "json");
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String mResponse = new String(buffer);
            return mResponse;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Failed to read JSON";
    }


}

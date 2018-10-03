package com.example.sunidhi.inclass04;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private BeaconManager beaconManager;
    private BeaconRegion region;
    static ListView listView;
    static List<Results.ResultsValue> resultsValueList =  null;
    static List<Results.ResultsValue> grocery =  null;
    static List<Results.ResultsValue> lifestyle =  null;
    static List<Results.ResultsValue> produce =  null;
    static String regionName="";
    APIInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        listView = findViewById( R.id.discountListView );
        apiInterface = APIClient.getClient().create(APIInterface.class);

        String json_string = null;
        try {
            InputStream inputStream = getAssets().open( "discount.json" );
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read( buffer );
            inputStream.close();

            json_string = new String( buffer, "UTF-8" );
            Gson gson = new Gson();
            Results results = gson.fromJson( json_string, Results.class );
            resultsValueList = results.getResults();

            discountAdapter adapter = new discountAdapter( MainActivity.this, R.layout.product_info, resultsValueList );
            listView.setAdapter( adapter );

        } catch (IOException e) {
            Toast.makeText( MainActivity.this, "inside catch", Toast.LENGTH_SHORT ).show();
            e.printStackTrace();
        }

            beaconManager = new BeaconManager(this);
            region = new BeaconRegion("ranged region",
                    UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);

            beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
                @Override
                public void onBeaconsDiscovered(BeaconRegion region, List<Beacon> list) {
                    if (!list.isEmpty()) {
                        Beacon nearestBeacon = list.get( 0 );
                        List<String> places = placesNearBeacon( nearestBeacon );
                        Log.d( "demo", "places = " + places );
                        if(places.contains( "Produce" )){
                            Log.d( "demo", "Connected to produce" );
                            String json_string3 = null;
                            try {
                                InputStream inputStream = getAssets().open( "produce.json" );
                                int size = inputStream.available();
                                byte[] buffer = new byte[size];
                                inputStream.read( buffer );
                                inputStream.close();

                                json_string3 = new String( buffer, "UTF-8" );
                                Gson gson = new Gson();
                                Results results = gson.fromJson( json_string3, Results.class );
                                produce = results.getResults();
                                Log.d( "demo", "produce = " + produce );

                                discountAdapter adapter = new discountAdapter( MainActivity.this, R.layout.product_info, produce );
                                listView.setAdapter( adapter );

                            } catch (IOException e) {
                                Toast.makeText( MainActivity.this, "inside catch", Toast.LENGTH_SHORT ).show();
                                e.printStackTrace();
                            }
                        }
                        else if(places.contains( "Lifestyle" )){
                            Log.d( "demo", "Connected to lifestyle" );
                            String json_string2 = null;
                            try {
                                InputStream inputStream = getAssets().open( "lifestyle.json" );
                                int size = inputStream.available();
                                byte[] buffer = new byte[size];
                                inputStream.read( buffer );
                                inputStream.close();

                                json_string2 = new String( buffer, "UTF-8" );
                                Gson gson = new Gson();
                                Results results = gson.fromJson( json_string2, Results.class );
                                lifestyle = results.getResults();
                                Log.d( "demo", "lifestyle = " + lifestyle );

                                discountAdapter adapter = new discountAdapter( MainActivity.this, R.layout.product_info, lifestyle );
                                listView.setAdapter( adapter );

                            } catch (IOException e) {
                                Toast.makeText( MainActivity.this, "inside catch", Toast.LENGTH_SHORT ).show();
                                e.printStackTrace();
                            }
                        }
                        else if(places.contains( "Grocery" )){
                            Log.d( "demo", "Connected to grocery" );
                            String json_string1 = null;
                            try {
                                InputStream inputStream = getAssets().open( "grocery.json" );
                                int size = inputStream.available();
                                byte[] buffer = new byte[size];
                                inputStream.read( buffer );
                                inputStream.close();

                                json_string1 = new String( buffer, "UTF-8" );
                                Gson gson = new Gson();
                                Results results = gson.fromJson( json_string1, Results.class );
                                grocery = results.getResults();
                                Log.d( "demo", "grocery = " + grocery );

                                discountAdapter adapter = new discountAdapter( MainActivity.this, R.layout.product_info, grocery );
                                listView.setAdapter( adapter );

                            } catch (IOException e) {
                                Toast.makeText( MainActivity.this, "inside catch", Toast.LENGTH_SHORT ).show();
                                e.printStackTrace();
                            }

                        }
                        else{
                            String json_string = null;
                            try {
                                InputStream inputStream = getAssets().open( "discount.json" );
                                int size = inputStream.available();
                                byte[] buffer = new byte[size];
                                inputStream.read( buffer );
                                inputStream.close();

                                json_string = new String( buffer, "UTF-8" );
                                Gson gson = new Gson();
                                Results results = gson.fromJson( json_string, Results.class );
                                resultsValueList = results.getResults();

                                discountAdapter adapter = new discountAdapter( MainActivity.this, R.layout.product_info, resultsValueList );
                                listView.setAdapter( adapter );

                            } catch (IOException e) {
                                Toast.makeText( MainActivity.this, "inside catch", Toast.LENGTH_SHORT ).show();
                                e.printStackTrace();
                            }
                        }
                        // TODO: update the UI here
//                        Log.d("Airport", "Nearest places: " + places);
//                        apiInterface = APIClient.getClient().create(APIInterface.class);

//                            apiInterface.getImage();
//                            Call<ResponseBody> str =  apiInterface.getImage();
//                            Log.d( "demo", "str = " + str );

//                        Call<ResponseBody> call = apiInterface.get_all();
//
//                        Log.d( "demo", "call = " + call );


                    }
                }
            });
        }

    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }

    @Override
    protected void onPause() {
        beaconManager.stopRanging(region);

        super.onPause();
    }

    private static final Map<String, List<String>> PLACES_BY_BEACONS;
    static {
        Map<String, List<String>> placesByBeacons = new HashMap<>();
        placesByBeacons.put("55125:738", new ArrayList<String>() {{
            add("Grocery");
//            regionName = "grocery";
        }});
        placesByBeacons.put("59599:33091", new ArrayList<String>() {{
            add("Lifestyle");
//            regionName = "lifestyle";
        }});
        placesByBeacons.put("1564:34409", new ArrayList<String>() {{
            add("Produce");
//            regionName = "produce";
        }});
        PLACES_BY_BEACONS = Collections.unmodifiableMap(placesByBeacons);
    }

    private List<String> placesNearBeacon(Beacon beacon) {
        String beaconKey = String.format("%d:%d", beacon.getMajor(), beacon.getMinor());
        if (PLACES_BY_BEACONS.containsKey(beaconKey)) {
            Toast.makeText( this, "" + PLACES_BY_BEACONS.get( beaconKey), Toast.LENGTH_SHORT ).show();
//            Log.d("demo", "beaconKey = " + beaconKey);
//            regionName = String.valueOf( PLACES_BY_BEACONS.get(0) );
//            String a = beacon.getUniqueKey();
//            Log.d( "demo", "a = " + a );
//            Log.d( "demo", "regionName= " + regionName );
//            fun(regionName, resultsValueList);
//            regionName = beaconKey;
//            region = PLACES_BY_BEACONS.get( beaconKey);
            return PLACES_BY_BEACONS.get(beaconKey);
        }
        return Collections.emptyList();
    }

//    public static void fun(String regionName, List<Results.ResultsValue> resultsValueList){
//
//        for (int i = 0; i < resultsValueList.size(); i++) {
//            if(resultsValueList.get( i ).getRegion() == regionName){
//                newResultsValueList.add( resultsValueList.get( i ) );
//            }
//        }
//
//        Log.d( "demo", "newResultsValueList = " + newResultsValueList );
//    }
}

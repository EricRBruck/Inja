package com.proto.whatscool.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.proto.whatscool.app.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivitiy extends FragmentActivity
{

    private GoogleMap mMap;

   // private static final String TAG = MainActivitiy.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitiy);
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_FILE)
                .build();

        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(3);


        Manager manager = null;
        try {
            manager = new Manager(getApplicationContext().getFilesDir(), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            Log.e("TAG", "Cannot create manager object");
            return;
        }

        // create a name for the database and make sure the name is legal
        String dbname = "hello";
        if (!Manager.isValidDatabaseName(dbname)) {
            Log.e("TAG", "Bad database name");
            return;
        }
// create a new database
        Database database;
        try {
            database = manager.getDatabase(dbname);
            Log.d ("TAG", "Database created");
        } catch (CouchbaseLiteException e) {
            Log.e("TAG", "Cannot get database");
            return;
        }

        // get the current date and time
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Calendar calendar = GregorianCalendar.getInstance();
        String currentTimeString = dateFormatter.format(calendar.getTime());
// create an object that contains data for a document
        Map<String, Object> docContent = new HashMap<String, Object>();
        docContent.put("message", "Hello Couchbase Lite");
        docContent.put("creationDate", currentTimeString);
// display the data for the new document
        Log.d("TAG", "docContent=" + String.valueOf(docContent));
// create an empty document
        Document document = database.createDocument();
// add content to document and write the document to the database
        try {
            document.putProperties(docContent);
            Log.d ("TAG", "Document written to database named " + dbname + " with ID = " + document.getId());
        } catch (CouchbaseLiteException e) {
            Log.e("TAG", "Cannot write document to database", e);
        }
        String docID = document.getId();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package firebase.mango.eletro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import firebase.mango.eletro.FireCall.sensorData;

public class Featurectivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;


    private SeekBar seekx;
    private SeekBar seeky;
    private TextView gas;
    private TextView light;
    private TextView temp;
    private TextView hum;
    private TextView seekxVal;
    private TextView seekyVal;
    private Button Restart;
    private  int progressChangedValue = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featurectivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gas=(TextView)findViewById(R.id.gasID);
        light=(TextView)findViewById(R.id.lightID);
        temp=(TextView)findViewById(R.id.TempID);
        hum=(TextView)findViewById(R.id.HueID);

            seekx=(SeekBar)findViewById(R.id.seekXID);
            seeky=(SeekBar)findViewById(R.id.seekYID);
        seekxVal=(TextView)findViewById(R.id.seekXIDValue);
        seekyVal=(TextView)findViewById(R.id.seekYIDValue);

        Restart=(Button)findViewById(R.id.RESTID);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();

        Restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {



                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                mFirebaseDatabase  =  mFirebaseInstance.getReference("reset");

                                mFirebaseDatabase.setValue(6);
                                Restart.setEnabled(false);
                                new Handler().postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        Restart.setEnabled(true);
                                        mFirebaseDatabase.setValue(0);
                                        Toast.makeText(view.getContext(),"\t\t\t\tDevice Restarted...!\n Check Sensor indicating lights", Toast.LENGTH_LONG).show();
                                    }
                                }, 5000);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Do you really want restart Hardware device?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });

        seekx.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override


            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressChangedValue = i;
                seekxVal.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mFirebaseDatabase  =  mFirebaseInstance.getReference("seekx");

               mFirebaseDatabase.setValue(progressChangedValue);


            }
        });

        seeky.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressChangedValue = i;
                seekyVal.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mFirebaseDatabase  =  mFirebaseInstance.getReference("seeky");

                mFirebaseDatabase.setValue(progressChangedValue);

            }
        });
     //   myRef.setValue("Hello, World!");
        // Read from the database
        mFirebaseDatabase.child("gas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String appTitle = dataSnapshot.getValue().toString();

               // sensor.setGas(Integer.parseInt(appTitle));
                gas.setText(appTitle);//Gas Intensity
             //   gas.setProgress(10);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
        mFirebaseDatabase.child("humidity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String appTitle = dataSnapshot.getValue().toString();
                sensorData sensor = new sensorData();
                // sensor.setGas(Integer.parseInt(appTitle));
                hum.setText(appTitle);//Humidity
                //   gas.setProgress(10);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
        mFirebaseDatabase.child("light").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String appTitle = dataSnapshot.getValue().toString();
                sensorData sensor = new sensorData();
                // sensor.setGas(Integer.parseInt(appTitle));
                light.setText(appTitle);//Light Intensity
                //   gas.setProgress(10);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
        mFirebaseDatabase.child("temp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String appTitle = dataSnapshot.getValue().toString();
                sensorData sensor = new sensorData();
                // sensor.setGas(Integer.parseInt(appTitle));
                temp.setText(appTitle);//Temprature
                //   gas.setProgress(10);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }
    private void showData(DataSnapshot dataSnapshot) {
//        for(DataSnapshot ds : dataSnapshot.getChildren()){
//            sensorData sensor = new sensorData();
////            uInfo.setName(ds.child(userID).getValue(UserInformation.class).getName()); //set the name
////            uInfo.setEmail(ds.child(userID).getValue(UserInformation.class).getEmail()); //set the email
////            uInfo.setPhone_num(ds.child(userID).getValue(UserInformation.class).getPhone_num()); //set the phone_num
//
//
//            sensor.setGas(Integer.parseInt(ds.child("gas").getValue().toString()));
//          //.getValue(sensorData.class).getGas();
//            sensor.setHumidity(Integer.parseInt(ds.child("humidity").getValue().toString()));
//            sensor.setLight(Integer.parseInt(ds.child("light").getValue().toString()));
//            sensor.setTemp(Integer.parseInt(ds.child("temp").getValue().toString()));
//
//
//
//
//
//            temp.setProgress((int)sensor.getTemp());
//            gas.setProgress(sensor.getGas());
//            humi.setProgress((int)sensor.getHumidity());
//            light.setProgress(sensor.getLight());
//
//
//
//        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.featurectivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            try {
                Intent k = new Intent(Featurectivity.this,ACControlActivity.class);
                startActivity(k);
            } catch(Exception e) {
                e.printStackTrace();
            }

        } else if (id == R.id.nav_gallery) {

            try {
                Intent k = new Intent(Featurectivity.this,Load_1_Activity.class);
                startActivity(k);
            } catch(Exception e) {
                e.printStackTrace();
            }

        } else if (id == R.id.nav_slideshow) {
            try {
                Intent k = new Intent(Featurectivity.this,Load_2_Activity.class);
                startActivity(k);
            } catch(Exception e) {
                e.printStackTrace();
            }

        } else if (id == R.id.nav_manage) {

            try {
                Intent k = new Intent(Featurectivity.this,Sensor_Activity.class);
                startActivity(k);
            } catch(Exception e) {
                e.printStackTrace();
            }

        } else if (id == R.id.nav_share) {
            try {
                Intent k = new Intent(Featurectivity.this,Device_info_Activity.class);
                startActivity(k);
            } catch(Exception e) {
                e.printStackTrace();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

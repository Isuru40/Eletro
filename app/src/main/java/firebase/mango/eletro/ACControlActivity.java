package firebase.mango.eletro;


import android.content.Intent;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.util.Calendar;
import java.util.Date;

public class ACControlActivity extends AppCompatActivity {

    private AlertDialog.Builder dialodBuilder;
    private AlertDialog dialog;
    private EditText Edit_threshol_temp;
    private EditText  Edit_threshol_hum;
    private EditText  Edit_trig_time;
    private Button addButton;
    private Button setTime;
    private CheckedTextView fanspeed;
    private CheckedTextView airswing;
    private CheckedTextView acstatus;
    private Button config;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accontrol);

        addButton=(Button)findViewById(R.id.add_point_id);



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatepopupDialog();
            }
        });


        GraphView graph = (GraphView) findViewById(R.id.graph);
        // generate Dates
        Calendar calendar = Calendar.getInstance();
      //  Time d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
          //      new DataPoint(d1, 1),
                new DataPoint(d2, 5),
                new DataPoint(d3, 3)
        });

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

// set manual x bounds to have nice steps
     //   graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d3.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
  //      graph.addSeries(series);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(), "Series1: On Data Point clicked: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });


        // set manual X bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-150);
        graph.getViewport().setMaxY(150);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(4);
        graph.getViewport().setMaxX(80);

        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        graph.addSeries(series);

        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling
    }


    private void CreatepopupDialog()
    {

        dialodBuilder= new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.ac_shedular,null);
        Edit_threshol_temp =(EditText) view.findViewById(R.id.Edit_tempID);
        Edit_threshol_hum =(EditText) view.findViewById(R.id.Edit_HumID);
      //  final Spinner AC_Fan_spin = findViewById(R.id.AC_Fan_spin_ID);





//
//        String[] AC_Fan_spinitems = new String[]{"HIGH", "MEDIUM", "LOW"};
//
////create an adapter to describe how the items are displayed, adapters are used in several places in android.
////There are multiple variations of this, but this is the basic variant.
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, AC_Fan_spinitems);
////set the spinners adapter to the previously created one.
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        AC_Fan_spin.setAdapter(adapter);
        config=(Button)view.findViewById(R.id.configID);

        dialodBuilder.setView(view);
        dialog=dialodBuilder.create();
        dialog.show();

        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(getApplicationContext(), "\nSpinner 1 : "+ AC_Fan_spin.getSelectedItem().toString() , Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });


    }
//    public void onCheckboxClicked(View v) {
//        // Is the view now checked?
//        boolean checked = ((CheckBox) v).isChecked();
//        TextView swing=(TextView)findViewById(R.id.AC_Swing_status_ID);
//        TextView status=(TextView)findViewById(R.id.AC_status_ID);
//        // Check which checkbox was clicked
//        switch(v.getId()) {
//            case R.id.AC_Swing_check_ID:
//                if (checked){
//
//
//                    swing.setText("ON");
//                    swing.setTextColor(getResources().getColor(R.color.green));
//
//                }
//
//            else
//                    swing.setText("OFF");
//                swing.setTextColor(getResources().getColor(R.color.Red));
//                break;
//            case R.id.AC_status_Check_ID:
//                if (checked){
//                    status.setText("ON");
//                    status.setTextColor(getResources().getColor(R.color.green));
//                }
//
//            else
//                    status.setText("OFF");
//                     status.setTextColor(getResources().getColor(R.color.Red));
//                break;
//
//        }
//    }

}

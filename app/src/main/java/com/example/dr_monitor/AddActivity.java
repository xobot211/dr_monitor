package com.example.dr_monitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private EditText edit_name;
    private CalendarView cal;
    private Button button;


    private DatabaseReference mDataBase;

    private String KEY = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edit_name = findViewById(R.id.editText);
        cal = findViewById(R.id.calendarView);
        button = findViewById(R.id.button2);


        Calendar calendar= Calendar.getInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        String t="";
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                String m="";
                switch (mMonth+1){
                    case 1: m="январь";
                    break;
                    case 2: m="февраль";
                    break;
                    case 3: m="март";
                    break;
                    case 4: m="апрель";
                    break;
                    case 5: m="май";
                    break;
                    case 6: m="июнь";
                    break;
                    case 7: m="июль";
                    break;
                    case 8: m="август";
                    break;
                    case 9: m= "сентябрь";
                    break;
                    case 10: m= "октябрь";
                    break;
                    case 11: m= "ноябрь";
                    break;
                    case 12: m= "декабрь";
                    break;
                }
                String selectedDate = new StringBuilder().append(m)
                        .append("-").append(mDay)
                        .append(" ").toString();

                String selectedDate1 = new StringBuilder().append(mMonth+1)
                        .append("-").append(mDay).append("-").append(mYear)
                        .append(" ").toString();



//                Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_LONG).show();
//                tv.setText(selectedDate);
                String curDate = String.valueOf(dayOfMonth);




                View.OnClickListener cl = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mDataBase = FirebaseDatabase.getInstance().getReference(KEY);
                        String id= mDataBase.getKey();
                        String name = edit_name.getText().toString();


                        DataClass dataClass =new DataClass(id,name,selectedDate,selectedDate1,mDay,mMonth+1);


                        mDataBase.push().setValue(dataClass);
                        Toast.makeText(getApplicationContext(),"Добавлено",Toast.LENGTH_LONG).show();

                    }

                };
                button.setOnClickListener(cl);
            }
        });








    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
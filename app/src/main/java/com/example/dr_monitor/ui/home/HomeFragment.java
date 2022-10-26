package com.example.dr_monitor.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.dr_monitor.AddActivity;
import com.example.dr_monitor.DataClass;
import com.example.dr_monitor.R;
import com.example.dr_monitor.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private Button add_data;
    private TextView to_day;
    private TextView close_dr;

    private DatabaseReference mDataBase;
    private String KEY="USER";

    private ArrayList<DataClass> arrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        add_data =root.findViewById(R.id.button);
        to_day=root.findViewById(R.id.tvToDay);
        close_dr=root.findViewById(R.id.tv_close);

        arrayList = new ArrayList<DataClass>();
        mDataBase = FirebaseDatabase.getInstance().getReference(KEY);


        View.OnClickListener cl =new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddActivity.class);
                startActivity(i);
            }
        };

        add_data.setOnClickListener(cl);

        Date currentDate = Calendar.getInstance().getTime();
        String d= currentDate.toString().substring(8,10);
        String m = currentDate.toString().substring(4,7);

        int i_d = Integer.parseInt(d);





        String curr = new StringBuilder().append("Сегодня: ").append(d).append(" ").append(m).toString();
        to_day.setText(curr);

//        Log.d("My_Log",Integer.toString(i_m));
//        to_day.setText(Integer.toString(i_m));

        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String min_name="";
                int min_month=12;
                boolean flag=true;
                //int i_d=i_d;
                int i_m =0;
                int min_off=31;
                int min=31;

                i_m=con1(m);



                for(DataSnapshot ds:snapshot.getChildren()){
                    DataClass dt1=ds.getValue(DataClass.class);
                    assert dt1 !=null;
                    arrayList.add(dt1);
                }
                Collections.sort(arrayList);
                for(DataClass d :arrayList) {
                    if (d.getM() == i_m) {
                        if (d.getD() > i_d && ((d.getD() - i_d) < min_off)) {
                            min = d.getD();
                            min_off = d.getD() - i_d;
                            Log.d("My_Log", Integer.toString(min));
                            min_month = d.getM();
                            flag=false;
                            min_name=d.getName();
                        }

                    }
                }
                if(flag) {
                    for (DataClass d : arrayList) {
                        if (d.getM() > i_m) {
                            min = d.getD();
                            min_month = d.getM();
                            min_name=d.getName();
                            break;
                        }


                    }
                }



                String s = new StringBuilder().append("Ближайшее др: \n").append("          ").append(min_name).append(", ").append(con(min_month)).append(" ").append(min).toString();

                close_dr.setText(s);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDataBase.addValueEventListener(vListener);







        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    public String con(int m){
        if(m==1) return "Январь";
        if(m==2) return "Февраль";
        if(m==3) return "Март";
        if(m==4) return "Апрель";
        if(m==5) return "Май";
        if(m==6) return "Июнь";
        if(m==7) return "Июль";
        if(m==8) return "Август";
        if(m==9) return "Сентябрь";
        if(m==10) return "Октябрь";
        if(m==11) return "Ноябрь";
        if(m==12) return "Декабрь";

        else return null;
    }

    public int con1(String m){
        if(m.equals("Jan")) return 1;
        else if(m.equals("Feb")) return 2;
        else if(m.equals("Mar"))  return 3 ;
        else if(m.equals("Apr")) return 4;
        else if(m.equals("May")) return 5;
        else if(m.equals("June")) return 6;
        else if(m.equals("July")) return 7;
        else if(m.equals("Aug")) return 8;
        else if(m.equals("Sep")) return 9;
        else if(m.equals("Oct")) return 10;
        else if(m.equals("Nov")) return 11;
        else if(m.equals("Dec")) return 12;

        return 0;
    }



            @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
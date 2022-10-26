package com.example.dr_monitor.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.dr_monitor.ArrayAdapterSpisok;
import com.example.dr_monitor.DataClass;
import com.example.dr_monitor.ListItemClass;
import com.example.dr_monitor.R;
import com.example.dr_monitor.databinding.FragmentGalleryBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    private ListView listView;
    private ArrayAdapterSpisok adapter;
    private List<ListItemClass> listData;

    private DatabaseReference mDataBase;
    private String KEY="USER";

    private ArrayList<DataClass> arrayList;

    private FloatingActionButton fl;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = root.findViewById(R.id.listViewSpisok);
        listData = new ArrayList<>();
        adapter = new ArrayAdapterSpisok(getActivity(), R.layout.pattern_spisok, listData, getLayoutInflater());
        listView.setAdapter(adapter);
        mDataBase = FirebaseDatabase.getInstance().getReference(KEY);

        arrayList = new ArrayList<DataClass>();

        fl=root.findViewById(R.id.floatingActionButton3);
        fl.setVisibility(View.GONE);

//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("My_Log", "itemClick: position = " + position + ", id = "
//                        + id);
//            }
//        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("My_Log", "itemClick: position = " + position + ", id = "
                        + id);

                fl.setVisibility(View.VISIBLE);

                fl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ListItemClass l = listData.get(position);
                        Log.d("My_Log",l.getData_1());



                        ValueEventListener vl = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    DataClass dt=dataSnapshot.getValue(DataClass.class);
                                    assert dt !=null;
                                    if(dt.getName().equals(l.getData_1())){
                                        Log.d("My_Log","привет");
                                        dataSnapshot.getRef().removeValue();
                                        arrayList.clear();
                                        break;

                                    }
                                }

                                adapter.notifyDataSetChanged();


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        };
                        mDataBase.addValueEventListener(vl);


                    }
                });
                return true;
            }
        });



        getData();







        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    private void getData(){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(listData.size()>0) listData.clear();

//                for(DataSnapshot ds : snapshot.getChildren()){
//                    DataClass dt1=ds.getValue(DataClass.class);
//                    assert dt1 !=null;
//                    ListItemClass items = new ListItemClass();
//                    for(int i=0;i<2;i++){
//
//                        items.setData_1(dt1.name);
//                        items.setData_2(dt1.time);
//
//                    }
//                    listData.add(items);
//                }
//
//                adapter.notifyDataSetChanged();
                for(DataSnapshot ds:snapshot.getChildren()){
                    DataClass dt1=ds.getValue(DataClass.class);
                    assert dt1 !=null;
                    arrayList.add(dt1);
                }
                Collections.sort(arrayList);

                for(DataClass dataClass : arrayList){
                    ListItemClass items = new ListItemClass();
                    for(int i=0;i<2;i++){

                        items.setData_1(dataClass.name);
                        items.setData_2(dataClass.time);

                   }
                    listData.add(items);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDataBase.addValueEventListener(vListener);

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
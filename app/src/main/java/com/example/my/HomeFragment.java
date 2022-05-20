package com.example.my;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements AdapterClass.ViewHolder.RecyclerViewClickListener{

    RecyclerView rv_lists;
    AdapterClass adapterClass;
    ArrayList<ModelClass> modelClasses;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseFirestore db;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        rv_lists=view.findViewById(R.id.rv_list);
        modelClasses = new ArrayList<>();
        initData();
//        setAdapter();
        return view;
    }

    private void setAdapter() {

        rv_lists.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        adapterClass =new AdapterClass(modelClasses,HomeFragment.this::onClickListner);
        rv_lists.setAdapter(adapterClass);
    }

    private void initData() {
        //get data from database

        modelClasses=new ArrayList<>();

        db.collection("post").whereEqualTo("UID",mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                if (task.isSuccessful()){

                    for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                        String sListID = documentSnapshot.getData().get("PostDescription").toString();
//                        int listID = Integer.valueOf(sListID);
                        String userID = documentSnapshot.getData().get("UserId").toString();


                        modelClasses.add(new ModelClass(photo,sListID,userID));
                        setAdapter();
                    }
                }else {
                    Toast.makeText(getContext(),"No Data!",Toast.LENGTH_SHORT).show();
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Error Occurred!!",Toast.LENGTH_SHORT).show();
            }
        });






//        modelClasses.add(new ModelClass(R.drawable.beach,"Isuru","dertgjhgh jhgjgjhghjgdertgjhgh jhgjgjhghjgdertgjhgh jhgjgjhghjgdertgjhgh jhgjgjhghjgdertgjhgh jhgjgjhghjgv dertgjhgh jhgjgjhghjg"));
//        modelClasses.add(new ModelClass(R.drawable.sigiriya,"Lahiru","dertgjhgh jhgjgjhghjg dertgjhgh jhgjgjhghjgdertgjhgh jhgjgjhghjgdertgjhgh jhgjgjhghjgdertgjhgh jhgjgjhghjg"));
//        modelClasses.add(new ModelClass(R.drawable.galle,"Thisara","dertgjhgh jhgjgjhghjg dertgjhgh jhgjgjhghjgdertgjhgh jhgjgjhghjgdertgjhgh jhgjgjhghjgdertgjhgh jhgjgjhghjgv"));
//        modelClasses.add(new ModelClass(R.drawable.beach,"sasindu4","dertgjhgh jhgjgjhghjg"));
//        modelClasses.add(new ModelClass(R.drawable.beach,"Yasiru","dertgjhgh jhgjgjhghjg"));
//        modelClasses.add(new ModelClass(R.drawable.beach,"Nishedha","dertgjhgh jhgjgjhghjg"));
    }

    @Override
    public void onClickListner(int position) {
        String name=modelClasses.get(position).getName();
        Toast.makeText(getContext(), "Hello!!"+name , Toast.LENGTH_SHORT).show();

    }
}
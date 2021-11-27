package com.example.my;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    EditText et_name,et_email,et_phone;
    TextView reset_password;
    RelativeLayout bt_logout,bt_update;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseFirestore db;
    String Uid;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        et_name=view.findViewById(R.id.edt_name);
        et_email=view.findViewById(R.id.edt_email);
        et_phone=view.findViewById(R.id.edt_contact_number);
        bt_logout=view.findViewById(R.id.btn_logout);
        bt_update=view.findViewById(R.id.btn_update);
        reset_password=view.findViewById(R.id.reset_password);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        Uid=mAuth.getCurrentUser().getUid();
        user=mAuth.getCurrentUser();

        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                getActivity().finish();
            }
        });

        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updadeDetails();
            }
        });

        DocumentReference documentReference=db.collection("USER").document(Uid);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                         DocumentSnapshot documentSnapshot=task.getResult();
                         if(documentSnapshot.exists())
                         {
                             String name,email,phone;
                             name=(String) documentSnapshot.getData().get("USER NAME");
                             email=(String) documentSnapshot.getData().get("EMAIL");
                             phone=(String) documentSnapshot.getData().get("PHONE");
                             setDetails(name,email,phone);

                         }else{
                            System.out.println("No such document");
                         }
                }else{
                    Toast.makeText(getContext(), "Error Occurred!!" , Toast.LENGTH_SHORT).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error Occurred!!" , Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

    private void updadeDetails() {
              //dialog box with input parameter
        String my_email=et_email.getText().toString();

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Please Authenticate");
        builder.setMessage("Please enter your password here...");
        EditText editText=new EditText(getContext());
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(editText);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String password= editText.getText().toString();
                AuthenticatePassword(my_email,password);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void AuthenticatePassword(String email,String password) {
        AuthCredential authCredential= EmailAuthProvider.getCredential(email,password);
        user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
             updateDatabase();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Your password is wrong!!" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateDatabase() {
        String sName,sEmail,sPhone;
        sName=et_name.getText().toString();
        sEmail=et_email.getText().toString();
        sPhone=et_phone.getText().toString();

        user.updateEmail(sEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                db.collection("USER").document(Uid).update("USER NAME",sName);
                db.collection("USER").document(Uid).update("EMAIL",sEmail);
                db.collection("USER").document(Uid).update("PHONE",sPhone);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void setDetails(String name, String email, String phone) {
            et_name.setText(name);
            et_email.setText(email);
            et_phone.setText(phone);

    }
}
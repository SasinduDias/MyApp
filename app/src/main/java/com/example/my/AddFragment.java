package com.example.my;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    TextView tv_add;
    EditText et_description;
    ImageView img_post;
    String Uid;
    RelativeLayout btn_upload;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Bitmap image_file;
    StorageReference storageReference;
    UploadTask uploadTask;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_CANCELED){
            switch (requestCode){
                case 0:
                    if(resultCode == RESULT_OK && data != null){
                        image_file=(Bitmap) data.getExtras().get("data");
                        img_post.setImageBitmap(image_file);

                    }else {
                        Toast.makeText(getContext(), "Error occurred", Toast.LENGTH_SHORT).show();

                    }
                    break;
                case 1:
                    if(resultCode == RESULT_OK && data != null){
                        Uri path=data.getData();
                        try {
                            image_file=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),path);
                            img_post.setImageBitmap(image_file);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }else {
                        Toast.makeText(getContext(), "Error occurred", Toast.LENGTH_SHORT).show();

                    }
                    break;
            }

        }
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
        View view= inflater.inflate(R.layout.fragment_add, container, false);

        tv_add=view.findViewById(R.id.tv_add);
        img_post=view.findViewById(R.id.img_post);
        btn_upload=view.findViewById(R.id.btn_upload);
        et_description=view.findViewById(R.id.description);

        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        Uid=mAuth.getUid();

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String description=et_description.getText().toString();
              String id= db.collection("post").document().getId();
                DocumentReference documentReference=db.collection("post").document(id);
                Map<String,Object> post= new HashMap<>();
                post.put("UserId",Uid);
                post.put("PostDescription",description);
                documentReference.set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                      uploadImage(image_file,id);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final CharSequence[] option={"Take Photo","From Gallery","Cancel"};
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Select your option");
                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                //cam
                                Intent takePicture=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(takePicture,0);
                                break;
                            case 1:
                                //gallery
                                Intent pickPicture=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPicture,1);
                                break;
                            case 2:
                                //cancel
                                dialog.dismiss();
                                break;
                        }
                    }
                });
                builder.show();
            }
        });


        return view;
    }

    private void uploadImage(Bitmap image_file, String id) {
        // bitmap to bytearray
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        image_file.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] byteArray=byteArrayOutputStream.toByteArray();
        storageReference= FirebaseStorage.getInstance().getReference().child("post_image/"+ id);

        uploadTask=storageReference.putBytes(byteArray);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,new HomeFragment()).commit();
                Toast.makeText(getContext(), "Saved successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Post not uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
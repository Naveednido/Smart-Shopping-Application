package com.example.smartshoppingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_Settings extends AppCompatActivity {


    CircleImageView profile_image;
    Button btn_change_image , btn_save_prof;

    EditText f_name , l_name , phone , address , postalcode ;

    private static String email1 , image1 ,thumb_image ;

    private static  final int GALLERY_PICK = 1;
    ProgressDialog mProgressDialog;

    private StorageReference mStorageReference;
    private DatabaseReference user_database ,mDatabaseReference;
    private FirebaseUser  mCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__settings);

        profile_image = findViewById(R.id.profilesetting_image);
        btn_change_image = findViewById(R.id.btn_change_image_profileseeting);
        btn_save_prof = findViewById(R.id.btn_save_prof);
        f_name = findViewById(R.id.f_name_prof);
        l_name = findViewById(R.id.l_name_prof);
        phone = findViewById(R.id.phone_prof);
        address = findViewById(R.id.address_prof);
        postalcode = findViewById(R.id.postal_code_prof);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String U_id = mCurrentUser.getUid();

        mStorageReference = FirebaseStorage.getInstance().getReference();
        user_database = FirebaseDatabase.getInstance().getReference().child("User").child(U_id);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(U_id);


        Toolbar setting_toolbar = findViewById(R.id.profile_setting_appbar);
        setSupportActionBar(setting_toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Account Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String image = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();
                String f_nam = Objects.requireNonNull(dataSnapshot.child("first_name").getValue()).toString();
                String l_nam = Objects.requireNonNull(dataSnapshot.child("last_name").getValue()).toString();
                String phn = Objects.requireNonNull(dataSnapshot.child("phone").getValue()).toString();
                String add = Objects.requireNonNull(dataSnapshot.child("address").getValue()).toString();
                String postcode = Objects.requireNonNull(dataSnapshot.child("postal_code").getValue()).toString();
                email1 = Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString();
                image1 = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();

                f_name.setText(f_nam); l_name.setText(l_nam); address.setText(add);
                phone.setText(phn); postalcode.setText(postcode);

                if (!image.equals("default"))
                Picasso.with(Profile_Settings.this).load(image).placeholder(R.drawable.profile_image_dummy).into(profile_image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_save_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgressDialog = new ProgressDialog(Profile_Settings.this);
                mProgressDialog.setTitle("Updating Information");
                mProgressDialog.setMessage("Please Wait While We Update Your Information !");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(!(dataSnapshot.child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getUid())).exists()))
                        {
                            HashMap<String, String> usermap =  new HashMap<>();
                            usermap.put("first_name",f_name.getText().toString());
                            usermap.put("last_name",l_name.getText().toString());
                            usermap.put("phone",phone.getText().toString());
                            usermap.put("address", address.getText().toString());
                            usermap.put("postal_code",postalcode.getText().toString());
                            usermap.put("email",email1);
                            usermap.put("image",image1);


                            mDatabaseReference.setValue(usermap).addOnCompleteListener(new OnCompleteListener<Void>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        mProgressDialog.dismiss();
                                        Toast.makeText(Profile_Settings.this , "Infroation  Updated" , Toast.LENGTH_LONG).show();

                                    }
                                    else {
                                        mProgressDialog.hide();
                                        Toast.makeText(Profile_Settings.this , "Infroation not Updated" , Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




            }
        });


        btn_change_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Using an Intent to get all the images from the gallery.
                Intent galleryIntent = new Intent();

                //Setting up the path of the image source.
                galleryIntent.setType("image/*");

                //Getting the content(images) from the above path.
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                //Returning the Activity with a result(selected Image).
                startActivityForResult(Intent.createChooser(galleryIntent , "SELECT IMAGE") , GALLERY_PICK);

            }
        });




    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    //Checking if the selected image is true or not and the result is Ok or not.
    if (requestCode == GALLERY_PICK && resultCode == RESULT_OK){

        //Getting the Data of the Image and saving it in a Uri.
        Uri imageUri = data.getData();

        //Instantiating the cropImage feature and setting the ratio in 1:1.
        CropImage.activity(imageUri).setAspectRatio(1 , 1)
                .start(Profile_Settings.this);

    }

    //Checking if the image is cropped or not.
    if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        //Checking if the result is Ok or not, if yes we will store the image in a uri.
        if (resultCode == RESULT_OK) {
            mProgressDialog = new ProgressDialog(Profile_Settings.this);
            mProgressDialog.setTitle("Uploading Image");
            mProgressDialog.setMessage("Please Wait While We Upload Your Image");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
            Uri resultUri = result.getUri();
            //Getting the Current UID of the User and storing it in a String.
            final String uid_img = FirebaseAuth.getInstance().getCurrentUser().getUid();

            //Saving the image in the Firebase Storage and naming the child with the UID.
            final StorageReference filepath = mStorageReference.child("Profile_Images").child(uid_img+".jpg");
            final StorageReference thumb_filepath = mStorageReference.child("Profile_Images").child("thumbs").child(uid_img+".jpg");
            //If the resultUri is nor Empty or NULL.
            if (resultUri != null) {
                //We Will setup an OnCompleteListener to store the image in the desired location in the storage.
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                        //If the task is Successful we will display a toast.
                        if (task.isSuccessful()){
                            mStorageReference.child("Profile_Images").child(uid_img+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String downloadUrl = uri.toString();

                                    user_database.child("image").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                            mProgressDialog.dismiss();
                                            }
                                        }
                                    });

                                }
                            });


                        }else {

                            Toast.makeText(Profile_Settings.this , "Error" , Toast.LENGTH_LONG).show();

                            mProgressDialog.dismiss();

                        }
                    }
                });
            }

            //If the task is not successful then we will display an Error Message.
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Exception error = result.getError();

        }
    }

}





    public static String random()
    {
        Random generator = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        int randomlength = generator.nextInt(6);
        char tempchar;
        for (int i=0 ; i<=randomlength ; i++)
        {
            tempchar = (char)(generator.nextInt(96)+32);
            stringBuilder.append(tempchar);
        }

        return stringBuilder.toString();
    }
}

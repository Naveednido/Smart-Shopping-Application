package com.example.smartshoppingapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class Product_Complete_Detail extends AppCompatActivity {

    private Toolbar toolbar;
    private String price,id,description,image_url ,name , brand_name , quantity1;
    private ProgressDialog mProgressDialog;

    FirebaseAuth mAuth;
    private DatabaseReference fav_DatabaseReference;
    private DatabaseReference recommended_DatabaseReference , rating_daDatabaseReference, add_to_cart_daDatabaseReference;
    private DatabaseReference checked_liked;

    int liked = 0;
    int likess = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__complete__detail);

        FirebaseUser mcurrentuser = FirebaseAuth.getInstance().getCurrentUser();
        final String U_ID = mcurrentuser.getUid();

        fav_DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Liked_Products").child(U_ID);
        checked_liked = FirebaseDatabase.getInstance().getReference().child("Liked_Products").child(U_ID);
        recommended_DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recommeded_Products");
        rating_daDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Recommeded_Products");
        add_to_cart_daDatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Cart").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());


        price = getIntent().getStringExtra("Price");
        id = getIntent().getStringExtra("Id");
        description = getIntent().getStringExtra("Discription");
        image_url = getIntent().getStringExtra("image_url");
        name = getIntent().getStringExtra("Name");
        brand_name = getIntent().getStringExtra("brand_name");
        quantity1 = getIntent().getStringExtra("quantity");

        toolbar = findViewById(R.id.product_complete_detail_appbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ImageView imagee = findViewById(R.id.image_detail_activity);
        final TextView pricee = findViewById(R.id.price_detail_activity);
        final TextView idd = findViewById(R.id.id_detail_activity);
        final TextView namee = findViewById(R.id.name_detail_activity);
        final TextView discription = findViewById(R.id.description_detail_activity);
        final ImageButton btn_like = findViewById(R.id.btn_like_detail_Activity);
        final EditText quantity = findViewById(R.id.quantity_complete);
        Button btn_add_to_cart = findViewById(R.id.btn_add_product_to_cart_detail_activity);

        quantity.setText("1");

        //Setting Details

        Picasso.with(getApplicationContext()).load(image_url).into(imagee);
        pricee.setText(price);
        idd.setText(id);
        namee.setText(name);
        discription.setText(description);

        //checking wheather products is liked or not


        checked_liked.child(idd.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    btn_like.setEnabled(false);
                    btn_like.setImageResource(R.drawable.like);
                }
                else
                {
                    btn_like.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        //Setting Button action listner

        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();
                mProgressDialog = new ProgressDialog(Product_Complete_Detail.this);
                mProgressDialog.setTitle("Liked");
                mProgressDialog.setMessage("Please Wait While We Add it to your Favourite Products !");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        liked++;

                        if (!dataSnapshot.child("Liked_Products").child(U_ID).child(id).exists())
                        {
                            final HashMap<String, String> productmap = new HashMap<>();
                            productmap.put("name",namee.getText().toString());
                            productmap.put("id", idd.getText().toString());
                            productmap.put("description" , discription.getText().toString());
                            productmap.put("image_url" , image_url);
                            productmap.put("price" , price);
                            productmap.put("likes", String.valueOf(liked));
                            productmap.put("brand_name",brand_name);

                            fav_DatabaseReference.child(idd.getText().toString()).setValue(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful())
                                    {
                                        mProgressDialog.dismiss();
                                        btn_like.setEnabled(false);
                                        btn_like.setImageResource(R.drawable.like);

                                    }

                                }
                            });

                    /////////////////////////////////////////////////Recommended PRoducts ////////////////////////////////////////

                           recommended_DatabaseReference.child(idd.getText().toString()).addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                   if (dataSnapshot.exists())
                                   {
                                       rating_daDatabaseReference.child(idd.getText().toString()).child("Liked_by").
                                               child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("liked");

                                   }
                                   else
                                   {
                                       recommended_DatabaseReference.child(idd.getText().toString()).setValue(productmap).
                                               addOnCompleteListener(new OnCompleteListener<Void>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<Void> task) {
                                                       if (task.isSuccessful())
                                                       {
                                                           Toast.makeText(getApplicationContext() , "Added to Reccomended" , Toast.LENGTH_LONG).show();
                                                       }
                                                   }
                                               });
                                       rating_daDatabaseReference.child(idd.getText().toString()).child("Liked_by").
                                               child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("liked");

                                   }
                               }
                               @Override
                               public void onCancelled(@NonNull DatabaseError databaseError) {

                               }
                           });

                            /////////////////////////////Recommended Products End/////////////////////////////////////////////////////////////
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });
/////////////////////////////////////////////////////////Add to cart///////////////////////////////////////////////////////////////////////
        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> productmap = new HashMap<>();
                productmap.put("name",namee.getText().toString());
                productmap.put("id", idd.getText().toString());
                productmap.put("description" , discription.getText().toString());
                productmap.put("image_url" , image_url);
                productmap.put("price" , pricee.getText().toString());
                productmap.put("brand_name" , brand_name);
                productmap.put("quantity" , quantity.getText().toString());

                add_to_cart_daDatabaseReference.child(idd.getText().toString()).setValue(productmap).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(getApplicationContext() , "Added to  Cart" , Toast.LENGTH_LONG).show();

                                }
                            }
                        });







            }
        });
///////////////////////////////////////////////////End of Add to Cart////////////////////////////////////////////////////////////////////


    }
}

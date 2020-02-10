package com.example.smartshoppingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartshoppingapp.Model.Product_model_class;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class All_Products_Display extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView allproductlist;
    private String category , brand ,product_category;


    int like = 0;


    String image_url , price , name , description , id;


    private DatabaseReference mdaDatabaseReference , productdatabase , fav_DatabaseReference , add_to_cart_daDatabaseReference , recommended_DatabaseReference  , rating_daDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__products__display);


        // Getting Brand Name , Brand Category & Product Category/////
        category = getIntent().getStringExtra("category");
        brand = getIntent().getStringExtra("brand");

        product_category = getIntent().getStringExtra("product_category");

        mdaDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Products")
                .child(category).child(brand).child(product_category);

        add_to_cart_daDatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Cart").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

        productdatabase = FirebaseDatabase.getInstance().getReference().child("Products")
                .child(category).child(brand).child(product_category);

        recommended_DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recommeded_Products");
        rating_daDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Recommeded_Products");


        fav_DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Liked_Products").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        toolbar = findViewById(R.id.all_product_display_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(brand +" -> " +product_category );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        allproductlist = findViewById(R.id.all_product_display_rcv);
        allproductlist.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions options =new FirebaseRecyclerOptions.Builder<Product_model_class>()
                .setQuery(mdaDatabaseReference , Product_model_class.class)
                .build();

     FirebaseRecyclerAdapter<Product_model_class , ProductViewHolder> adapter = new
             FirebaseRecyclerAdapter<Product_model_class, ProductViewHolder>(options) {
         @Override
         protected void onBindViewHolder(final ProductViewHolder holder, int position, final Product_model_class model) {


             String U_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
             holder.quantity.setText("1");

             final DatabaseReference checked_liked = FirebaseDatabase.getInstance().getReference().child("Liked_Products").child(U_ID);

//////////////////////////////////Start of liked products//////////////////////////////////////////////////////

             final String product_category = getRef(position).getKey();

             productdatabase.child(product_category).addValueEventListener(new ValueEventListener() {
                 @SuppressLint("ResourceAsColor")
                 @Override
                 public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                     checked_liked.child(dataSnapshot.child("product_id").getValue().toString()).addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                             if (dataSnapshot.exists())
                             {
                                 holder.mark_as_liked.setEnabled(false);
                                 holder.mark_as_liked.setImageResource(R.drawable.like);
                             }
                             else
                             {
                                 holder.mark_as_liked.setEnabled(true);
                             }
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }
                     });
                     if(dataSnapshot.exists())
                     {
                         image_url = dataSnapshot.child("imageurl").getValue().toString();
                         name = dataSnapshot.child("product_name").getValue().toString();
                         price = dataSnapshot.child("product_price").getValue().toString();
                         description = dataSnapshot.child("product_description").getValue().toString();
                         id = dataSnapshot.child("product_id").getValue().toString();


                         holder.price.setText(price);
                         Picasso.with(getApplicationContext()).load(image_url).into(holder.image);
                         holder.p_name.setText(name);

                         if (product_category.equals("Sale"))
                         {
                             holder.price.setTextColor(R.color.colorAccent);
                         }

                         model.setProduct_id(dataSnapshot.child("product_id").getValue().toString());
                         model.setImage_url(dataSnapshot.child("imageurl").getValue().toString());
                         model.setProduct_name(dataSnapshot.child("product_name").getValue().toString());
                         model.setProduct_Discription(dataSnapshot.child("product_description").getValue().toString());
                         model.setPrice(dataSnapshot.child("product_price").getValue().toString());
                     }


                     /////////////////////////Liked Button//////////////////////////////////////
                     holder.mark_as_liked.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {

                             DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();

                             rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                     like++;
                                     if (!dataSnapshot.child("Liked_Products").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(model.getProduct_id()).exists())
                                     {
                                         final HashMap<String, String> productmap = new HashMap<>();
                                         productmap.put("name",model.getProduct_name());
                                         productmap.put("id", model.getProduct_id());
                                         productmap.put("description" , model.getProduct_Discription());
                                         productmap.put("image_url" ,model.getImage_url() );
                                         productmap.put("price" , model.getPrice());
                                         productmap.put("likes", String.valueOf(like));
                                         productmap.put("brand_name",brand);
                                         fav_DatabaseReference.child(model.getProduct_id()).setValue(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                             @Override
                                             public void onComplete(@NonNull Task<Void> task) {

                                                 if (task.isSuccessful())
                                                 {

                                                     Toast.makeText(getApplicationContext() , "Added to Favourites" , Toast.LENGTH_LONG).show();
                                                     holder.mark_as_liked.setEnabled(false);
                                                     holder.mark_as_liked.setImageResource(R.drawable.like);

                                                 }
                                             }
                                         });

                                         recommended_DatabaseReference.child(model.getProduct_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                                             @Override
                                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                 if (dataSnapshot.exists())
                                                 {
                                                     rating_daDatabaseReference.child(model.getProduct_id()).child("Liked_by").
                                                             child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("liked");

                                                 }
                                                 else
                                                 {
                                                     recommended_DatabaseReference.child(model.getProduct_id()).setValue(productmap).
                                                             addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                 @Override
                                                                 public void onComplete(@NonNull Task<Void> task) {
                                                                     if (task.isSuccessful())
                                                                     {
                                                                         Toast.makeText(getApplicationContext() , "Added to Reccomended" , Toast.LENGTH_LONG).show();
                                                                     }
                                                                 }
                                                             });
                                                     rating_daDatabaseReference.child(model.getProduct_id()).child("Liked_by").
                                                             child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("liked");

                                                 }
                                             }

                                             @Override
                                             public void onCancelled(@NonNull DatabaseError databaseError) {

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
//////////////////////////////////////////////End of Liked Products/////////////////////////////////////////////////////////////////////

///////////////////////////////////////////Add to Cart////////////////////////////////////////////////////////////////////////////////

                     holder.add_to_cart.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             HashMap<String, String> productmap = new HashMap<>();
                             productmap.put("name",model.getProduct_name());
                             productmap.put("id", model.getProduct_id());
                             productmap.put("description" , model.getProduct_Discription());
                             productmap.put("image_url" , model.getImage_url());
                             productmap.put("price" , model.getPrice());
                             productmap.put("brand_name" , brand);
                             productmap.put("quantity",holder.quantity.getText().toString());

                             add_to_cart_daDatabaseReference.child(model.getProduct_id()).setValue(productmap).
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
/////////////////////////////////////////End of Add to Cart//////////////////////////////////////////////////////////////////////////
                     holder.itemView.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {

                             Intent main = new Intent(All_Products_Display.this , Product_Complete_Detail.class);
                             main.putExtra("Price",model.getPrice());
                             main.putExtra("image_url",model.getImage_url());
                             main.putExtra("Name",model.getProduct_name());
                             main.putExtra("Id",model.getProduct_id());
                             main.putExtra("Discription",model.getProduct_Discription());
                             main.putExtra("brand_name" , brand);
                             startActivity(main);

                         }
                     });



                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });



         }

         @Override
         public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
             View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_displaying_layout , parent , false);
             return new ProductViewHolder(view);
         }
     };


        allproductlist.setAdapter(adapter);
        adapter.startListening();




    }

    public class ProductViewHolder extends RecyclerView.ViewHolder
    {

        ImageView image;
        TextView price;
        TextView p_name;
        ImageButton mark_as_liked;
        Button add_to_cart;
        EditText quantity;

        public ProductViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.product_image_rcv);
            price = itemView.findViewById(R.id.product_price_rcv);
            p_name = itemView.findViewById(R.id.product_name_rcv);
            add_to_cart = itemView.findViewById(R.id.btn_add_product_to_cart_rcv);
            mark_as_liked = itemView.findViewById(R.id.btn_like_rcv);
            quantity = itemView.findViewById(R.id.quantity_rcv);



        }
    }
}

package com.example.smartshoppingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class Favourite_Products extends AppCompatActivity {

    RecyclerView favouriteProductlist;
    private Toolbar toolbar;

    String image_url , price , name , description , id , brand_name;


    private DatabaseReference favourite_database , databaseReference ,remove_data , remove_like ,add_to_cart_daDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite__products);

        favourite_database = FirebaseDatabase.getInstance().getReference().child("Liked_Products").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference =FirebaseDatabase.getInstance().getReference().child("Liked_Products").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        remove_data = FirebaseDatabase.getInstance().getReference().child("Liked_Products").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        remove_like = FirebaseDatabase.getInstance().getReference().child("Recommeded_Products");
        add_to_cart_daDatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Cart").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

        toolbar = findViewById(R.id.favourite_products_appbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Favourites");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        favouriteProductlist = findViewById(R.id.favourite_products_product_display_rcv);
        favouriteProductlist.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();




        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Product_model_class>().
                setQuery(favourite_database  , Product_model_class.class).build();

        final FirebaseRecyclerAdapter<Product_model_class , ProductViewHolder > adapter =
                new FirebaseRecyclerAdapter<Product_model_class, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(final ProductViewHolder holder, final int position, final Product_model_class model) {

                        String product_ids = getRef(position).getKey();

                        databaseReference.child(product_ids).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists())
                                {
                                    image_url = dataSnapshot.child("image_url").getValue().toString();
                                    name = dataSnapshot.child("name").getValue().toString();
                                    price = dataSnapshot.child("price").getValue().toString();
                                    description = dataSnapshot.child("description").getValue().toString();
                                    id = dataSnapshot.child("id").getValue().toString();
                                    brand_name = dataSnapshot.child("brand_name").getValue().toString();


                                    model.setProduct_name(dataSnapshot.child("name").getValue().toString());
                                    model.setImage_url(dataSnapshot.child("image_url").getValue().toString());
                                    model.setBrand_name(dataSnapshot.child("brand_name").getValue().toString());
                                    model.setProduct_id(dataSnapshot.child("id").getValue().toString());
                                    model.setProduct_Discription(dataSnapshot.child("description").getValue().toString());
                                    model.setPrice(dataSnapshot.child("price").getValue().toString());

                                    holder.p_name.setText(name);
                                    holder.price.setText(price);
                                    Picasso.with(getApplicationContext()).load(image_url).into(holder.image);
                                    holder.p_id.setText(id);


                                }
                                holder.itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent main = new Intent(Favourite_Products.this , Product_Complete_Detail.class);
                                        main.putExtra("Price",model.getPrice());
                                        main.putExtra("image_url",model.getImage_url());
                                        main.putExtra("Name",model.getProduct_name());
                                        main.putExtra("Id",model.getProduct_id());
                                        main.putExtra("Discription",model.getProduct_Discription());
                                        main.putExtra("brand_name",model.getBrand_name());
                                        startActivity(main);
                                    }
                                });
                                //////////////////////////Adding to cart////////////////////////////////////////////
                                holder.add_to_cart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HashMap<String, String> productmap = new HashMap<>();
                                        productmap.put("name", model.getProduct_name());
                                        productmap.put("id", model.getProduct_id());
                                        productmap.put("description" ,model.getProduct_Discription());
                                        productmap.put("image_url" , model.getImage_url());
                                        productmap.put("price" , model.getPrice());
                                        productmap.put("brand_name",model.getBrand_name());
                                        productmap.put("quantity",holder.quantity.getText().toString());


                                        add_to_cart_daDatabaseReference.child(model.getProduct_id())
                                                .setValue(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful())
                                                {
                                                    Toast.makeText(Favourite_Products.this , "Added to Cart" , Toast.LENGTH_LONG).show();


                                                }
                                            }
                                        });

                                    }
                                });
                                ////////////////////////End of Add to cart///////////////////////////////////////////

                                ////////////////////////Remove Favourite////////////////////////////////////////////
                                holder.remove_fav.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        CharSequence options[] = new CharSequence[]{
                                                "cancel",
                                                "remove"
                                        };
                                        final AlertDialog.Builder builder = new AlertDialog.Builder(Favourite_Products.this);
                                        builder.setTitle("Remove From Favourites");
                                        builder.setItems(options, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int i) {
                                                if (i==0)
                                                {
                                                    builder.setCancelable(true);
                                                }
                                                if (i==1)
                                                {
                                                    final DatabaseReference fav8_list = FirebaseDatabase.getInstance().getReference()
                                                            .child("Liked_Products").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                                    fav8_list.child(model.getProduct_id()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if (task.isSuccessful())
                                                            {
                                                                Toast.makeText(Favourite_Products.this , "Removed From Favourites" , Toast.LENGTH_LONG).show();

                                                                remove_like.child(model.getProduct_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                        if (dataSnapshot.child("Liked_by").exists())
                                                                        {
                                                                            remove_like.child(model.getProduct_id()).child("Liked_by").
                                                                                    child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                                                                        }
                                                                        else
                                                                        {
                                                                            remove_like.child(model.getProduct_id()).removeValue();
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                    }
                                                                });



                                                            }
                                                        }
                                                    });

                                                }
                                            }
                                        });
                                        builder.show();
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
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout , parent , false);
                        return new ProductViewHolder(view);
                    }
                };
        favouriteProductlist.setAdapter(adapter);
        adapter.startListening();
    }
    public class ProductViewHolder extends RecyclerView.ViewHolder
    {

        ImageView image;
        TextView price;
        TextView p_name;
        TextView p_id;
        Button add_to_cart;
        Button remove_fav;
        EditText quantity;

        public ProductViewHolder(View itemView) {
            super(itemView);

            p_id = itemView.findViewById(R.id.product_id_item);
            image = itemView.findViewById(R.id.product_image_item);
            price = itemView.findViewById(R.id.product_price_item);
            p_name = itemView.findViewById(R.id.product_name_item);
            add_to_cart = itemView.findViewById(R.id.btn_add_product_to_cart_item);
            remove_fav = itemView.findViewById(R.id.btn_remove_favourie);

            quantity = itemView.findViewById(R.id.quantity_item);
        }
    }
}

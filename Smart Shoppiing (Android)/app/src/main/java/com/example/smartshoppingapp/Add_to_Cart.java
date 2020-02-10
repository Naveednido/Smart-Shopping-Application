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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartshoppingapp.Model.cart_model_class;
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

import java.util.Objects;

public class Add_to_Cart extends AppCompatActivity {

    private RecyclerView addCart_list;
    Button btn_proceed_to_checkout;
    TextView total_amount;
    cart_model_class model;
    Toolbar toolbar;
    private String id , name , quantity , price , size , image_url , brand_name;

    private DatabaseReference cart_DatabaseReference , remove_item_daDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to__cart);


        cart_DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        remove_item_daDatabaseReference =FirebaseDatabase.getInstance().getReference().child("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        toolbar = findViewById(R.id.add_cart_appbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("My Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addCart_list = findViewById(R.id.addcart_rcv);

        btn_proceed_to_checkout = findViewById(R.id.procced_to_checkout);

        addCart_list.setLayoutManager(new LinearLayoutManager(this));


        btn_proceed_to_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Add_to_Cart.this , CheckOut.class));


            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<cart_model_class>().
                setQuery(cart_DatabaseReference , cart_model_class.class).build();

        FirebaseRecyclerAdapter<cart_model_class , cartViewHolder> adapter = new FirebaseRecyclerAdapter<cart_model_class, cartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final cartViewHolder holder, int position, final cart_model_class model) {


                final String cartList = getRef(position).getKey();

                cart_DatabaseReference.child(cartList).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists())
                        {
                            id = dataSnapshot.child("id").getValue().toString();
                            name = dataSnapshot.child("name").getValue().toString();
                            price = dataSnapshot.child("price").getValue().toString();
                            image_url = dataSnapshot.child("image_url").getValue().toString();
                            brand_name = dataSnapshot.child("brand_name").getValue().toString();
                            quantity = dataSnapshot.child("quantity").getValue().toString();


                            Picasso.with(Add_to_Cart.this).load(image_url).into(holder.imageView);
                            holder.price.setText(price);
                            holder.name.setText(name);
                            holder.idd.setText(id);
                            holder.quantity.setText(quantity);
                        }
                        holder.remove_from_cart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]{
                                        "cancel",
                                        "remove"
                                };
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Add_to_Cart.this);
                                builder.setTitle("Remove From Cart");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if (i==0)
                                        {
                                            builder.setCancelable(true);
                                        }
                                        if (i==1)
                                        {
                                            remove_item_daDatabaseReference.child(model.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(Add_to_Cart.this , "Removed From Cart" , Toast.LENGTH_LONG).show();
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
            public cartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout , parent , false);
                return new cartViewHolder(view);

            }
        };


        addCart_list.setAdapter(adapter);
        adapter.startListening();

    }

    public class cartViewHolder extends RecyclerView.ViewHolder
    {

        TextView idd;
        TextView name;
        TextView price;
        TextView quantity;
        EditText size;
        ImageView imageView;
        ImageButton remove_from_cart;


        public cartViewHolder(View itemView) {
            super(itemView);

            idd = itemView.findViewById(R.id.product_id_addcart_rcv);
            name = itemView.findViewById(R.id.product_name_addcart_rcv);
            price = itemView.findViewById(R.id.product_price_addcart_rcv);
            quantity = itemView.findViewById(R.id.quantity_addcart);
            imageView = itemView.findViewById(R.id.image_addcart_rcv);
            remove_from_cart = itemView.findViewById(R.id.remov_from_cart);

        }
    }

}


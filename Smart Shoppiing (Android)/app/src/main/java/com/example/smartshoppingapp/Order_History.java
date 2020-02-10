package com.example.smartshoppingapp;

import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartshoppingapp.Model.Order_history_model_class;
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

public class Order_History extends AppCompatActivity {

    private RecyclerView orderHistoryList;
    private DatabaseReference user_order_DatabaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__history);


        Toolbar toolbar = findViewById(R.id.order_history_appbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Orders History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user_order_DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users_Order").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        orderHistoryList = findViewById(R.id.order_history_rcv);
        orderHistoryList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options  = new FirebaseRecyclerOptions.Builder<Order_history_model_class>().
                setQuery(user_order_DatabaseReference , Order_history_model_class.class).build();

        FirebaseRecyclerAdapter<Order_history_model_class , OrderViewHolder> adapter = new
                FirebaseRecyclerAdapter<Order_history_model_class, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final OrderViewHolder holder, int position, final Order_history_model_class model) {

                String ordered_products = getRef(position).getKey();

                user_order_DatabaseReference.child(ordered_products).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            model.setDate(dataSnapshot.child("Date_of_order").getValue().toString());
                            model.setIdd(dataSnapshot.child("product_id").getValue().toString());
                            model.setImage_url(dataSnapshot.child("image_url").getValue().toString());
                            model.setQuantity(dataSnapshot.child("quantity").getValue().toString());
                            model.setNamee(dataSnapshot.child("product_name").getValue().toString());
                            model.setPrice(dataSnapshot.child("price").getValue().toString());


                            holder.price.setText(model.getPrice());
                            holder.idd.setText(model.getIdd());
                            holder.date.setText(model.getDate());
                            holder.quantity.setText(model.getQuantity());
                            holder.name.setText(model.getNamee());
                            Picasso.with(Order_History.this).load(model.getImage_url()).into(holder.image);
                        }
                        ////////////////////////Remving item from order history////////////////////////////
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                CharSequence options[] = new CharSequence[]{
                                        "cancel",
                                        "remove"
                                };
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Order_History.this);
                                builder.setTitle("Remove From Order History");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if (i==0)
                                        {
                                            builder.setCancelable(true);
                                        }
                                        if (i==1)
                                        {
                                            user_order_DatabaseReference.child(model.getIdd()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(Order_History.this , "Removed From Order History" , Toast.LENGTH_LONG).show();
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
            public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_rcv_layout , parent , false);
                return new OrderViewHolder(view);
            }
        };
        orderHistoryList.setAdapter(adapter);
        adapter.startListening();
    }

    //////////////////////View Holder Class///////////////////////////
    public class OrderViewHolder extends RecyclerView.ViewHolder
    {
        TextView idd , name , date , quantity , price;
        ImageView image;
        public OrderViewHolder(View itemView) {
            super(itemView);
            idd = itemView.findViewById(R.id.product_id_history);
            name = itemView.findViewById(R.id.product_name_history);
            date = itemView.findViewById(R.id.date_rcv_order_history);
            quantity = itemView.findViewById(R.id.quantity_history);
            price = itemView.findViewById(R.id.product_price_history);
            image = itemView.findViewById(R.id.product_image_history);
        }
    }
}

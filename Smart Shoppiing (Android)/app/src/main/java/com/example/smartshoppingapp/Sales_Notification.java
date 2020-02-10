package com.example.smartshoppingapp;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartshoppingapp.Model.Sales_Model_Class;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class Sales_Notification extends AppCompatActivity {


    RecyclerView saleList;
    DatabaseReference sales_notification_database , databaseReference , check_DatabaseReference , brandtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales__notification);


        Toolbar toolbar = findViewById(R.id.sales_appbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Sales Notification");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sales_notification_database = FirebaseDatabase.getInstance().getReference().child("Sale");
        check_DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Products");
        brandtype = FirebaseDatabase.getInstance().getReference().child("Admin");




         databaseReference = FirebaseDatabase.getInstance().getReference().child("Admin");

        saleList = findViewById(R.id.sales_rcv);
        saleList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Sales_Model_Class>()
                .setQuery(sales_notification_database , Sales_Model_Class.class)
                .build();
        FirebaseRecyclerAdapter<Sales_Model_Class , SaleViewHolder> adapter = new
                FirebaseRecyclerAdapter<Sales_Model_Class, SaleViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(final SaleViewHolder holder, int position, final Sales_Model_Class model) {

                        final String brandSale = getRef(position).getKey();
                        model.setBrandname(brandSale);

                        sales_notification_database.child(brandSale).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.exists())
                                {
                                    model.setEnddate(dataSnapshot.child("end_date").getValue().toString());
                                    model.setStartdate(dataSnapshot.child("start_date").getValue().toString());
                                    model.setImage(dataSnapshot.child("sale_image").getValue().toString());
                                    model.setPercent_off(dataSnapshot.child("price_off").getValue().toString());
                                    model.setSaletype(dataSnapshot.child("sale_type").getValue().toString());

                                    holder.saletype.setText(model.getSaletype());
                                    holder.startdate.setText(model.getStartdate());
                                    holder.enddate.setText(model.getEnddate());
                                    Picasso.with(Sales_Notification.this).load(model.getImage()).into(holder.image_sale);
                                    holder.percent_off.setText(model.getPercent_off());
                                    holder.brandname.setText(brandSale);

                                }


                                databaseReference.child(model.getBrandname()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.exists())
                                             model.setBrandcategory(dataSnapshot.child("brand_category").getValue().toString());
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                holder.itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent main = new Intent(Sales_Notification.this , All_Products_Display.class);
                                        main.putExtra("product_category" , "Sale");
                                        main.putExtra("brand" , brandSale );
                                        main.putExtra("category", model.getBrandcategory());
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
                    public SaleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_rcv_layout , parent , false);
                        return new SaleViewHolder(view);
                    }
                };
        saleList.setAdapter(adapter);
        adapter.startListening();

    }

    public class SaleViewHolder extends RecyclerView.ViewHolder
    {
        TextView saletype , startdate , enddate , percent_off ,brandname;
        ImageView image_sale;


        public SaleViewHolder(View itemView) {
            super(itemView);

            saletype = itemView.findViewById(R.id.Sale_type_txt);
            startdate = itemView.findViewById(R.id.startdate1);
            enddate = itemView.findViewById(R.id.enddate1);
            percent_off = itemView.findViewById(R.id.sale_percent_off);
            image_sale = itemView.findViewById(R.id.sales_image);
            brandname = itemView.findViewById(R.id.Sale_brand_txt);

        }
    }

}


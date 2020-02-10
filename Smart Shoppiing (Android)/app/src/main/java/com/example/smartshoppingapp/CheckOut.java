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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartshoppingapp.Model.checkout_cart_model_class;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class CheckOut extends AppCompatActivity {

    EditText f_name , l_name , phone_num , address, country , postal_code , eemail;
    Button btn_confirm_order;
    RecyclerView cartlist;

    boolean check = false ;
    String savecurrentdate , savecurrenttime;

    private ArrayList<checkout_cart_model_class> model_class = new ArrayList<>();

    private DatabaseReference contact_info_DatabaseReference,User_DatabaseReference , cartinfo_DatabaseReference , remove_item_daDatabaseReference , Admin_order_DatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        /////////////Getting Views/////////////////////
        btn_confirm_order = findViewById(R.id.btn_order_checkout);
        f_name = findViewById(R.id.f_name_checkout);
        l_name = findViewById(R.id.l_name_checkout);
        phone_num = findViewById(R.id.phone_checkout);
        address = findViewById(R.id.address_checkout);
        country = findViewById(R.id.country_checkout);
        postal_code = findViewById(R.id.postal_code_checkout);
        eemail = findViewById(R.id.email_Checkout);

        Toolbar toolbar = findViewById(R.id.checkout_appbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Check-Out");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        ////////////Cartinfor database////////////
        cartinfo_DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ////////////Contact database//////////////////
        contact_info_DatabaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        //////////cart deleting database//////////////
        remove_item_daDatabaseReference =FirebaseDatabase.getInstance().getReference().child("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ///////////Admin order database///////////////
        Admin_order_DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Admin_Orders");

        ///////////User Orders Database///////////////
        User_DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users_Order").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        contact_info_DatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String f_nam = Objects.requireNonNull(dataSnapshot.child("first_name").getValue()).toString();
                String l_nam = Objects.requireNonNull(dataSnapshot.child("last_name").getValue()).toString();
                String phn = Objects.requireNonNull(dataSnapshot.child("phone").getValue()).toString();
                String add = Objects.requireNonNull(dataSnapshot.child("address").getValue()).toString();
                String postcode = Objects.requireNonNull(dataSnapshot.child("postal_code").getValue()).toString();
                String email = dataSnapshot.child("email").getValue().toString();

                f_name.setText(f_nam);
                l_name.setText(l_nam);
                phone_num.setText(phn);
                address.setText(add);
                postal_code.setText(postcode);
                eemail.setText(email);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        btn_confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());


                for (int i = 0 ; i<model_class.size() ; i++)
                {

                    HashMap<String, String> productmap = new HashMap<>();
                    productmap.put("image_url" , model_class.get(i).getImage_url());
                    productmap.put("product_id",model_class.get(i).getId());
                    productmap.put("product_name" , model_class.get(i).getName());
                    productmap.put("quantity", model_class.get(i).getQuantity());
                    productmap.put("name" , f_name.getText().toString());
                    productmap.put("address" , address.getText().toString());
                    productmap.put("phone" , phone_num.getText().toString());
                    productmap.put("postal_code",postal_code.getText().toString());
                    productmap.put("country" , country.getText().toString());
                    productmap.put("Date_of_order" , mydate);
                    productmap.put("price",model_class.get(i).getPrice());
                    productmap.put("email",eemail.getText().toString());


                    Admin_order_DatabaseReference.child(model_class.get(i).getBrand_name()).child(model_class.get(i).getId())
                    .setValue(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                check = true;
                            }
                        }
                    });

                    User_DatabaseReference.child(model_class.get(i).getId()).setValue(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });

                }
                if (!check)
                {
                    DatabaseReference remove_carts = FirebaseDatabase.getInstance().getReference().child("Cart");
                    remove_carts.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue().addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toast.makeText(CheckOut.this , "Your Order Have Been placed Successfull" , Toast.LENGTH_LONG).show();

                                }
                            }
                    );
                }
                else
                {
                    Toast.makeText(CheckOut.this , "Not deleted" , Toast.LENGTH_LONG).show();

                }
                Intent main = new Intent(CheckOut.this , MainActivity.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(main);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //////////////Recycler View/////////////////////
        cartlist = findViewById(R.id.checkout_rcv);
        cartlist.setLayoutManager(new LinearLayoutManager(this , LinearLayout.HORIZONTAL , true));

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<checkout_cart_model_class>()
                .setQuery(cartinfo_DatabaseReference , checkout_cart_model_class.class).build();

        FirebaseRecyclerAdapter<checkout_cart_model_class , checkoutViewholder> adapter = new FirebaseRecyclerAdapter<checkout_cart_model_class, checkoutViewholder>(options) {
            @Override
            protected void onBindViewHolder(final checkoutViewholder holder, int position, final checkout_cart_model_class model) {

                String product_list = getRef(position).getKey();

                cartinfo_DatabaseReference.child(product_list).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            model.setId(dataSnapshot.child("id").getValue().toString());
                            model.setName(dataSnapshot.child("name").getValue().toString());
                            model.setPrice(dataSnapshot.child("price").getValue().toString());
                            model.setBrand_name(dataSnapshot.child("brand_name").getValue().toString());
                            model.setImage_url(dataSnapshot.child("image_url").getValue().toString());
                            model.setQuantity(dataSnapshot.child("quantity").getValue().toString());




                            model_class.add(model);

                            holder.idd.setText(model.getId());
                            holder.pricee.setText(model.getPrice());
                            holder.namee.setText(model.getName());
                        }

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]{
                                        "cancel",
                                        "remove"
                                };
                                final AlertDialog.Builder builder = new AlertDialog.Builder(CheckOut.this);
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
                                                    Toast.makeText(CheckOut.this , "Removed From Cart" , Toast.LENGTH_LONG).show();
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
            public checkoutViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_rcv_layout , parent , false);
                return new checkoutViewholder(view);
            }
        };

        cartlist.setAdapter(adapter);
        adapter.startListening();

    }

    public class checkoutViewholder extends RecyclerView.ViewHolder
    {
        TextView idd , namee , pricee;

        public checkoutViewholder(View itemView) {
            super(itemView);

            idd = itemView.findViewById(R.id.id_rcv_checkout);
            namee = itemView.findViewById(R.id.name_rcv_checkout);
            pricee = itemView.findViewById(R.id.price_rcv_checkout);
        }
    }
}

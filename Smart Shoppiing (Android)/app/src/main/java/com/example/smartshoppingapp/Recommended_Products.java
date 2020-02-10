package com.example.smartshoppingapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartshoppingapp.Model.Reccomended_products_Model_class;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Recommended_Products extends Fragment {

    String image_url , price , name , description , id ,brand_name;

    private RecyclerView recommendedProduct_list;
    private View recommended_View;
    private ProgressDialog mProgressDialog;

    private DatabaseReference liked_counts_Database , reccomended_Database , add_to_cart_daDatabaseReference;

    public Recommended_Products() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        recommended_View =inflater.inflate(R.layout.fragment_recommended__products, container, false);


        reccomended_Database = FirebaseDatabase.getInstance().getReference().child("Recommeded_Products");
        liked_counts_Database = FirebaseDatabase.getInstance().getReference().child("Recommeded_Products");
        add_to_cart_daDatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Cart").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

        recommendedProduct_list = recommended_View.findViewById(R.id.reecoended_recyclerview);
        recommendedProduct_list.setLayoutManager(new LinearLayoutManager(getContext()));

        //////////Progress Dialog Boc//////////
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Updating");
        mProgressDialog.setMessage("Please Wait While We Gather Things !");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        ////////////////////////////////////////

        return recommended_View;
    }

    @Override
    public void onStart() {
        super.onStart();



        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Reccomended_products_Model_class>()
                .setQuery(reccomended_Database , Reccomended_products_Model_class.class)
                .build();

        FirebaseRecyclerAdapter<Reccomended_products_Model_class, reccomendedViewHolder> adapter =
                new FirebaseRecyclerAdapter<Reccomended_products_Model_class, reccomendedViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(final reccomendedViewHolder holder, int position, final Reccomended_products_Model_class model) {

                        String Reccomended_Product_list = getRef(position).getKey();

                        reccomended_Database.child(Reccomended_Product_list).addValueEventListener(new ValueEventListener() {
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
                                    model.setPrice(dataSnapshot.child("price").getValue().toString());
                                    model.setProduct_id(dataSnapshot.child("id").getValue().toString());
                                    model.setProduct_Discription(dataSnapshot.child("description").getValue().toString());
                                    model.setBrand_name(dataSnapshot.child("brand_name").getValue().toString());

                                    holder.p_name.setText(name);
                                    holder.price.setText(price);
                                    Picasso.with(getContext()).load(image_url).into(holder.image);
                                    holder.p_id.setText(id);

                                    liked_counts_Database.child(model.getProduct_id()).child("Liked_by").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            if(dataSnapshot.exists())
                                            {
                                                int likes = (int) dataSnapshot.getChildrenCount();
                                                holder.likes.setText("Liked by "+ likes +" People");
                                            }
                                            else
                                            {
                                                liked_counts_Database.child(model.getProduct_id()).removeValue();
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });


                                }
                                ////////////////////item holder///////////////////////////////////

                                holder.itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent main = new Intent(getActivity()
                                                , Product_Complete_Detail.class);
                                        main.putExtra("Price",price);
                                        main.putExtra("image_url",image_url);
                                        main.putExtra("Name",name);
                                        main.putExtra("Id",id);
                                        main.putExtra("Discription",description);
                                        main.putExtra("brand_name",brand_name);
                                        main.putExtra("quantitiy" , holder.quantity.getText().toString());
                                        startActivity(main);
                                    }
                                });

                                ////////////////////////Adding To Cart//////////////////////////////
                                holder.add_to_cart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        HashMap<String, String> productmap = new HashMap<>();
                                        productmap.put("name", model.getProduct_name());
                                        productmap.put("id", model.getProduct_id());
                                        productmap.put("description" , model.getProduct_Discription());
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
                                                    Toast.makeText(getContext() , "Added to Cart" , Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        });

                                    }
                                });
                                ///////////////////////End Of Add to Cart///////////////////////////

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }

                    @Override
                    public reccomendedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(getContext()).inflate(R.layout.reccomended_products_layout , parent , false);
                        return new reccomendedViewHolder(view);
                    }
                };

        recommendedProduct_list.setAdapter(adapter);
        adapter.startListening();
        mProgressDialog.dismiss();
    }

    public class reccomendedViewHolder  extends RecyclerView.ViewHolder
    {

        ImageView image;
        TextView price;
        TextView p_name;
        TextView p_id;
        TextView likes;
        Button add_to_cart;
        EditText quantity;

        public reccomendedViewHolder(View itemView) {
            super(itemView);

            p_id = itemView.findViewById(R.id.product_id_recommeded);
            image = itemView.findViewById(R.id.product_image_recommeded);
            price = itemView.findViewById(R.id.product_price_recommeded);
            p_name = itemView.findViewById(R.id.product_name_recommeded);
            add_to_cart = itemView.findViewById(R.id.btn_add_product_to_cart_recommeded);
            likes = itemView.findViewById(R.id.liked_recomended_products_recommeded);
            quantity = itemView.findViewById(R.id.quantity_reccoemended);
        }
    }
}

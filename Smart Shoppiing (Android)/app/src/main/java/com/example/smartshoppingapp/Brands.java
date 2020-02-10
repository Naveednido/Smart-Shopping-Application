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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartshoppingapp.Model.Brands_model_class;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class Brands extends Fragment {


    RecyclerView mybrandList;
    private ProgressDialog mProgressDialog;
    private DatabaseReference mBrandsReference  , brandref;

    private Brands_model_class model;

    private View brandsView;

    View brandsFragentview;

    public Brands() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        brandsView = inflater.inflate(R.layout.fragment_brands, container, false);

        mBrandsReference = FirebaseDatabase.getInstance().getReference().child("Admin");
        brandref = FirebaseDatabase.getInstance().getReference().child("Admin");

       ///////////Recycler View///////////////
        mybrandList = brandsView.findViewById(R.id.brands_recyclerview);
        mybrandList.setLayoutManager(new LinearLayoutManager(getContext()));
        ////////////////////////////////////////

        //////////Progress Dialog Boc//////////
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Updating");
        mProgressDialog.setMessage("Please Wait While We Gather Things !");
        mProgressDialog.setCanceledOnTouchOutside(false);
        ////////////////////////////////////////

        return brandsView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Brands_model_class>()
                .setQuery(mBrandsReference , Brands_model_class.class)
                .build();

        FirebaseRecyclerAdapter<Brands_model_class , brandsViewHolder> adapter = new FirebaseRecyclerAdapter<Brands_model_class, brandsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final brandsViewHolder holder, int position, @NonNull final Brands_model_class model) {

                String brandslist = getRef(position).getKey();

                brandref.child(brandslist).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("brand_image"))
                        {
                            String address = dataSnapshot.child("address").getValue().toString();
                            String phone = dataSnapshot.child("mobile").getValue().toString();
                            String img_url = dataSnapshot.child("brand_image").getValue().toString();
                            String emaill = dataSnapshot.child("email").getValue().toString();


                            holder.phone.setText(phone);
                            holder.address.setText(address);
                            mProgressDialog.dismiss();
                            holder.emaill.setText(emaill);
                            Picasso.with(getContext()).load(img_url).into(holder.img);

                        }
                        else
                        {

                            String address = Objects.requireNonNull(dataSnapshot.child("address").getValue()).toString();
                            String phone = Objects.requireNonNull(dataSnapshot.child("mobile").getValue()).toString();
                            holder.phone.setText(phone);
                            mProgressDialog.dismiss();
                            holder.address.setText(address);

                        }

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent main = new Intent(getActivity() , Categories.class);
                                main.putExtra("brand_name",model.getBrand());
                                main.putExtra("categ" , Objects.requireNonNull(dataSnapshot.child("brand_category").getValue()).toString());
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
            public brandsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.brands_recyclerview_layout , parent , false);
                brandsViewHolder viewHolder = new brandsViewHolder(view);

                return viewHolder;
            }
        };

        mybrandList.setAdapter(adapter);
        adapter.startListening();
    }

    public class brandsViewHolder extends RecyclerView.ViewHolder
    {

        TextView address , phone , emaill ;
        ImageView img;

        public brandsViewHolder(View itemView) {
            super(itemView);

            address =itemView.findViewById(R.id.address_brand_rcv);
            phone = itemView.findViewById(R.id.phone_brand_rcv);
            img = itemView.findViewById(R.id.image_brand_rcv);
            emaill = itemView.findViewById(R.id.email_brands);
        }
    }
}

package com.example.smartshoppingapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartshoppingapp.Model.PrizeRunner_Model_class;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class Prize_Runner extends Fragment {


    private Button btn_laptops , btn_tv , btn_mobiles ;
    private View prize_runner_view;
    private RecyclerView rcv1 , rcv2;
    String keyyy = "Price Runner";

    private DatabaseReference database_1 , database_2;

    public Prize_Runner() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        prize_runner_view = inflater.inflate(R.layout.fragment_prize__runner, container, false);

        btn_laptops = prize_runner_view.findViewById(R.id.btn_laptops);
        btn_mobiles = prize_runner_view.findViewById(R.id.btn_mobile);
        btn_tv = prize_runner_view.findViewById(R.id.btn_led_tv);


        rcv1 = prize_runner_view.findViewById(R.id.rcv_1);
        rcv2 = prize_runner_view.findViewById(R.id.rcv_2);


        rcv1.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayout.HORIZONTAL , false));
        rcv2.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayout.HORIZONTAL , false));


        return prize_runner_view;
    }


    @Override
    public void onStart() {
        super.onStart();

        btn_mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database_1 = FirebaseDatabase.getInstance().getReference().child(keyyy).child("Mobiles").child("Huawei_Mobile").child("Huawei");
                database_2 = FirebaseDatabase.getInstance().getReference().child(keyyy).child("Mobiles").child("Samsung_Mobile").child("Mobiles");

                FirebaseRecyclerOptions options1 = new FirebaseRecyclerOptions.Builder<PrizeRunner_Model_class>().
                        setQuery(database_1 , PrizeRunner_Model_class.class).build();
                FirebaseRecyclerAdapter< PrizeRunner_Model_class , PrizeRunnerViewHolder > adapter1 = new
                        FirebaseRecyclerAdapter<PrizeRunner_Model_class, PrizeRunnerViewHolder>(options1) {
                            @Override
                            protected void onBindViewHolder(final PrizeRunnerViewHolder holder, int position, final PrizeRunner_Model_class model) {
                                String list = getRef(position).getKey();
                                database_1.child(list).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists())
                                        {
                                            model.setImage_url(dataSnapshot.child("image_url").getValue().toString());
                                            model.setName(dataSnapshot.child("name").getValue().toString());
                                            model.setPrice(dataSnapshot.child("price").getValue().toString());
                                            holder.pricee.setText(model.getPrice());
                                            holder.name.setText(model.getName());
                                            Picasso.with(getContext()).load(model.getImage_url()).into(holder.image);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                            @Override
                            public PrizeRunnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(getContext()).inflate(R.layout.prize_runner_rcv_layout , parent , false);
                                return new PrizeRunnerViewHolder(view);
                            }
                        };

                rcv1.setAdapter(adapter1);
                adapter1.startListening();

                FirebaseRecyclerOptions options2 = new FirebaseRecyclerOptions.Builder<PrizeRunner_Model_class>().
                        setQuery(database_2 , PrizeRunner_Model_class.class).build();

                FirebaseRecyclerAdapter< PrizeRunner_Model_class , PrizeRunnerViewHolder > adapter2 = new
                        FirebaseRecyclerAdapter<PrizeRunner_Model_class, PrizeRunnerViewHolder>(options2) {
                            @Override
                            protected void onBindViewHolder(final PrizeRunnerViewHolder holder, int position, final PrizeRunner_Model_class model) {
                                String list = getRef(position).getKey();
                                database_2.child(list).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists())
                                        {
                                            model.setImage_url(dataSnapshot.child("image_url").getValue().toString());
                                            model.setName(dataSnapshot.child("name").getValue().toString());
                                            model.setPrice(dataSnapshot.child("price").getValue().toString());
                                            holder.pricee.setText(model.getPrice());
                                            holder.name.setText(model.getName());
                                            Picasso.with(getContext()).load(model.getImage_url()).into(holder.image);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                            @Override
                            public PrizeRunnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(getContext()).inflate(R.layout.prize_runner_rcv_layout , parent , false);
                                return new PrizeRunnerViewHolder(view);
                            }
                        };

                rcv2.setAdapter(adapter2);
                adapter2.startListening();





            }
        });




        btn_laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                database_1 = FirebaseDatabase.getInstance().getReference().child(keyyy).child("Laptops").child("Dell_Laptops").child("Dell_Laptops");
                database_2 = FirebaseDatabase.getInstance().getReference().child(keyyy).child("Laptops").child("Lenovo_Laptops").child("Lenovo_Laptops");

                FirebaseRecyclerOptions options1 = new FirebaseRecyclerOptions.Builder<PrizeRunner_Model_class>().
                        setQuery(database_1 , PrizeRunner_Model_class.class).build();
                FirebaseRecyclerAdapter< PrizeRunner_Model_class , PrizeRunnerViewHolder > adapter1 = new
                        FirebaseRecyclerAdapter<PrizeRunner_Model_class, PrizeRunnerViewHolder>(options1) {
                            @Override
                            protected void onBindViewHolder(final PrizeRunnerViewHolder holder, int position, final PrizeRunner_Model_class model) {
                                String list = getRef(position).getKey();
                                database_1.child(list).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists())
                                        {
                                            model.setImage_url(dataSnapshot.child("image_url").getValue().toString());
                                            model.setName(dataSnapshot.child("name").getValue().toString());
                                            model.setPrice(dataSnapshot.child("price").getValue().toString());
                                            holder.pricee.setText(model.getPrice());
                                            holder.name.setText(model.getName());
                                            Picasso.with(getContext()).load(model.getImage_url()).into(holder.image);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                            @Override
                            public PrizeRunnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(getContext()).inflate(R.layout.prize_runner_rcv_layout , parent , false);
                                return new PrizeRunnerViewHolder(view);
                            }
                        };

                rcv1.setAdapter(adapter1);
                adapter1.startListening();

                FirebaseRecyclerOptions options2 = new FirebaseRecyclerOptions.Builder<PrizeRunner_Model_class>().
                        setQuery(database_2 , PrizeRunner_Model_class.class).build();

                FirebaseRecyclerAdapter< PrizeRunner_Model_class , PrizeRunnerViewHolder > adapter2 = new
                        FirebaseRecyclerAdapter<PrizeRunner_Model_class, PrizeRunnerViewHolder>(options2) {
                            @Override
                            protected void onBindViewHolder(final PrizeRunnerViewHolder holder, int position, final PrizeRunner_Model_class model) {
                                String list = getRef(position).getKey();
                                database_2.child(list).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists())
                                        {
                                            model.setImage_url(dataSnapshot.child("image_url").getValue().toString());
                                            model.setName(dataSnapshot.child("name").getValue().toString());
                                            model.setPrice(dataSnapshot.child("price").getValue().toString());
                                            holder.pricee.setText(model.getPrice());
                                            holder.name.setText(model.getName());
                                            Picasso.with(getContext()).load(model.getImage_url()).into(holder.image);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                            @Override
                            public PrizeRunnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(getContext()).inflate(R.layout.prize_runner_rcv_layout , parent , false);
                                return new PrizeRunnerViewHolder(view);
                            }
                        };

                rcv2.setAdapter(adapter2);
                adapter2.startListening();



            }
        });



        btn_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database_1 = FirebaseDatabase.getInstance().getReference().child(keyyy).child("TV").child("TV_Samsung").child("Samsung");
                database_2 = FirebaseDatabase.getInstance().getReference().child(keyyy).child("TV").child("TV_Sony").child("Sony");

                FirebaseRecyclerOptions options1 = new FirebaseRecyclerOptions.Builder<PrizeRunner_Model_class>().
                        setQuery(database_1 , PrizeRunner_Model_class.class).build();
                FirebaseRecyclerAdapter< PrizeRunner_Model_class , PrizeRunnerViewHolder > adapter1 = new
                        FirebaseRecyclerAdapter<PrizeRunner_Model_class, PrizeRunnerViewHolder>(options1) {
                            @Override
                            protected void onBindViewHolder(final PrizeRunnerViewHolder holder, int position, final PrizeRunner_Model_class model) {
                                String list = getRef(position).getKey();
                                database_1.child(list).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists())
                                        {
                                            model.setImage_url(dataSnapshot.child("image_url").getValue().toString());
                                            model.setName(dataSnapshot.child("name").getValue().toString());
                                            model.setPrice(dataSnapshot.child("price").getValue().toString());
                                            holder.pricee.setText(model.getPrice());
                                            holder.name.setText(model.getName());
                                            Picasso.with(getContext()).load(model.getImage_url()).into(holder.image);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                            @Override
                            public PrizeRunnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(getContext()).inflate(R.layout.prize_runner_rcv_layout , parent , false);
                                return new PrizeRunnerViewHolder(view);
                            }
                        };

                rcv1.setAdapter(adapter1);
                adapter1.startListening();

                FirebaseRecyclerOptions options2 = new FirebaseRecyclerOptions.Builder<PrizeRunner_Model_class>().
                        setQuery(database_2 , PrizeRunner_Model_class.class).build();

                FirebaseRecyclerAdapter< PrizeRunner_Model_class , PrizeRunnerViewHolder > adapter2 = new
                        FirebaseRecyclerAdapter<PrizeRunner_Model_class, PrizeRunnerViewHolder>(options2) {
                            @Override
                            protected void onBindViewHolder(final PrizeRunnerViewHolder holder, int position, final PrizeRunner_Model_class model) {
                                String list = getRef(position).getKey();
                                database_2.child(list).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists())
                                        {
                                            model.setImage_url(dataSnapshot.child("image_url").getValue().toString());
                                            model.setName(dataSnapshot.child("name").getValue().toString());
                                            model.setPrice(dataSnapshot.child("price").getValue().toString());
                                            holder.pricee.setText(model.getPrice());
                                            holder.name.setText(model.getName());
                                            Picasso.with(getContext()).load(model.getImage_url()).into(holder.image);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                            @Override
                            public PrizeRunnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(getContext()).inflate(R.layout.prize_runner_rcv_layout , parent , false);
                                return new PrizeRunnerViewHolder(view);
                            }
                        };

                rcv2.setAdapter(adapter2);
                adapter2.startListening();


            }
        });



    }

    public class PrizeRunnerViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView name , pricee;

        public PrizeRunnerViewHolder(View itemView) {
            super(itemView);


            image = itemView.findViewById(R.id.product_image_runner);
            pricee = itemView.findViewById(R.id.product_price_runner);
            name = itemView.findViewById(R.id.product_name_runner);


        }
    }
}

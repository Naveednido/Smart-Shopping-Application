package com.example.smartshoppingapp;

import android.app.ProgressDialog;
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

import com.example.smartshoppingapp.Model.Categories_model_class;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Categories extends AppCompatActivity {

    private String brand , Categ;
    private Toolbar categories_toolbar;
    private RecyclerView brandCategorieslist;
    private ProgressDialog mProgressDialog;
    Categories_model_class model_class;

    private DatabaseReference mCategoiesRef;
    private DatabaseReference categref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        brand = getIntent().getStringExtra("brand_name");
        Categ = getIntent().getStringExtra("categ");


        categories_toolbar = findViewById(R.id.catogries_appbar);
        setSupportActionBar(categories_toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(brand);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCategoiesRef = FirebaseDatabase.getInstance().getReference().child("Products").child(Categ).child(brand);
        categref = FirebaseDatabase.getInstance().getReference().child("Products").child(Categ).child(brand);


        brandCategorieslist = findViewById(R.id.catogries_rcv);
        brandCategorieslist.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();

        mProgressDialog = new ProgressDialog(getApplicationContext());
        mProgressDialog.setTitle("Updating");
        mProgressDialog.setMessage("Please Wait While We Gather Things !");
        mProgressDialog.setCanceledOnTouchOutside(false);
//        mProgressDialog.show();


        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Categories_model_class>()
                .setQuery(mCategoiesRef , Categories_model_class.class)
                .build();

        FirebaseRecyclerAdapter<Categories_model_class , categoriesViewHolder> adapter = new
                FirebaseRecyclerAdapter<Categories_model_class, categoriesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final categoriesViewHolder holder, int position, Categories_model_class model) {

                final String categ_list = getRef(position).getKey();
                categref.child(categ_list).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            String categories = dataSnapshot.getKey();
                            holder.txt_cat.setText(categories);

                            if (categories.equals("Mobile")) {
                                holder.img_cat.setImageResource(R.drawable.mobiles);
                            }
                            if (categories.equals("Laptop")) {
                                holder.img_cat.setImageResource(R.drawable.laptops);
                            }
                            if (categories.equals("LED_TV")) {
                                holder.img_cat.setImageResource(R.drawable.television);
                            }
                            if (categories.equals("Camera")) {
                                holder.img_cat.setImageResource(R.drawable.camera);
                            }
                            if (categories.equals("Sale"))
                            {
                                holder.img_cat.setImageResource(R.drawable.saletag);
                            }
                            if (categories.equals("Shirt"))
                            {
                                holder.img_cat.setImageResource(R.drawable.shirt);
                            }
                            if (categories.equals("Jeans"))
                            {
                                holder.img_cat.setImageResource(R.drawable.trousers);
                            }
                            if (categories.equals("Shoes"))
                            {
                                holder.img_cat.setImageResource(R.drawable.shoess);
                            }
                            if (categories.equals("Jackets"))
                            {
                                holder.img_cat.setImageResource(R.drawable.jackets);
                            }
                            if (categories.equals("Glasses"))
                            {
                                holder.img_cat.setImageResource(R.drawable.glasses);
                            }

                        }
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent main = new Intent(Categories.this , All_Products_Display.class);
                                main.putExtra("category" , Categ);
                                main.putExtra("brand",brand);
                                main.putExtra("product_category",categ_list);
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
            public categoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_brand_layout , parent , false);
                return new categoriesViewHolder(view);

            }
        };

        brandCategorieslist.setAdapter(adapter);
        adapter.startListening();

    }

    public class categoriesViewHolder extends RecyclerView.ViewHolder
    {
        TextView txt_cat;
        ImageView img_cat;

        public categoriesViewHolder(View itemView) {
            super(itemView);

            txt_cat = itemView.findViewById(R.id.categories_txt_rcv);
            img_cat= itemView.findViewById(R.id.categories_img_rcv);
        }
    }
}

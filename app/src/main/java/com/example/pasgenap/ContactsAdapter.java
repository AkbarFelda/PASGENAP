package com.example.pasgenap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    private Context context;
    private List<CoktailModel> listDataDrink;
    private ContactsAdapterListener listener;
    private AdapterView.OnItemLongClickListener longClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvname, tvkategori;
        public ImageView ivlogodrink;

        public MyViewHolder(View view) {
            super(view);
            tvname = view.findViewById(R.id.tvname);
            tvkategori = view.findViewById(R.id.tvkategori);
            ivlogodrink = view.findViewById(R.id.ivlogodrink);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(listDataDrink.get(getAdapterPosition()));
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onDeleteClickListener(listDataDrink.get(getAdapterPosition()));
                    return true;
                }
            });

        }
    }

    public ContactsAdapter(Context context, List<CoktailModel> contactList , ContactsAdapterListener listener) {
        this.context = context;
        this.listDataDrink = contactList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ContactsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.MyViewHolder holder, int position) {
        final CoktailModel drink = this.listDataDrink.get(position);
        holder.tvname.setText(drink.getDrinkName());
        holder.tvkategori.setText(drink.getCategory());
        Glide.with(holder.itemView.getContext()).load(drink.getStrDrinkThumb()).into(holder.ivlogodrink);

    }

    @Override
    public int getItemCount() {
        return listDataDrink.size();
    }

    public interface ContactsAdapterListener {
        void onItemLongClick(int position);

        void onContactSelected(CoktailModel contact);

        void onDeleteClickListener(CoktailModel coktailModel);
    }
    }


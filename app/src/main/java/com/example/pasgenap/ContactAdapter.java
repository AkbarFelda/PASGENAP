package com.example.pasgenap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private Context context;
    private List<CoktailModel> listDrink;
    private ContactAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvname, tving;
        public ImageView ivLogoDrink;

        public MyViewHolder(View view) {
            super(view);
            tvname = view.findViewById(R.id.tvname);
            tving = view.findViewById(R.id.tving);
            ivLogoDrink = view.findViewById(R.id.ivlogodrink);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(listDrink.get(getAdapterPosition()));
                }
            });
        }
    }

    public ContactAdapter(Context context, List<CoktailModel> drinkList, ContactAdapterListener listener) {
        this.context = context;
        this.listDrink = drinkList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.MyViewHolder holder, int position) {
        final CoktailModel drink = this.listDrink.get(position);
        holder.tvname.setText(drink.getDrinkName());
        holder.tving.setText(drink.getIngredient1());
        Glide.with(context).load(drink.getStrDrinkThumb()).into(holder.ivLogoDrink);
    }

    @Override
    public int getItemCount() {
        return this.listDrink.size();
    }

    public interface ContactAdapterListener {
        void onContactSelected(CoktailModel drink);
    }
}

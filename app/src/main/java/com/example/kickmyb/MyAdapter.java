package com.example.kickmyb;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public List<task> list;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvNom;
        public TextView tvPourcentage;
        public TextView tvDateLimite;
        public TextView tvTempsEcoule;
        //public TextView tvAge;
        public MyViewHolder(LinearLayout v) {
            super(v);
            tvNom = v.findViewById(R.id.tvNom);
            tvPourcentage = v.findViewById(R.id.tvPourcentage);
            tvDateLimite = v.findViewById(R.id.tvDateLimite);
            tvTempsEcoule = v.findViewById(R.id.tvTempsEcoule);
            //tvAge = v.findViewById(R.id.tvAge);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter() {
        list = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        task personneCourante = list.get(position);
        holder.tvNom.setText(personneCourante.nom);
        holder.tvPourcentage.setText(""+personneCourante.pourcentage);
        holder.tvDateLimite.setText(""+personneCourante.dateLimite);
        holder.tvTempsEcoule.setText(""+personneCourante.tempsEcoule);// TODO setText sur un integer crash

    }

    // renvoie la taille de la liste
    @Override
    public int getItemCount() {
        return list.size();
    }
}

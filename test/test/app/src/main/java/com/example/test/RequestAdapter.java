package com.example.mymedseller10;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> implements Filterable {
    private ArrayList<medicineitem> exampleList;
    private ArrayList<medicineitem> exampleListFull;
    private OnItemClickListener mListener;
Context context;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView1name;
        private TextView textView2details;
        private TextView textView3contact;
        private TextView textView4state;


        public RequestViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView=itemView.findViewById(R.id.picpic);
            textView1name = itemView.findViewById(R.id.cardtext1);
            textView2details = itemView.findViewById(R.id.cardtext2);
            textView3contact = itemView.findViewById(R.id.cardtext3);
             textView4state=itemView.findViewById(R.id.cardtext4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
public void insertAll(ArrayList<medicineitem> exampleList)
{
    exampleListFull = new ArrayList<>(exampleList);
    Log.e("ttt", "alller" +   exampleListFull.size()+"/"+exampleList.size());
}

    public RequestAdapter(ArrayList<medicineitem> exampleList) {
        if (exampleList != null) {
            this.exampleList = exampleList;

        }
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item,
                parent, false);
        RequestViewHolder exvh = new RequestViewHolder(v, mListener);
        return exvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {

        medicineitem currentItem = exampleList.get(position);
        Glide.with(context).load(currentItem.getImgs())
                .centerCrop()
                .into(holder.imageView);


        holder.textView1name.setText(currentItem.getName());
        holder.textView2details.setText(currentItem.getDetails());
        holder.textView3contact.setText( currentItem.getContact());
        holder.textView4state.setText(currentItem.getState());
        Log.d("new", "product:" + currentItem.getName() + "|price :" + currentItem.getDetails() + "| quantity :" + currentItem.getContact());
    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.e("ttt", "ddd1"+constraint);
            ArrayList<medicineitem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll((List) exampleListFull);

                Log.e("ttt", "ddd2" +  exampleListFull.size());
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (medicineitem item : exampleListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern) || item.getContact().toLowerCase().contains(filterPattern) ) {
                        filteredList.add(item);
                        Log.e("ttt", "ddd3" + filteredList.size());
                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            try {
                exampleList.clear();
                exampleList.addAll((List) results.values);
                notifyDataSetChanged();
            }
            catch (Exception e)
            {

            }
        }
    };
}
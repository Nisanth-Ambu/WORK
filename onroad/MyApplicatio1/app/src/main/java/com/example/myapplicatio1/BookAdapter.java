package com.example.myapplicatio1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class Eadapter extends RecyclerView.Adapter<Eadapter.ExampleViewHolder> implements Filterable {
    private List<ExampleItem> exampleList;
    private List<ExampleItem> exampleListFull;
    private OnItemClickListener mListener;
    public interface  OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener=listener;
    }
    class ExampleViewHolder extends RecyclerView.ViewHolder {

        TextView textView1;
        TextView textView2;
        TextView textView3;

        ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.txtname);
            textView2 = itemView.findViewById(R.id.txtlocation);
            textView3 = itemView.findViewById(R.id.txtph);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    Eadapter(List<ExampleItem> exampleList) {
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,
                parent, false);
        return new ExampleViewHolder(v,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItem currentItem = exampleList.get(position);
        holder.textView1.setText(currentItem.getName());
        holder.textView2.setText(currentItem.getLocation());
        holder.textView3.setText(currentItem.getPh());
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
            List<ExampleItem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ExampleItem item : exampleListFull) {
                    if (item.getLocation().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}

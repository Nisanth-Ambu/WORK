package com.example.test;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> implements Filterable {
    private ArrayList<Drugitem> exampleList;
    private ArrayList<Drugitem> exampleListFull;
    private OnItemClickListener mListener;

    public interface  OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener=listener;
    }
   public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
       public ExampleViewHolder(View itemView,final OnItemClickListener listener) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.cardtext1);
            textView2 = itemView.findViewById(R.id.cardtext2);
            textView3 = itemView.findViewById(R.id.cardtext3);

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
  public ExampleAdapter(ArrayList<Drugitem> exampleList) {
        if(exampleList!=null) {
            this.exampleList = exampleList;
            exampleListFull = new ArrayList<>(exampleList);
        }
    }
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,
                parent, false);
        ExampleViewHolder exvh=new ExampleViewHolder(v,mListener);
        return exvh;
    }
    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
       Drugitem currentItem = exampleList.get(position);
        holder.textView1.setText(currentItem.getProduct());
        holder.textView2.setText(""+currentItem.getPrice());
     holder.textView3.setText(""+currentItem.getQty());
        Log.d("new","product:"+currentItem.getProduct()+"|price :"+currentItem.getPrice()+"| quantity :"+currentItem.getQty());
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
            ArrayList<Drugitem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll((List)exampleListFull);
                Log.e("ttt","ddd"+filteredList.size());
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Drugitem item : exampleListFull) {
                    if (item.getProduct().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
                Log.e("ttt","ddd"+filteredList.size());
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
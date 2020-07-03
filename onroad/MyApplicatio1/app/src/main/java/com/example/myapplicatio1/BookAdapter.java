package com.example.myapplicatio1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ExampleViewHolder> implements Filterable {
    private List<BookItem> exampleList;
    private List<BookItem> exampleListFull;
    private OnItemClickListener mListener;
    public interface  OnItemClickListener{
        void onItemClick(int position);
        void onCallClick(int position);
        void onLocateClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener=listener;
    }
    class ExampleViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
ImageView caller;
ImageView Locator;
TextView textView4;
        ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.txtNames);
            textView2 = itemView.findViewById(R.id.txtContact);
            textView3 = itemView.findViewById(R.id.txtaddresser);
            textView4=itemView.findViewById(R.id.txtstatus);
             caller=itemView.findViewById(R.id.recycCalller);
             Locator=itemView.findViewById(R.id.recycLoca);
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

            caller.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION)
                        {
                            listener.onCallClick(position);
                        }
                    }
                }
            });

            Locator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION)
                        {
                            listener.onLocateClick(position);
                        }
                    }
                }
            });
        }
    }

    BookAdapter(List<BookItem> exampleList) {
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item,
                parent, false);
        return new ExampleViewHolder(v,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        BookItem currentItem = exampleList.get(position);
        holder.textView1.setText(currentItem.getUname());
        holder.textView2.setText(currentItem.getUcontact());
        holder.textView4.setText(currentItem.getStatus());
        String typ=currentItem.getLoctype();
        if(typ.equals("Other"))
        {
            holder.textView3.setText(currentItem.getLocation());
        }
        else if(typ.equals("Current"))
        {
        holder.Locator.setVisibility(View.VISIBLE);
        }

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
            List<BookItem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (BookItem item : exampleListFull) {
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

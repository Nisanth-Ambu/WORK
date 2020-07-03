package com.example.simpleapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BloodDonateAdapter extends RecyclerView.Adapter <BloodDonateAdapter.BloodDonateViewHolder>{
   private ArrayList<StoreAllDatabaseInfo> mBloodDonateList;
   private OnItemClickListener mListener;

   public interface  OnItemClickListener{
       void onItemClick(int position);
   }
   public void setOnItemClickListener(OnItemClickListener listener)
   {
       mListener=listener;
   }
    public  static class BloodDonateViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView1;
        public TextView mTextView2;


        public BloodDonateViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextView1=itemView.findViewById(R.id.rate);
            mTextView2=itemView.findViewById(R.id.name);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
if(listener!=null)
{
int position=getAdapterPosition();
if (position!= RecyclerView.NO_POSITION)
{
    listener.onItemClick(position);
}
}
                }
            });
        }
    }
    public BloodDonateAdapter(ArrayList<StoreAllDatabaseInfo> bloodlist)
    {
        mBloodDonateList=bloodlist;
    }

    @NonNull
    @Override
    public BloodDonateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

     View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list, parent, false);
     BloodDonateViewHolder bdvh=new BloodDonateViewHolder(v,mListener);
        return bdvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BloodDonateViewHolder holder, int position) {
        StoreAllDatabaseInfo currentitem=mBloodDonateList.get(position);
        holder.mTextView2.setText(currentitem.getRemind());
        holder.mTextView1.setText(currentitem.getDay()+"/"+currentitem.getMonth()+"/"+currentitem.getYear());


    }

    @Override
    public int getItemCount() {
        return mBloodDonateList.size();
    }

}

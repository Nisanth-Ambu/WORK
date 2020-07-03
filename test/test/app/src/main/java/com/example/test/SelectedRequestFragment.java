package com.example.mymedseller10;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectedRequestFragment extends Fragment {
View view;
    private static String Date;
    private static String ids;
    private static String Details;
    private static String userid;
    private static String Address;
    private static String name;
    private static String contact;
    private static String state;
    private static int Price;
    private static Uri imgs;
    private static String bill;
    EditText edtbill,edtprice;
    String tmpbill;
    int tmpprice;
    String tmpstatus;
TextView txtname,txtaddress,txtdetails,txtcontact;
ImageView imageView;
Button btn;
Spinner requestspinner;
ProgressBar progs;
FirebaseDatabase db;
    public SelectedRequestFragment() {
        // Required empty public constructor
    }

    public SelectedRequestFragment(String Date, String ids, String Details, String  userid, String Address, String name, String contact, String state, int Price, Uri imgs,String bill) {
        this.ids=ids;
        this.Date=Date;
        this.Details=Details;
        this.userid=userid;
        this.Address=Address;
        this.name=name;
        this.contact=contact;
        this.state=state;
        this.Price=Price;
        this.imgs=imgs;
        this.bill=bill;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_selected_request, container, false);
        imageView=view.findViewById(R.id.imageView);
        txtname=view.findViewById(R.id.reqname);
        txtdetails=view.findViewById(R.id.reqdetails);
        txtaddress=view.findViewById(R.id.reqaddress);
        txtcontact=view.findViewById(R.id.reqcontact);
        edtbill=view.findViewById(R.id.reqbillist);
        edtprice=view.findViewById(R.id.txtprice);
        progs=view.findViewById(R.id.selectedspinner);
        btn=view.findViewById(R.id.selectedbutton);
        db=FirebaseDatabase.getInstance();
        requestspinner=view.findViewById(R.id.statusspinner);
        Glide.with(getContext()).load(imgs).into(imageView);
        txtname.setText(name);
        txtdetails.setText(Details);
        txtaddress.setText(Address);
        txtcontact.setText(contact);
        edtbill.setText(bill);
        edtprice.setText(""+Price);
        switch(state)
        {
            case "N/A":
                requestspinner.setSelection(0);
                break;
            case"Not Available":
                requestspinner.setSelection(1);
                break;
            case "Shipped":
                requestspinner.setSelection(2);
                break;
            case "Delivered":
                requestspinner.setSelection(3);
            default:
                requestspinner.setSelection(0);
                break;
        }
        if(state.contains("Delivered") || state.contains("Not Available"))
        {   requestspinner.setEnabled(false);
            edtbill.setEnabled(false);
            edtprice.setEnabled(false);
            btn.setEnabled(false);
        }
        else
        {
            requestspinner.setEnabled(true);
            edtbill.setEnabled(true);
            edtprice.setEnabled(true);
            btn.setEnabled(true);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSelectedData();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadImageFragment dr=new LoadImageFragment(imgs);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.selected_layout, dr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        requestspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
           if(position==1)
           {
               edtbill.setEnabled(false);
               edtprice.setEnabled(false);
               btn.setEnabled(true);
           }
           else
           {
               edtbill.setEnabled(true);
               edtprice.setEnabled(true);
               btn.setEnabled(true);
           }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }
    public void UpdateSelectedData()

    {

        Log.e("select","update function");

        edtbill.setEnabled(false);
        edtprice.setEnabled(false);
        btn.setEnabled(false);

        progs.setVisibility(View.VISIBLE);
        int n=0;
        tmpbill=edtbill.getText().toString().trim();
        tmpstatus=requestspinner.getSelectedItem().toString().trim();
        tmpprice=0;
        try {
            tmpprice = Integer.parseInt(edtprice.getText().toString().trim());
        }
        catch (Exception e)
        {

        }

        if(tmpbill.isEmpty() || tmpbill.length()==0)
        {
            n=1;
            edtbill.setError("Please Fill");
        }
        else
        {
            n=0;
            edtbill.setError(null);
        }
        Log.e("select","bill n :"+n+"//"+tmpstatus+"//");
        if(tmpstatus.equals("Not select") || tmpstatus.contains("Not select"))
        {
            n=1;
            Snackbar.make(view,"select status", 1000);
        }
            else
        {
            n=0;

        }
        Log.e("select","status n :"+n);
        if(tmpprice==0)
        {

            edtprice.setError("please fill");
            n=1;
        }
        else
        {
            n=0;
            edtprice.setError(null);
        }
        Log.e("select","price n :"+n);
        Log.e("select","n :"+n);
        if(n==0)
        {
            Log.e("select","update function inner");
            edtbill.setEnabled(true);
            edtprice.setEnabled(true);
            btn.setEnabled(true);
            progs.setVisibility(View.INVISIBLE);
          final Map<String, Object> map=new HashMap<>();
          if(tmpstatus.contains("Not Available"))
          {
              map.put("state",tmpstatus);
          }
          else if(tmpstatus.contains("Shipped"))
          {
              map.put("state",tmpstatus);
              map.put("bill",tmpbill);
              map.put("price",tmpprice);
          }
          db.getReference().child("REQUEST_LIST").child(Date).child(userid).child(ids).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {
                  if(task.isSuccessful())
                  {
                      StyleableToast.makeText(getContext(),"Sucessfully Saved",R.style.exampleToast).show();
                      progs.setVisibility(View.INVISIBLE);
                      if(tmpstatus.equals("Delivered") || tmpstatus.equals("Not Available") ||tmpstatus.contains("Delivered") || tmpstatus.contains("Not Available") )
                      {
                              edtbill.setEnabled(false);
                              edtprice.setEnabled(false);
                              btn.setEnabled(false);
                      }
                      else
                      {
                          edtbill.setEnabled(true);
                          edtprice.setEnabled(true);
                          btn.setEnabled(true);


                      }
                      getActivity().onBackPressed();
                  }
              }
          }).addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {
                  edtbill.setEnabled(true);
                  edtprice.setEnabled(true);
                  btn.setEnabled(true);
                  Log.e("select","update function exception");
                  progs.setVisibility(View.INVISIBLE);
              }
          });
        }
        else
        {  edtbill.setEnabled(true);
            edtprice.setEnabled(true);
            btn.setEnabled(true);

            progs.setVisibility(View.INVISIBLE);
            Snackbar.make(view,"please Fill form fully", 1000);
        }

    }
}

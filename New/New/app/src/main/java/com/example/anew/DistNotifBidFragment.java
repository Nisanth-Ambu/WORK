package com.example.anew;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.anew.FisherMainHome.fab;
import static com.example.anew.FisherMainHome.navView;


public class DistNotifBidFragment extends Fragment {
    RecyclerView bloodlist;
    private BloodDonateAdapter mAdaptor;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<BloodDonateItem> bloodDonatelist;
    static String currentDate1, fid;
    static boolean bb = true;
    static int c = 0;
    int cnt = 0;
    static boolean nxt=false;
    String iddd;
    ProgressBar prog;
    DatabaseReference dbref, udbref;
    private static FirebaseDatabase rdb;
    int cumin = 0, cusec = 0, h1 = 0, s1 = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onPause() {
        if(nxt==false) {
            bb = false;
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        bb = true;
        super.onResume();
      // new LongOperation().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bb = true;
        final View view = inflater.inflate(R.layout.fragment_dist_notif_bid, container, false);
        Log.d("snapshot", "daddaadadadda..........................");
        fab.setVisibility(View.INVISIBLE);
        navView.setVisibility(View.VISIBLE);
        prog = view.findViewById(R.id.prog);
        rdb = FirebaseDatabase.getInstance();
        navView.getMenu().findItem(R.id.navigation_notifications).setChecked(true);
        currentDate1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        dbref = FirebaseDatabase.getInstance().getReference().child("BID").child(currentDate1);
        bloodlist = view.findViewById(R.id.recyclerView);
        bloodDonatelist = new ArrayList<>();
        bloodlist.setHasFixedSize(true);
        prog.setVisibility(View.VISIBLE);
        mLayoutManager = new LinearLayoutManager(getContext());
        bloodlist.setLayoutManager(mLayoutManager);
        mAdaptor = new BloodDonateAdapter(bloodDonatelist);
        bloodlist.setAdapter(mAdaptor);
        mAdaptor.setOnItemClickListener(new BloodDonateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(isNetworkAvailable()==true) {
                    nxt=true;
                    String sfishrID = bloodDonatelist.get(position).getFisherid();
                    Uri simg = bloodDonatelist.get(position).getImg();
                    String sname = bloodDonatelist.get(position).getName();
                    String sdic = bloodDonatelist.get(position).getDiscription();
                    String sminbid = bloodDonatelist.get(position).getMinbid();
                    String spid=bloodDonatelist.get(position).getProductid();
                //    rdb.getReference().child("BID").child(currentDate1).child(fishrID).child(productid).updateChildren(map1)

                    Fragment fragment = new BuyerAddBidFragment(simg,sfishrID,sname,sdic,sminbid,spid,currentDate1);
                    FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else
                {
                    Snackbar.make(view,"No network connection ",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        Log.d("snapshot", "daddaadadaddar..........................");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                prog.setVisibility(View.VISIBLE);
               bloodDonatelist.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    cnt = 0;
                    for (DataSnapshot childsnapshot : snapshot.getChildren()) {
                        if (childsnapshot.exists()) {
                            BidAddContent bid = childsnapshot.getValue(BidAddContent.class);
                            String name = null;
                            String discription = null;
                            String minbid = null;
                            Uri img = null;
                            String bidrate = null;
                            String status = null;
                            String time = null;
                            String buyer = null;
                            String productid = null;
                            String fisherid = null;

                            //    Log.d("snapshot", "child snapshot name" + bid.getName());
                            //     Log.d("snapshot", "child snapshot status" + status);
                            status = bid.getStatus();
                            name = bid.getName();
                            discription = bid.getDiscription();
                            minbid = bid.getMinbid();
                            img = Uri.parse(bid.getImg());
                            bidrate = bid.getBidrate();
                            time = bid.getTime();
                            buyer = bid.getBuyer();

                            productid = bid.getProductid();
                            fisherid = bid.getFisherid();
                            fid = bid.getFisherid();
                            if (status.contains("open")) {
                                Log.d("snapshot", "child snapshot name" + bid.getName());
                                Log.d("snapshot", "child snapshot status" + status);
                                Log.d("snapshot", "child snapshot status" + img);
                                cnt = cnt + 1;
                                bloodDonatelist.add(new BloodDonateItem(name, discription, minbid, img, bidrate, status, time, buyer, productid, fisherid));
                            }
                        }
                    }

                    Log.d("ggg", "2nd end " + cnt);
                    if (cnt == 0) {
                        updateWaitings(fid);
                    }


                }


              new LongOperation().execute();
                mAdaptor.notifyDataSetChanged();
                c = bloodDonatelist.size();
                Log.d("snapshot", "blooddonatesize" + c);

                    prog.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("snapshot", "ERROR.....r" + databaseError);
            }
        });
        Log.d("snapshot", "daddaadadadda..........................");

        return view;
    }




    public void updateWaitings(String id) {
        iddd=null;
        iddd = id;
        dbref.child(iddd).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childsnapshot : dataSnapshot.getChildren()) {
                    if (childsnapshot.exists()) {
                        BidAddContent bid = childsnapshot.getValue(BidAddContent.class);
                        String name = bid.getName();
                        String id = bid.getFisherid();
                        String stati = bid.getStatus();
                        String pro = bid.getProductid();
                        Log.d("snapshot", "current id:" + id + "  product " + pro + " status  :" + stati);
                        if (stati.contains("wait") && iddd != null) {
                            Log.d("              snapshot", "current id:" + id + "  product " + pro + " status  :" + stati);
                            String currentTime1 = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                            Map<String, Object> map1 = new HashMap<>();
                            map1.put("status", "open");
                            map1.put("time", currentTime1);
                            dbref.child(id).child(pro).updateChildren(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("              snapshot", "update sucessss     :");
                                        iddd = null;
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    iddd = null;
                                }
                            });

                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iddd = null;
            }
        });

    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private class LongOperation extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
           while(true){
                try {
                    Log.d("snapshot", "background");
                    dbFun();

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


        }



        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }



        public void dbFun() {

            final String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            String[] Timer1 = currentTime.split(":");
            cumin = Integer.parseInt(Timer1[0]);
            cusec = Integer.parseInt(Timer1[1]);
            for (int i = 0; i < c; i++) {
                final String fishrID = bloodDonatelist.get(i).getFisherid();
                String productid = bloodDonatelist.get(i).getProductid();
                String time = bloodDonatelist.get(i).getTime();
                String[] splitter = time.split(":");
                int dbminute = Integer.parseInt(splitter[0]);
                int dbsec = Integer.parseInt(splitter[1]);
                dbminute = dbminute + 1;
                Map<String, Object> map1 = new HashMap<>();
                map1.put("status", "close");
                Log.d("snapshot", "current :" + cumin + ":" + cusec + " dbtime  :" + dbminute + ":" + dbsec);
                if (cumin > dbminute) {
                    rdb.getReference().child("BID").child(currentDate1).child(fishrID).child(productid).updateChildren(map1).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "sucess 1..............: ");
                            updateWaitings(fishrID);
                            SystemClock.sleep(1000);


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "exception 1..............: " + e);
                            SystemClock.sleep(1000);

                        }
                    });

                } else if (cumin == dbminute) {
                    if (cusec >= dbsec) {
                        rdb.getReference().child("BID").child(currentDate1).child(fishrID).child(productid).updateChildren(map1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "sucess 1..............: ");

                                SystemClock.sleep(1000);


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "exception 1..............: " + e);
                                SystemClock.sleep(1000);

                            }
                        });
                    }

                }
            }
        }
    }
}

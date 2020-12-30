package com.hfad.machineparts2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class InspectionRecyclerFragment extends Fragment {
    private RecyclerView mRecyclerview;
    Query query1;
    View view;
    static int machineID;
    //String currentPO, partNumber;
    ArrayList<MachinesPOJO> po;

    private FirebaseFirestore db;
    private CollectionReference myRef;
private InspectionCriteriaAdapter adapter;
    public InspectionRecyclerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Inspection Sheet");
    }

    private void setUpRecyclerView() {
        Query query = myRef;
            FirestoreRecyclerOptions<InspectionCriteriaPOJO> options = new FirestoreRecyclerOptions.Builder<InspectionCriteriaPOJO>()
                    .setQuery(query1, InspectionCriteriaPOJO.class).build();
            adapter = new InspectionCriteriaAdapter(options);
            adapter.startListening();
            RecyclerView mRecyclerview = (RecyclerView) view.findViewById(R.id.recyclerView);
            mRecyclerview.setHasFixedSize(true);
            mRecyclerview.setLayoutManager(new LinearLayoutManager(view.getContext()));
            mRecyclerview.setAdapter(adapter);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final String[] partNumber = new String[1];
        final String[] customerName = new String[1];
        MachineRecordActivity activity =(MachineRecordActivity)getActivity();
        machineID = activity.getMyData();
        view =inflater.inflate(R.layout.fragment_inspection_recycler, container, false);
     db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("machines").document(String.valueOf(machineID));
docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
    @Override
    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if(task.isSuccessful()){
            DocumentSnapshot doc =task.getResult();
                   String.valueOf(doc.get("currentPO"));
           String currentPO = String.valueOf(doc.get("currentPO"));
            DocumentReference docRef1 = db.collection("PO").document(currentPO);
            docRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot doc1 =task.getResult();
                         partNumber[0] =String.valueOf (doc1.get("Part"));
                         customerName[0] = String.valueOf(doc1.get("Customer"));

                    }
                    else{

                    }
                    query1 = db.collection("InspectionCriteria").whereEqualTo("partNumber",partNumber[0]).whereEqualTo("customerName",customerName[0]).whereEqualTo("active","1");
                    setUpRecyclerView();
                }
            });
        }
        else{

        }

    }
}) ;


               // query1 = db.collection("InspectionCriteria").whereEqualTo("partNumber",partNumber[0]);
        //setUpRecyclerView();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
      // adapter.stopListening();
    }
}
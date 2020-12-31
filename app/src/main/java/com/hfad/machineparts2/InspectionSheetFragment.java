package com.hfad.machineparts2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InspectionSheetFragment extends Fragment {

private String name,partNumber,description1;
private int position,userChoice,i;
    private FirebaseFirestore db;
    View view;
    private ProgressDialog progressDialog;
    Spinner dropdown;
    private EditText measurement;
    private ImageView imageDropdown;
    private TextView CustomerName,number,prompt,planID,minimum,maximum,nominal,description,gaugeType,
            textView,textView2,textView3,textView4,textView5,textView7,textView8,textView9;
    public InspectionSheetFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Inspection Sheet");
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_inspection_sheet,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.submit:


                InspectionSheetActivity activity =(InspectionSheetActivity)getActivity();
                name = activity.getName();
                partNumber = activity.getNumber();
                position = activity.getId();
                description1=activity.getDescription();
                db = FirebaseFirestore.getInstance();
                Timestamp myTimestamp = Timestamp.now();
                final PurchaseOrderPOJO[] pojo = new PurchaseOrderPOJO[1];
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext(),R.style.AppTheme_Dark_Dialog);
                alert.setMessage("Are you Sure you want to Save?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                db.collection("InspectionCriteria")
                        .whereEqualTo("customerName",name).whereEqualTo("partNumber",partNumber).whereEqualTo("description",description1)
                        .whereEqualTo("active","1").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                showDialog();
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document :task.getResult()) {
                                        if (document.get("inspectionType").equals("Value")) {
                                            if(document.exists()) {

                                                pojo[0] = document.toObject(PurchaseOrderPOJO.class);
                                                pojo[0].setCustomerName(String.valueOf(document.get("customerName")));
                                                pojo[0].setGaugeType(String.valueOf(document.get("gaugeType")));
                                                pojo[0].setIterationID(document.getId());
                                                String strInput = measurement.getText().toString();
                                                long measurementValue = Long.parseLong(strInput);
                                                pojo[0].setMeasurementValue(measurementValue);
                                                pojo[0].setPartNumber(String.valueOf(document.get("partNumber")));
                                                pojo[0].setPlanID(String.valueOf(document.get("planID")));
                                                pojo[0].setTimestamp(myTimestamp);
                                               /*db.collection("users")
                                                       .whereEqualTo("Email",FirebaseAuth.getInstance().getCurrentUser().getEmail())
                                                       .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                       if (task.isSuccessful()) {
                                                           for (QueryDocumentSnapshot document :task.getResult()) {
                                                               if(document.exists()) {
                                                                   String userId = String.valueOf(document.get("employeeID"));
                                                                   pojo[0].setUserID(userId);
                                                               }
                                                           }
                                                       }
                                                   }
                                               });**/
                                            }

                                            DocumentReference docRef= db.collection("machines").document(String.valueOf(InspectionRecyclerFragment.machineID));
                                                   docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot document = task.getResult();
                                                        String currentPO = String.valueOf(document.get("currentPO"));

                                                        db.collection("PO").document(currentPO).collection("data").document()
                                                                .set(pojo[0])
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        Toast.makeText(getContext(),"Successfully saved",Toast.LENGTH_LONG).show();
                                                                        hideDialog();
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(getContext(),"Failed to Save, try again",Toast.LENGTH_LONG).show();
                                                                hideDialog();
                                                            }
                                                        });
                                                    }
                                                }
                                            });

                                        }
                                        else if (document.get("inspectionType").equals("Binary")){
                                            if(document.exists()) {
                                                pojo[0] = document.toObject(PurchaseOrderPOJO.class);
                                                pojo[0].setCustomerName(String.valueOf(document.get("customerName")));
                                                pojo[0].setGaugeType(String.valueOf(document.get("gaugeType")));
                                                pojo[0].setIterationID(document.getId());
                                                pojo[0].setMeasurementValue(Long.parseLong(String.valueOf(userChoice)));
                                                pojo[0].setPartNumber(String.valueOf(document.get("partNumber")));
                                                pojo[0].setPlanID(String.valueOf(document.get("planID")));
                                                pojo[0].setTimestamp(myTimestamp);
                                            }
                                            //po.put("userID", String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid()));
                                            DocumentReference docRef= db.collection("machines").document(String.valueOf(InspectionRecyclerFragment.machineID));
                                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot document = task.getResult();
                                                        String currentPO = String.valueOf(document.get("currentPO"));
                                                        db.collection("PO").document(currentPO).collection("data").document()
                                                                .set(pojo[0])
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        hideDialog();
                                                                        Toast.makeText(getContext(),"Successfully saved",Toast.LENGTH_LONG).show();
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                hideDialog();
                                                                Toast.makeText(getContext(),"Failed to Save, try again",Toast.LENGTH_LONG).show();
                                                            }
                                                        });
                                                    }
                                                }
                                            });

                                        }
                                    }
                            }
                        }
        });
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                return  true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        InspectionSheetActivity activity =(InspectionSheetActivity)getActivity();
        name = activity.getName();
        partNumber = activity.getNumber();

        description1=activity.getDescription();
        view =inflater.inflate(R.layout.fragment_inspection_sheet, container, false);
        db = FirebaseFirestore.getInstance();
        dropdown = (Spinner)view.findViewById(R.id.spinner);
        String[] items = new String[]{"Pass","Fail"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        userChoice = 1;
                        break;
                    case 1:
                        userChoice = 0;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        db.collection("InspectionCriteria")
                .whereEqualTo("customerName",name).whereEqualTo("partNumber",partNumber).whereEqualTo("description",description1)
                .whereEqualTo("active","1").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document :task.getResult()) {
                                if(document.get("inspectionType").equals("Value")) {
                                    measurement = (EditText) view.findViewById(R.id.editText2);
                                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                                    Query query =database.child("Micrometer1");
                                    query.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(snapshot.exists()){
                                                Map<String,Object> objectMap =(HashMap<String, Object>)snapshot.getValue();
                                                Double value =(double) objectMap.get("Measurement");
                                                measurement.setText(String.valueOf(value));}
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    dropdown = (Spinner)view.findViewById(R.id.spinner);
                                    prompt = (TextView)view.findViewById(R.id.prompt);
                                    imageDropdown = (ImageView)view.findViewById(R.id.imageView2);
                                    textView =(TextView)view.findViewById(R.id.textView);
                                    textView2 =(TextView)view.findViewById(R.id.textView2);
                                    textView3 =(TextView)view.findViewById(R.id.textView3);
                                    textView4 =(TextView)view.findViewById(R.id.textView4);
                                    textView5 =(TextView)view.findViewById(R.id.textView5);
                                    textView7 =(TextView)view.findViewById(R.id.textView7);
                                    textView8 =(TextView)view.findViewById(R.id.textView8);
                                    textView9 =(TextView)view.findViewById(R.id.textView9);
                                    CustomerName = (TextView) view.findViewById(R.id.name);
                                    number = (TextView) view.findViewById(R.id.partNumber);
                                    gaugeType = (TextView) view.findViewById(R.id.gaugeType);
                                    planID = (TextView) view.findViewById(R.id.planID);
                                    minimum = (TextView) view.findViewById(R.id.minimum);
                                    maximum = (TextView) view.findViewById(R.id.maximum);
                                    nominal = (TextView) view.findViewById(R.id.nominal);
                                    description = (TextView) view.findViewById(R.id.description);
                                    ConstraintLayout constraintLayout = view.findViewById(R.id.constraint);
                                    ConstraintSet constraintSet = new ConstraintSet();
                                    constraintSet.clone(constraintLayout);
                                    constraintSet.connect(R.id.editText2,ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP,15);
                                    constraintSet.applyTo(constraintLayout);
                                    CustomerName.setText(String.valueOf(document.get("customerName")));
                                    number.setText(String.valueOf(document.get("partNumber")));
                                    gaugeType.setText(String.valueOf(document.get("gaugeType")));
                                    planID.setText(String.valueOf(document.get("planID")));
                                    minimum.setText(String.valueOf(document.get("minimum")));
                                    maximum.setText(String.valueOf(document.get("maximum")));
                                    nominal.setText(String.valueOf(document.get("nominal")));
                                    description.setText(String.valueOf(document.get("description")));
                                    ((ViewGroup)prompt.getParent()).removeView(prompt);
                                    ((ViewGroup)dropdown.getParent()).removeView(dropdown);
                                    ((ViewGroup)imageDropdown.getParent()).removeView(imageDropdown);
                                }
                                else if(document.get("inspectionType").equals("Binary")){
                                    textView =(TextView)view.findViewById(R.id.textView);
                                    textView2 =(TextView)view.findViewById(R.id.textView2);
                                    textView3 =(TextView)view.findViewById(R.id.textView3);
                                    textView4 =(TextView)view.findViewById(R.id.textView4);
                                    textView5 =(TextView)view.findViewById(R.id.textView5);
                                    textView7 =(TextView)view.findViewById(R.id.textView7);
                                    textView8 =(TextView)view.findViewById(R.id.textView8);
                                    textView9 =(TextView)view.findViewById(R.id.textView9);
                                    measurement=(EditText)view.findViewById(R.id.editText2);
                                    prompt = (TextView)view.findViewById(R.id.prompt);
                                    CustomerName = (TextView) view.findViewById(R.id.name);
                                    number = (TextView) view.findViewById(R.id.partNumber);
                                    gaugeType = (TextView) view.findViewById(R.id.gaugeType);
                                    planID = (TextView) view.findViewById(R.id.planID);
                                    minimum = (TextView) view.findViewById(R.id.minimum);
                                    maximum = (TextView) view.findViewById(R.id.maximum);
                                    nominal = (TextView) view.findViewById(R.id.nominal);
                                    description = (TextView) view.findViewById(R.id.description);
                                    ConstraintLayout constraintLayout = view.findViewById(R.id.constraint);
                                    ConstraintSet constraintSet = new ConstraintSet();
                                    constraintSet.clone(constraintLayout);
                                    constraintSet.connect(R.id.mylayout,ConstraintSet.TOP,R.id.spinner,ConstraintSet.BOTTOM,24);
                                    constraintSet.applyTo(constraintLayout);
                                    CustomerName.setText(String.valueOf(document.get("customerName")));
                                    number.setText(String.valueOf(document.get("partNumber")));
                                    gaugeType.setText(String.valueOf(document.get("gaugeType")));
                                    planID.setText(String.valueOf(document.get("planID")));
                                    description.setText(String.valueOf(document.get("description")));
                                    ((ViewGroup)textView7.getParent()).removeView(textView7);
                                    ((ViewGroup)textView8.getParent()).removeView(textView8);
                                    ((ViewGroup)textView9.getParent()).removeView(textView9);
                                    ((ViewGroup)maximum.getParent()).removeView(maximum);
                                   ((ViewGroup)minimum.getParent()).removeView(minimum);
                                   ((ViewGroup)nominal.getParent()).removeView(nominal);
                                    ((ViewGroup)measurement.getParent()).removeView(measurement);
                                }

                            }

                        }
                    }
                });
        return view;
    }
    private void showDialog()
    {
        progressDialog = new ProgressDialog(getContext(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Saving..");
        progressDialog.show();
    }
    private void hideDialog(){
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
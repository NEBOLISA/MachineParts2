package com.hfad.machineparts2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class NotesFragment extends Fragment {
private int position;
private EditText note;
    private ProgressDialog progressDialog;
    private FirebaseFirestore db;
    public NotesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Notes");
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_notes,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.submit:
                db= FirebaseFirestore.getInstance();
                MachineRecordActivity activity =(MachineRecordActivity) getActivity();
                position=activity.getMyData();
                final NotesPOJO[] pojo = new NotesPOJO[1];
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext(),R.style.AppTheme_Dark_Dialog);
                alert.setMessage("Are you Sure you want to Save?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                DocumentReference docRef = db.collection("machines").document(String.valueOf(position));
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        showDialog();
                        if(task.isSuccessful()){
                            DocumentSnapshot doc =task.getResult();
                            String currentPO = String.valueOf(doc.get("currentPO"));
                            DocumentReference docRef1 = db.collection("PO").document(currentPO);
                            docRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()){
                                        DocumentSnapshot doc1 =task.getResult();
                                        if(doc1.exists()) {
                                            Timestamp myTimestamp = Timestamp.now();
                                            pojo[0] =doc1.toObject(NotesPOJO.class);//String.valueOf (doc1.get("Part"));
                                            pojo[0].setCustomerName(String.valueOf(doc1.get("Customer")));
                                            pojo[0].setPartNumber(String.valueOf(doc1.get("Part")));
                                            pojo[0].setCreation(myTimestamp);
                                            pojo[0].setMachine(position);
                                            String notes = note.getText().toString();
                                            pojo[0].setNote(notes);
                                        }
                                        db.collection("Notes").document().set(pojo[0]).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getContext(),"Note Successfully saved",Toast.LENGTH_LONG).show();
                                                hideDialog();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(),"Failed to Save, try again later",Toast.LENGTH_LONG).show();
                                                hideDialog();
                                            }
                                        });

                                    }

                                    else{

                                    }

                                }
                            });
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
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
note =(EditText)view.findViewById(R.id.note);
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
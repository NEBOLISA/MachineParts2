package com.hfad.machineparts2;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PrintFragment extends Fragment {
    private FirebaseFirestore db;
    int machineID;
 PDFView pdfView;
    private ProgressDialog progressDialog;
    public PrintFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Print");

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_print, container, false);
        showDialog();
        pdfView =(PDFView)view.findViewById(R.id.pdfview);
       //pdfView =(PDFView)view.findViewById(R.id.pdfview);
        final String[] partNumber = new String[1];
        final String[] customerName = new String[1];
        db = FirebaseFirestore.getInstance();
        MachineRecordActivity activity =(MachineRecordActivity)getActivity();
        machineID = activity.getMyData();
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
                            db.collection("PrintPointers")
                                    .whereEqualTo("partNumber",partNumber[0]).whereEqualTo("customerName",customerName[0]).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document :task.getResult()) {

                                            new DownloadPDF().execute(String.valueOf(document.get("path")));

                                        }

                                    }

                                }
                            });

                        }
                    });
                }
                else{

                }

            }
        });
        return  view;
    }
    class DownloadPDF extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try{
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection =(HttpURLConnection)url.openConnection();
                if (urlConnection.getResponseCode() == 200){
                    inputStream=new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            super.onPostExecute(inputStream);
            pdfView.fromStream(inputStream).load();
            hideDialog();
        }
    }
    private void showDialog()
    {
        progressDialog = new ProgressDialog(getContext(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading File...");
        progressDialog.show();


    }
    private void hideDialog(){
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
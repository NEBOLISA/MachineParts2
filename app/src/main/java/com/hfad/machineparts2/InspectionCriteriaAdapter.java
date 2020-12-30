package com.hfad.machineparts2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class InspectionCriteriaAdapter extends FirestoreRecyclerAdapter<InspectionCriteriaPOJO,InspectionCriteriaAdapter.CriteriaHolder> {

    public InspectionCriteriaAdapter(@NonNull FirestoreRecyclerOptions<InspectionCriteriaPOJO> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CriteriaHolder holder, int position, @NonNull InspectionCriteriaPOJO model) {
        final CardView cardView = holder.cardView;
        TextView name = (TextView)cardView.findViewById(R.id.name);
        TextView partNumber = (TextView)cardView.findViewById(R.id.partNumber);
        TextView description =(TextView)cardView.findViewById(R.id.description);
        name.setText(model.getCustomerName());
        partNumber.setText(model.getPartNumber());
        description.setText(model.getDescription());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cardView.getContext(), InspectionSheetActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("description",model.getDescription());
                intent.putExtra("customer",model.getCustomerName());
                intent.putExtra("number",model.getPartNumber());
                cardView.getContext().startActivity(intent);}});

    }

    @NonNull
    @Override
    public CriteriaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.inspectionsheet_cardview,parent,false);
        return new CriteriaHolder(v);
    }

    class CriteriaHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public CriteriaHolder(@NonNull CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }
}


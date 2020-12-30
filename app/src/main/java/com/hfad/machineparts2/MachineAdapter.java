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

public class MachineAdapter extends FirestoreRecyclerAdapter<MachinesPOJO,MachineAdapter.MachineHolder> {

    public MachineAdapter(@NonNull FirestoreRecyclerOptions<MachinesPOJO> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MachineHolder holder, int position, @NonNull MachinesPOJO model) {
        final CardView cardView = holder.cardView;
        TextView name = (TextView)cardView.findViewById(R.id.machine);
        name.setText("Machine " + model.getMachineID());
        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cardView.getContext(), MachineRecordActivity.class);
                intent.putExtra("position", position);
                cardView.getContext().startActivity(intent);}});


    }

    @NonNull
    @Override
    public MachineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView)LayoutInflater.from(parent.getContext()).inflate(R.layout.production_cardview,parent,false);
        return new MachineHolder(v);
    }

    class MachineHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public MachineHolder(@NonNull CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }
}


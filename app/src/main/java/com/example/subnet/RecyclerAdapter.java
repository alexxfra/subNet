package com.example.subnet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subnet.address.Network;

import java.util.List;

/**
 * @author Alex,Daniel
 * @version 1.0
 *
 * A basic RecyclerViewAdapter used by our VlsmActivity to display Networks
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    // log TAG
    private static final String TAG = "RecyclerAdapter";

    //List of Networks to display
    private List<Network> mDataset;


    public RecyclerAdapter(List<Network> mDataSet){
        this.mDataset = mDataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycleritem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    /**
     * We are using the information of a network to set the output text of an element in a recyclerView
     * @param holder default parameter
     * @param position default parameter
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Network net = mDataset.get(position);

        holder.holderStats.setText(R.string.vlsmSpecs);
        holder.holderOutput.setText(net.toVlsm());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView holderStats, holderOutput;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            holderStats = itemView.findViewById(R.id.recItemStats);
            holderOutput = itemView.findViewById(R.id.recDecOut);
        }
    }

}

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

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private static final String TAG = "RecyclerAdapter";

    private List<Network> mDataset;
    private Context mContext;


    public RecyclerAdapter(Context Context,List<Network> mDataSet){
        this.mContext = Context;
        this.mDataset = mDataSet;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycleritem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Network net = mDataset.get(position);

        holder.holderStats.setText(R.string.vlsmSpecs);
        holder.holderOutput.setText(net.getVlsmInfo());
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
            holderOutput = itemView.findViewById(R.id.recItemOutput);
        }
    }

}

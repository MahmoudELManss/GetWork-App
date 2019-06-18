package com.example.android.test1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>{

    private List<RecyclerItem> itemList;
    private Context mContext;

    public MyRecyclerAdapter(List<RecyclerItem> itemList, Context mContext) {
        this.itemList = itemList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RecyclerItem recyclerItem = itemList.get(position);

        holder.aName.setText(recyclerItem.getName());
        holder.aCatg.setText(recyclerItem.getCatg());
        holder.aState.setText(recyclerItem.getState());
        holder.aDesc.setText(recyclerItem.getDescription());
        holder.aImageView.setImageResource(recyclerItem.getmImageView());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView aName, aCatg, aState, aDesc;
        public ImageView aImageView;

        public ViewHolder(View view) {
            super(view);

            aName = view.findViewById(R.id.name_tv);
            aCatg = view.findViewById(R.id.catg_tv);
            aState  = view.findViewById(R.id.state_tv);
            aDesc = view.findViewById(R.id.desc_tv);
            aImageView = view.findViewById(R.id.workImage);
        }
    }
}

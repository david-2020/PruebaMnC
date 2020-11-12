package com.sms.pruebamnc;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterGrid extends RecyclerView.Adapter<AdapterGrid.ViewHoolder> {
    Context Contexto;
    LayoutInflater inflater;
    private ArrayList<GridItem> mGridData = new ArrayList<GridItem>();

    public AdapterGrid(Context cxt,ArrayList<GridItem> mGridData){
        this.inflater=LayoutInflater.from(cxt);
        this.Contexto=cxt;
        this.mGridData = mGridData;
    }

    public void setGridData(ArrayList<GridItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHoolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View View= inflater.inflate(R.layout.grid_item_layout,parent,false);
        return new ViewHoolder(View);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoolder holder, int position) {
        GridItem item = mGridData.get(position);
        holder.grid_item_title.setText(Html.fromHtml(item.getTitle()));
        Picasso.with(Contexto).load(item.getImage()).into(holder.grid_item_image);

    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class  ViewHoolder extends   RecyclerView.ViewHolder{
        TextView grid_item_title;
        ImageView grid_item_image;

        public ViewHoolder(@NonNull View itemView) {
            super(itemView);
            grid_item_title=itemView.findViewById(R.id.grid_item_title);
            grid_item_image=itemView.findViewById(R.id.grid_item_image);

        }
    }
}

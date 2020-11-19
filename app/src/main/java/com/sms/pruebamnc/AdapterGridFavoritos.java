package com.sms.pruebamnc;

import android.content.Context;
import android.database.Cursor;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterGridFavoritos extends RecyclerView.Adapter<AdapterGridFavoritos.ViewHoolder> {
    Context Contexto;
    LayoutInflater inflater;
    private ArrayList<GridItem> mGridData = new ArrayList<GridItem>();
    private boolean accion = true;
    private OperacionesDB db;
    private Cursor cursor;
    String favoritoGuardado="";

    public AdapterGridFavoritos(Context cxt, ArrayList<GridItem> mGridData){
        this.inflater=LayoutInflater.from(cxt);
        this.Contexto=cxt;
        this.mGridData = mGridData;
        db = new OperacionesDB(Contexto);
        db.open();
    }

    public void setGridData(ArrayList<GridItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHoolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View View= inflater.inflate(R.layout.grid_item_layout_favorito,parent,false);
        return new ViewHoolder(View);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoolder holder, int position) {
        GridItem item = mGridData.get(position);
        holder.grid_item_title_favo.setText("Autor "+Html.fromHtml(item.getUsername()));
        holder.txtNumLike_favor.setText(Html.fromHtml(item.getLikes()));
        Picasso.with(Contexto).load(item.getImage()).into(holder.grid_item_image_favo);
        cursor = db.obtenerIdImagen(item.getId());

    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class  ViewHoolder extends   RecyclerView.ViewHolder{
        TextView grid_item_title_favo,txtNumLike_favor;
        ImageView grid_item_image_favo;

        public ViewHoolder(@NonNull View itemView) {
            super(itemView);
            grid_item_title_favo=itemView.findViewById(R.id.grid_item_title_favo);
            grid_item_image_favo=itemView.findViewById(R.id.grid_item_image_favo);
            txtNumLike_favor=itemView.findViewById(R.id.txtNumLike_favor);

        }
    }
}

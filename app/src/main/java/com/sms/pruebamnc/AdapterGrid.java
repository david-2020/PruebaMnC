package com.sms.pruebamnc;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterGrid extends RecyclerView.Adapter<AdapterGrid.ViewHoolder> {
    Context Contexto;
    LayoutInflater inflater;
    private ArrayList<GridItem> mGridData = new ArrayList<GridItem>();
    private boolean accion = true;
    private OperacionesDB db;
    private Cursor cursor;
    String favoritoGuardado="";

    public AdapterGrid(Context cxt,ArrayList<GridItem> mGridData){
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
        View View= inflater.inflate(R.layout.grid_item_layout,parent,false);
        return new ViewHoolder(View);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoolder holder, int position) {
        GridItem item = mGridData.get(position);
        holder.grid_item_title.setText("Autor "+Html.fromHtml(item.getUsername()));
        holder.txtNumLike.setText(Html.fromHtml(item.getLikes()));
        Picasso.with(Contexto).load(item.getImage()).into(holder.grid_item_image);
       holder.imgFavorito.setBackgroundResource(R.drawable.ic_favorito);
        cursor = db.obtenerIdImagen(item.getId());
        if (cursor.moveToFirst()) {
            do {
                favoritoGuardado=cursor.getString(0);//id
            }
            while (cursor.moveToNext());
        }
        if(favoritoGuardado.equals("1")){
            holder.imgFavorito.setBackgroundResource(R.drawable.ic_favorita_guardada);
            accion = false;
        }else{
            holder.imgFavorito.setBackgroundResource(R.drawable.ic_favorito);
        }


        holder.imgFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(accion ){
                    holder.imgFavorito.setBackgroundResource(R.drawable.ic_favorita_guardada);
                    Toast.makeText(Contexto,"Se marco favorito",Toast.LENGTH_SHORT).show();
                    db.deleteDetalleImagen(item.getId());
                        int favorito=1;
                        boolean b= db.insertarDetalleImagen(item.getId(),item.getUsername(), item.getLikes(), item.getImage(), String.valueOf(favorito));
                        if(b){
                            Log.d("bd","Inserto");
                        }else{
                            Log.d("bd","No inserto"+item.getId());
                        }
                    accion = false;
                }else{
                    db.deleteDetalleImagen(item.getId());
                    Toast.makeText(Contexto,"Se desmarco como favorito",Toast.LENGTH_SHORT).show();
                    holder.imgFavorito.setBackgroundResource(R.drawable.ic_favorito);
                    accion = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class  ViewHoolder extends   RecyclerView.ViewHolder{
        TextView grid_item_title,txtNumLike;
        ImageView grid_item_image,imgFavorito;

        public ViewHoolder(@NonNull View itemView) {
            super(itemView);
            grid_item_title=itemView.findViewById(R.id.grid_item_title);
            grid_item_image=itemView.findViewById(R.id.grid_item_image);
            txtNumLike=itemView.findViewById(R.id.txtNumLike);
            imgFavorito=itemView.findViewById(R.id.imgFavorito);

        }
    }
}

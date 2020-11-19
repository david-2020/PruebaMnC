package com.sms.pruebamnc;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ManagerDB extends SQLiteOpenHelper {
	private static final String TAG = "MnC";

	// guardamos en un String toda la creación de la tabla
	private static final String dropTablaDetalleFoto = "drop table if exists detallefotos;";
	//Se modifico la tabla user para agregar el campo detallefotos.
	private static final String crearTablaDetalleFoto = "create table if not exists detallefotos(iddetallefotos text primary key, autor text not null, id text,  numerolike integer , urlimagen text null, favorito text null);";


   	// NOMBRE Y VERSION DE LA BASE DE DATOS A CREAR
    private static final String databaseName = "mnc";
	private static final int databaseVersion = 1; // Para el codigo de version 1

    public ManagerDB(Context contexto) {
		super(contexto, databaseName, null, databaseVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			// Creacion de la tabla detallefotos
			db.execSQL(crearTablaDetalleFoto);

		} catch (Exception e) {
			Log.i(TAG, "Error al abrir o crear la base de datos" + e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
		if(versionNueva>versionAnterior)
		{
		try {
			if (versionAnterior < 1 ) {
				// Eliminacion de la tabla detallefotos
				db.execSQL(dropTablaDetalleFoto);
				 onCreate(db);
	        }
		} catch (Exception e) {
			Log.i(TAG, "Error al abrir o crear la base de datos" + e);
			}
		}
    }

}

package com.sms.pruebamnc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class OperacionesDB {
	// Declaracion de variables para el uso de las tablas en SQLite
	private static final String detallefotos = "detallefotos";
	private Context context;
	private SQLiteDatabase database;
	private ManagerDB dbHelper;
	private boolean estado = false;

	public OperacionesDB(Context context) {
		this.context = context;

	}

	public OperacionesDB open() throws SQLException {
		dbHelper = new ManagerDB(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}


	/**
	 Nombre: deleteUsuarios
	 Descripcion: Funcion para eliminar todos los usuarios que existan en la base local
	 */
	public boolean deleteDetalleImagen(String id) {
		boolean transact = false;
		if (database != null && database.isOpen()) {
			transact = database.delete(detallefotos, "id= ?", new String[]{id}) > 0;
		}
		return (transact);
	}


	public boolean insertarDetalleImagen(String id,String autor, String numerolike,String urlimagen, String favorito) {
		ContentValues values = new ContentValues();
		values.put("id", id);
		values.put("autor", autor);
		values.put("numerolike", numerolike);
		values.put("urlimagen", urlimagen);
		values.put("favorito", favorito);
		return (database.insert(detallefotos, null, values) > 0);
	}

	public Cursor obtenerIdImagen(String id) {
		String parametros[] = new String[] { id};
		return database.rawQuery("select count(*) as cantidad from detallefotos where id=?", parametros);
	}
	public Cursor obtenerCantidadIdImagen() {
		return database.rawQuery("select count(*) as cantidad from detallefotos ", null);
	}

	public Cursor obtenerFavoritos() {
		return database
				.rawQuery("select id, urlimagen  ,autor ,numerolike from detallefotos", null);
	}
}
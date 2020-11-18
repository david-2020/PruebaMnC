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
	 * Nombre: obtenerUsuario Descripcion: Funcion para obtener los datos del
	 * usuario
	 *
	 * @return
	 */
	public Cursor obtenerUsuario() {
		return database
				.rawQuery(
						"SELECT userid AS _id, password, rememberpass, login FROM user as u;",
						null);
	}
	/**
	 Nombre: consultarUsuario
	 Descripcion: Funcion para verificar si usuario  existe
	 */

	public boolean consultarUsuario(String iduser) {
		if (database != null && database.isOpen()) {
			Cursor cursor = null;
			String parametro[] = new String[] { iduser };
			try {
				cursor = database.rawQuery(
						"SELECT userid AS _id from user where userid=?",
						parametro);
				if (cursor.getCount() == 0) {
					estado = false;
				} else {
					estado = true;
				}
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
		}
		return estado;

	}
	/**
	 Nombre: deleteUsuarios
	 Descripcion: Funcion para eliminar todos los usuarios que existan en la base local
	 */
	public boolean deleteUsuarios() {
		boolean transact = false;
		if (database != null && database.isOpen()) {
			transact = database.delete("user", null, null) > 0;
		}
		return (transact);
	}


	public boolean insertarDetalleImagen(String autor, String numerolike,
								   String urlimagen, String favorito) {

		ContentValues values = new ContentValues();
		values.put("autor", autor);
		values.put("numerolike", numerolike);
		values.put("urlimagen", urlimagen);
		values.put("favorito", favorito);
		return (database.insert(detallefotos, null, values) > 0);
	}
}
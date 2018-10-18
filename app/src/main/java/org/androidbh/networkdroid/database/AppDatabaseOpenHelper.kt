package org.androidbh.networkdroid.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class AppDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "AppDatabase", null, 1) {
    companion object {
        private var instance: AppDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): AppDatabaseOpenHelper {
            if (instance == null) {
                instance = AppDatabaseOpenHelper(ctx.getApplicationContext())
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("Contact", true,
            "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            "name" to TEXT,
            "phone" to TEXT,
            "email" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
    }
}

// Access property for Context
val Context.database: AppDatabaseOpenHelper
    get() = AppDatabaseOpenHelper.getInstance(getApplicationContext())
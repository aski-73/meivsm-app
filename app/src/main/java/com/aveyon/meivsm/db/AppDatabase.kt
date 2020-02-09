package com.aveyon.meivsm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.Externalizable

@Database(entities = [Contact::class, Contract::class, ExternallyOwnedAccount::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var appDao = database.appDao()

                    // Delete all content here.
                    appDao.deleteAllEOAs()

                    // Add sample words.
                    var eoa = ExternallyOwnedAccount(
                        0,
                        "TestKonto",
                        "0x60696E2f6bd0f26386eF6BC23658c43334a9bD76",
                        "4fe33962a4b49a7490cf29958468a78c391f10cd70983ce17d9e4d5df949d8d7"
                    )
                    appDao.insertEOA(eoa)
                }
            }
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "meivsm.db"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
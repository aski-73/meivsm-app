package com.aveyon.meivsm.services.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.aveyon.meivsm.model.entities.Contact
import com.aveyon.meivsm.model.entities.Contract
import com.aveyon.meivsm.model.entities.ExternallyOwnedAccount
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Contact::class, ExternallyOwnedAccount::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
//            INSTANCE?.let { database ->
//                scope.launch {
//                    var appDao = database.appDao()
//
//                    // Delete all content here.
//                    appDao.deleteAllEOAs()
//
//                    // Add sample words.
//                    var eoa =
//                        ExternallyOwnedAccount(
//                            0,
//                            "TestKonto",
//                            "0x027b61b2340D8Ba16B35e122a969fC9b4484ccc6",
//                            "2f9029de3aa4d154ab603820a4d2ededddcff6f391aec81f9d2fbdf2cb0afbc1"
//                        )
//                    appDao.insertEOA(eoa)
//
//                    appDao.deleteAllContacts()
//
//                    var contact = Contact(
//                        0,
//                        "company",
//                        "0xD52910c88309A7014078E3795c15753A858aee2A"
//                    )
//                    appDao.insertContacts(contact)
//                }
//            }
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
package br.com.fiap.fiapweb.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.fiap.fiapweb.model.Email
import br.com.fiap.fiapweb.model.HistoricoDeBusca
import br.com.fiap.fiapweb.model.Marcadores
import br.com.fiap.fiapweb.utils.Converters

@Database(
    entities = [HistoricoDeBusca::class, Email::class, Marcadores::class],
    version = 8
)
@TypeConverters(Converters::class)
abstract class UsuarioDb : RoomDatabase() {

    abstract fun historicoDeBuscaDAO(): HistoricoDeBuscaDAO

    abstract fun emailDAO(): EmailDAO

    abstract fun marcadoresDAO(): MarcadoresDAO

    companion object {

        private lateinit var instance: UsuarioDb

        fun getDatabase(context: Context): UsuarioDb {
            if (!::instance.isInitialized) {
                instance = Room.databaseBuilder(
                    context = context,
                    klass = UsuarioDb::class.java,
                    name = "usuario_db"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}
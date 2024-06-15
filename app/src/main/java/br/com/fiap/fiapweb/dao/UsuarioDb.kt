package br.com.fiap.fiapweb.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.fiap.fiapweb.model.Email
import br.com.fiap.fiapweb.model.EventoCalendario
import br.com.fiap.fiapweb.model.HistoricoDeBusca
import br.com.fiap.fiapweb.utils.Converters

@Database(
    entities = [HistoricoDeBusca::class, Email::class, EventoCalendario::class],
    version = 7
)
@TypeConverters(Converters::class)
abstract class UsuarioDb : RoomDatabase() {

    abstract fun historicoDeBuscaDAO(): HistoricoDeBuscaDAO

    abstract fun emailDAO(): EmailDAO

    abstract fun eventoCalendarioDAO(): EventoCalendarioDAO

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
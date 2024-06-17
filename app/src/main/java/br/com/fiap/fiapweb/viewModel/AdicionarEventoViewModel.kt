package br.com.fiap.fiapweb.viewModel

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import androidx.lifecycle.ViewModel

class AdicionarEventoViewModel(private val context: Context) : ViewModel() {

    var titulo: String = ""
    var local: String = ""
    var descricao: String = ""

    fun adicionarEventoAoCalendario(titulo: String, local: String, descricao: String) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, titulo)
            putExtra(CalendarContract.Events.EVENT_LOCATION, local)
            putExtra(CalendarContract.Events.DESCRIPTION, descricao)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis())
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, System.currentTimeMillis() + 60 * 60 * 1000) // 1 hora depois do início
        }
        context.startActivity(intent)
    }
}

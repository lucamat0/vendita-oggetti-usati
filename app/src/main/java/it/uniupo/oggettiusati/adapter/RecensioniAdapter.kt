package it.uniupo.oggettiusati.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.uniupo.oggettiusati.R
import it.uniupo.oggettiusati.RecensioniActivity

class RecensioniAdapter(private val listaRecensioni: List<RecensioniActivity.Recensione>) : RecyclerView.Adapter<RecensioniAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_recensioni_design, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaRecensioni.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewTitolo.text = listaRecensioni[position].titoloRecensione
        holder.textViewTesto.text = listaRecensioni[position].descrizioniRecensione
        holder.textViewAutore.text = listaRecensioni[position].idUtenteEspresso
        val valoreVoto = listaRecensioni[position].votoAlUtente
//        val valutazione = "${valoreVoto}/5"
//        holder.textViewData.text = valutazione
        holder.ratingBarRecensione.rating = valoreVoto
        holder.ratingBarRecensione.setIsIndicator(true) // false quando devo creare la recensione
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textViewTitolo :TextView = itemView.findViewById(R.id.titolo_recensione)
        val textViewTesto :TextView = itemView.findViewById(R.id.testo_recensione)
        val textViewAutore :TextView = itemView.findViewById(R.id.autore_recensione)
//        val textViewData :TextView = itemView.findViewById(R.id.data_recensione)
        val ratingBarRecensione :RatingBar = itemView.findViewById(R.id.valutazione_utente)
    }
}

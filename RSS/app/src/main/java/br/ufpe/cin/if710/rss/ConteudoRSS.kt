package br.ufpe.cin.if710.rss

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.itemlista.view.*

class ConteudoRSS(val itemLista: List<ItemRSS>, private val c: Context) : RecyclerView.Adapter<ConteudoRSS.ViewHolder>() {

//    Função simples para abrir o conteudo no navegador
    fun open_web_page(url: String, mContext: Context){
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intent.putExtras(b)
        mContext.startActivity(intent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(c).inflate(R.layout.itemlista, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = itemLista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemLista[position]
        holder?.titulo?.text = item.title
        holder?.conteudo?.text = item.description
        holder?.itemView.setOnClickListener{
            open_web_page(item.link, c)
            c.database.markAsRead(item.link)
        }
    }

    class ViewHolder(item : View) : RecyclerView.ViewHolder(item) {

        val titulo = item.item_titulo
        val conteudo = item.item_data

    }
}
package br.ufpe.cin.if710.rss
import java.io.Serializable;


class ItemRSS(val title: String, val link: String, val pubDate: String, val description: String, val readStatus: Boolean) : Serializable {

    override fun toString(): String {
        return title
    }

}

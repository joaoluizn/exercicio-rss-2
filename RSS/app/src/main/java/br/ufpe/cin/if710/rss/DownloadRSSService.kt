package br.ufpe.cin.if710.rss

import android.app.IntentService
import android.content.Intent
import android.os.Environment
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList
import android.os.Bundle
import android.widget.Toast
import java.io.Serializable

class DownloadRSSService : IntentService("DownloadRSSService") {
//Criando um serviço encarregado de fazer o download dos dados
    public override fun onHandleIntent(i: Intent?) {
        val unreadFeed = ArrayList<ItemRSS>()

        try {
//            Usando anko e doAsync para tratar requisições em outra thread
            doAsync {
                // Using default from shared preferences
                val feedXML = ParserRSS.parse(getRssFeed(i!!.data.toString()))

                for (e in feedXML){
                    Log.d("DB", "Buscando no Banco por link: " + e.link)
                    var item = database.getItemRSS(e.link)
                    if(item == null){
                        Log.d("DB", "Encontrado pela primeira vez: " + e.title)
                        database.insertItem(e)
                    }else{
                        if(!item.readStatus){
                            unreadFeed.add(e)
                        }
                    }
                }
//                Lançando intents dinamicas e staticas
                val intent = Intent(DOWNLOAD_COMPLETE)
                Log.d("DB", "Tamanho antes de enviar: " + unreadFeed.size)
                intent.putExtra("UnreadData", unreadFeed as Serializable)
                LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
                sendBroadcast(intent)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    //  Para esse exemplo achei mais elegante apenas usar o padrão de kotlin
//  mas encontrei uma lib chamada khttp que parece ser Bem util e eficiente para projetos maiores.
    @Throws(IOException::class)
    fun getRssFeed(feed: String): String {
//        Solicitando o conteudo do xml
        return URL(feed).readText()
    }

    companion object {

        val DOWNLOAD_COMPLETE = "br.ufpe.cin.if710.rss.action.RSSDownload"
    }
}
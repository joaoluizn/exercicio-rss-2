package br.ufpe.cin.if710.rss

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast




class RSSBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Toast.makeText(context, "Static Broadcast Recebido", Toast.LENGTH_SHORT).show()
        Log.d("StaticReceiver", "StaticBroadcast Recebido")

//        var feed: List<ItemRSS> = intent.getSerializableExtra("UnreadData") as List<ItemRSS>
//
//        doAsync {
//            uiThread {
//                Toast.makeText(applicationContext, "Atualizando Tela", Toast.LENGTH_SHORT).show()
//                conteudoRSS.adapter = ConteudoRSS(feed, applicationContext)
//            }
//        }
    }



}

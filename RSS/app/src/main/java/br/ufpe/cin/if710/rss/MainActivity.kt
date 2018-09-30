package br.ufpe.cin.if710.rss

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException
import java.net.URL
import android.view.MenuItem
import java.util.ArrayList


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        conteudoRSS.layoutManager = LinearLayoutManager(this)
        conteudoRSS.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu items for use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.main_activity_actions, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        when (item.getItemId()) {
            R.id.action_change_source -> {
                startActivity(Intent(
                        this@MainActivity,
                        SharedPrefsEditActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        try {
//            Usando anko e doAsync para tratar requisições em outra thread
            val prefs = PreferenceManager.getDefaultSharedPreferences(this)
            doAsync {
                // Using default from shared preferences
                val prefsSource = prefs.getString(RSS_SOURCE, getString(R.string.rssfeed))
                val feedXML = ParserRSS.parse(getRssFeed(prefsSource))
                val unreadFeed = ArrayList<ItemRSS>()

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
                uiThread {
//                  provendo conteudo para a lista através do custom adapter
                    conteudoRSS.adapter = ConteudoRSS(unreadFeed, it)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun onResume() {
        super.onResume()
        //Obtém o valor para a preference de nome de usuário
//        val source = prefs.getString(RSS_SOURCE, "Ainda n escolheu...")
//        textoUsername.text = user_name
    }

    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }

//  Para esse exemplo achei mais elegante apenas usar o padrão de kotlin
//  mas encontrei uma lib chamada khttp que parece ser Bem util e eficiente para projetos maiores.
    @Throws(IOException::class)
    private fun getRssFeed(feed: String): String {
//        Solicitando o conteudo do xml
        return URL(feed).readText()
    }

    companion object {
        val RSS_SOURCE = "rssfeed"
    }
}

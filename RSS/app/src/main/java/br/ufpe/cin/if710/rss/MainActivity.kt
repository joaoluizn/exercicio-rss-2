package br.ufpe.cin.if710.rss

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.*
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import android.view.MenuItem
import android.widget.Toast


class MainActivity : Activity(){

//    Receiver dinamico para tratar a atualização da pagina
    private val mMessageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Get extra data included in the Intent
            Toast.makeText(applicationContext, "BroadCast Recebido", Toast.LENGTH_SHORT).show()
            var feed: List<ItemRSS> = intent.getSerializableExtra("UnreadData") as List<ItemRSS>
            Toast.makeText(applicationContext, feed.size.toString(), Toast.LENGTH_SHORT).show()

            doAsync {
                uiThread {
                    Toast.makeText(applicationContext, "Atualizando Tela", Toast.LENGTH_SHORT).show()
                    conteudoRSS.adapter = ConteudoRSS(feed, applicationContext)
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        conteudoRSS.layoutManager = LinearLayoutManager(this)
        conteudoRSS.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
//        Registrando o receiver dinamico
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, IntentFilter(DownloadRSSService.DOWNLOAD_COMPLETE))
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
//        Configuração baseada nas preferencias e inicio de serviço
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val prefsSource = prefs.getString(RSS_SOURCE, getString(R.string.rssfeed))
        val downloadService = Intent(applicationContext, DownloadRSSService::class.java)
        downloadService.data = Uri.parse(prefsSource)
        startService(downloadService)
        Toast.makeText(applicationContext, "Iniciou o Service", Toast.LENGTH_SHORT).show()
    }

//    Gerenciamento de receivers dinamico nos diferentes estados
    override fun onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter(DownloadRSSService.DOWNLOAD_COMPLETE));
        super.onResume()
    }

    override fun onDestroy() {
        database.close()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
        super.onDestroy()
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
        super.onPause()
    }

    companion object {
        val RSS_SOURCE = "rssfeed"
    }
}

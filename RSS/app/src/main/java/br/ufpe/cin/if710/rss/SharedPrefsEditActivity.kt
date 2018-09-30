package br.ufpe.cin.if710.rss

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_prefs_edit.*

class SharedPrefsEditActivity : Activity () {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prefs_edit)
//        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
//        val rss_source = prefs.getString(MainActivity.RSS_SOURCE, getString(R.string.rssfeed))

    }

    class UserPreferenceFragment : PreferenceFragment() {
//        private var mListener: SharedPreferences.OnSharedPreferenceChangeListener? = null
//        private var mUserNamePreference: Preference? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            // Carrega preferences a partir de um XML
            addPreferencesFromResource(R.xml.preferencias)

            /*
            // pega a Preference especifica do username
            mUserNamePreference = preferenceManager.findPreference(PrefsActivity.USERNAME)

            // Define um listener para atualizar descricao ao modificar preferences
            mListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
                mUserNamePreference!!.summary = sharedPreferences.getString(
                        PrefsActivity.USERNAME, "Nada ainda")
            }

            // Pega objeto SharedPreferences gerenciado pelo PreferenceManager para este Fragmento
            val prefs = preferenceManager
                    .sharedPreferences

            // Registra listener no objeto SharedPreferences
            prefs.registerOnSharedPreferenceChangeListener(mListener)

            // Invoca callback manualmente para exibir username atual
            mListener!!.onSharedPreferenceChanged(prefs, PrefsActivity.USERNAME)
            */
        }

        companion object {
            protected val TAG = "UserPrefsFragment"
        }
    }

}
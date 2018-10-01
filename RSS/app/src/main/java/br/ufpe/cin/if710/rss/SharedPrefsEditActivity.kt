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
    }

    class UserPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            // Carrega preferences a partir de um XML
            addPreferencesFromResource(R.xml.preferencias)
        }

        companion object {
            protected val TAG = "UserPrefsFragment"
        }
    }

}
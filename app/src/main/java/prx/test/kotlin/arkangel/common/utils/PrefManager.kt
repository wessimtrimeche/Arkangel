package prx.test.kotlin.arkangel.common.utils

/**
 * Created by wessim on 2/19/17.
 */

import android.content.Context
import android.content.SharedPreferences

class PrefManager(internal var _context: Context) {
    internal var pref: SharedPreferences
    internal var editor: SharedPreferences.Editor

    // shared pref mode
    internal var PRIVATE_MODE = 0

    var isFirstTimeLaunch: Boolean
        get() = pref.getBoolean(IS_FIRST_TIME_LAUNCH, true)
        set(isFirstTime) {
            editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
            editor.commit()
        }

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    companion object {

        // Shared preferences file name
        private val PREF_NAME = "Arkangel"

        private val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
    }

}
package prx.test.kotlin.arkangel.common.utils

/**
 * Created by wessim on 2/19/17.
 */

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import prx.test.kotlin.arkangel.module.home.view.MainActivity
import prx.test.kotlin.arkangel.module.profile.view.EditProfileActivity


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
        private val IS_USER_LOGIN = "IsUserLoggedIn"
    }

//
    var isLoggedIn: Boolean
        get() = pref.getBoolean(IS_USER_LOGIN, true)
        set(isLoggedIn) {
            editor.putBoolean(IS_USER_LOGIN, isLoggedIn)
            editor.commit()
        }

    fun checkLogin(mAuth: FirebaseAuth){

        val firebaseAuthStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser!=null){
                val intent = Intent(_context, MainActivity::class.java)
                _context.startActivity(intent)
            }
        }
        firebaseAuthStateListener.onAuthStateChanged(mAuth)


    }
}
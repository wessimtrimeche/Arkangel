package prx.test.kotlin.arkangel.module.authentication.presenter

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.util.Patterns
import android.view.WindowManager
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kelvinapps.rxfirebase.RxFirebaseAuth
import prx.test.kotlin.arkangel.data.DataManager
import prx.test.kotlin.arkangel.module.profile.view.EditProfileActivity
import rx.functions.Action0
import rx.functions.Action1

/**
 * Created by wessim23 on 3/7/18.
 */
class LoginPresenter (val inter: AuthenticationView) {

    fun validateInputs(email: String, password: String): Boolean {

        var isValid = true
        var isValidEmail = true
        var isValidPassword = true

        if (email.isEmpty()) {
            inter.OnErrorMessageEmail("Email is required")
            isValidEmail = false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inter.OnErrorMessageEmail("Please enter a valid email")
            isValidEmail = false
        }

        if (password.isEmpty()) {
            inter.OnErrorMessagePassword("Password is required")
            isValidPassword = false
        }

        if (password.length < 6) {
            inter.OnErrorMessagePassword("Minimum length of password should be 6")
            isValidPassword = false
        }
        isValid = isValidEmail and isValidPassword
        return isValid

    }


    fun loginWithEmailPassword(mAuth: FirebaseAuth, email: String, password: String) {

        inter.OnShowLoader()

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->

        }

        RxFirebaseAuth.signInWithEmailAndPassword(mAuth, email, password).subscribe(

                Action1 {
                },
                Action1 {
                    //                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    inter.OnHideLoader()

                    if (it.message != null)
                        inter.onAuthenticationError(it.message)
                },
                Action0 {
                    //                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    inter.OnHideLoader()
                    inter.OnComplete()
                }
        )
    }

    fun firebaseAuthWithGoogle(mAuth: FirebaseAuth,acct: GoogleSignInAccount) {
        Log.d("Firebase Auth Google", "firebaseAuthWithGoogle:" + acct.id!!)

        inter.OnShowLoader()

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        RxFirebaseAuth.signInWithCredential(mAuth,credential).subscribe({
            inter.OnHideLoader()
            Log.d("Success", "signInWithCredential:success")
            val user = mAuth.currentUser
            val dm = DataManager()
            dm.createUserGoogle(mAuth,acct)

        },{
            inter.onAuthenticationError(it.message)

        },{
            inter.OnHideLoader()

            inter.OnComplete()

        })

    }


}
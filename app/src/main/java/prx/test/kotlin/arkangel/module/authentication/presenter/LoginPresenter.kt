package prx.test.kotlin.arkangel.module.authentication.presenter

import android.util.Log
import android.util.Patterns
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kelvinapps.rxfirebase.RxFirebaseAuth
import prx.test.kotlin.arkangel.data.DataManager
import rx.functions.Action0
import rx.functions.Action1

/**
 * Created by wessim23 on 3/7/18.
 */
class LoginPresenter(val inter: AuthenticationView) {

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

    fun firebaseAuthWithGoogle(mAuth: FirebaseAuth, acct: GoogleSignInAccount) {
        Log.d("Firebase Auth Google", "firebaseAuthWithGoogle:" + acct.id!!)

        inter.OnShowLoader()

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        RxFirebaseAuth.signInWithCredential(mAuth, credential).subscribe({
            inter.OnHideLoader()
            Log.d("Success", "signInWithCredential:success")
            val user = mAuth.currentUser

            //how to do it only at first login?

            val dm = DataManager()
            dm.createUserGoogle(mAuth, acct)

        }, {
            inter.onAuthenticationError(it.message)
            inter.OnHideLoader()


        }, {
            inter.OnHideLoader()

            inter.OnComplete()

        })

    }


}
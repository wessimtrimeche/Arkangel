package prx.test.kotlin.arkangel.module.authentication.presenter

import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.kelvinapps.rxfirebase.RxFirebaseAuth
import prx.test.kotlin.arkangel.data.DataManager
import rx.functions.Action0
import rx.functions.Action1

/**
 * Created by wessim23 on 2/28/18.
 */

class RegisterPresenter(val inter: AuthenticationView) {

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


    fun createUserWithEmailAndPassword(mAuth: FirebaseAuth, email: String, password: String, firstName: String, lastName: String) {

        inter.OnShowLoader()

        RxFirebaseAuth.createUserWithEmailAndPassword(mAuth, email, password).subscribe(

                Action1 {


                    val dm = DataManager()
                    dm.createUser(mAuth,firstName,lastName)
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


}

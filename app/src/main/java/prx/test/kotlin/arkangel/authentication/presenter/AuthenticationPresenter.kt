package prx.test.kotlin.arkangel.authentication.presenter

import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import prx.test.kotlin.arkangel.authentication.view.AuthenticationView

/**
 * Created by wessim23 on 2/28/18.
 */

class AuthenticationPresenter {

    val inter  : AuthenticationView

    //constructeur pour que l'interface ne soit pas nulle
    constructor(inter: AuthenticationView) {
        this.inter = inter
    }




     fun validateInputs(email: String, password: String): Boolean {

         var isValid = true
         var isValidEmail =true
         var isValidPassword= true

        if (email.isEmpty()) {
            inter.OnErrorMessageEmail("Email is required")
            isValidEmail=false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inter.OnErrorMessageEmail("Please enter a valid email")
            isValidEmail=false
        }

        if (password.isEmpty()) {
            inter.OnErrorMessagePassword("Password is required")
            isValidPassword=false
        }

        if (password.length < 6) {
            inter.OnErrorMessagePassword("Minimum length of password should be 6")
            isValidPassword=false
        }
         isValid = isValidEmail and isValidPassword
        return isValid

    }


}

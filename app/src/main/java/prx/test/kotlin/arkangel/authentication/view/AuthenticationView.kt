package prx.test.kotlin.arkangel.authentication.view

import android.widget.EditText
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

/**
 * Created by wessim23 on 2/28/18.
 */

 interface AuthenticationView {


    fun OnErrorMessageEmail (errorMessage:String)
    fun OnErrorMessagePassword (errorMessage:String)
}

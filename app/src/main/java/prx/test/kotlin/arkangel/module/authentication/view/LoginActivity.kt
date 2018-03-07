package prx.test.kotlin.arkangel.module.authentication.view

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import prx.test.kotlin.arkangel.R
import prx.test.kotlin.arkangel.module.authentication.presenter.AuthenticationView
import prx.test.kotlin.arkangel.module.authentication.presenter.LoginPresenter
import prx.test.kotlin.arkangel.module.profile.view.EditProfileActivity


class LoginActivity : AppCompatActivity(), AuthenticationView, View.OnClickListener {

    val presenter = LoginPresenter(this)
    val mAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_login)

        login.setOnClickListener(this)
        goToSignUp.setOnClickListener(this)

    }


    override fun onClick(p0: View?) {

        if(p0?.id==R.id.login){
            val email = emailEdit.text.toString().trim { it <= ' ' }
            val password = passwordEdit.text.toString().trim { it <= ' ' }


            if (presenter?.validateInputs(email,password)) {

                presenter.loginWithEmailPassword(mAuth, email, password)
            }

        }
        else if (p0?.id==R.id.goToSignUp){
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }
    }


    override fun OnComplete() {
        finish()
        val intent = Intent(this@LoginActivity, EditProfileActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onAuthenticationError(errorMessage: String?) {
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show()


    }

    override fun OnShowLoader() {
        val prog = ProgressDialog(this@LoginActivity)

        prog.setMessage("loading")
        prog.show()
    }

    override fun OnHideLoader() {
        val prog = ProgressDialog(this@LoginActivity)

        prog.hide()
    }

    override fun OnErrorMessageEmail(errorMessage: String) {
        emailEdit.error = errorMessage
        emailEdit.requestFocus()

    }

    override fun OnErrorMessagePassword(errorMessage: String) {
        passwordEdit.error = errorMessage
        passwordEdit.requestFocus()
    }




}

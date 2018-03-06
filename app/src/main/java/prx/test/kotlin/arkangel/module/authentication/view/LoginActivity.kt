package prx.test.kotlin.arkangel.module.authentication.view

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import prx.test.kotlin.arkangel.R
import prx.test.kotlin.arkangel.module.authentication.presenter.AuthenticationPresenter
import prx.test.kotlin.arkangel.module.authentication.presenter.AuthenticationView
import prx.test.kotlin.arkangel.module.profile.view.EditProfileActivity


class LoginActivity : AppCompatActivity() , AuthenticationView {

    override fun OnErrorMessageEmail(errorMessage: String) {
        emailEdit.error = errorMessage
        emailEdit.requestFocus()

    }

    override fun OnErrorMessagePassword(errorMessage: String) {
        passwordEdit.error = errorMessage
        passwordEdit.requestFocus()
    }

    val presenter = AuthenticationPresenter(this)
    val mAuth = FirebaseAuth.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_login)


        login.setOnClickListener({
            val email = emailEdit.text.toString().trim { it <= ' ' }
            val password = passwordEdit.text.toString().trim { it <= ' ' }


            if(presenter?.validateInputs(email,password)){

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                val prog = ProgressDialog(this@LoginActivity)
                prog.setMessage("loading")
                prog.show()

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    prog.hide()
                    if (task.isSuccessful) {

                        finish()
                        val intent = Intent(this@LoginActivity, EditProfileActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })



        goToSignUp.setOnClickListener({
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        })


    }



}

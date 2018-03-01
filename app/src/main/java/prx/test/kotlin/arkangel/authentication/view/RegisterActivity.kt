package prx.test.kotlin.arkangel.authentication.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.android.synthetic.main.activity_authentication.*
import prx.test.kotlin.arkangel.R
import prx.test.kotlin.arkangel.authentication.presenter.AuthenticationPresenter
import prx.test.kotlin.arkangel.profile.view.ProfileActivity


class RegisterActivity : AppCompatActivity() , AuthenticationView {
    override fun OnErrorMessageEmail(errorMessage: String) {
            emailEditText.error = errorMessage
            emailEditText.requestFocus()

    }

    override fun OnErrorMessagePassword(errorMessage: String) {
        passwordEditText.error = errorMessage
        passwordEditText.requestFocus()
    }


    override fun onCreate(savedInstanceState: Bundle?) {

         val mAuth = FirebaseAuth.getInstance()
         val presenter = AuthenticationPresenter(this)

        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_authentication)

        userTypeRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.childRadioButton -> {
                    emailEditText.visibility = View.GONE
                    passwordEditText.visibility = View.GONE
                    goToLogin.visibility= View.GONE
                    parentCode.visibility=View.VISIBLE

                    val marginParams = MarginLayoutParams(registerBtn.getLayoutParams())
                    val marginParamsRadio = MarginLayoutParams(userTypeRadioGroup.getLayoutParams())
                    marginParamsRadio.setMargins(marginParams.leftMargin,82,marginParams.rightMargin,marginParams.bottomMargin)
                    marginParams.setMargins(marginParams.leftMargin,marginParams.topMargin,marginParams.rightMargin,80)
                    registerBtn.setText("Link to parent")

                }
                R.id.parentsRadioButton->{
                    emailEditText.visibility = View.VISIBLE
                    passwordEditText.visibility = View.VISIBLE
                    goToLogin.visibility= View.VISIBLE
                    parentCode.visibility=View.GONE
                    registerBtn.setText("Register")

                }
            }
        }


        registerBtn.setOnClickListener({

            val email = emailEditText.text.toString().trim { it <= ' ' }
            val password = passwordEditText.text.toString().trim { it <= ' ' }

            progressbar.visibility = View.VISIBLE

            if(presenter?.validateInputs(email,password)){
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
                    progressbar.visibility = View.GONE

                    if (task.isSuccessful) {

                        finish()
                        startActivity(Intent(this@RegisterActivity, ProfileActivity::class.java))

                        val firebaseUser = mAuth.currentUser!!
                    } else {
                        if (task.getException() is FirebaseAuthUserCollisionException) {
                            Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }


        })

        goToLogin.setOnClickListener({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })

    }

}

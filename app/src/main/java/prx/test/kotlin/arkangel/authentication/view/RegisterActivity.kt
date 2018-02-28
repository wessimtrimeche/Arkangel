package prx.test.kotlin.arkangel.authentication.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.android.synthetic.main.activity_authentication.*
import prx.test.kotlin.arkangel.R
import prx.test.kotlin.arkangel.profile.view.ProfileActivity


class RegisterActivity : AppCompatActivity() {


    val mAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
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

        goToLogin.setOnClickListener({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })

        registerBtn.setOnClickListener({
            registerUser()

        })
    }


    private fun registerUser() {
        val email = emailEditText.text.toString().trim { it <= ' ' }
        val password = passwordEditText.text.toString().trim { it <= ' ' }

        if (email.isEmpty()) {
            emailEditText.error = "Email is required"
            emailEditText.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Please enter a valid email"
            emailEditText.requestFocus()
            return
        }

        if (password.isEmpty()) {
            passwordEditText.error = "Password is required"
            passwordEditText.requestFocus()
            return
        }

        if (password.length < 6) {
            passwordEditText.error = "Minimum lenght of password should be 6"
            passwordEditText.requestFocus()
            return
        }


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
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








}

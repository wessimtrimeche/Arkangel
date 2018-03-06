package prx.test.kotlin.arkangel.module.authentication.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import android.widget.Toast
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_authentication.*
import prx.test.kotlin.arkangel.R
import prx.test.kotlin.arkangel.module.authentication.presenter.AuthenticationPresenter
import android.app.ProgressDialog
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import prx.test.kotlin.arkangel.module.authentication.presenter.AuthenticationView
import prx.test.kotlin.arkangel.module.home.view.HomeActivity
import prx.test.kotlin.arkangel.module.profile.model.User


class RegisterActivity : AppCompatActivity() , AuthenticationView {
    override fun OnErrorMessageEmail(errorMessage: String) {
            emailEditText.error = errorMessage
            emailEditText.requestFocus()

    }

    override fun OnErrorMessagePassword(errorMessage: String) {
        passwordEditText.error = errorMessage
        passwordEditText.requestFocus()
    }
    val user = User()

    lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
         mAuth = FirebaseAuth.getInstance()
         val presenter = AuthenticationPresenter(this)

        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_authentication)

        userTypeRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.childRadioButton -> {
                    firstnameEditText.visibility = View.GONE
                    lastnameEditText.visibility = View.GONE
                    emailEditText.visibility = View.GONE
                    passwordEditText.visibility = View.GONE
                    goToLogin.visibility= View.GONE
                    parentCode.visibility=View.VISIBLE

                    val marginParams = MarginLayoutParams(registerBtn.getLayoutParams())
                    val marginParamsRadio = MarginLayoutParams(userTypeRadioGroup.getLayoutParams())
                    marginParamsRadio.setMargins(marginParams.leftMargin,82,marginParams.rightMargin,marginParams.bottomMargin)
                    marginParams.setMargins(marginParams.leftMargin,marginParams.topMargin,marginParams.rightMargin,80)
                    user.accountType= childRadioButton.text.toString()

                    registerBtn.setText("Link to parent")

                }
                R.id.parentsRadioButton->{
                    firstnameEditText.visibility = View.VISIBLE
                    lastnameEditText.visibility = View.VISIBLE
                    emailEditText.visibility = View.VISIBLE
                    passwordEditText.visibility = View.VISIBLE
                    goToLogin.visibility= View.VISIBLE
                    parentCode.visibility=View.GONE
                    registerBtn.setText("Register")

                    user.accountType= parentsRadioButton.text.toString()


                }
            }

        }



        registerBtn.setOnClickListener({
            val email = emailEditText.text.toString().trim { it <= ' ' }
            val password = passwordEditText.text.toString().trim { it <= ' ' }

            if(presenter?.validateInputs(email,password)){

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                val prog = ProgressDialog(this@RegisterActivity)
                prog.setMessage("loading")
                prog.show()


                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    prog.hide()
                    if (task.isSuccessful) {
                        save()
                        finish()
                        startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
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

    fun save(){


        var database = FirebaseDatabase.getInstance();
        val userId = mAuth.currentUser?.uid;
        val firstName1 = firstnameEditText.text.toString()
        val lastName1 = lastnameEditText.text.toString()

        user.firstName=firstName1
        user.lastName=lastName1

        val userEmail = mAuth.currentUser?.email
        user.email = userEmail

        val ref : DatabaseReference = database.getReference("users/" + userId)
        ref.setValue(user)

    }

}


//        var displayName :String = first_name.text.toString() +" "+last_name.text.toString()
//        var user : FirebaseUser? = mAuth.currentUser
//
//        if (user != null && profileImageUrl != null) {
//            val profile = UserProfileChangeRequest.Builder()
//                    .setDisplayName(displayName)
//                    .setPhotoUri(Uri.parse(profileImageUrl))
//                    .build()
//
//            user.updateProfile(profile)
//                    .addOnCompleteListener(OnCompleteListener {
//                        prog.hide()
//                        if (it.isSuccessful()) {
//                            Toast.makeText(applicationContext, "Profile Updated", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//
//        }




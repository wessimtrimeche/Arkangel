package prx.test.kotlin.arkangel.module.authentication.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_authentication.*
import prx.test.kotlin.arkangel.R
import prx.test.kotlin.arkangel.module.authentication.presenter.RegisterPresenter
import android.app.ProgressDialog
import android.widget.RadioGroup
import com.google.firebase.auth.*
import prx.test.kotlin.arkangel.module.authentication.presenter.AuthenticationView
import prx.test.kotlin.arkangel.module.home.view.HomeActivity
import prx.test.kotlin.arkangel.module.profile.model.User


class RegisterActivity : AppCompatActivity(), AuthenticationView, RadioGroup.OnCheckedChangeListener, View.OnClickListener {


    val user = User()
    lateinit var mAuth: FirebaseAuth
    val presenter = RegisterPresenter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        mAuth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_authentication)

        userTypeRadioGroup.setOnCheckedChangeListener(this)
        registerBtn.setOnClickListener(this)
        goToLogin.setOnClickListener(this)
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {

        when (p1) {
            R.id.childRadioButton -> {
                firstnameEditText.visibility = View.GONE
                lastnameEditText.visibility = View.GONE
                emailEditText.visibility = View.GONE
                passwordEditText.visibility = View.GONE
                goToLogin.visibility = View.GONE
                parentCode.visibility = View.VISIBLE

                val marginParams = MarginLayoutParams(registerBtn.getLayoutParams())
                val marginParamsRadio = MarginLayoutParams(userTypeRadioGroup.getLayoutParams())
                marginParamsRadio.setMargins(marginParams.leftMargin, 82, marginParams.rightMargin, marginParams.bottomMargin)
                marginParams.setMargins(marginParams.leftMargin, marginParams.topMargin, marginParams.rightMargin, 80)
                registerBtn.setText("Link to parent")

            }
            R.id.parentsRadioButton -> {
                firstnameEditText.visibility = View.VISIBLE
                lastnameEditText.visibility = View.VISIBLE
                emailEditText.visibility = View.VISIBLE
                passwordEditText.visibility = View.VISIBLE
                goToLogin.visibility = View.VISIBLE
                parentCode.visibility = View.GONE
                registerBtn.setText("Register")
            }
        }
    }

    override fun onClick(p0: View?) {
        if(p0?.id==R.id.registerBtn) {
            val email = emailEditText.text.toString().trim { it <= ' ' }
            val password = passwordEditText.text.toString().trim { it <= ' ' }
            val firstName = firstnameEditText.text.toString().trim { it <= ' ' }
            val lastName = lastnameEditText.text.toString().trim { it <= ' ' }

            if (presenter?.validateInputs(email, password)) {

                presenter.createUserWithEmailAndPassword(mAuth, email, password, firstName, lastName);

            }
        }
        else if (p0?.id == R.id.goToLogin){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
    }

    override fun onAuthenticationError(errorMessage: String?) {
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show()

    }

    override fun OnComplete() {
        finish()
        startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))

    }


    override fun OnShowLoader() {
        val prog = ProgressDialog(this@RegisterActivity)

        prog.setMessage("loading")
        prog.show()
    }

    override fun OnHideLoader() {
        val prog = ProgressDialog(this@RegisterActivity)


        prog.hide()
    }

    override fun OnErrorMessageEmail(errorMessage: String) {
        emailEditText.error = errorMessage
        emailEditText.requestFocus()

    }

    override fun OnErrorMessagePassword(errorMessage: String) {
        passwordEditText.error = errorMessage
        passwordEditText.requestFocus()
    }

}






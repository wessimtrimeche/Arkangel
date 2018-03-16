package prx.test.kotlin.arkangel.module.authentication.view

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.RadioGroup
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.hypertrack.lib.HyperTrack
import kotlinx.android.synthetic.main.activity_authentication.*
import kotlinx.android.synthetic.main.activity_authentication.view.*
import kotlinx.android.synthetic.main.activity_login.*
import prx.test.kotlin.arkangel.R
import prx.test.kotlin.arkangel.common.utils.PrefManager
import prx.test.kotlin.arkangel.module.authentication.presenter.AuthenticationView
import prx.test.kotlin.arkangel.module.authentication.presenter.RegisterPresenter
import prx.test.kotlin.arkangel.module.home.view.HomeActivity
import prx.test.kotlin.arkangel.module.home.view.MainActivity
import prx.test.kotlin.arkangel.module.profile.model.User
import prx.test.kotlin.arkangel.module.profile.view.EditProfileActivity
import android.widget.TextView




class RegisterActivity : AppCompatActivity(), AuthenticationView, RadioGroup.OnCheckedChangeListener, View.OnClickListener {


    val user = User()
    lateinit var mAuth: FirebaseAuth
    val presenter = RegisterPresenter(this)
    var prog: ProgressDialog? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        mAuth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_authentication)

        var prefManager = PrefManager(this)
        prog = ProgressDialog(this@RegisterActivity)

        prefManager.checkLogin(mAuth)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        FirebaseAuth.AuthStateListener({
            if (it.currentUser != null) {
                startActivity(Intent(this, EditProfileActivity::class.java))
            }
        })

        userTypeRadioGroup.setOnCheckedChangeListener(this)
        registerBtn.setOnClickListener(this)
        goToLogin.setOnClickListener(this)
        val textView = googleSignup.getChildAt(0) as TextView
        textView.setText("Sign Up with google")
        googleSignup.setOnClickListener(this)

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
                googleSignup.visibility=View.GONE
                //logo height : 194
                //logo width : 100


//                val marginParams = MarginLayoutParams(registerBtn.getLayoutParams())
//                val marginParamsRadio = MarginLayoutParams(userTypeRadioGroup.getLayoutParams())
//                marginParamsRadio.setMargins(marginParams.leftMargin, 82, marginParams.rightMargin, marginParams.bottomMargin)
//                marginParams.setMargins(marginParams.leftMargin, marginParams.topMargin, marginParams.rightMargin, 80)
                registerBtn.setText("Link to parent")

            }
            R.id.parentsRadioButton -> {
                firstnameEditText.visibility = View.VISIBLE
                lastnameEditText.visibility = View.VISIBLE
                emailEditText.visibility = View.VISIBLE
                passwordEditText.visibility = View.VISIBLE
                goToLogin.visibility = View.VISIBLE
                parentCode.visibility = View.GONE
                googleSignup.visibility=View.VISIBLE

                registerBtn.setText("Register")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                presenter.firebaseAuthWithGoogle(mAuth, account)
            } catch (e: ApiException) {
                Log.w("Sign In failure", "Google sign in failed", e)
            }

        }
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.registerBtn) {
            val email = emailEditText.text.toString().trim { it <= ' ' }
            val password = passwordEditText.text.toString().trim { it <= ' ' }
            val firstName = firstnameEditText.text.toString().trim { it <= ' ' }
            val lastName = lastnameEditText.text.toString().trim { it <= ' ' }

            if (presenter?.validateInputs(email, password)) {
                HyperTrack.requestPermissions(this);
                HyperTrack.requestLocationServices(this);

                presenter.createUserWithEmailAndPassword(mAuth, email, password, firstName, lastName);

            }
        } else if (p0?.id == R.id.goToLogin) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        } else if (p0?.id == R.id.googleSignup) {
            val signInIntent: Intent = mGoogleSignInClient!!.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);


        }

    }

    override fun onAuthenticationError(errorMessage: String?) {
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show()

    }

    override fun OnComplete() {
        finish()
        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))

    }


    override fun OnShowLoader() {

        prog?.setMessage("loading")
        prog?.show()
    }

    override fun OnHideLoader() {


        prog?.hide()
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






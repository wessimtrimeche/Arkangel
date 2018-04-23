package prx.test.kotlin.arkangel.module.authentication.view

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.hypertrack.lib.HyperTrack
import com.hypertrack.lib.callbacks.HyperTrackCallback
import com.hypertrack.lib.models.SuccessResponse
import com.hypertrack.lib.models.UserParams
import kotlinx.android.synthetic.main.activity_authentication.*
import kotlinx.android.synthetic.main.activity_login.*
import prx.test.kotlin.arkangel.R
import prx.test.kotlin.arkangel.R.id.emailEdit
import prx.test.kotlin.arkangel.R.id.passwordEdit
import prx.test.kotlin.arkangel.common.utils.PrefManager
import prx.test.kotlin.arkangel.module.authentication.presenter.AuthenticationView
import prx.test.kotlin.arkangel.module.authentication.presenter.LoginPresenter
import prx.test.kotlin.arkangel.module.home.view.HomeActivity
import prx.test.kotlin.arkangel.module.home.view.MainActivity
import prx.test.kotlin.arkangel.module.profile.view.EditProfileActivity


class LoginActivity : AppCompatActivity(), AuthenticationView, View.OnClickListener {

    val presenter = LoginPresenter(this)
    val mAuth = FirebaseAuth.getInstance()
    private val RC_SIGN_IN = 9001
    private var mGoogleSignInClient: GoogleSignInClient? = null

    var prog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_login)

        prog = ProgressDialog(this@LoginActivity)

        var prefManager = PrefManager(this)

        prefManager.checkLogin(mAuth)

//
//        val firebaseAuthStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
//            if (firebaseAuth.currentUser!=null){
//                startActivity(Intent(this,EditProfileActivity::class.java))
//            }
//        }
//
//
//        firebaseAuthStateListener.onAuthStateChanged(mAuth)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        login.setOnClickListener(this)
        goToSignUp.setOnClickListener(this)
        val textView = googleSignin.getChildAt(0) as TextView
        textView.setText("Sign In with google")
        googleSignin.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()

        val account = GoogleSignIn.getLastSignedInAccount(this)
//        updateUI(account)
        if (account != null) {
            Log.d("account", "not null")
        }
    }


    override fun onClick(p0: View?) {

        if (p0?.id == R.id.login) {
            val email = emailEdit.text.toString().trim { it <= ' ' }
            val password = passwordEdit.text.toString().trim { it <= ' ' }


            if (presenter?.validateInputs(email, password)) {
                presenter.loginWithEmailPassword(mAuth, email, password)
            }

        } else if (p0?.id == R.id.goToSignUp) {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        } else if (p0?.id == R.id.googleSignin) {
            val signInIntent: Intent = mGoogleSignInClient!!.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);


        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)

                presenter.firebaseSignInWithGoogle(mAuth, account)
            } catch (e: ApiException) {
                Log.w("Sign In failure", "Google sign in failed", e)
            }

        }
    }


    override fun OnComplete() {
        finish()
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onAuthenticationError(errorMessage: String?) {
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show()


    }

    override fun OnShowLoader() {

        prog?.setMessage("loading")
        prog?.show()
    }

    override fun OnHideLoader() {

        prog?.hide()
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

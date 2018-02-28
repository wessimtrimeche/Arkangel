package prx.test.kotlin.arkangel.authentication.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import prx.test.kotlin.arkangel.R
import kotlinx.android.synthetic.main.activity_authentication.*
import android.view.ViewGroup.MarginLayoutParams


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_authentication)

        userTypeRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.childRadioButton -> {
                    email.visibility = View.GONE
                    password.visibility = View.GONE
                    goToLogin.visibility= View.GONE
                    parentCode.visibility=View.VISIBLE

                    val marginParams = MarginLayoutParams(registerBtn.getLayoutParams())
                    val marginParamsRadio = MarginLayoutParams(userTypeRadioGroup.getLayoutParams())
                    marginParamsRadio.setMargins(marginParams.leftMargin,82,marginParams.rightMargin,marginParams.bottomMargin)
                    marginParams.setMargins(marginParams.leftMargin,marginParams.topMargin,marginParams.rightMargin,80)
                    registerBtn.setText("Link to parent")

                }
                R.id.parentsRadioButton->{
                    email.visibility = View.VISIBLE
                    password.visibility = View.VISIBLE
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
    }




}

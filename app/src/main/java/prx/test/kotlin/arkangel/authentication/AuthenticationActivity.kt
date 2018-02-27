package prx.test.kotlin.arkangel.authentication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import prx.test.kotlin.arkangel.R
import kotlinx.android.synthetic.main.activity_authentication.*
import kotlinx.android.synthetic.main.activity_child_authentication.*


class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_authentication)
    }


    //fl méthode onClick mtaa kol radioButton fl les 2 layouts XML
    fun onRadioButtonClicked(v: View) {


        val checked = (v as RadioButton).isChecked

        when (v.getId()) {

            R.id.child -> {
                if (checked)
                    setContentView(R.layout.activity_child_authentication)
            }

            R.id.parents -> {
                if (checked)
                    setContentView(R.layout.activity_authentication)
            }
            R.id.guardian -> {
                if (checked)
                    setContentView(R.layout.activity_authentication)
            }
            R.id.angel -> if (checked)
                setContentView(R.layout.activity_child_authentication)
        }
    }

}

package prx.test.kotlin.arkangel.profile.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import prx.test.kotlin.arkangel.R
import prx.test.kotlin.arkangel.profile.presenter.ProfilePresenter

class ProfileActivity : AppCompatActivity(), ProfileView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        val profilePresenter = ProfilePresenter(this)

    }
}

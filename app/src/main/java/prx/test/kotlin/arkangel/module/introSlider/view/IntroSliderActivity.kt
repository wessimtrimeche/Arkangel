package prx.test.kotlin.arkangel.module.introSlider.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntroFragment
import prx.test.kotlin.arkangel.R
import prx.test.kotlin.arkangel.common.utils.PrefManager
import prx.test.kotlin.arkangel.module.authentication.view.LoginActivity
import prx.test.kotlin.arkangel.module.authentication.view.RegisterActivity
import prx.test.kotlin.arkangel.module.introSlider.presenter.IntroSliderView


class IntroSliderActivity : AppIntro(), IntroSliderView {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        getSupportActionBar()?.hide();

//
//        if (!prefManager.isFirstTimeLaunch) {
//            launchHomeScreen()
//            finish()
//        }
//        prefManager.isFirstTimeLaunch=false


        addSlide(AppIntroFragment.newInstance(getString(R.string.slide_1_title), getString(R.string.slide_1_desc), R.drawable.arkangel, getResources().getColor( R.color.red_f64c73)))
        addSlide(AppIntroFragment.newInstance(getString(R.string.slide_2_title), getString(R.string.slide_2_desc), R.drawable.code, getResources().getColor( R.color.turquoise_20d2bb)))
        addSlide(AppIntroFragment.newInstance(getString(R.string.slide_3_title), getString(R.string.slide_3_desc), R.drawable.addlocation, getResources().getColor( R.color.green_4fb65d)))
        addSlide(AppIntroFragment.newInstance(getString(R.string.slide_4_title), getString(R.string.slide_4_desc), R.drawable.reminder, getResources().getColor( R.color.blue_3395ff)))
        addSlide(AppIntroFragment.newInstance(getString(R.string.slide_5_title), getString(R.string.slide_5_desc), R.drawable.way, getResources().getColor( R.color.grey_a3a5a3)))
        setDepthAnimation()
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        startActivity(Intent(this@IntroSliderActivity, RegisterActivity::class.java))
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        startActivity(Intent(this@IntroSliderActivity, RegisterActivity::class.java))
        finish()
    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)
    }

    private fun launchHomeScreen() {
        var prefManager = PrefManager(this)
        prefManager.isFirstTimeLaunch = false
        startActivity(Intent(this@IntroSliderActivity, LoginActivity::class.java))
        finish()
    }


}




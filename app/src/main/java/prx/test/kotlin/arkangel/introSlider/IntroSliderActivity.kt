package prx.test.kotlin.arkangel.introSlider

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntroFragment
import prx.test.kotlin.arkangel.R
import prx.test.kotlin.arkangel.authentication.AuthenticationActivity

class IntroSliderActivity : AppIntro() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide();

//        var  prefManager = PrefManager(this)
//
//        if (!prefManager.isFirstTimeLaunch) {
//            launchHomeScreen()
//            finish()
//        }
//        prefManager.isFirstTimeLaunch=false


        addSlide(AppIntroFragment.newInstance(getString(R.string.slide_1_title), getString(R.string.slide_1_desc), R.drawable.arkangel, getResources().getColor( R.color.slide1)))
        addSlide(AppIntroFragment.newInstance(getString(R.string.slide_2_title), getString(R.string.slide_2_desc), R.drawable.code, getResources().getColor( R.color.slide2)))
        addSlide(AppIntroFragment.newInstance(getString(R.string.slide_3_title), getString(R.string.slide_3_desc), R.drawable.addlocation, getResources().getColor( R.color.slide3)))
        addSlide(AppIntroFragment.newInstance(getString(R.string.slide_4_title), getString(R.string.slide_4_desc), R.drawable.reminder, getResources().getColor( R.color.slide4)))
        addSlide(AppIntroFragment.newInstance(getString(R.string.slide_5_title), getString(R.string.slide_5_desc), R.drawable.way, getResources().getColor( R.color.slide5)))
        // setFadeAnimation();
        setDepthAnimation()
        //        setFadeAnimation();
        //        setZoomAnimation();
        //        setFlowAnimation();
        //        setSlideOverAnimation();
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        // Do something when users tap on Skip button.
        startActivity(Intent(this@IntroSliderActivity, AuthenticationActivity::class.java))
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        // Do something when users tap on Done button.
        startActivity(Intent(this@IntroSliderActivity, AuthenticationActivity::class.java))
        finish()
    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)
        // Do something when the slide changes.

    }

    private fun launchHomeScreen() {
        var prefManager = PrefManager(this)
        prefManager.isFirstTimeLaunch = false;
        startActivity(Intent(this@IntroSliderActivity, AuthenticationActivity::class.java))
        finish()
    }


}




package prx.test.kotlin.arkangel.splash.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import prx.test.kotlin.arkangel.R
import prx.test.kotlin.arkangel.splash.presenter.SplashPresenter
import android.content.Intent
import android.os.Handler
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*
import prx.test.kotlin.arkangel.introSlider.IntroSliderActivity


class SplashActivity : AppCompatActivity(), SplashView {

    private var presenter: SplashPresenter? = null

    private val SPLASH_DISPLAY_LENGTH = 3000
    var splashThread: Thread? = null
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide();
        setContentView(R.layout.activity_splash)
        presenter = SplashPresenter(this)

        var anim = AnimationUtils.loadAnimation(this, R.anim.splash)
        anim.reset()
        img.clearAnimation()
        img.startAnimation(anim)


        Handler().postDelayed(Runnable {
            /* Create an Intent that will start the Menu-Activity. */
            val mainIntent = Intent(this@SplashActivity, IntroSliderActivity::class.java)
            this@SplashActivity.startActivity(mainIntent)
            this@SplashActivity.finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }


    override fun onAttach() {

    }

    override fun onDetach() {

    }
    //    public SplashView getSplashView() {
    //        return this;
    //    }
}

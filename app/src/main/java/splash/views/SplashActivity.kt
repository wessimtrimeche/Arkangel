package splash.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import prx.test.kotlin.arkangel.R
import splash.presenter.SplashPresenter
import android.content.Intent
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*
import prx.test.kotlin.arkangel.authentication.MainActivity



class SplashActivity : AppCompatActivity(), SplashView {

    private var presenter: SplashPresenter? = null


    var splashThread : Thread? = null
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide();
        setContentView(R.layout.activity_splash)
        presenter = SplashPresenter(this)

        var anim = AnimationUtils.loadAnimation(this, R.anim.splash)
        anim.reset()
        img.clearAnimation()
        img.startAnimation(anim)

        splashThread = object : Thread() {
            override fun run() {
                try {
                    var waited = 0
                    // Splash screen pause time
                    while (waited < 3500) {
                        Thread.sleep(100)
                        waited += 100
                    }
                    val intent = Intent(this@SplashActivity,
                            MainActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    this@SplashActivity.finish()
                } catch (e: InterruptedException) {
                    // do nothing
                } finally {
                    this@SplashActivity.finish()
                }

            }
        }
        (splashThread as Thread).start()


    }


    override fun onAttach() {

    }

    override fun onDetach() {

    }
    //    public SplashView getSplashView() {
    //        return this;
    //    }
}

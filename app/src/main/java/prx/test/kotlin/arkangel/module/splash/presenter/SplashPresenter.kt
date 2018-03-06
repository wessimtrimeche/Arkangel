package prx.test.kotlin.arkangel.module.splash.presenter

import prx.test.kotlin.arkangel.base.presenter.BasePresenter

/**
 * Created by wessim23 on 2/26/18.
 */

class SplashPresenter(val splashView: SplashView) : BasePresenter<SplashView> {

    override fun onAttach(view: SplashView) {

    }

    override fun onDetach() {

    }

    fun validate(){
        splashView.onValidate()
    }




}

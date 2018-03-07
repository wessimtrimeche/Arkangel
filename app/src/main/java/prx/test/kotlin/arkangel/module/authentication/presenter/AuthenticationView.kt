package prx.test.kotlin.arkangel.module.authentication.presenter

/**
 * Created by wessim23 on 2/28/18.
 */

 interface AuthenticationView {


    fun OnErrorMessageEmail (errorMessage:String)
    fun OnErrorMessagePassword (errorMessage:String)
    fun OnShowLoader()
    fun OnHideLoader()
    fun OnComplete()
    fun onAuthenticationError(errorMessage: String?)
}

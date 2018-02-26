package base.presenter

import base.views.BaseView

/**
 * Created by wessim23 on 2/26/18.
 */

interface BasePresenter <in T : BaseView> {

    fun onAttach(view: T)


    fun onDetach()
    
}

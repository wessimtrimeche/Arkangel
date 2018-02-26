package prx.test.kotlin.arkangel.authentication.presenter;

import prx.test.kotlin.arkangel.authentication.views.MainView;

/**
 * Created by wessim23 on 2/26/18.
 */

public class MainPresenterImpl implements MainPresenter {



    private MainView mainView;


    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onResume() {

        //traitement

    }

    @Override
    public void onDestroy() {

        //traitement

    }

    public MainView getMainView() {
        return mainView;
    }
}

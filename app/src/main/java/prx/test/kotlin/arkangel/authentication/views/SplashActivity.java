package prx.test.kotlin.arkangel.authentication.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import prx.test.kotlin.arkangel.R;
import prx.test.kotlin.arkangel.authentication.presenter.MainPresenter;
import prx.test.kotlin.arkangel.authentication.presenter.MainPresenterImpl;

public class MainActivity extends AppCompatActivity implements MainView{
    private MainPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenterImpl(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}

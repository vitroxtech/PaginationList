package squaring.vitrox.paginationlist;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import squaring.vitrox.paginationlist.DependencyInjection.Component.AppComponent;
import squaring.vitrox.paginationlist.DependencyInjection.Component.DaggerAppComponent;
import squaring.vitrox.paginationlist.DependencyInjection.Model.ApplicationModule;


public class App extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }


    @VisibleForTesting
    public void setAppComponent(AppComponent appComponent) {
        mAppComponent = appComponent;
    }
}

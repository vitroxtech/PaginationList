package squaring.vitrox.paginationlist.DependencyInjection.Model;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import squaring.vitrox.paginationlist.App;
import squaring.vitrox.paginationlist.Common.PaginationHelper;
import squaring.vitrox.paginationlist.Common.SearchHelper;

@Module
public class ApplicationModule {

    private final App mApp;

    public ApplicationModule(App app) {
        mApp = app;
    }

    @Provides
    @Singleton
    public Context appContext() {
        return mApp;
    }

    @Provides
    @Singleton
    public PaginationHelper paginationHelper()
    {
        return new PaginationHelper();
    }

    @Provides
    @Singleton
    public SearchHelper searchHelper(){return new SearchHelper();}

}

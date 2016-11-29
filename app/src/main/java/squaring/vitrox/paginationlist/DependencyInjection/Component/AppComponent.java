package squaring.vitrox.paginationlist.DependencyInjection.Component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import squaring.vitrox.paginationlist.Common.PaginationHelper;
import squaring.vitrox.paginationlist.Common.SearchHelper;
import squaring.vitrox.paginationlist.Common.Utils;
import squaring.vitrox.paginationlist.DependencyInjection.Model.ApplicationModule;
import squaring.vitrox.paginationlist.Network.ApiService;
import squaring.vitrox.paginationlist.Network.ServiceModule;

@Singleton
@Component(modules = {ApplicationModule.class, ServiceModule.class})
public interface AppComponent {

    Context appContext();

    ApiService apiService();

    PaginationHelper paginationHelper();

    SearchHelper searchHelper();

}

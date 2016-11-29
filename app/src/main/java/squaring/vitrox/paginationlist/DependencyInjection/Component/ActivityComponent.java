package squaring.vitrox.paginationlist.DependencyInjection.Component;

import dagger.Component;
import squaring.vitrox.paginationlist.DependencyInjection.ActivityScope;
import squaring.vitrox.paginationlist.MainActivity;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface ActivityComponent extends AppComponent{

    void inject(MainActivity activity);

}
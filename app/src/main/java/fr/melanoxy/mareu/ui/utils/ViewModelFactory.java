package fr.melanoxy.mareu.ui.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

//import fr.melanoxy.mareu.filter.FilterPageViewModel;
import fr.melanoxy.mareu.config.BuildConfigResolver;
import fr.melanoxy.mareu.ui.list.MaReuViewModel;
import fr.melanoxy.mareu.ui.newreu.NewReuViewModel;
import fr.melanoxy.mareu.data.ReunionRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(
                            new ReunionRepository(
                                    new BuildConfigResolver()
                            )
                    );
                }
            }
        }

        return factory;
    }

    // This field inherit the singleton property from the ViewModelFactory : it is scoped to the ViewModelFactory
    // Ask your mentor about DI scopes (Singleton, ViewModelScope, ActivityScope)
    @NonNull
    private final ReunionRepository reunionRepository;

    private ViewModelFactory(@NonNull ReunionRepository reunionRepository) {
        this.reunionRepository = reunionRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NewReuViewModel.class)) {
            return (T) new NewReuViewModel(
                    reunionRepository
            );
        /*} else if (modelClass.isAssignableFrom(FilterPageViewModel.class)) {
            return (T) new FilterPageViewModel(
                    MainApplication.getInstance(),
                    reunionRepository
            );*/
        } else if (modelClass.isAssignableFrom(MaReuViewModel.class)) {
            return (T) new MaReuViewModel(
                    reunionRepository
            );
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

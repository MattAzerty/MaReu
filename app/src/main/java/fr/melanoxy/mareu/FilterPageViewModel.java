package fr.melanoxy.mareu;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import fr.melanoxy.mareu.repo.ReunionRepository;

public class FilterPageViewModel extends ViewModel {

    // Injected thanks to ViewModelFactory
    @NonNull
    private final Application application;

    // Injected thanks to ViewModelFactory
    @NonNull
    private final ReunionRepository reunionRepository;

    public FilterPageViewModel(@NonNull Application application, @NonNull ReunionRepository neighbourRepository) {
        this.application = application;
        this.reunionRepository = neighbourRepository;
    }
}

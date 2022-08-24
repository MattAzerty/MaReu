package fr.melanoxy.mareu.list;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import fr.melanoxy.mareu.repo.Reunion;
import fr.melanoxy.mareu.repo.ReunionRepository;

public class MaReuViewModel extends ViewModel {

    @NonNull
    private final ReunionRepository reunionRepository;

    public MaReuViewModel(@NonNull ReunionRepository reunionRepository) {
        this.reunionRepository = reunionRepository;
    }


    public LiveData<List<ReunionsViewStateItem>> getReunionsViewStateItemLiveData(){

        return Transformations.map(reunionRepository.getReunionsLiveData(), reunions -> {
            List<ReunionsViewStateItem> reunionsViewStateItems = new ArrayList<>();

            // This is called mapping !
            // Ask your mentor why it is important to separate "data" models (like Neighbour class)
            // and "view" models (like NeighboursViewStateItem)
            for (Reunion reunion : reunions) {

                reunionsViewStateItems.add(
                            new ReunionsViewStateItem(
                                    reunion.getId(),
                                    reunion.getSubject()+" - "+reunion.getDate()+" - "+reunion.getPlace(),
                                    reunion.getPeople()
                            )
                    );
                }

            return reunionsViewStateItems;
        });
    }

    public void onDeleteReunionClicked(long reunionId) {
        reunionRepository.deleteReunion(reunionId);
    }



}//END


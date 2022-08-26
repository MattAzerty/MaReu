package fr.melanoxy.mareu.repo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.melanoxy.mareu.config.BuildConfigResolver;


public class ReunionRepository {

    private final MutableLiveData<List<Reunion>> reunionsLiveData = new MutableLiveData<>(new ArrayList<>());

    private long maxId = 0;

    public ReunionRepository(BuildConfigResolver buildConfigResolver) {
        // At startup, when creating repo, if we're in debug mode, add random Neighbours
        if (buildConfigResolver.isDebug()) {
            generateRandomReu();
        }
    }

    public void addReunion(
            @NonNull String date,
            @NonNull String place,
            @Nullable String subject,
            @Nullable String people
    ) {
        List<Reunion> reunions = reunionsLiveData.getValue();

        if (reunions == null) return;

        reunions.add(
                new Reunion(
                        maxId++,
                        date,
                        place,
                        subject,
                        people
                )
        );

        reunionsLiveData.setValue(reunions);
    }

    public void deleteReunion(long reunionId) {
        List<Reunion> reunions = reunionsLiveData.getValue();

        if (reunions == null) return;

        for (Iterator<Reunion> iterator = reunions.iterator(); iterator.hasNext(); ) {
            Reunion reunion = iterator.next();

            if (reunion.getId() == reunionId) {
                iterator.remove();
                break;
            }
        }

        reunionsLiveData.setValue(reunions);
    }


    public LiveData<List<Reunion>> getReunionsLiveData() {
        return reunionsLiveData;
    }

    public LiveData<Reunion> getNeighbourLiveData(long reunionId) {
        // We use a Transformation here so whenever the neighboursLiveData changes, the underlying lambda will be called too, and
        // the Neighbour will be re-emitted (with potentially new information like isFavorite set to true or false)

        // This Transformation transforms a List of Neighbours into a Neighbour (matched by its ID)
        return Transformations.map(reunionsLiveData, reunions -> {
            for (Reunion reunion : reunions) {
                if (reunion.getId() == reunionId) {
                    return reunion;
                }
            }

            return null;
        });
    }

    private void generateRandomReu() {
        addReunion(
                "14h00 [2022.01.01]",
                "Soleil",
                "Réunion A",
                "maxime@lamzone.com, alex@lamzone.com, paul@lamzone.com"
        );
        addReunion(
                "08h00 [2022.02.02]",
                "Etoile",
                "Réunion B",
                "paul@lamzone.com, viviane@lamzone.com, amandine@lamzone.com"
        );
        addReunion(
                "08h00 [2022.03.03]",
                "Lune",
                "Réunion C",
                "amandine@lamzone.com, luc@lamzone.com, jean@lamzone.com"
        );


}}

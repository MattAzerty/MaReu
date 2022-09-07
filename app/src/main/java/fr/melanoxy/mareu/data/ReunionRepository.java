package fr.melanoxy.mareu.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import fr.melanoxy.mareu.config.BuildConfigResolver;


public class ReunionRepository {

    private final MutableLiveData<List<Reunion>> reunionsLiveData = new MutableLiveData<>(new ArrayList<>());

    private long maxId = 0;
    private Date iniDate = new Date();

    private final MutableLiveData<Integer> filterTypeLiveData = new MutableLiveData<>(0);
    private final MutableLiveData<Date> filterDateLiveData = new MutableLiveData<>(iniDate);
    private final MutableLiveData<String> filterRoomLiveData = new MutableLiveData<>("Soleil");


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


    public LiveData<Integer> getFilterTypeLiveData() {
        return filterTypeLiveData;
    }

    public LiveData<Date> getFilterDateLiveData() {
        return filterDateLiveData;
    }

    public LiveData<String> getFilterRoomLiveData() {
        return filterRoomLiveData;
    }

    public void filterType(int type) {
        Integer previousValue = filterTypeLiveData.getValue();

        if (previousValue == null) {
            previousValue = 0;
        }

        Integer newValue = type;

        // On change la valeur de la LiveData, cela va activer l'Observer qui regarde cette LiveData (comme dans le MainViewModel) et
        // provoquer l'appel de la méthode "combine" du MainViewModel avec les nouvelles valeurs
        filterTypeLiveData.setValue(newValue);
    }

    public void filterDate(Date date) {
        Date previousValue = filterDateLiveData.getValue();

        if (previousValue == null) {
            previousValue = new Date();
        }

        Date newValue = date;

        // On change la valeur de la LiveData, cela va activer l'Observer qui regarde cette LiveData (comme dans le MainViewModel) et
        // provoquer l'appel de la méthode "combine" du MainViewModel avec les nouvelles valeurs
        filterDateLiveData.setValue(newValue);
    }

    public void filterRoom(String room) {
        String previousValue = filterRoomLiveData.getValue();

        if (previousValue == null) {
            previousValue = "Soleil";
        }

        String newValue = room;

        // On change la valeur de la LiveData, cela va activer l'Observer qui regarde cette LiveData (comme dans le MainViewModel) et
        // provoquer l'appel de la méthode "combine" du MainViewModel avec les nouvelles valeurs
        filterRoomLiveData.setValue(newValue);
    }


    public LiveData<Reunion> getReunionByIDLiveData(long reunionId) {

        return Transformations.map(reunionsLiveData, reunions -> {
            for (Reunion reunion : reunions) {
                reunion.getId();
                if (reunion.getId() == reunionId) {
                    return reunion;
                }
            }

            return null;
        });

        /*List<Reunion> reunions = reunionsLiveData.getValue();

        for (Iterator<Reunion> iterator = reunions.iterator(); iterator.hasNext(); ) {
            Reunion reunion = iterator.next();

            if (reunion.getId() == reunionId) {
                return reunion;
        }
    }
        return null;*/
    }

    public void clearReunion(){
        reunionsLiveData.setValue(new ArrayList<>());

        /*List<Reunion> reunions = reunionsLiveData.getValue();

        if (reunions == null) return;

        for (Iterator<Reunion> iterator = reunions.iterator(); iterator.hasNext(); ) {
            Reunion reunion = iterator.next();

                iterator.remove();

        }

        reunionsLiveData.setValue(reunions);*/

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

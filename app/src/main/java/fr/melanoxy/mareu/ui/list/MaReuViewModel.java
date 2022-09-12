package fr.melanoxy.mareu.ui.list;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.melanoxy.mareu.data.Reunion;
import fr.melanoxy.mareu.data.ReunionRepository;

public class MaReuViewModel extends ViewModel {


    @NonNull
    private final ReunionRepository reunionRepository;
    private final MediatorLiveData<List<ReunionsViewStateItem>> myMediatorLiveData = new MediatorLiveData<>();

    //InfoFilter to get number of result from filterfragment
    private String initInfoFilter = "";
    private final MutableLiveData<String> infoFilterMutableLiveData = new MutableLiveData<>(initInfoFilter);

    public MaReuViewModel(@NonNull ReunionRepository reunionRepository) {
        this.reunionRepository = reunionRepository;

        // Attention au bug !! Quand on utilise un mediatorLiveData, lorsqu'on fait les "addSource", il faut bien utiliser la même variable
        // dans le "addSource" et dans le onChanged des autres sources (quand on fait "filterTypeLiveData.getValue()" par exemple)
        final LiveData<List<Reunion>> reunionsLiveData = reunionRepository.getReunionsLiveData();
        LiveData<Integer> filterTypeLiveData = reunionRepository.getFilterTypeLiveData();
        LiveData<Date> filterDateLiveData = reunionRepository.getFilterDateLiveData();
        LiveData<String> filterRoomLiveData = reunionRepository.getFilterRoomLiveData();

        myMediatorLiveData.addSource(reunionsLiveData, new Observer<List<Reunion>>() {
            @Override
            public void onChanged(List<Reunion> reunions) {
                combine(reunions, filterTypeLiveData.getValue(), filterDateLiveData.getValue(), filterRoomLiveData.getValue());
            }
        });

        myMediatorLiveData.addSource(filterTypeLiveData, new Observer<Integer>() {
            @Override
            public void onChanged(Integer type) {
                combine(reunionsLiveData.getValue(), type, filterDateLiveData.getValue(), filterRoomLiveData.getValue());
            }
        });

        myMediatorLiveData.addSource(filterDateLiveData, new Observer<Date>() {
            @Override
            public void onChanged(Date date) {
                combine(reunionsLiveData.getValue(), filterTypeLiveData.getValue(), date, filterRoomLiveData.getValue());
            }
        });

        myMediatorLiveData.addSource(filterRoomLiveData, new Observer<String>() {
            @Override
            public void onChanged(String room) {
                combine(reunionsLiveData.getValue(), filterTypeLiveData.getValue(), filterDateLiveData.getValue(), room);
            }
        });


    }

    private void combine(@Nullable final List<Reunion> reunions, @Nullable Integer type, @Nullable Date date, @Nullable String room) {

        if (type == null || date == null || room == null) {
            throw new IllegalStateException("All internal LiveData must be initialized !");

        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        ;
        String strDate = dateFormat.format(date);

        // Filter reunion with parameters
        List<Reunion> filteredReunions = getFilteredReunions(reunions, type, date, room);

        // map on a ViewStateItem
        List<ReunionsViewStateItem> reunionsViewStateItem = new ArrayList<>();
        for (Reunion filteredReunion : filteredReunions) {
            reunionsViewStateItem.add(mapReunion(filteredReunion));
        }


        myMediatorLiveData.setValue(reunionsViewStateItem);
    }

    // Getter typé en LiveData (et pas MediatorLiveData pour éviter la modification de la valeur de la LiveData dans la View)
    public LiveData<List<ReunionsViewStateItem>> getViewStateLiveData() {
        return myMediatorLiveData;
    }

    public LiveData<String> getInfoFilterLiveData() {
        return infoFilterMutableLiveData;
    }
    //TODO:1
    public LiveData<Date> getFilterDateLiveData() {
        return reunionRepository.getFilterDateLiveData();
    }

    public LiveData<Integer> getFilterTypeLiveData() {
        return reunionRepository.getFilterTypeLiveData();
    }

    @NonNull
    private List<Reunion> getFilteredReunions(
            @Nullable final List<Reunion> reunions,
            @Nullable Integer type,
            @Nullable Date date,
            @Nullable String room
    ) {
        List<Reunion> filteredReunions = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        ;
        String strDate = dateFormat.format(date);


        if (reunions == null) {
            return filteredReunions;
        }

        for (Reunion reunion : reunions) {

            switch (type) {

                case 0://No filter
                    filteredReunions.add(reunion);
                    break;

                case 1://Date filter
                    if (reunion.getDate().contains(strDate)) {
                        filteredReunions.add(reunion);
                    }
                    break;

                case 2://Room filter
                    if (reunion.getPlace().contains(room)) {
                        filteredReunions.add(reunion);
                    }
                    break;

                case 3://Date and Room filter
                    if (reunion.getPlace().contains(room) && reunion.getDate().contains(strDate)) {
                        filteredReunions.add(reunion);
                        break;

                    }
            }
        }

        Integer numberOfEntryFound = filteredReunions.size();
        infoFilterMutableLiveData.setValue("- " + numberOfEntryFound.toString() + " résultat(s) trouvé(s) -");
        return filteredReunions;
    }

    // This is here we transform the "raw data" (Reunion) into a "user pleasing" view model (ReunionViewStateItem)
    // Reunion is for databases, it has technical values
    // ReunionsViewStateItem is for the view (Activity or Fragment), it mostly has Strings or Android Resource Identifiers
    @NonNull
    private ReunionsViewStateItem mapReunion(@NonNull Reunion reunion) {
        return new ReunionsViewStateItem(
                reunion.getId(),
                reunion.getSubject() + " - " + reunion.getDate() + " - " + reunion.getPlace(),
                reunion.getPeople()
        );
    }


    public void onFilterTypeChanged(int type) {
        reunionRepository.filterType(type);
    }

    public void onFilterDateChanged(Date date, TextView year) {//TODO:2
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int newYear = Integer.parseInt(year.getText().toString());
        cal.set(Calendar.YEAR, newYear);
        date= cal.getTime();
        reunionRepository.filterDate(date);
    }

    public void onFilterRoomChanged(String room) {
        reunionRepository.filterRoom(room);
    }

    public void onDeleteReunionClicked(long reunionId) {
        reunionRepository.deleteReunion(reunionId);
    }

    public void onClearReunionsClicked() {
        reunionRepository.clearReunion();
    }


}//END


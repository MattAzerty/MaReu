package fr.melanoxy.mareu.list;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.melanoxy.mareu.repo.Reunion;
import fr.melanoxy.mareu.repo.ReunionRepository;

public class MaReuViewModel extends ViewModel {

    @NonNull
    private final ReunionRepository reunionRepository;
    private int mFilterType = 0;
    private String mFilterDate = "";

    public MaReuViewModel(@NonNull ReunionRepository reunionRepository) {
        this.reunionRepository = reunionRepository;
    }


    /*//Filter type selector
    private Integer initSelector =0;
    private final MutableLiveData<Integer> filterSelectorMutableLiveData = new MutableLiveData<>(initSelector);

    //Filter date choice
    private Date iniDate = new Date();
    private final MutableLiveData<Date> filterDateMutableLiveData = new MutableLiveData<>(iniDate);

    //Filter place choice
    private Integer initPlace =0;
    private final MutableLiveData<Integer> filterPlaceMutableLiveData = new MutableLiveData<>(initPlace);*/



    public LiveData<List<ReunionsViewStateItem>> getReunionsViewStateItemLiveData(){

        return Transformations.map(reunionRepository.getReunionsLiveData(), reunions -> {
            List<ReunionsViewStateItem> reunionsViewStateItems = new ArrayList<>();

            // This is called mapping !
            // Ask your mentor why it is important to separate "data" models (like Neighbour class)
            // and "view" models (like NeighboursViewStateItem)
            for (Reunion reunion : reunions) {

                if(mFilterType==0){
                reunionsViewStateItems.add(
                            new ReunionsViewStateItem(
                                    reunion.getId(),
                                    reunion.getSubject()+" - "+reunion.getDate()+" - "+reunion.getPlace(),
                                    reunion.getPeople()
                            )
                    );
                }

            else if(mFilterType==1){
                if(reunion.getDate().contains(mFilterDate)){
                    reunionsViewStateItems.add(
                            new ReunionsViewStateItem(
                                    reunion.getId(),
                                    reunion.getSubject()+" - "+reunion.getDate()+" - "+reunion.getPlace(),
                                    reunion.getPeople()
                            )
                    );

                }


                }


            }

            return reunionsViewStateItems;
        });
    }

    /*public LiveData<Integer> getFilterSelectorMutableLiveData() {
        return filterSelectorMutableLiveData;
    }*/

    public void onFilterTypeChanged(int type, Date date, String room) {
        mFilterType=type;

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");  ;
        String strDate = dateFormat.format(date);
        mFilterDate=strDate;
        reunionRepository.updateReunion();


    }


    public void onDeleteReunionClicked(long reunionId) {
        reunionRepository.deleteReunion(reunionId);
    }



}//END


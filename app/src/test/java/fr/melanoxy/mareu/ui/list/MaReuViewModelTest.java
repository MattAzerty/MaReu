package fr.melanoxy.mareu.ui.list;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static fr.melanoxy.mareu.UnitTestUtils.getOrAwaitValue;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.melanoxy.mareu.LiveDataTestUtils;
import fr.melanoxy.mareu.config.BuildConfigResolver;
import fr.melanoxy.mareu.data.Reunion;
import fr.melanoxy.mareu.data.ReunionRepository;

@RunWith(MockitoJUnitRunner.class)
public class MaReuViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private ReunionRepository reunionRepository;


    private MutableLiveData<List<Reunion>> reunionsMutableLiveData;
    private MutableLiveData<Integer> filterTypeLiveData;
    private MutableLiveData<Date> filterDateLiveData;
    private MutableLiveData<String> filterRoomLiveData;

    private MaReuViewModel viewModel;
    private Date date;
    private Date dateINI;

    @Before
    public void setUp() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2008, 9, 23, 12, 01, 01);
        dateINI = calendar.getTime();

    }

    @Test
    public void when_filterType_is_on_none_should_display_filteredDateList_with_all() throws InterruptedException {

        // Given
        // On crée notre propre MutableLiveData pendant le test pour la manipuler comme on souhaite
        reunionsMutableLiveData = new MutableLiveData<>();
        filterTypeLiveData = new MutableLiveData<>();
        filterDateLiveData = new MutableLiveData<>();
        filterRoomLiveData = new MutableLiveData<>();


        //Les variables livedata sont atribuées à la place du repository
        List<Reunion> reunions = getDefaultReunions(4);
        reunionsMutableLiveData.setValue(reunions);
        filterTypeLiveData.setValue(0);
        filterDateLiveData.setValue(dateINI);
        filterRoomLiveData.setValue("room3");

        // On construit le mock du Repository... Un Mock c'est une "coquille vide". Il fait semblant d'être un RandomRepository mais il ne
        // se passe rien dans ses méthodes et il renverra "null" s'il doit renvoyer une valeur. On peut lui dire quelle variable renvoyer
        // si quelqu'un utilise une de ses fonctions (dans notre cas MaReuViewModel)
        reunionRepository = Mockito.mock(ReunionRepository.class);

        // Pour pouvoir modifier l'objet retourné par la méthode getRandomNumberLiveData(). Maintenant, c'est notre MutableLiveData qui est
        // est donnée au MainViewModel et  pas la "vraie" MutableLiveData du RandomRepository.

        Mockito.doReturn(reunionsMutableLiveData).when(reunionRepository).getReunionsLiveData();
        Mockito.doReturn(filterTypeLiveData).when(reunionRepository).getFilterTypeLiveData();
        Mockito.doReturn(filterDateLiveData).when(reunionRepository).getFilterDateLiveData();
        Mockito.doReturn(filterRoomLiveData).when(reunionRepository).getFilterRoomLiveData();

        // On injecte notre mock dans notre MaReuViewModel pour "intervenir" lorsque
        // le MaReuViewModel va demander au ReunionRepository les LiveData.
        MaReuViewModel viewModel = new MaReuViewModel(reunionRepository);

        List<ReunionsViewStateItem> listReunionsViewStateItem = new ArrayList<>();
        listReunionsViewStateItem.add(getDefaultReunionViewStateItem(0));
        listReunionsViewStateItem.add(getDefaultReunionViewStateItem(1));
        listReunionsViewStateItem.add(getDefaultReunionViewStateItem(2));
        listReunionsViewStateItem.add(getDefaultReunionViewStateItem(3));

        // When
        List<ReunionsViewStateItem> result = getOrAwaitValue(viewModel.getViewStateLiveData());

        assertEquals(listReunionsViewStateItem,
                result);

        String result2 = getOrAwaitValue(viewModel.getInfoFilterLiveData());

        assertEquals(
                "- 4 résultat(s) trouvé(s) -",
                result2
        );


    }

    @Test
    public void when_filterType_is_on_filterDate_only_should_display_filteredDateList() throws InterruptedException {

        // Given
        // On crée notre propre MutableLiveData pendant le test pour la manipuler comme on souhaite
        reunionsMutableLiveData = new MutableLiveData<>();
        filterTypeLiveData = new MutableLiveData<>();
        filterDateLiveData = new MutableLiveData<>();
        filterRoomLiveData = new MutableLiveData<>();


        //Les variables livedata sont atribuées à la place du repository
        List<Reunion> reunions = getDefaultReunions(4);
        reunionsMutableLiveData.setValue(reunions);
        filterTypeLiveData.setValue(1);
        filterDateLiveData.setValue(dateINI);
        filterRoomLiveData.setValue("room3");

        // On construit le mock du Repository... Un Mock c'est une "coquille vide". Il fait semblant d'être un RandomRepository mais il ne
        // se passe rien dans ses méthodes et il renverra "null" s'il doit renvoyer une valeur. On peut lui dire quelle variable renvoyer
        // si quelqu'un utilise une de ses fonctions (dans notre cas MaReuViewModel)
        reunionRepository = Mockito.mock(ReunionRepository.class);

        // Pour pouvoir modifier l'objet retourné par la méthode getRandomNumberLiveData(). Maintenant, c'est notre MutableLiveData qui est
        // est donnée au MainViewModel et  pas la "vraie" MutableLiveData du RandomRepository.

        Mockito.doReturn(reunionsMutableLiveData).when(reunionRepository).getReunionsLiveData();
        Mockito.doReturn(filterTypeLiveData).when(reunionRepository).getFilterTypeLiveData();
        Mockito.doReturn(filterDateLiveData).when(reunionRepository).getFilterDateLiveData();
        Mockito.doReturn(filterRoomLiveData).when(reunionRepository).getFilterRoomLiveData();

        // On injecte notre mock dans notre MaReuViewModel pour "intervenir" lorsque
        // le MaReuViewModel va demander au ReunionRepository les LiveData.
        MaReuViewModel viewModel = new MaReuViewModel(reunionRepository);

        List<ReunionsViewStateItem> listReunionsViewStateItem = new ArrayList<>();
        listReunionsViewStateItem.add(getDefaultReunionViewStateItem(0));

        // When
        List<ReunionsViewStateItem> result = getOrAwaitValue(viewModel.getViewStateLiveData());

        assertEquals(listReunionsViewStateItem,
                result);

        String result2 = getOrAwaitValue(viewModel.getInfoFilterLiveData());

        assertEquals(
                "- 1 résultat(s) trouvé(s) -",
                result2
        );


    }

    @Test
    public void when_filterType_is_on_filterRoom_only_should_display_filteredRoomList() throws InterruptedException {

        // Given
        // On crée notre propre MutableLiveData pendant le test pour la manipuler comme on souhaite
        reunionsMutableLiveData = new MutableLiveData<>();
        filterTypeLiveData = new MutableLiveData<>();
        filterDateLiveData = new MutableLiveData<>();
        filterRoomLiveData = new MutableLiveData<>();


        //Les variables livedata sont atribuées à la place du repository
        List<Reunion> reunions = getDefaultReunions(4);
        reunionsMutableLiveData.setValue(reunions);
        filterTypeLiveData.setValue(2);
        filterDateLiveData.setValue(date);
        filterRoomLiveData.setValue("room3");

        // On construit le mock du Repository... Un Mock c'est une "coquille vide". Il fait semblant d'être un RandomRepository mais il ne
        // se passe rien dans ses méthodes et il renverra "null" s'il doit renvoyer une valeur. On peut lui dire quelle variable renvoyer
        // si quelqu'un utilise une de ses fonctions (dans notre cas MaReuViewModel)
        reunionRepository = Mockito.mock(ReunionRepository.class);

        // Pour pouvoir modifier l'objet retourné par la méthode getRandomNumberLiveData(). Maintenant, c'est notre MutableLiveData qui est
        // est donnée au MainViewModel et  pas la "vraie" MutableLiveData du RandomRepository.

        Mockito.doReturn(reunionsMutableLiveData).when(reunionRepository).getReunionsLiveData();
        Mockito.doReturn(filterTypeLiveData).when(reunionRepository).getFilterTypeLiveData();
        Mockito.doReturn(filterDateLiveData).when(reunionRepository).getFilterDateLiveData();
        Mockito.doReturn(filterRoomLiveData).when(reunionRepository).getFilterRoomLiveData();

        // On injecte notre mock dans notre MaReuViewModel pour "intervenir" lorsque
        // le MaReuViewModel va demander au ReunionRepository les LiveData.
        MaReuViewModel viewModel = new MaReuViewModel(reunionRepository);

        List<ReunionsViewStateItem> listReunionsViewStateItem = new ArrayList<>();
        listReunionsViewStateItem.add(getDefaultReunionViewStateItem(3));

        // When
        List<ReunionsViewStateItem> result = getOrAwaitValue(viewModel.getViewStateLiveData());

        assertEquals(listReunionsViewStateItem,
                result);

        String result2 = getOrAwaitValue(viewModel.getInfoFilterLiveData());

        assertEquals(
                "- 1 résultat(s) trouvé(s) -",
                result2
        );

    }

    @Test
    public void when_filterType_is_on_filterDate_and_filterRoom_should_display_filteredDateList() throws InterruptedException {

        // Given
        // On crée notre propre MutableLiveData pendant le test pour la manipuler comme on souhaite
        reunionsMutableLiveData = new MutableLiveData<>();
        filterTypeLiveData = new MutableLiveData<>();
        filterDateLiveData = new MutableLiveData<>();
        filterRoomLiveData = new MutableLiveData<>();


        //Les variables livedata sont atribuées à la place du repository
        List<Reunion> reunions = getDefaultReunions(4);
        reunionsMutableLiveData.setValue(reunions);
        filterTypeLiveData.setValue(3);
        filterDateLiveData.setValue(dateINI);
        filterRoomLiveData.setValue("room3");

        // On construit le mock du Repository... Un Mock c'est une "coquille vide". Il fait semblant d'être un RandomRepository mais il ne
        // se passe rien dans ses méthodes et il renverra "null" s'il doit renvoyer une valeur. On peut lui dire quelle variable renvoyer
        // si quelqu'un utilise une de ses fonctions (dans notre cas MaReuViewModel)
        reunionRepository = Mockito.mock(ReunionRepository.class);

        // Pour pouvoir modifier l'objet retourné par la méthode getRandomNumberLiveData(). Maintenant, c'est notre MutableLiveData qui est
        // est donnée au MainViewModel et  pas la "vraie" MutableLiveData du RandomRepository.

        Mockito.doReturn(reunionsMutableLiveData).when(reunionRepository).getReunionsLiveData();
        Mockito.doReturn(filterTypeLiveData).when(reunionRepository).getFilterTypeLiveData();
        Mockito.doReturn(filterDateLiveData).when(reunionRepository).getFilterDateLiveData();
        Mockito.doReturn(filterRoomLiveData).when(reunionRepository).getFilterRoomLiveData();

        // On injecte notre mock dans notre MaReuViewModel pour "intervenir" lorsque
        // le MaReuViewModel va demander au ReunionRepository les LiveData.
        MaReuViewModel viewModel = new MaReuViewModel(reunionRepository);

        List<ReunionsViewStateItem> listReunionsViewStateItem = new ArrayList<>();
        //listReunionsViewStateItem.add(getDefaultReunionViewStateItem(0));

        // When
        List<ReunionsViewStateItem> result = getOrAwaitValue(viewModel.getViewStateLiveData());

        assertEquals(listReunionsViewStateItem,
                result);

        String result2 = getOrAwaitValue(viewModel.getInfoFilterLiveData());

        assertEquals(
                "- 0 résultat(s) trouvé(s) -",
                result2
        );


    }


    // region IN (this is the default values that "enters" the ViewModel)
    private List<Reunion> getDefaultReunions(int count) {
        List<Reunion> reunions = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            reunions.add(getDefaultReunion(i));
        }
        return reunions;
    }

    private Reunion getDefaultReunion(long index) {

            String strDate = getIndexedDate((int)index);

        return new Reunion(
                index,
                strDate,
                "room" + index,
                "subject" + index,
                "people" + index
        );
    }
    // endregion IN


    @NonNull
    private String getIndexedDate(int index) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2008, 9, 23, 12, 01, 01);
        calendar.add(Calendar.DATE, (int)index);
        date = calendar.getTime();

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String strDate = dateFormat.format(date);

        return strDate;
    }

    @NonNull
    private ReunionsViewStateItem getDefaultReunionViewStateItem(int index) {
        return new ReunionsViewStateItem(
                index,
                "subject" + index + " - " + getIndexedDate(index) + " - " + "room" + index,
                "people"+ index
        );
    }



}//end

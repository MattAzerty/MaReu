package fr.melanoxy.mareu.ui.add;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import fr.melanoxy.mareu.LiveDataTestUtils;
import fr.melanoxy.mareu.data.ReunionRepository;
import fr.melanoxy.mareu.ui.newreu.NewReuViewModel;

@RunWith(MockitoJUnitRunner.class)
public class NewReuViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private ReunionRepository neighbourRepository;

    private NewReuViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new NewReuViewModel(neighbourRepository);
    }

    @Test
    public void add_button_state_on_start() {
        // When
        LiveDataTestUtils.observeForTesting(viewModel.getIsAddButtonEnabledLiveData(), value ->
                // Then
                assertFalse(value)
        );
    }

    @Test
    public void add_button_state_on_subject_changed() {
        // Given
        String subject = "Reunion subject";

        // When
        viewModel.onSubjectChanged(subject);
        LiveDataTestUtils.observeForTesting(viewModel.getIsAddButtonEnabledLiveData(), value ->
                // Then
                assertTrue(value)
        );
    }

    @Test
    public void ok_button_state_on_start() {
        // Given
        Integer gone = 8;

        // When
        LiveDataTestUtils.observeForTesting(viewModel.getIsOkButtonEnabledMutableLiveData(), value ->
                // Then
                assertEquals(value, gone)
        );
    }

    @Test
    public void ok_button_state_on_start_on_correct_people_changed() {
        // Given
        String people = "test@lamzone.com";
        String previousEntry = "";
        Integer visible = 0;
        // When
        viewModel.onPeopleChanged(people, previousEntry);
        LiveDataTestUtils.observeForTesting(viewModel.getIsOkButtonEnabledMutableLiveData(), value ->
                // Then
                assertEquals(value, visible)
        );
    }

    @Test
    public void ok_button_state_on_start_on_wrong_people_changed() {
        // Given
        String people = "test@wrong.com";
        String previousEntry = "";
        Integer gone = 8;
        // When
        viewModel.onPeopleChanged(people, previousEntry);
        LiveDataTestUtils.observeForTesting(viewModel.getIsOkButtonEnabledMutableLiveData(), value ->
                // Then
                assertEquals(value, gone)
        );
        viewModel.onPeopleChanged(people, previousEntry);
        LiveDataTestUtils.observeForTesting(viewModel.getErrorPeopleMutableLiveData(), value ->
                // Then
                assertEquals(value, "Erreur de syntaxe ")
        );
    }

    @Test
    public void ok_button_state_on_start_on_same_people_changed() {
        // Given
        String people = "Test@lamzone.com";
        String previousEntry = "test@lamzone.com";
        Integer gone = 8;
        // When
        viewModel.onPeopleChanged(people, previousEntry);
        LiveDataTestUtils.observeForTesting(viewModel.getIsOkButtonEnabledMutableLiveData(), value ->
                // Then
                assertEquals(value, gone)
        );
        viewModel.onPeopleChanged(people, previousEntry);
        LiveDataTestUtils.observeForTesting(viewModel.getErrorPeopleMutableLiveData(), value ->
                // Then
                assertEquals(value, "Déjà présent ")
        );
    }

    @Test
    public void add_a_new_people() {
        // Given
        String peoples = "test@lamzone.com;";
        String people = "test2@lamzone.com";

        // When
        viewModel.onPeopleAdded(peoples, people);
        LiveDataTestUtils.observeForTesting(viewModel.getPeopleLiveData(), value ->
                // Then
                assertEquals(value, "test@lamzone.com;\ntest2@lamzone.com;")
        );

    }


    @Test
    public void nominal_case_add_button_clicked() {
        // Given
        String date = "date";
        String place = "place";
        String subject = "subject";
        String people = "people";

        // When
        viewModel.onAddButtonClicked(date, place, subject, people);
        LiveDataTestUtils.observeForTesting(viewModel.getCloseActivitySingleLiveEvent(), value -> {
            // Then
            verify(neighbourRepository).addReunion(
                    eq(date),
                    eq(place),
                    eq(subject),
                    eq(people)
            );
            verifyNoMoreInteractions(neighbourRepository);
        });
    }


}//END

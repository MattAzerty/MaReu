package fr.melanoxy.mareu.data;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import fr.melanoxy.mareu.config.BuildConfigResolver;
import fr.melanoxy.mareu.LiveDataTestUtils;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class ReunionRepositoryTest {

    private static final String DEFAULT_DATE = "DEFAULT_DATE";
    private static final String DEFAULT_PLACE = "DEFAULT_PLACE";
    private static final String DEFAULT_SUBJECT = "DEFAULT_SUBJECT";
    private static final String DEFAULT_PEOPLE = "DEFAULT_PEOPLE";


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private BuildConfigResolver buildConfigResolver;

    private ReunionRepository reunionRepository;

    @Before
    public void setUp() {
        given(buildConfigResolver.isDebug()).willReturn(false);

        reunionRepository = new ReunionRepository(buildConfigResolver);

        verify(buildConfigResolver).isDebug();
        verifyNoMoreInteractions(buildConfigResolver);
    }


    @Test
    public void nominal_case_list_is_empty_for_prod() {
        // When
        LiveDataTestUtils.observeForTesting(reunionRepository.getReunionsLiveData(), value -> {
            // Then
            assertEquals(0, value.size());
        });
    }

    @Test
    public void add_two_reunions_should_change_list() {
        // When
        addDefaultReunion(0);
        addDefaultReunion(1);
        LiveDataTestUtils.observeForTesting(reunionRepository.getReunionsLiveData(), value -> {
            // Then
            assertEquals(2, value.size());
        });
    }

    @Test
    public void clear_reunions() {
        // When
        addDefaultReunion(0);
        addDefaultReunion(1);
        reunionRepository.clearReunion();

        LiveDataTestUtils.observeForTesting(reunionRepository.getReunionsLiveData(), value -> {
            // Then
            assertEquals(0, value.size());
        });

    }

    @Test
    public void delete_one_reunion_on_a_list_of_two_reunion() {
        // When
        addDefaultReunion(0);
        addDefaultReunion(1);
        reunionRepository.deleteReunion(0);
        LiveDataTestUtils.observeForTesting(reunionRepository.getReunionsLiveData(), value -> {
            // Then
            assertEquals(1, value.size());
        });
        LiveDataTestUtils.observeForTesting(reunionRepository.getReunionByIDLiveData(0), value -> {
            // Then
            assertEquals(null, value);
        });
    }

    @Test
    public void add_two_reunions_and_get_one_from_id() {
        // When
        addDefaultReunion(0);
        addDefaultReunion(1);
        LiveDataTestUtils.observeForTesting(reunionRepository.getReunionByIDLiveData(1), value -> {
            // Then
            assertEquals("DEFAULT_DATE1", value.getDate());
        });
    }

    @Test
    public void filter_reunion_by_date() {
        // When
        addDefaultReunion(0);
        addDefaultReunion(1);

    }




    private void addDefaultReunion(long index) {

        String strIndex = Long.toString(index);

        reunionRepository.addReunion(DEFAULT_DATE+strIndex, DEFAULT_PLACE+strIndex, DEFAULT_SUBJECT+strIndex, DEFAULT_PEOPLE+strIndex);
    }

/*
    private List<Reunion> getDefaultReunionList(int count) {
        List<Reunion> reunions = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            reunions.add(getDefaultReunion(i));
        }
        return reunions;
    }

    private Reunion getDefaultReunion(long index) {

        return new Reunion(
                index,
                DEFAULT_DATE,
                DEFAULT_PLACE,
                DEFAULT_SUBJECT,
                DEFAULT_PEOPLE
        );
    }
*/

}//end


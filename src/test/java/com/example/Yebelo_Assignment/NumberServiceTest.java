package com.example.Yebelo_Assignment;

import com.example.Yebelo_Assignment.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NumberServiceTest {
    @Mock
    private NumberRepository numberSequenceRepository;

    @InjectMocks
    private NumberService numberService;

    public NumberServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchNextNumber_ExistingNumber() {

        NumberSequence numberSequence = new NumberSequence("category", 10);
        when(numberSequenceRepository.findByCategoryCode("category")).thenReturn(Optional.of(numberSequence));

        NumberResponse response = numberService.fetchNextNumber(new CategoryRequest("category"));

        verify(numberSequenceRepository, times(1)).findByCategoryCode("category");
        verify(numberSequenceRepository, times(1)).save(numberSequence);

        assertEquals(10, response.getOldValue());
        assertEquals(19, response.getNewValue());
    }

    @Test
    void testFetchNextNumber_NoNumberFound() {

        when(numberSequenceRepository.findByCategoryCode("category")).thenReturn(Optional.empty());

        NumberResponse response = numberService.fetchNextNumber(new CategoryRequest("category"));

        verify(numberSequenceRepository, times(1)).findByCategoryCode("category");
        verify(numberSequenceRepository, never()).save(any());

        assertEquals(0, response.getOldValue());
        assertEquals(1, response.getNewValue());
    }

    @Test
    void testFetchNextNumber_NegativeNumber() {

        NumberSequence numberSequence = new NumberSequence("category", -5);
        when(numberSequenceRepository.findByCategoryCode("category")).thenReturn(Optional.of(numberSequence));

        NumberResponse response = numberService.fetchNextNumber(new CategoryRequest("category"));

        verify(numberSequenceRepository, times(1)).findByCategoryCode("category");
        verify(numberSequenceRepository, times(1)).save(numberSequence);

        assertEquals(-5, response.getOldValue());
        assertEquals(6, response.getNewValue());
    }

    @Test
    void testFetchNextNumber_ZeroSumOfDigits() {
        NumberSequence numberSequence = new NumberSequence("category", 9);
        when(numberSequenceRepository.findByCategoryCode("category")).thenReturn(Optional.of(numberSequence));

        NumberResponse response = numberService.fetchNextNumber(new CategoryRequest("category"));

        verify(numberSequenceRepository, times(1)).findByCategoryCode("category");
        verify(numberSequenceRepository, times(1)).save(numberSequence);

        assertEquals(9, response.getOldValue());
        assertEquals(19, response.getNewValue());
    }

    @Test
    void testFetchNextNumber_LongProcessingTime() {
        // Mock the repository behavior
        NumberSequence numberSequence = new NumberSequence("category", 10);
        when(numberSequenceRepository.findByCategoryCode("category")).thenReturn(Optional.of(numberSequence));

        long startTime = System.currentTimeMillis();
        NumberResponse response = numberService.fetchNextNumber(new CategoryRequest("category"));
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        verify(numberSequenceRepository, times(1)).findByCategoryCode("category");
        verify(numberSequenceRepository, times(1)).save(numberSequence);

        assertEquals(10, response.getOldValue());
        assertEquals(19, response.getNewValue());
        assertTrue(elapsedTime >= 5000, "Processing time should be at least 5 seconds");
    }
}

package com.example.Yebelo_Assignment;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class NumberService {

    @Autowired
    NumberRepository numberRepository;

    @Transactional
    public NumberResponse fetchNextNumber(CategoryRequest categoryRequest){
        String categoryCode = categoryRequest.getCategoryCode();

        NumberSequence numberSequence = numberRepository.findByCategoryCode(categoryCode).orElseGet(() -> new NumberSequence(categoryCode, 0));

        int oldValue = numberSequence.getValue();
        int newValue = generateNextNumber(oldValue);  //this function will generate the next number

        //saving the new value to table
        numberSequence.setValue(newValue);
        numberRepository.save(numberSequence);

        //Introduced delay of 5 seconds
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new NumberResponse(oldValue, newValue);
    }
    private int generateNextNumber(int value) {
        value++;

        int digitSum = calculateDigitSum(value);
        while (digitSum != 1 && digitSum != 10) {
            value++; // Increment until the sum of digits becomes 1
            digitSum = calculateDigitSum(value);
        }

        return value;
    }

    private int calculateDigitSum(int number) {
        int sum = 0;
        while (number != 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }
}

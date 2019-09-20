package com.healthycoderapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class BMICalculatorTest {

    private String environment_variable = "prod";

    @BeforeAll
    static void beforeAllMethod()
    {
        // this will be generally be used for initializing expensive operations
        // like starting a server or a database connection
        System.out.println("Before all Unit test");
    }

    @AfterAll
    static void afterAllMethod()
    {
        // this is exactly the opposite of before all
        // its used for closing operations, eg . closing a DB connection or server
        System.out.println("After all unit test");
    }

   @Nested
   class calculateBMIScore {
       @Test
       void should_return_coder_with_worstBMI_whenCoderListNotEmpty() {
           // given
           List<Coder> coders = new ArrayList<>();
           coders.add(new Coder(1.80, 60.0));
           coders.add(new Coder(1.82, 90.0));
           coders.add(new Coder(1.82, 65.0));
           // when
           Coder coderWithWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
           // then
           // assertAll should be used for assertions that make sense as a whole
           assertAll(
                   () -> assertEquals(1.82, coderWithWorstBMI.getHeight()),
                   () -> assertEquals(90.0, coderWithWorstBMI.getWeight())
           );
       }

       @Test
       void should_returnNull_when_the_coderListIsEmpty() {
           // given an empty array list
           List<Coder> coders = new ArrayList<>();
           // when
           Coder coderWithWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
           //then
           assertNull(coderWithWorstBMI);
       }

       @Test
       void should_ReturnCorrectBMI_ScoreArray_whenCoderList_notEmpty() {
           // given
           List<Coder> coders = new ArrayList<>();
           coders.add(new Coder(1.80, 60.0));
           coders.add(new Coder(1.82, 90.0));
           coders.add(new Coder(1.82, 65.0));
           double[] expected = {18.52, 27.17, 19.62};
           // when
           double[] bmiScoreArray = BMICalculator.getBMIScores(coders);
           // then
           // If we use assertEquals for array comparison it will fail, since the two array objects have different memory locations
           // We need to compare and check if every individual array items are equal, hence using assertArrayEquals
           assertArrayEquals(bmiScoreArray, expected);
       }
   }

   @Nested
   class returnCoderWithWorstBMI {
       @Test
       @DisplayName(">> some sample display name for a unit test")
       // used to skip a test
       //@Disabled
       // Disable on a specific OS
       @DisabledOnOs(OS.WINDOWS)
       void should_return_coderWithWorstBMI_in1Ms_whenCoderListHas10000Elements() {
           // consider a situation where you want to execute the test on a particular environment
           // assumeTrue will skip the test if it fails
           assumeTrue(BMICalculatorTest.this.environment_variable.equals("prod"));
           // given
           List<Coder> coders = new ArrayList<>();
           for (int i = 0; i < 10000; i++) {
               coders.add(new Coder(1.0 + i, 10.0 + i));
           }
           //then
           Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);
           //when
           assertTimeout(Duration.ofMillis(500), executable);

       }
   }

}
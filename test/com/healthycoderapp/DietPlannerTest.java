package com.healthycoderapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class DietPlannerTest {

    private DietPlanner dietPlanner;

    // this function will be invoked before each unit test
    @BeforeEach
    void setUp()
    {
       this.dietPlanner = new DietPlanner(20,30,50);
    }

    // this will be invoked after executing each unit test
    @AfterEach
    void afterEach()
    {
     System.out.println("A unit test was finished.");
    }

    // organizing unit test with help of @Nested annotation
    @Nested
    class dietRecommendedClass
    {
        @ParameterizedTest(name = "weight={0},height={1}")
        // @ValueSource(doubles = {89.0,95.0,110})
        // @CsvSource(value = {"89.0,1.72","95.0,1.75","110.0,1.78"})
        @CsvFileSource(resources = "diet-input-data.csv",numLinesToSkip = 1)
        void should_return_true_when_diet_recommended(Double coderWeight,Double coderHeight) {
            // given (arrange)
            double weight = coderWeight;
            double height = coderHeight;
            // when  (act) here we invoke the method on the test and save its result in a variable
            boolean recommended = BMICalculator.isDietRecommended(weight,height);
            // then  (assert)
            assertTrue(recommended);
        }

        @Test
        void should_return_false_when_diet_not_recommended()
        {
            // given (arrange)
            double weight = 45.0;
            double height = 1.72;
            // when  (act) here we invoke the method on the test and save its result in a variable
            boolean recommended = BMICalculator.isDietRecommended(weight,height);
            // then  (assert)
            assertFalse(recommended);
        }

        @Test
        void should_throw_arithmetic_expression_when_height_is_zero()
        {
            // given (arrange)
            double weight = 45.0;
            double height = 0.0;
            // when  (act) here we invoke the method on the test and save its result in a variable
            Executable executable = () -> BMICalculator.isDietRecommended(weight,height);
            // then  (assert)
            assertThrows(ArithmeticException.class,executable);
        }
    }


    @Nested
    class calculateDiet {
        //@RepeatedTest(2)
        @Test
        void should_return_correct_diet_plan_when_correct_coder() {
            // given
            Coder givenCoder = new Coder(1.82, 75.0, 26, Gender.MALE);
            DietPlan expected = new DietPlan(2202, 110, 73, 275);
            //when
            DietPlan recommendedDietPlan = dietPlanner.calculateDiet(givenCoder);
            // then
            assertAll(() -> assertEquals(expected.getCalories(), recommendedDietPlan.getCalories()),
                    () -> assertEquals(expected.getProtein(), recommendedDietPlan.getProtein()),
                    () -> assertEquals(expected.getFat(), recommendedDietPlan.getFat()),
                    () -> assertEquals(expected.getCarbohydrate(), recommendedDietPlan.getCarbohydrate()));
        }
    }

}
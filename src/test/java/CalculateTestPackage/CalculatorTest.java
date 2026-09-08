package CalculateTestPackage;

import org.example.Calculator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import java.util.List;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeMethod
    public void setUp() {
        System.out.println("Calculator created");
        calculator = new Calculator();
    }

    @AfterMethod
    public void finishTest() {
        calculator = null;
        System.out.println("Test finish\n");
    }

    @DataProvider(name = "sumData")
    public Object[][] sumData() {
        return new Object[][]{
                {12, 8, 20},
                {45, 12, 57},
                {2, 6, 8},
                {100, 200, 300}
        };
    }

    @Test(dataProvider = "sumData")
    public void severalSumShouldReturnCorrectResult(int a, int b, int expected) {
        System.out.println("Test 1 started...");

        int result = calculator.sum(a, b);

        Assert.assertEquals(result, expected);
    }

    @DataProvider(name = "multiplyData")
    public Object[][] multiplyData() {
        return new Object[][]{
                ///one with negative result for checking fail
                {12, 2, 25},
                {5, 2, 10},
                {-2, 3, -6},
                {0, 65, 0}
        };
    }

    @Test(dataProvider = "multiplyData")
    public void multiplyShouldReturnCorrectResult(int e, int g, int expected) {
        System.out.println("Test 2 started...");

        int result = calculator.multiply(e, g);

        Assert.assertEquals(result, expected);
    }

    @Test (groups = {"smoke"})
    public void singleSumReturnCorrectResult(){
        System.out.println("Test 3 started...");
        int result = calculator.sum(6, 4);
        Assert.assertEquals(result,10);

    }
    @Test (groups = {"regression"})
    public void sumShouldReturnZero() {
        System.out.println("Test 4 started...");
        int result = calculator.sum(-5,5);
        Assert.assertEquals(result,0);

    }

    @Test(groups = {"smoke"})
    public void subtractShouldReturnCorrectResult() {
        System.out.println("Test 5 started...");

        int result = calculator.subtract(13,5);

        Assert.assertEquals(result, 8);
    }

    @Test(groups = {"smoke"})
    public void divideShouldReturnCorrectResult() {
        System.out.println("Test 6 started...");

        int result = calculator.divide(39,3);

        Assert.assertEquals(result, 13);
    }



    @Test(groups = {"collection"})
    public void resultsShouldBeStoredInCollection() {
        System.out.println("Collection Test started...");

        List<Integer> results = List.of(
                calculator.sum(1, 2),
                calculator.sum(3, 4),
                calculator.sum(5, 5)
        );

        Assert.assertEquals(results.size(), 3);
        Assert.assertTrue(results.contains(3));
        Assert.assertTrue(results.contains(7));
        Assert.assertTrue(results.contains(10));
    }
}

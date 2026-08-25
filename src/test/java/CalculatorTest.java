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

    @Test (groups = {"smoke"})
    public void sumNeedToBeCorrect(){
        System.out.println("Test 1 started...");
        int result = calculator.sum(6, 4);
        Assert.assertEquals(result,10);

    }
    @Test (groups = {"regression"})
    public void sumShouldReturnZero() {
        System.out.println("Test 2 started...");
        int result = calculator.sum(-5,5);
        Assert.assertEquals(result,0);

    }

    @Test(groups = {"smoke"})
    public void subtractShouldReturnCorrectResult() {

        int result = calculator.subtract(10, 3);

        Assert.assertEquals(result, 7);
    }




    @Test(groups = {"collection"})
    public void resultsShouldBeStoredInCollection() {

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

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class Testing {

    @Test
    @DisplayName("STUDENT TEST - Case #1")
    public void firstCaseTest() {
        double budget = 500;
        
        List<Region> sites = new ArrayList<>();
        List<Region> actualOutput = new ArrayList<>();

        sites.add(new Region("Region #1", 100, 400));
        sites.add(new Region("Region #2", 150, 600));
        
        actualOutput.add(new Region("Region #1", 100, 400));
        assertEquals(actualOutput, Client.allocateRelief(budget, sites).getRegions());
    }

    @Test
    @DisplayName("STUDENT TEST - Case #2")
    public void secondCaseTest() {
        double budget = 500;
        
        List<Region> sites = new ArrayList<>();
        List<Region> actualOutput = new ArrayList<>();
        
        sites.add(new Region("Region #1", 150, 400));
        sites.add(new Region("Region #2", 100, 500));
        actualOutput.add(new Region("Region #1", 150, 400));
        assertEquals(actualOutput, Client.allocateRelief(budget, sites).getRegions());
    }

    @Test
    @DisplayName("STUDENT TEST - Case #3")
    public void thirdCaseTest() {
        double budget = 500;
        
        List<Region> sites = new ArrayList<>();
        List<Region> actualOutput = new ArrayList<>();
        
        sites.add(new Region("Region #1", 150, 450));
        sites.add(new Region("Region #2", 150, 400));
        actualOutput.add(new Region("Region #2", 150, 400));
        assertEquals(actualOutput, Client.allocateRelief(budget, sites).getRegions());
    }
}

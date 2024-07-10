import java.util.*;
//Noah Zhao
//CSE 123
//5/14/23
//P2: Disaster Relief
//TA: Daniel Welicki

//this class creates a client that runs through scenarios of regions in need of relief, 
//picking the best region to help based on # of people saved. The orders of such allocations
//changes the program as the cost increases the later the region is helped.
public class Client {
    private static Random rand = new Random();

    public static void main(String[] args) throws Exception {
        // List<Region> scenario = createRandomScenario(10, 10, 100, 1000, 100000);
        List<Region> scenario = createSimpleScenario();
        System.out.println(scenario);

        double budget = 2000;
        Allocation allocation = allocateRelief(budget, scenario);
        printResult(allocation, budget);
    }

    //Checks through a list of regions to see what allocation of regions helped
    //helps the most people within the provided budget. If two allocations help the same amount
    //of people, the allocation that uses less money will be chosen.
    //Parameters:
    // - budget: budget cost that the allocation must stay within 
    // - sites: list of sites that could be potentially helped in each allocation
    //Returns:
    // - Allocation: an ordered list of the most efficient allocation of regions
    // (most people helped)
    public static Allocation allocateRelief(double budget, List<Region> sites) {
        
        Set<Allocation> generated = generateOptions(budget, sites);
        Allocation max = new Allocation();
        int maxPop = 0;
        for (Allocation a : generated) {
            if (a.totalPeople() > maxPop) {
                maxPop = a.totalPeople();
                max = a;
            }

            if (a.totalPeople() == maxPop) {
                if (a.totalCost() < max.totalCost()) {
                    max = a;
                }
            }
        }
        return max;
    }

    //generates a list of all potential allocations of orders of regions helped, that stays within
    //the provided budget
    //Parameters:
    // - budget: budget cost that the allocation must stay within 
    // - sites: list of sites that could be potentially helped in each allocation
    //Returns:
    // - Set<Allocation>: a list of all potential allocations that stay within the provided budget
    private static Set<Allocation> generateOptions(double budget, List<Region> sites) {

        // loop through each allocation?
        Set<Allocation> options = new HashSet<>();
        generateAllocation(options, new Allocation(), null, sites, budget, 0);
        return options;
    }

    //helper method for generateOptions that generates possible allocations and fills a set
    //with them.
    private static void generateAllocation(Set<Allocation> generated, Allocation a, Region last, 
            List<Region> sites, double budget, int index) {

        // singular case
        if (sites.size() == 1 && (budget - sites.get(0).getCost(0)) >= 0) {
            generated.add(a.withRegion(sites.get(0)));
        }
        // going negative case
        if (budget < 0) {
            generated.add(a.withoutRegion(last));
        } else {
            // budget not negative yet!
            for (int i = 0; i < sites.size(); i++) {
                if (!(a.getRegions().contains(sites.get(i)))) {
                    Region curr = sites.get(i);
                    generateAllocation(generated, a.withRegion(curr), curr, sites, 
                            (budget - curr.getCost(index)), index + 1);
                    generated.add(a);
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // PROVIDED HELPER METHODS - **DO NOT MODIFY ANYTHING BELOW THIS LINE!** //
    ///////////////////////////////////////////////////////////////////////////

    public static void printAllocations(Set<Allocation> allocations) {
        System.out.println("All Allocations:");
        for (Allocation a : allocations) {
            System.out.println("  " + a);
        }
    }
    
    //private method to
    public static void printResult(Allocation alloc, double budget) {
        System.out.println("Result: ");
        System.out.println("  " + alloc);
        System.out.println("  People helped: " + alloc.totalPeople());
        System.out.printf("  Cost: $%.2f\n", alloc.totalCost());
        System.out.printf("  Unused budget: $%.2f\n", (budget - alloc.totalCost()));
    }

    public static List<Region> createRandomScenario(int numLocs, int minPop, int maxPop,
            double minCostPer, double maxCostPer) {
        List<Region> result = new ArrayList<>();

        for (int i = 0; i < numLocs; i++) {
            int pop = rand.nextInt(minPop, maxPop + 1);
            double cost = rand.nextDouble(minCostPer, maxCostPer) * pop;
            result.add(new Region("Region #" + i, pop, round2(cost)));
        }

        return result;
    }

    public static List<Region> createSimpleScenario() {
        List<Region> result = new ArrayList<>();

        result.add(new Region("Region #1", 50, 500));
        result.add(new Region("Region #2", 100, 700));
        result.add(new Region("Region #3", 60, 1000));
        result.add(new Region("Region #4", 20, 1000));
        result.add(new Region("Region #5", 200, 900));

        return result;
    }

    private static double round2(double num) {
        return Math.round(num * 100) / 100.0;
    }
}

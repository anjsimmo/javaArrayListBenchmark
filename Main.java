import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

class Main {
    public static ArrayList<String> genData() {
        /* Generate sample data */
        ArrayList<String> test = new ArrayList<String>();
        for (int i = 0; i < 100000; i++) {
            test.add("line " + i);
        }
        test.add("duplicate line");
        test.add("duplicate line");
        test.add("duplicate line");

        return test;
    }

    public static ArrayList<String> genEvens() {
        /* Generate expected result (evens) */
        ArrayList<String> test = new ArrayList<String>();
        // keep even, throwaway odd
        for (int i = 0; i < 100000; i+=2) {
            test.add("line " + i);
        }
        test.add("duplicate line");
        //test.add("duplicate line"); // Skip (odd)
        test.add("duplicate line");

        return test;
    }

    public static ArrayList<String> genOdds() {
        /* Generate expected result (odds) */
        ArrayList<String> test = new ArrayList<String>();
        // throw away odd, keep even
        for (int i = 1; i < 100000; i+=2) {
            test.add("line " + i);
        }
        //test.add("duplicate line"); // Skip (even)
        test.add("duplicate line");
        //test.add("duplicate line"); // Skip (even)

        return test;
    }

    public static ArrayList<String> genOddsNoDup() {
        /* Generate expected result (odds, no duplicates) */
        ArrayList<String> test = new ArrayList<String>();
        // throw away odd, keep even
        for (int i = 1; i < 100000; i+=2) {
            test.add("line " + i);
        }
        //test.add("duplicate line"); // Skip (even)
        //test.add("duplicate line"); // Skip (should keep, but is identical to even element!)
        //test.add("duplicate line"); // Skip (even)

        return test;
    }

    public static void timeOp(Consumer<ArrayList<String>> methodToExecute, ArrayList<String> input, String description) {
        // https://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java
        long startTime = System.nanoTime();
        methodToExecute.accept(input);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; //divide by 1000000 to get milliseconds.
        System.out.println(description + " took " + duration + " milliseconds");
    }

    public static void assertEquivalent(ArrayList<String> expected, ArrayList<String> actual, String description) {
        if (!expected.equals(actual)) {
            throw new AssertionError(description + " did not behave as expected");
        }
    }

    public static void main(String[] args) {
        ArrayList<String> expectedEvens = genEvens();
        ArrayList<String> expectedOdds = genOdds();
        ArrayList<String> expectedOddsNoDup = genOddsNoDup();

        ArrayList<String> data1 = genData();
        timeOp(Main::removeUsingRemoveAll, data1, "removeUsingRemoveAll");
        assertEquivalent(expectedOddsNoDup, data1, "removeUsingRemoveAll"); // will remove ALL duplicates

        ArrayList<String> data2 = genData();
        timeOp(Main::removeUsingIter, data2, "removeUsingIter");
        assertEquivalent(expectedOdds, data2, "removeUsingIter");

        ArrayList<String> data3 = genData();
        timeOp(Main::removeFromEnd, data3, "removeFromEnd");
        assertEquivalent(expectedEvens, data3, "removeFromEnd"); // removes odd elements, so left with even

        ArrayList<String> data4 = genData();
        timeOp(Main::removeUsingIterLinked, data4, "removeUsingIterLinked");
        assertEquivalent(expectedOdds, data4, "removeUsingIterLinked");

        ArrayList<String> data5 = genData();
        timeOp(Main::removeUsingSecondList, data5, "removeUsingSecondList");
        assertEquivalent(expectedOdds, data5, "removeUsingSecondList");
    }


    public static void removeUsingRemoveAll(ArrayList<String> list) {
        // https://stackoverflow.com/questions/61845242/removing-every-other-element-in-an-array-list/61845346#61845346
        // Credit: https://stackoverflow.com/users/4459069/sleeptoken
        ArrayList<String> toRemove = new ArrayList<String>();
        int count = 2;

        for (int i = 0; i < list.size(); i++) {
            if (i % count == 0) {
                toRemove.add(list.get(i));
            }
        }

        list.removeAll(toRemove);
    }

    public static void removeUsingIter(ArrayList<String> list) {
        // https://stackoverflow.com/questions/61845242/removing-every-other-element-in-an-array-list/61845315#61845315
        // Credit: https://stackoverflow.com/users/10819573/arvind-kumar-avinash
        Iterator<String> itr = list.iterator();
        int i = 0;
        while(itr.hasNext()) {
            itr.next();
            if(i % 2 == 0) {
                itr.remove();
            }
            i++;
        }
    }

    public static void removeFromEnd(ArrayList<String> list) {
        // https://stackoverflow.com/questions/61845242/removing-every-other-element-in-an-array-list/61846039#61846039
        // Credit: https://stackoverflow.com/users/1552534/wjs
        for(int i = (list.size()&~1)-1; i>=0; i-=2) {
            list.remove(i);
        }
    }

    public static void removeUsingIterLinked(ArrayList<String> list) {
        // Adapted from removeUsingIter
        LinkedList<String> linked = new LinkedList<String>(list); // convert to a LinkedList
        Iterator<String> itr = linked.iterator();
        int i = 0;
        while(itr.hasNext()) {
            itr.next();
            if (i % 2 == 0) {
                itr.remove();
            }
            i++;
        }
        // overwrite the ArrayList with the contents of the LinkedList
        list.clear();
        list.addAll(linked);
    }

    public static void removeUsingSecondList(ArrayList<String> list) {
        // Adapted from removeUsingRemoveAll
        ArrayList<String> newList = new ArrayList<String>();
        int count = 2;

        for (int i = 0; i < list.size(); i++) {
            if (i % count != 0) {
                newList.add(list.get(i));
            }
        }

        list.clear();
        list.addAll(newList);
    }
}

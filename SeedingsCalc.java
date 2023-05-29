import java.util.*;
import java.io.*;

public class SeedingsCalc {
    public static void main(String[] args) throws Exception {
        System.out.print("Enter the team you want to find opponents for: ");
        Scanner input = new Scanner(System.in);
        int teamSeed = input.nextInt();
        System.out.println();
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Brackets for the previous round were: ");

        // create hashmaps
        TreeMap<Integer, String> upperBracket = new TreeMap<>();
        TreeMap<Integer, String> lowerBracket = new TreeMap<>();

        /** read upper bracket and put into UB TreeMap */
        File UpperBracket = new File("C://Users//plwdr//Documents//Java//src//UpperBracket.txt");
        Scanner UpperBracketReader = new Scanner(UpperBracket);

        int parity = 0; int seed = 0; String str;
        while (UpperBracketReader.hasNext()) {
            if (parity % 2 == 0) {
                seed = UpperBracketReader.nextInt();
            }
            else {
                str = UpperBracketReader.next();
                upperBracket.put(seed, str);
            }
            parity++;
        }

        print(upperBracket);

        /** read lowerBracket and put into TreeMap */
        File LowerBracket = new File("C://Users//plwdr//Documents//Java//src//LowerBracket.txt");
        Scanner LowerBracketReader = new Scanner(LowerBracket);

        parity = 0; seed = 0;
        while (LowerBracketReader.hasNext()) {
            if (parity % 2 == 0) {
                seed = LowerBracketReader.nextInt();
            }
            else {
                str = LowerBracketReader.next();
                lowerBracket.put(seed, str);
            }
            parity++;
        }

        print(lowerBracket);

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Teams for the next round sorted in order of seeds are: ");
        System.out.println();

        /** MOVE UPPER LOSERS TO LOWERS AND DELETE LOWER LOSERS */
        // move 4 teams
        int[] upperDrops = new int[]{3};
        for (int i = 0; i < upperDrops.length; i++) {
            moveTeam(upperBracket, lowerBracket, upperDrops[i]);
        }
        int[] lowerDrops = new int[]{4, 8, 24};
        for (int i = 0; i < lowerDrops.length; i++) {
            lowerBracket.remove(lowerDrops[i]);
        }

        print(upperBracket);
        print(lowerBracket);

        /** SEARCH THE LOWER BRACKET FOR THE PAIRING*/
        int count = 0;
        boolean encountered = false;
        for (Map.Entry<Integer, String> n : lowerBracket.entrySet()) {
            if (n.getKey() == teamSeed) {
                encountered = true;
            }
            if (encountered) {
                count++;
            }
        }
        int index = 1;
        for (Map.Entry<Integer, String> n : lowerBracket.entrySet()) {
            if (index == count) {
                System.out.println("--------------------------------------------------------------------------");
                System.out.println(n.getKey() + " " + n.getValue() + " is the pairing");
                System.out.println("--------------------------------------------------------------------------");
                break;
            }
            index++;
        }

        /** SEARCH THE UPPER BRACKET BRACKET FOR THE PAIRING*/
        count = 0;
        encountered = false;
        for (Map.Entry<Integer, String> n : upperBracket.entrySet()) {
            if (n.getKey() == teamSeed) {
                encountered = true;
            }
            if (encountered) {
                count++;
            }
        }
        index = 1;
        for (Map.Entry<Integer, String> n : upperBracket.entrySet()) {
            if (index == count) {
                System.out.println("--------------------------------------------------------------------------");
                System.out.println(n.getKey() + " " + n.getValue() + " is the pairing");
                System.out.println("--------------------------------------------------------------------------");
                break;
            }
            index++;
        }
    }

    public static void print(TreeMap<Integer, String> map) {
        for (Map.Entry<Integer, String> n : map.entrySet()) {
            System.out.println(n.getKey() + " " + n.getValue());
        }
        System.out.println();
    }

    public static void moveTeam(TreeMap<Integer, String> uppers, TreeMap<Integer, String> lowers, int seed) {
        String team = uppers.remove(seed);
        lowers.put(seed, team);
    }
}



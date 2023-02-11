import java.io.*;
import java.util.*;

public class Sorter {
    static Scanner scan = new Scanner(System.in);
    static Random rand = new Random();
    //dla main oraz init
    static int[] a;
    static int w;
    static int n;
    //dla setDebug
    static String algorithmName;
    static int nLiczb;
    static long nParse = 0, nSave = 0;
    static long setTime, startTime, endTime, sortTime, startSortTime, endSortTime;

    static void createFile(int n) throws IOException {
        try (FileWriter out = new FileWriter("test.txt", false)) {
            for (int i = 0; i < n; i++) {
                String str = String.valueOf(rand.nextInt(99) + 1);
                out.write(str);
                out.append('\n');
                out.flush();
            }
        }
    }

    static int[] fileToArray(String f) throws IOException {
        int size = 0;
        try (BufferedReader in = new BufferedReader(new FileReader(f))) {
            String line = in.readLine();
            while(line != null) {
                size++;
                line = in.readLine();
            }
        }
        int[] a = new int[size];
        int i = 0;
        try (BufferedReader in = new BufferedReader(new FileReader(f))) {
            String line = in.readLine();
            while(line != null) {
                a[i] = Integer.parseInt(line);
                line = in.readLine();
                i++;
            }
        }
        return a;
    }

    public static void saveAs(String fileName) throws IOException {
        try (FileWriter out = new FileWriter(fileName, false)) {
            for (int i = 0; i < a.length; i++) {
                String str = String.valueOf(a[i]);
                out.write(str);
                out.append('\n');
                out.flush();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        startTime = System.currentTimeMillis();
        System.out.println("Wpisz nazwe pliku lub ilosc liczb:");
        String sciezka = scan.nextLine();
        try {
            n = Integer.parseInt(sciezka);
            createFile(n);
            init("test.txt");
        }
        catch (NumberFormatException e) {
            init(sciezka);
        }
        endTime = System.currentTimeMillis();
        setDebug(true);
    }

    static void init(String s) throws IOException {
        a = fileToArray(s);
        System.out.println("W jaki sposob sortowac?\n1. Bubble sort\n2. Bubble sort with guard\n3. Insection sort\n4. Selection sort");
        w = scan.nextInt();
        switch (w) {
            case 1:
                doBubbleSort(a);
                break;
            case 2:
                doBubbleSortG(a);
                break;
            case 3:
                doInsectionSort(a);
                break;
            case 4:
                doSelectionSort(a);
                break;
            default:
                System.out.println("Niepoprawna operacja\nSortowania nie bedzie");
                break;
        }
        System.out.println("Wpisz nazwe pliku do zapisania posortowanych liczb:");
        String zapis = scan.next();
        saveAs(zapis);
    }

    static void doBubbleSort(int[] a) {
        startSortTime = System.currentTimeMillis();
        n = a.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                nParse++;
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    nSave++;
                }
            }
        }
        endSortTime = System.currentTimeMillis();
    }

    static void doBubbleSortG(int[] a) {
        startSortTime = System.currentTimeMillis();
        n = a.length;
        boolean guard = true;
        for (int i = 0; i < n - 1; i++) {
            guard = false;
            for (int j = 0; j < n - 1; j++) {
                nParse++;
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    guard = true;
                    nSave++;
                }
            }
        }
        endSortTime = System.currentTimeMillis();
    }

    static void doInsectionSort(int[] a) {
        startSortTime = System.currentTimeMillis();
        n = a.length;
        for (int i = 0; i < n; ++i) {
            nParse++;
            int temp = a[i];
            int j = i - 1;
            while (j >= 0 && a[j] > temp) {
                a[j + 1] = a[j];
                j = j - 1;
                nSave++;
            }
            a[j + 1] = temp;
        }
        endSortTime = System.currentTimeMillis();
    }

    static void doSelectionSort(int[] a) {
        startSortTime = System.currentTimeMillis();
        n = a.length;
        for (int i = 0; i < n - 1; i++) {
            nParse++;
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (a[j] < a[min]) {
                    min = j;
                    nSave++;
                }
            }
            int temp = a[min];
            a[min] = a[i];
            a[i] = temp;
        }
        endSortTime = System.currentTimeMillis();
    }

    public static void setDebug(boolean b) {
        switch (w) {
            case 1:
                algorithmName = "BubbleSort";
                break;
            case 2:
                algorithmName = "BubbleSortG";
                break;
            case 3:
                algorithmName = "InsectionSort";
                break;
            case 4:
                algorithmName = "SelectionSort";
                break;
            default:
                algorithmName = "NULL";
                break;
        }
        nLiczb = n;
        setTime = endTime - startTime;
        sortTime = endSortTime - startSortTime;
        System.out.println("----------------------------------------------------------");
        System.out.println("Nazwa algorytmu: " + algorithmName);
        System.out.println("Ilosc sortowanych liczb: " + nLiczb);
        System.out.println("Czas dzialania programu: " + setTime + " ms");
        System.out.println("Czas sortowania: " + sortTime + " ms");
        System.out.println("Liczba porownywan: " + nParse);
        System.out.println("Liczba przypisan: " + nSave);
    }
}

import java.io.*;
import java.util.Scanner;

/**
 * Created by ehsan on 6/17/2018.
 */
public class System {

    private Graph myGraph;

    public void start() {

        int n = numnodes();
        myGraph = new Graph(n);
        java.lang.System.out.print("dfsfs");
        init();
        int count = 0;

       // java.lang.System.out.println(myGraph);
        boolean b = true;
        while(b && count < 7 ) {
            Graph.numChange = 0;
            myGraph.propagation();
            myGraph.inflation(3);
            myGraph.cutoff(0.1);
           // java.lang.System.out.println(myGraph);
            java.lang.System.out.println(count);
            myGraph.update(0.7);
            b = !myGraph.stop();
            count++;
        }
         myGraph.setResult();
        Test test = new Test();
        float NMI = 0;
        try {
            NMI = test.NMI(myGraph.result , "C:\\Users\\ehsan\\Desktop\\N1000MU.5\\community.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.lang.System.out.print(NMI);
        java.lang.System.out.println(myGraph);

    }


    public void init() {

        File myfile = new File("C:\\Users\\ehsan\\Desktop\\N1000MU.5\\network.txt");
        try {
            Scanner scanner = new Scanner(myfile);
            while(scanner.hasNextLine()) {
                int source  = scanner.nextInt();
                int destination = scanner.nextInt();
                myGraph.addedge(source , destination);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        myGraph.intializeDist();

    }


    public int numnodes() {
        File myfile = new File("C:\\Users\\ehsan\\Desktop\\N1000MU.5\\network.txt");
        int n = 0;
        int m = 0;
        try {
            Scanner scanner = new Scanner(myfile);
            n = scanner.nextInt();
            m = scanner.nextInt();
            while(scanner.hasNextLine()) {
                n = scanner.nextInt();
                m = scanner.nextInt();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return n;
    }


}

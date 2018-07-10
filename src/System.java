import java.io.*;
import java.util.Scanner;

/**
 * Created by ehsan on 6/17/2018.
 */
public class System {

    private Graph myGraph;

    public void start() {

        float NMI = 0;
        double time = 0;

        int nn = 0;
        while (nn < 100) {

            int n = numnodes();
            myGraph = new Graph(n);
            init();
            int count = 0;
            double aa = 0, bb = 0;

            // java.lang.System.out.println(myGraph);
            boolean b = true;
            while (b && count < 7) {
                aa = java.lang.System.currentTimeMillis();
                Graph.numChange = 0;
                myGraph.propagation();
            myGraph.inflation(3);
            myGraph.cutoff(0.1);
            myGraph.update(0.7);
                b = !myGraph.stop();
                count++;
                bb = java.lang.System.currentTimeMillis();
            }
            myGraph.setResult();
            time += (bb - aa);
            Test test = new Test();
            try {
                NMI = test.NMI(myGraph.result, "/home/ehsan/Desktop/T/TestCase14-N100000-k20-mu50/community.txt");
            } catch (Exception e) {
                e.printStackTrace();
            }
            nn++;
            java.lang.System.out.println("time is calculated " + time);
        }
        java.lang.System.out.println("n is " + nn);
        java.lang.System.out.println("NMI : " + NMI);
        java.lang.System.out.println("Time : " + (time / nn));
//        java.lang.System.out.println(myGraph);

    }


    public void init() {

        File myfile = new File("/home/ehsan/Desktop/T/TestCase14-N100000-k20-mu50/network.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(myfile));
            String line = bufferedReader.readLine();
            while(line != null) {
                String[] nums = line.split(" ");
                int source  = Integer.parseInt(nums[0]);
                int destination = Integer.parseInt(nums[1]);
                myGraph.addedge(source , destination);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        myGraph.intializeDist();

    }


    public int numnodes() {
        File myfile = new File("/home/ehsan/Desktop/T/TestCase14-N100000-k20-mu50/network.txt");
        int n = 0;
        int m = 0;
        try {
            Scanner scanner = new Scanner(myfile);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(myfile));
            String line = bufferedReader.readLine();
            while(line != null) {
                String[] nums = line.split(" ");
                n = Integer.parseInt(nums[0]);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return n;
    }


}

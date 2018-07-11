import java.lang.*;
import java.util.Vector;
import java.util.Collections;

/**
 * Created by ehsan on 6/17/2018.
 */

public class Graph {

    public static int numChange = 0 ;
    private Vector graph;
    private Vector distributions;
    public Vector result;
    private int numNodes;


    public Graph(int numNodes) {
        this.numNodes = numNodes;
        graph = new Vector<Vector>();
        for(int i = 0 ; i <= numNodes ; i++)
            graph.add(new Vector<Integer>());

        for (int i = 1; i <= numNodes ; i++)
            addedge(i,i);

        distributions = new Vector<Vector>();
        for (int i = 0; i <= numNodes ; i++)
            distributions.add(new Vector<Label>());

        result = new Vector<Integer>();

    }


   public void intializeDist() {

       for (int i = 1; i <= numNodes ; i++) {
           Collections.sort(((Vector) (graph.get(i))));
           Vector v = (Vector) (graph.get(i));
           Vector vd = (Vector)(distributions.get(i));
           for (int j = 0; j < v.size() ; j++)
               vd.add(new Label((int) v.get(j), 1.0 / v.size()));

       }



//       for (int i = 1; i <= numNodes ; i++) {
//           Vector v = (Vector) (graph.get(i));
//           Vector vd = (Vector)(distributions.get(i));
//           for (int j = 0; j < v.size() ; j++) {
//               Vector v1 = (Vector) (graph.get((int) v.get(j)));
//               for (int k = 0; k < v1.size() ; k++)
//                   setElement(vd , (int)v1.get(k));
//
//           }
//         }



   }


    public void addedge(int source, int destination) {
        ((Vector) (graph.get(source))).add(destination);
    }

    public void propagation() {

        for (int i = 1; i <= numNodes ; i++) {
            Vector temp = (Vector) distributions.get(i);
            Vector adjacent = (Vector) graph.get(i);

            for (int j = 0; j < temp.size() ; j++) {
                double newP = 0;
                Label la = (Label)temp.get(j);
                int label = la.label;
                for (int k = 0; k < adjacent.size() ; k++) {
                    int a = (int)adjacent.get(k);
                    Vector t = (Vector) distributions.get(a);
                    for (int l = 0; l < t.size() ; l++)
                        if(((Label)t.get(l) ).label == label) {
                            newP += ((Label) t.get(l)).p0;
                        }
                }
                la.p0 = la.p;
                la.p = newP;
            }
        }


    }

    public void inflation(double in) {

        for (int i = 1; i <= numNodes ; i++) {
            Vector temp = (Vector) distributions.get(i);
            double sum = 0;
            for (int j = 0; j < temp.size() ; j++)
                sum += Math.pow(((Label)temp.get(j)).p , in);
            for(int j = 0; j < temp.size() ; j++)
                ((Label)temp.get(j)).p = Math.pow(((Label)temp.get(j)).p, in)/sum;
        }

}

    public void cutoff(double r) {

        for (int i = 1; i <= numNodes ; i++) {
            Vector temp = (Vector) distributions.get(i);

            for (int j = 0; j < temp.size() ; j++)
               if(((Label)temp.get(j)).p < r)
                   temp.remove(j);
        }

    }


    public void update(double q) {

        for (int i = 1; i <= numNodes ; i++) {
            int sum = 0;
            Vector me = (Vector) graph.get(i);
            Vector medist = (Vector) distributions.get(i);
            for(int j = 0 ; j < me.size() ; j++ )
                sum += isSubset(medist , (Vector) distributions.get((Integer) me.get(j)));
            if(sum < q * me.size()) {
                numChange++;
                for (int j = 0; j < medist.size(); j++)
                    ((Label) medist.get(j)).p0 = ((Label) medist.get(j)).p;
            }
            else
                for (int j = 0; j < medist.size() ; j++)
                    ((Label)medist.get(j)).p = ((Label)medist.get(j)).p0;
        }
    }


    public boolean stop() {

         return numChange == 0;
    }

    public void setResult() {

        result.add(0);
        for (int i = 1; i <= numNodes ; i++)
            result.add(((Label)((Vector)distributions.get(i)).get(0)).label);

    }

    public int isSubset(Vector ci , Vector cj) {


        for (int i = 0; i < ci.size() ; i++) {
            if(!findelement(cj , ((Label)ci.get(i)).label , 0 , cj.size() - 1)) ;
              return 0;
        }

        return 1;
    }
    @Override
    public String toString() {

        String result = "";
//        for (int i = 1; i <= numNodes  ; i++) {
//            Vector v = (Vector) graph.get(i);
//            result += i + " => ";
//            for (int j = 0; j < v.size() ; j++) {
//                int a = (int) v.get(j);
//                result += a+ "   ";
//            }
//            result += "\n";
//        }

        for (int i = 1; i <= numNodes  ; i++) {
            Vector v = (Vector) distributions.get(i);
            for (int j = 0; j < v.size() ; j++) {
                Label l = (Label) v.get(j);
                result += l.label + "=>" + l.p + "   ";
                java.lang.System.out.print(" "+v.size()+" ");
            }
            result += "\n";
            java.lang.System.out.print(" "+ i+ " ");
        }

        return result;
    }


    public boolean findelement(Vector v, int x, int i , int j) {

        if(j - i >= 0 ) {
            int q = (i + j) / 2;

            if (((Label) v.get(q)).label == x)
                return true;

            if (((Label) v.get(q)).label > x)
                return findelement(v, x, q + 1, j);

            if (((Label) v.get(q)).label < x)
                return findelement(v, x, i, q - 1);

        }
        return false;
    }


    public void setElement(Vector v, int x) {

        int k = 0;
        v.add(new Label(Integer.MAX_VALUE , 0 ));
        while(((Label) v.get(k)).label < x)
            k++;
        if(((Label) v.get(k)).label != x)
          v.insertElementAt(new Label(x , 0) , k);

        v.remove(v.size() - 1);
    }


    public class Label {

        int label;
        double p;
        double p0;

        public Label(int label, double p) {

            this.label = label;
            this.p = p;
            p0 = p;
        }

     }

    }


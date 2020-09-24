import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
//import java.util.Scanner;
public class PercolationStats {
    private final double[] percolated;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if(n<=0) throw new IllegalArgumentException("n must be greater than zero");
        if(trials<=0) throw new IllegalArgumentException("trials must be greater than zero");

        percolated=new double [trials];
        for(int i=0;i<trials;i++){
            System.out.println(i);
            //System.out.println("trial #"+i);
            Percolation chart=new Percolation(n); 
            while(!(chart.percolates())){

                int row=StdRandom.uniform(n);
                int col=StdRandom.uniform(n);
                if(!chart.isOpen(row+1,col+1)){
                    chart.open(row+1,col+1);
                }
            }
            percolated[i]=(double) chart.numberOfOpenSites()/(n*n);
        }
      /*  for(int i=0;i<trials;i++){
            System.out.println(percolated[i]);
        }*/
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(percolated);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(percolated);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean()-1.96*stddev()/Math.sqrt(percolated.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean()+1.96*stddev()/Math.sqrt(percolated.length);
    }

   // test client (see below)
    public static void main(String[] args){
        /*Scanner inp=new Scanner(System.in);
        System.out.println("enter grid");
        int grid=inp.nextInt();
        System.out.println("enter trials");
        int trials=inp.nextInt();
        long start=System.currentTimeMillis();*/
        int trials=100;
        int grid=50;
        PercolationStats randObj=new PercolationStats(grid,trials);
        System.out.printf("mean= %f \n",randObj.mean());

        System.out.printf("stddev= %f \n",randObj.stddev());
        System.out.printf("95%% confidence interval = [%f, %f] \n",randObj.confidenceLo(),randObj.confidenceLo());

        //long end=System.currentTimeMillis();
        //System.out.println("elapsed time in ms: "+(end - start));
    }

}


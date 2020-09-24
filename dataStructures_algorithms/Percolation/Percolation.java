//import java.util.Scanner;  //Import the Scanner class 
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation{
    private final WeightedQuickUnionUF sites;
    private final WeightedQuickUnionUF fullSites;
    private int openSites=0;
    private boolean [] openOrClose;
    private final int n;
    private final int src;
    private final int dst;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if(n<=0) throw new IllegalArgumentException("out of bounds");
        this.n=n;
        src=RowColToUniDim(n,0);
        dst=RowColToUniDim(n,1);
        sites=new WeightedQuickUnionUF(n*n+2);
        fullSites=new WeightedQuickUnionUF(n*n+1);
        openOrClose=new boolean[n*n+2];
        openOrClose[src]=true;
        openOrClose[dst]=true;
        //printer(openOrClose);
        for(int i=0;i<n;i++){
            sites.union(src,i);
            fullSites.union(src,i);
            sites.union(dst,RowColToUniDim((n-1),i));
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        row--; 
        col--; //i count from zero but tests from 1
        checkIndices(row,col);
        if(!(isOpen(row+1,col+1))) {
            openOrClose[RowColToUniDim(row,col)]=true;
            openSites+=1;
        }
        if(row>=1 && isOpen(row,col+1)){
            sites.union(RowColToUniDim(row,col),RowColToUniDim(row-1,col));
            fullSites.union(RowColToUniDim(row,col),RowColToUniDim(row-1,col));
        }
        if(row<n-1 && isOpen(row+2,col+1)){
            sites.union(RowColToUniDim(row,col),RowColToUniDim(row+1,col));
            fullSites.union(RowColToUniDim(row,col),RowColToUniDim(row+1,col));
        }
        if(col>=1 && isOpen(row+1,col)){
            sites.union(RowColToUniDim(row,col),RowColToUniDim(row,col-1));
            fullSites.union(RowColToUniDim(row,col),RowColToUniDim(row,col-1));
        }
        if(col<n-1 && isOpen(row+1,col+2)){
            sites.union(RowColToUniDim(row,col),RowColToUniDim(row,col+1));
            fullSites.union(RowColToUniDim(row,col),RowColToUniDim(row,col+1));
        }
        //System.out.println(openSites);
    //printer(openOrClose);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row,int col){
        row--;
        col--;
        checkIndices(row,col);
        return openOrClose[RowColToUniDim(row,col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        row--;
        col--;
        checkIndices(row,col);
        return (isOpen(row+1,col+1) && this.fullSites.find(RowColToUniDim(row,col)) ==this.fullSites.find(this.src));
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return openSites;
    }

    // does the system percolate?
    public boolean percolates(){
        if(n==1) return isOpen(1,1);
        return this.sites.find(this.src) ==this.sites.find(this.dst);
    }

    //print array to check my progress
    /*private void printer(boolean [] arr){
        for(int i=0 ; i <=n*n+1 ; i++){
            System.out.print(arr[i]+" ");
            if( (i+1)%n ==0){
                System.out.println();
            }
        }
        System.out.println();
    }*/
    private void checkIndices(int row, int col){
        if(row<0||row>n-1||col<0||col>n-1){
            System.out.printf("n %d row %d col %d",n,row,col);
            throw new IllegalArgumentException("out of bounds");
       }
    }
    private int RowColToUniDim(int row,int col){
        return row*n+col;
    }
    // test client (optional)
    public static void main(String[] args){
       /* System.out.println("enter n:");
        Scanner obj=new Scanner(System.in);
        int n=obj.nextInt();
        Percolation xy=new Percolation(n);*/
    }

}


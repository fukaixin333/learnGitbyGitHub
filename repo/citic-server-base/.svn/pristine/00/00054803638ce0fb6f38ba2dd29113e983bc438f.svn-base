package com.citic.server.utils.claster;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;
import java.util.Vector;

import com.citic.server.utils.claster.*;

import ch.qos.logback.classic.Logger;

/**
 * distance measures ： 数据点间的距离计算方法
 * 1-euclidean 欧几里得距离
 * 2-manhattan 马氏距离（Mahalanobis distance）
 * 
 * 
 * 
 * @author hubq
 * 
 */
public class ClusterUtils {

	/**
	 * 算法一：
	 * BASIC_KMEANS = 1  //默认用此算法
	 * k-means 算法接受输入量 k ；然后将n个数据对象划分为 k个聚类以便使得所获得的聚类满足：同一聚类中的对象相似度较高；而不同聚类中的对象相似度较小。
	 * 聚类相似度是利用各聚类中对象的均值所获得一个“中心对象”（引力中心）来进行计算的。
	 * 
	 * 算法二：
	 * EM_KMEANS = 2
	 * 最大期望算法（Expectation Maximization Algorithm，又译期望最大化算法）
	 * ，是一种迭代算法，用于含有隐变量（hidden variable）的概率参数模型的最大似然估计或极大后验概率估计。
	 * 
	 * 算法三：
	 * BISECTING_KMEANS = 3
	 * 二分K均值(bisecting k-means)算法
	 * 算法主要分为以下步骤，一开始是把所有数据初始化为一个cluster,第二步从所有cluster 中选其中一个出来用基本k-means算法（k设为2）
	 * 再划分成两个cluster(初始时只有一个cluster).然后是一直重复第二步的划分（选一个cluster划成两个）直到得到k个cluster算法停止。
	 * 
	 * K_MEDOIDS = 4
	 * 和K-means比较相似，但是K-medoids和K-means是有区别的，不一样的地方在于中心点的选取，在K-means中，我们将中心点取为当前
	 * cluster中所有数据点的平均值，在 K-medoids算法中，我们将从当前cluster 中选取这样一个点——它到其他所有（当前cluster中的）点的距离之和最小——作为中心点
	 */
	private int KMeansType;
	
	/**
	 * distance measures ： 数据点间的距离计算方法
	 * 1-euclidean 欧几里得距离
	 * 2-manhattan 马氏距离（Mahalanobis distance）
	 */
	private int DistanceType;
	/**
	 * 最大跌代数
	 */
	private int MaxLoops;
	/**
	 * 
	 */
	private Vector<Point3D> VP;
	/**
	 * 聚类输入量
	 */
	private int K;
	
	/**
	 * 构造函数
	 * @param VP
	 * @param K
	 */
	public ClusterUtils(Vector<Point3D> VP, int K) {
		
		this.KMeansType=3;
		this.MaxLoops=3;
		this.DistanceType = 1;
		
		this.VP = new Vector<Point3D>();
        for (int i = 0; i < VP.size(); i++) {
            this.VP.add(new Point3D(VP.elementAt(i).x, VP.elementAt(i).y, VP.elementAt(i).z));
        }
        this.K = K;
		
	}
	
	/**
	 * 构造函数
	 * @param KMeansType
	 * @param VP
	 * @param K
	 */
	public ClusterUtils(int KMeansType, Vector<Point3D> VP, int K) {
		
		this.MaxLoops=3;
		this.DistanceType = 1;
	
		this.KMeansType = KMeansType;
		this.VP = new Vector<Point3D>();
        for (int i = 0; i < VP.size(); i++) {
        	//System.out.println("x_"+i+"="+VP.elementAt(i).x);
            this.VP.add(new Point3D(VP.elementAt(i).x, VP.elementAt(i).y, VP.elementAt(i).z));
        }
        this.K = K;
        
	}
	
	/**
	 * 构造函数
	 * @param KMeansType 算法
	 * @param VP 数据源
	 * @param K 聚类输入量
	 * @param MaxLoops 最大跌代数（默认不超过10）
	 */
	public ClusterUtils(int KMeansType, Vector<Point3D> VP, int K, int MaxLoops, int DistanceType) {
		this.KMeansType = KMeansType;
		this.VP = new Vector<Point3D>();
        for (int i = 0; i < VP.size(); i++) {
            this.VP.add(new Point3D(VP.elementAt(i).x, VP.elementAt(i).y, VP.elementAt(i).z));
        }
        this.K = K;
        this.MaxLoops = MaxLoops;
        this.DistanceType = DistanceType;
	}

    public ArrayList startClusterCal() {
    	
    	ArrayList list = new ArrayList();
        Cluster C = new Cluster(VP);

        switch (KMeansType) {
            case ClusterCONST.BASIC_KMEANS:   //1
                int[] Seeds = new int[K];
                for (int i = 0; i < K; i++) {
                    Seeds[i] = i;
                }
                Vector<Cluster> v = K_Means_Clustering(C, K, MaxLoops, Seeds);
                
                list = createCluster(v);
                
                break;
            case ClusterCONST.BISECTING_KMEANS:     //3
                list = Bisecting_K_Means_Clustering(C, K, MaxLoops);
                break;
            case ClusterCONST.EM_KMEANS:     //2
                list = EM_K_Means_Clustering(C, K);
                break;
            case ClusterCONST.K_MEDOIDS:    //4
                list = K_Medoids_Clustering(C, K);
                break;
        }
        
        return list;
    }
	
    /**
     * @param MainCluster
     * @param K
     * @param MaxLoops
     */
    private ArrayList Bisecting_K_Means_Clustering(Cluster MainCluster, int K, int MaxLoops) {
    	ArrayList list = new ArrayList();
    	
        MainCluster.SSE = ClusterCONST.calSSE(MainCluster, this.DistanceType);
        Comparator<Cluster> cmp = new MyComparator();
        PriorityQueue<Cluster> Q = new PriorityQueue<Cluster>(K, cmp);
        Q.add(MainCluster);

        while (Q.size() < K) {
            Vector<Cluster> V = new Vector<Cluster>();
            Iterator<Cluster> I = Q.iterator();
            while (I.hasNext()) {
                V.add(I.next());
            }
            
            list = createCluster(V);
            Cluster MaxCluster = Q.poll();
            double MinTotalSSE = ClusterCONST.INF;
            Cluster C1 = new Cluster( new Point3D(0.0,0.0,0.0) );
            Cluster C2 = new Cluster( new Point3D(0.0,0.0,0.0) );

            for (int i = 0; i < MaxCluster.V.size() - 1; i = i * 10 + 1) {
                for (int j = i + 1; j < MaxCluster.V.size(); j *= 10) {
                    int[] Seeds = new int[2];
                    Seeds[0] = i;
                    Seeds[1] = j;
                    
                    Vector<Cluster> BiClusters = K_Means_Clustering(MaxCluster, 2, Math.min(MaxLoops, 5), Seeds);

                    if (MinTotalSSE > BiClusters.elementAt(0).SSE + BiClusters.elementAt(1).SSE) {
                        C1 = BiClusters.elementAt(0);
                        C2 = BiClusters.elementAt(1);
                        MinTotalSSE = BiClusters.elementAt(0).SSE + BiClusters.elementAt(1).SSE;
                    }
                }
            }

            Q.add(C1);
            Q.add(C2);
        }

        Vector<Cluster> V = new Vector<Cluster>();
        Iterator<Cluster> I = Q.iterator();
        while (I.hasNext()) {
        	Cluster c = I.next();
        	//System.out.println("x=="+c.Centroid.x);
            V.add(c);
        }
        
       //System.out.println("V="+V.size()); 
       
        
        list = createCluster(V);
        
        return list;
    }
    
    private Vector<Cluster> K_Means_Clustering(Cluster MainCluster, int K, int MaxLoops, int[] Seeds) {
        Vector<Cluster> ClusterVector = new Vector<Cluster>();
        for (int i = 0; i < K; i++) {
            ClusterVector.add(new Cluster(MainCluster.V.elementAt(Seeds[i])));
        }

        int loops = 0;
        while (loops < Math.min(10, MaxLoops)) {
            for (int i = 0; i < K; i++) {
                ClusterVector.elementAt(i).V.removeAllElements();
            }

            for (int i = 0; i < MainCluster.V.size(); i++) {
                Point3D p = MainCluster.V.elementAt(i);
                double MinDist = ClusterCONST.INF;
                int ChosenCluster = 0;
                for (int j = 0; j < K; j++) {
                    if (MinDist > ClusterCONST.Distance(p, ClusterVector.get(j).Centroid, this.DistanceType)) {
                        MinDist = ClusterCONST.Distance(p, ClusterVector.get(j).Centroid, this.DistanceType);
                        ChosenCluster = j;
                    }
                }

                ClusterVector.get(ChosenCluster).V.add(p);
            }
            //boolean Terminate = true;
            for (int i = 0; i < K; i++) {
                if (!ClusterCONST.Equal(ClusterVector.get(i).Centroid, ClusterCONST.getCentroid(ClusterVector.get(i).V), this.DistanceType)) {
                    //Terminate = false;
                }
                ClusterVector.get(i).Centroid = ClusterCONST.getCentroid(ClusterVector.get(i).V);
                ClusterVector.get(i).SSE = ClusterCONST.calSSE(ClusterVector.get(i), this.DistanceType);
            }
            //if (Terminate) {
            //    break;
            //}
            loops++;
            //if (ClusterCONST.STOP) {
                //this.jLabel4.setVisible(false);
            //    break;
            //}
            //if (showChart) {
                //list = createCuster(ClusterVector);
            //}
        }

        return ClusterVector;
    }
	
    private ArrayList EM_K_Means_Clustering(Cluster MainCluster, int K) {
        int R = MainCluster.V.size();
        Point3D[] Means = new Point3D[K];
        double[][] PxBelongsToC = new double[R][K];
        Vector<Cluster> ClusterVector;
        MainCluster.SSE = ClusterCONST.calSSE(MainCluster, ClusterCONST.EUCLIDEAN);
        double Deviation = MainCluster.SSE;
        Deviation /= MainCluster.V.size();
        Deviation = Math.sqrt(Deviation);

        for (int i = 0; i < K; i++) {
            Means[i] = new Point3D(MainCluster.V.elementAt(i));
        }

        ClusterVector = new Vector<Cluster>();
        for (int i = 0; i < K; i++) {
            ClusterVector.add(new Cluster(new Point3D(Means[i])));
        }

        //Expectation Step
        for (int k = 0; k < R; k++) {
            double SumOfPxBelongsToC = 0;
            for (int i = 0; i < K; i++) {
                SumOfPxBelongsToC += ClusterCONST.NormalDistribution(MainCluster.V.elementAt(k), Means[i], Deviation);
            }

            for (int i = 0; i < K; i++) {
                PxBelongsToC[k][i] = ClusterCONST.NormalDistribution(MainCluster.V.elementAt(k), Means[i], Deviation) / SumOfPxBelongsToC;
            }
        }

        //Maximization Step
        for (int i = 0; i < K; i++) {
            Point3D SumOfMeanPx = new Point3D(0, 0, 0);
            double SumOfPx = 0;
            for (int k = 0; k < R; k++) {
                SumOfMeanPx.x += PxBelongsToC[k][i] * MainCluster.V.elementAt(k).x;
                SumOfMeanPx.y += PxBelongsToC[k][i] * MainCluster.V.elementAt(k).y;
                SumOfMeanPx.z += PxBelongsToC[k][i] * MainCluster.V.elementAt(k).z;
                SumOfPx += PxBelongsToC[k][i];
            }

            Means[i].x = SumOfMeanPx.x / SumOfPx;
            Means[i].y = SumOfMeanPx.y / SumOfPx;
            Means[i].z = SumOfMeanPx.z / SumOfPx;

        }

        ClusterVector = new Vector<Cluster>();
        for (int i = 0; i < K; i++) {
            ClusterVector.add(new Cluster(new Point3D(Means[i])));
        }

        for (int i = 0; i < R; i++) {
            double Min = ClusterCONST.INF;
            int pos = 0;
            for (int k = 0; k < K; k++) {
                if (Min > ClusterCONST.Distance(Means[k], MainCluster.V.elementAt(i), ClusterCONST.EUCLIDEAN)) {
                    Min = ClusterCONST.Distance(Means[k], MainCluster.V.elementAt(i), ClusterCONST.EUCLIDEAN);
                    pos = k;
                }
            }
            ClusterVector.elementAt(pos).V.add(MainCluster.V.elementAt(i));
        }
        return createCluster(ClusterVector);
    }

    private ArrayList K_Medoids_Clustering(Cluster MainCluster, int K) {
    	ArrayList list = new ArrayList();
    	
        int N = MainCluster.V.size();

        int[] M = new int[K];//Array Of Medoids
        boolean[] IsMedoids = new boolean[N];
        Arrays.fill(IsMedoids, false);
        for (int i = 0; i < K; i++) {
            M[i] = i;
            IsMedoids[i] = true;
        }

        int[] B = new int[N];//which medoid point i belongs to
        int[] NB = new int[N];
        double SE = ClusterCONST.SumOfError(M, B, IsMedoids, K, MainCluster.V, this.DistanceType);
        int loops = 0;
        while (loops < this.MaxLoops) {
            boolean found = false;
            int O = 0;
            int P = 0;
            for (int i = 0; i < K; i++) {
                for (int j = 0; j < N; j++) {
                    if (!IsMedoids[j]&&NB[j]==i) {
                        IsMedoids[j] = true;
                        IsMedoids[M[i]] = false;
                        double tmp = ClusterCONST.SumOfError(M, B, IsMedoids, K, MainCluster.V, this.DistanceType);
                        if (SE > tmp) {
                            SE = tmp;
                            O = i;
                            P = j;
                            NB = Arrays.copyOf(B, N);
                            found = true;
                        }
                        IsMedoids[j] = false;
                        IsMedoids[M[i]] = true;
                    }
                }
            }

            if (found) {
                IsMedoids[M[O]] = false;
                M[O] = P;
                IsMedoids[P] = true;

                Vector<Cluster> ClusterVector = new Vector<Cluster>();
                for (int i = 0; i < K; i++) {
                    ClusterVector.add(new Cluster(new Point3D(0, 0, 0)));
                }

                for (int i = 0; i < K; i++) {
                    ClusterVector.elementAt(i).V.add(MainCluster.V.elementAt(M[i]));
                }

                for (int i = 0; i < N; i++) {
                    if (!IsMedoids[i]) {
                        ClusterVector.elementAt(NB[i]).V.add(MainCluster.V.elementAt(i));
                    }
                }
                for(int i=0;i<K;i++)
                    ClusterVector.elementAt(i).Centroid = ClusterCONST.getCentroid(ClusterVector.elementAt(i).V);
                
                list = createCluster(ClusterVector);
            } else {
                //this.jLabel4.setVisible(false);
                break;
            }
            if (ClusterCONST.STOP) {
                //this.jLabel4.setVisible(false);
                break;
            }
            loops++;
        }
        return list;
    }
    
    class MyComparator implements Comparator<Cluster> {

        public int compare(Cluster o1, Cluster o2) {
            if (o1.SSE < o2.SSE) {
                return 1;
            }
            if (o1.SSE > o2.SSE) {
                return -1;
            }
            return 0;
        }
    }
	
    
    /**
     * 
     * @param ClusterVector
     */
    private ArrayList createCluster(Vector<Cluster> ClusterVector) {
    	
    	ArrayList list = new ArrayList();
    	
        try {
        	
        	/**
        	 * 按聚类段循环
        	 */
            for (int i = 0; i < ClusterVector.size(); i++) {
            	
            	LinkedHashMap map = new LinkedHashMap();
            	
                double[] x = new double[ClusterVector.elementAt(i).V.size()];
                double[] y = new double[ClusterVector.elementAt(i).V.size()];
                double[] z = new double[ClusterVector.elementAt(i).V.size()];

                //System.out.println("xxx_"+i+"="+ClusterVector.elementAt(i).Centroid.x);
                
                /**
                 * 聚类群组样本量
                 */
                map.put("xNum", x.length);
                //map.put("yNum", y.length);
                //map.put("zNum", z.length);
                /**
                 * 找到聚类最大值，最小值
                 * 即聚类群组的区间
                 */
                double xMax = Double.MIN_VALUE;
                double xMin = Double.MAX_VALUE;
                //double yMax = Double.MIN_VALUE;
                //double yMin = Double.MAX_VALUE;
                //double zMax = Double.MIN_VALUE;
                //double zMin = Double.MAX_VALUE;
                
                for (int j = 0; j < ClusterVector.elementAt(i).V.size(); j++) {
                    x[j] = ClusterVector.elementAt(i).V.elementAt(j).x;
                    y[j] = ClusterVector.elementAt(i).V.elementAt(j).y;
                    z[j] = ClusterVector.elementAt(i).V.elementAt(j).z;
                    
                    if(x[j]>xMax) xMax = x[j];
                    if(x[j]<xMin) xMin = x[j];
                    
                    //if(y[j]>yMax) yMax = y[j];
                    //if(y[j]<yMin) yMin = y[j];
                    
                    //if(z[j]>zMax) zMax = z[j];
                    //if(z[j]<zMin) zMin = z[j];
                }
                
                if(xMax == Double.MIN_VALUE) xMax = 0;
                if(xMin == Double.MAX_VALUE) xMin = 0;
                
                map.put("xMax", xMax);
                map.put("xMin", xMin);
                //map.put("yMax", yMax);
                //map.put("yMin", yMin);
                //map.put("zMax", zMax);
                //map.put("zMin", zMin);
                
                /**
                 * 聚类群组中间点
                 */
                double xMpoint = ClusterVector.elementAt(i).Centroid.x ;
                //System.out.println("xMpoint="+xMpoint);
                //System.out.println("xMpoint="+(Double.isNaN(xMpoint) ));
                if(Double.isNaN(xMpoint)){
                	xMpoint = 0;
            	}else{
            		xMpoint = new Double( String.format("%.2f", xMpoint) );
            	}
                
                map.put("xMpoint", xMpoint);
                //map.put("yMpoint", ClusterVector.elementAt(i).Centroid.y);
                //map.put("zMpoint", ClusterVector.elementAt(i).Centroid.z);
                
                list.add(map);
            }
        } catch (Exception e) {
        }
        
        
        return list;
        
    }
    
	public static void main(String[] args) {
		
		Vector<Point3D> v = new Vector();
		
		//int[] idata = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,3,2,2,1,1,1,1,1,1,4,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,4,1,1,1,1,3,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,3,1,1,1,1,2,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,3,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,2,1,1,2,1,1,1,1,3,1,1,1,2,1,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,3,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,2,1,2,3,1,1,1,1,1,1,2,1,1,1};
		//double[] idata={0.0000,0.0000,0.0000,1820.0000,49000.0000,0.0000,100000.0000,0.0000,138250.0000,0.0000,0.0000,93900.0000,0.0000,1650.0000,2004.0000,600000.0000,0.0000,35775.0000,0.0000,0.0000,0.0000,2000000.0000,0.0000,495000.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,92000.0000,0.0000,0.0000,0.0000,0.0000,100000.0000,0.0000,0.0000,0.0000,0.0000,0.0000,5000.0000,4000.0000,0.0000,0.0000,0.0000,1800.0000,17000.0000,0.0000,99000.0000,0.0000,767.0000,0.0000,1635.0000,0.0000,10000.0000,0.0000,0.0000,0.0000,800.0000,0.0000,3654.0000,0.0000,3306.7200,0.0000,0.0000,0.0000,0.0000,0.0000,1280.0000,0.0000,98000.0000,500.0000,0.0000,0.0000,0.0000,0.0000,200.0000,0.0000,866.0000,0.0000,241372.0000,0.0000,1370.0000,0.0000,0.0000,0.0000,0.0000,757.0000,0.0000,4100.0000,0.0000,0.0000,5000.0000,0.0000,0.0000,0.0000,0.0000};
		double[] idata={1820.0000,49000.0000};//,1820.0000,49000.0000,0.0000,100000.0000,0.0000,138250.0000,0.0000,0.0000,93900.0000,0.0000,1650.0000,2004.0000,600000.0000,0.0000,35775.0000,0.0000,0.0000,0.0000,2000000.0000,0.0000,495000.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,92000.0000,0.0000,0.0000,0.0000,0.0000,100000.0000,0.0000,0.0000,0.0000,0.0000,0.0000,5000.0000,4000.0000,0.0000,0.0000,0.0000,1800.0000,17000.0000,0.0000,99000.0000,0.0000,767.0000,0.0000,1635.0000,0.0000,10000.0000,0.0000,0.0000,0.0000,800.0000,0.0000,3654.0000,0.0000,3306.7200,0.0000,0.0000,0.0000,0.0000,0.0000,1280.0000,0.0000,98000.0000,500.0000,0.0000,0.0000,0.0000,0.0000,200.0000,0.0000,866.0000,0.0000,241372.0000,0.0000,1370.0000,0.0000,0.0000,0.0000,0.0000,757.0000,0.0000,4100.0000,0.0000,0.0000,5000.0000,0.0000,0.0000,0.0000,0.0000};
		
		for(int i=0;i<idata.length;i++){
			Double x = new Double(idata[i]);
			Point3D p3 = new Point3D(x,0,0);
			v.add(p3);
		}
		
		/**
		for(int i=1;i<=3000;i++){
			Double x = Math.random()*100;
			//x=Math.round(x/0.01)*0.01;
			
			x = new Double( String.format("%.2f", x) );
			
			Point3D p3 = new Point3D(x,0,0);
			v.add(p3);
		}
		
		Point3D p = new Point3D(5,0,0);
		v.add(p);
		for(int i=1;i<=400;i++){
			Double x = new Double(1);
			Point3D p3 = new Point3D(x,0,0);
			v.add(p3);
		}
		for(int i=1;i<=20;i++){
			Double x = new Double(4);
			Point3D p3 = new Point3D(x,0,0);
			v.add(p3);
		}
		for(int i=1;i<=20;i++){
			Double x = new Double(3);
			Point3D p3 = new Point3D(x,0,0);
			v.add(p3);
		}
		for(int i=1;i<=20;i++){
			Double x = new Double(2);
			Point3D p3 = new Point3D(x,0,0);
			v.add(p3);
		}
		for(int i=1;i<=40;i++){
			Double x = new Double(5);
			Point3D p3 = new Point3D(x,0,0);
			v.add(p3);
		}
		**/
		//ClusterUtils(1, VP, K, 5 ,1);
		ClusterUtils cUtils = new ClusterUtils(3,v,5);
		
		ArrayList list = cUtils.startClusterCal();
		System.out.println(list);
	}
}

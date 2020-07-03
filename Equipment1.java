import java.lang.Math;

import java.util.*;

public class Equipment1 {
	static FileIO reader = new FileIO();
	public static String [] x = reader.load("C:\\Users\\Amber\\eclipse-workspace\\CS211project\\airportsX.txt");
	public static String [] y = reader.load("C:\\Users\\Amber\\eclipse-workspace\\CS211project\\airportsY.txt");
	
	public static void main(String[] args) {
	
	double bestDistance=10000000, bestTime=1000000;
	String bestSolution="";
	
	//This loop is used to find the optimum second position
	//change run from to run from 0 to 1000 to test all possible second position
	for(int run=532; run<538; run++)
	{
			List<String> airports = new ArrayList<String>();
			List<Integer> position = new ArrayList<Integer>();
        for( int i=0; i<x.length; i++ )
        {
        		//reading in latitudes and longitude in an array list
               	airports.add(Double.parseDouble(x[i]) + "," + Double.parseDouble(y[i]));
               	position.add(i);
        }
                //Changing which position comes second to sort the array according to this 
                String start= airports.get(run);
                airports.remove(run);
                airports.add(0, start);
                position.remove(run);
                position.add(0,run);
                int h=1;
                
                //sorts the array lists by relative distance
                // One by one move boundary of unsorted subarray  
                while(h<airports.size()-2)
                {
                	String p=airports.get(h);
                	String retrieve [] = p.split(",");
        		 	double lat1 = Double.parseDouble(retrieve[0]);
        		 	double lon1 = Double.parseDouble(retrieve[1]);
                	int j=h;
                	j++;
                	int bestIndex=0;
                	double best=1000000;
                	//version of selection sort	  
                	while(j<airports.size()-1)
                	{
                   	 	String q = airports.get(j);
                   	 	String retrieve1 [] = q.split(",");
        			 	double lat2 = Double.parseDouble(retrieve1[0]);
        			 	double lon2 = Double.parseDouble(retrieve1[1]);    
        		        double result = distance(lat1, lat2, lon1, lon2);
        		        // Find the minimum element in unsorted array
                         if(result<best)
                         {
                         	best=result;
                         	bestIndex=j;
                         }
                         j++;
                	}
                		// Swap the found minimum element with the first element
        		        String insert= airports.get(bestIndex);
        		        airports.remove(bestIndex);
        	            airports.add(h+1, insert);
        	            
        		        int insert2= position.get(bestIndex);
        		        position.remove(bestIndex);
        		        position.add(h+1, insert2);
        	            h++;
                }
                
                	//Ensures there is at least 100kms between consecutive elements
                	int i=0;
                	// One by one move boundary of unsorted subarray  
		            while(i<airports.size()-2)
					{
		            	String p=airports.get(i);
		            	String retrieve [] = p.split(",");
					 	double lat1 = Double.parseDouble(retrieve[0]);
					 	double lon1 = Double.parseDouble(retrieve[1]);
		            	int j=i;
			            	j++;
			            	//finds the next elements over 100 to we're on
			 			       while(j!=i)
				                 {
				                	 String q = airports.get(j);
				                	 int qc = position.get(j);
				                	 String retrieve1 [] = q.split(",");
				    			 	double lat2 = Double.parseDouble(retrieve1[0]);
				    			 	double lon2 = Double.parseDouble(retrieve1[1]);    
				 			        double result = distance(lat1, lat2, lon1, lon2);
				 			        //if element over than 100 is found swap it so it comes after the point we're on 
				 			        if(result>100)
				 			        {
				 			        	if(j>i)
				 			        	{
				 			        		airports.remove(j);
				 			        		airports.add(i+1, q);
				 		                   
				 		                   	position.remove(j);
				 		                   	position.add(i+1, qc);
				 		                    break;
				 			        	}
				 			        	//if we're searching the already sorted subarray
				 				        if(j<i)
				 				        {
				 				        	String r=airports.get(j-1);
				 				        	String retrieve2 [] = r.split(",");
				 		    			 	double checkX1 = Double.parseDouble(retrieve2[0]);
				 		    			 	double checkY1 = Double.parseDouble(retrieve2[1]); 
				
				 				        	String s=airports.get(j+1);
				 				        	String retrieve3 [] = s.split(",");
				 		    			 	double checkX2 = Double.parseDouble(retrieve3[0]);
				 		    			 	double checkY2 = Double.parseDouble(retrieve3[1]); 
				 					        double result2 = distance(checkX1, checkX2, checkY1, checkY2);
				 					       //we can only swap it if it doesn't effect our already sorted data
				 					       if(result2>100)
				 					        {
				 					    	   airports.remove(j);
				 	 		                   airports.add(i+2, q);
				 	 		                   
				 	 		                   position.remove(j);
				 	 		                   position.add(i+2, qc);
				 	 		                    break;
				 					        } 
				 					     }
				 				    }
				                 j++;
				                 //if we come to the end of the array search through the already sorted subarray 
			                     if(j==airports.size()-1)
			                     {
			                         j=1;
			                     }
			                 }
					        i++;
					}
		            
		            //Placing point 0 at the beginning and end of the array list
	                String fix3= airports.get(1);
	                airports.remove(1);
	                airports.add(0,fix3);
	                int in=position.get(1);;
	                position.remove(1);
	                position.add(0,in);
	                airports.add(fix3);
	                position.add(in);
	                
		        int m=0;
		        double distance=0;
		        String output="";
		        double time=0;
		        boolean valid=true;
		        //calculating our output, distance and time
		        while(m<airports.size()-1)
				{
		        	String retrieve [] = airports.get(m).split(",");
				 	double lat1 = Double.parseDouble(retrieve[0]);
				 	double lon1 = Double.parseDouble(retrieve[1]);
				 	String retrieve1 [] = airports.get(m+1).split(",");
					double lat2 = Double.parseDouble(retrieve1[0]);
					double lon2 = Double.parseDouble(retrieve1[1]);    
				    double result2 = distance(lat1, lat2, lon1, lon2);
				    //checking validity
				        if(result2<=100)
				        {
				        	valid=false;
				        }
				        distance += result2;
				        output=output +position.get(m)+ ",";
				        m++;
				        time+=(result2/800)+0.5;
					}
		        	//adding on the last point to our output array
			        output=output+position.get(position.size()-1);
			        time=Math.round(time);
			        //checking to see if this is our optimum solution
			        if(distance<bestDistance && valid==true)
			        {
			        	bestDistance=distance;
			        	bestTime=time;
			        	bestSolution=output;
			        }
		}
		//Printing out our results
		System.out.println("Distance : "+ bestDistance);
		System.out.println("Time : "+ bestTime);
		System.out.println("Solution : "+ bestSolution);
	}
	//Haversine equation to calculate distance between points
	public static double distance(double lat1, double lat2, double lon1,
	        double lon2) {

	    final int R = 6371;

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c;

	    distance = Math.pow(distance, 2);
	    int distance2=(int)Math.sqrt(distance);
	    return distance2;
	    }
}
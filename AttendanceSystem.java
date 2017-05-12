package attendancesystem;

import java.sql.*;
import java.util.Scanner;
public class AttendanceSystem {

    public static void main(String[] args) {
        
        
            try 
              {
                  int i,rows = 6;
                  Scanner sc = new Scanner(System.in);
              // loading thejdbc odbc driver
              Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
              // creating connection toth data base
              Connection con = DriverManager.getConnection("jdbc:odbc:attendance","","");
              Statement st = con.createStatement();
              // create an execute sql command on database    
              ResultSet rs = st.executeQuery("Select * from attendance order by ID");
              ResultSetMetaData rsmd = rs.getMetaData();
              // this getColumnCount reurn the number of column in the selected table
              int numberOfColumns = rsmd.getColumnCount();
              
              Double m1[] = new Double[rows];
              Double m2[] = new Double[rows];
              Double m3[] = new Double[rows];
              String defaulter[] = new String[rows];
              
              
              int j=0;
              while(rs.next()){
                  
                   for (i = 1; i <= numberOfColumns; i++) 
                       {
                           if (i > 1)
                                System.out.print("\t ");
                          String columnValue = rs.getString(i);
                           //System.out.print(columnValue);
                           if(i==2)
                               m1[j] = Double.parseDouble(columnValue);
                           if(i==3)
                               m2[j] = Double.parseDouble(columnValue);
                           if(i==4)
                               m3[j] = Double.parseDouble(columnValue);
                           if(i==5)
                               defaulter[j] = columnValue;
                       }
                       j++;
              }
           
            System.out.println("Month1\tMonth2\tMonth\tDefaulter");
               for(i=0;i<rows;i++)
                   System.out.println(m1[i]+"\t"+m2[i]+"\t"+m3[i]+"\t"+defaulter[i]);
           
               
            System.out.println("\n\nProvide the attendance for below mentioned months");
            System.out.println("Month1: ");
            Double im1 = sc.nextDouble();
            System.out.println("Month2: ");
            Double im2 = sc.nextDouble();
            System.out.println("Month3: ");
            Double im3 = sc.nextDouble();
            int m1Y = 0, m2Y = 0, m3Y = 0, y = 0, n = 0, m1N = 0, m2N = 0, m3N = 0;
            
            for(i=0;i<rows;i++)
		{
		//for probabilities of yes'
		if(Double.compare(im1, m1[i])>0 || Double.compare(im1, m1[i])==0 )
                    if( defaulter[i].equalsIgnoreCase("yes"))
                        m1Y++;
		if(Double.compare(im2, m2[i])>=0 || Double.compare(im1, m2[i])==0 )
                    if( defaulter[i].equalsIgnoreCase("yes"))
                        m2Y++;
		if(Double.compare(im3, m3[i])>=0 || Double.compare(im3, m3[i])==0 )
                    if( defaulter[i].equalsIgnoreCase("yes"))
			m3Y++;
		

		//for probabilities of no's
		if(Double.compare(im1, m1[i])>0 || Double.compare(im1, m1[i])==0 )
                    if( defaulter[i].equalsIgnoreCase("no"))
                    m1N++;
		if(Double.compare(im2, m2[i])>=0 || Double.compare(im2, m2[i])==0 )
                    if( defaulter[i].equalsIgnoreCase("no"))
                        m2N++;
		if(Double.compare(im3, m3[i])>=0 || Double.compare(im3, m3[i])==0 )
                    if( defaulter[i].equalsIgnoreCase("no"))
			m3N++;
		

		//for yes' and no's
		if(defaulter[i].equalsIgnoreCase("yes"))
			y++;
		if(defaulter[i].equalsIgnoreCase("no"))
			n++;			
		}
            System.out.println(m1Y+" "+m2Y+" "+m3Y+" "+m1N+" "+m2N+" "+m3N+" "+y+" "+n);
           double tyes=((double)m1Y/y)*((double)m1Y/y)*((double)m3Y/y)*((double)y/rows);
	double tno=((double)m1N/n)*((double)m1N/n)*((double)m3N/n)*((double)n/rows);
	System.out.print("\nP(X|Yes)*P(Yes) : "+tyes+"\nP(X|No)*P(No) : "+tno);
            
	if(tyes>=tno)
		System.out.println("\n\nStudent is a defaulter");
	else System.out.println("\n\nStudent isn't a defaulter");
           
            st.close();
            con.close();
               } catch (ClassNotFoundException | SQLException | NumberFormatException ex)
                         {
                      System.err.print("Exception: ");
                      System.err.println(ex.getMessage());
                         }
    }
    
}

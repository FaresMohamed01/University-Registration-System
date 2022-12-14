package individual.project;

import java.io.File;
import java.io.FileNotFoundException; import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.NoSuchElementException; import java.util.Scanner;

public static void main_menu_functions(ArrayList<Department> department, ArrayList<Instructor> instructor) throws SQLException, ClassNotFoundException, IOException{
    Scanner input = new Scanner (System.in); 
    int select;
    do {
       select = main_menu(); 
      switch (select) {
        case 1: 
            System.out.println ("Enter the department name:");
            String department_name = input.next();
            boolean check = false;
            
            //Creating a connection with university database
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "f7aaa8re9s");
            Statement s = c.createStatement(); 
            s.executeUpdate("use university");
            
            //Resultset result execute the query stated below.
            ResultSet result = s.executeQuery("Select dept_name, budget from department;");
            
            //The while loop goes through the department table to match the user's department_name and print the budget
            while(result.next()){
              if (department_name.equals(result.getString(1))){
                  System.out.println ("Department's budget: "); 
                  System.out.println (result.getString(2)); 
                  check = true;
              }
            }
            

            if (check == false){
               System.out.print ("Department not found");
            }
            c.close(); 
            break;
          
         case 2:
             System.out.println("Enter the department name: "); 
             String Department_name = input.next();
              
             System.out.println("Enter the new budget amount: "); 
             String bud = input.next();
              
             int b = Integer.parseInt(bud); 
              
             boolean search = false;

             //create connection to university database
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "f7aaa8re9s");
             Statement statements = con.createStatement(); statements.executeUpdate("use university");

             //Resultset with required select statement
             ResultSet results = statements.executeQuery ("select dept_name, budget from department");

             //The while loop makes sure that both the department name user enters exist and budget user enters is greater than zero
             while(results.next()){
               if (Department_name.equals(results.getString(1)) && b >= 0){
                   search = true; 
               }
             }

             if (search == false) {
                System.out.println("Error! Budget less than zero or department name does not appear in the database.");
             }
             
             else if (search == true){
                statements.executeUpdate("update department set budget = '"+bud+" 'where '"+Department_name+"' = dept_name");
                System.out.println("Budget Updated!"); 
              }

              con.close(); 
              break;
        
        case 3:
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "f7aaa8re9s");
            Statement statement = connection.createStatement(); statement.executeUpdate("use university");
            
            System.out.println ("Enter the department name:");
            String dept_name = input.next();
            
            System.out.println ("Enter the location:"); 
            String location = input.next();
            
            System.out.println ("Enter the budget:"); 
            String budget = input.next();
            
            boolean check_department = false;
    
            //Resultset execute the query below
            ResultSet result1 = statement.executeQuery("Select dept_name from department;");
            
            // The while loop checks one of the two conditions and compares between the departments entered by user to the ones in the database
            while (result1.next()){
              for (int j = 0; j < department.size(); j++){
                    if (dept_name.equals(result1.getString(1))){ 
                        check_department = true;
                   }
              }
            }

           int bu = Integer.parseInt(budget); 

           if (bu < 0 || check_department == true){
               System.out.println ("Error! Budget is less than 0 or the department entered exists in the database"); 
           }

           else if (check_department == false && bu >= 0 ) {
              int insert = statement.executeUpdate("insert into department values ('"+dept_name+"', '"+location+"', '"+budget+"')");
              System.out.println ("New Department Added to database!"); 
           }

            connection.close(); 
            break;
            
          default: 
             break;
          }
        }
        
        while (select != 4); 
        }
     }

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fees_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.InputStreamReader;
import java.io.BufferedReader;
/**
 *
 * @author DEL
 */
public class DBConnection {
    
    public static Connection getConnection(){
        Connection con=null;
        try{
            Class.forName("org.postgresql.Driver");
            con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/fees_management","postgres","system");
        }catch(Exception e){
            e.printStackTrace();;
        }
        return con;
    }
    public static void main(String[] args) {
        try{
            //Connection con =DBConnection.getConnection();



            Class.forName("org.postgresql.Driver");
            Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/fees_management","postgres","system");
        
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter ID : ");
            int id=Integer.parseInt(br.readLine());
            
            System.out.print("Enter Name : ");
            String name=br.readLine();
            
            PreparedStatement pst=con.prepareStatement("INSERT INTO AIF VALUES(?,?)");
            pst.setInt(1,id);
            pst.setString(2,name);
            System.out.print(pst.executeUpdate()+"  Refference No\n\n\n");           
            
            PreparedStatement pst1=con.prepareStatement("Select * from aif");
            ResultSet rs=pst1.executeQuery();
            while(rs.next()){
                System.out.println(rs.getInt(1)+"  "+rs.getString(2));
            }
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
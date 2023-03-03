/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fees_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author DEL
 */
public class signupPage extends javax.swing.JFrame {
    String fname,lname,uname,passworld,con_pass,contact_no;
    Date dob;
    int id=0;
    /**
     * Creates new form signupPage
     */
    public signupPage() {
        initComponents();
    }
    
    boolean validation(){
           
           fname=txt_firstname.getText();
           lname=txt_lastname.getText();
           uname=txt_username.getText();
           passworld=txt_passworld.getText();
           con_pass=txt_con_passworld.getText();
           contact_no=txt_contactno.getText(); 
           dob=txt_dob.getDate();
            
           if(fname.equals("")){
               JOptionPane.showMessageDialog(this,"Plese Enter FirstName");
               return false;
           }
           
           if(lname.equals("")){
               JOptionPane.showMessageDialog(this,"Plese Enter LastName");
               return false;
           }
           
           if(uname.equals("")){
               JOptionPane.showMessageDialog(this,"Plese Enter UserName");
               return false;
           }
           
           if(passworld.equals("")){
               JOptionPane.showMessageDialog(this,"Plese Enter Passworld");
               return false;
           }
        
           
           if(con_pass.equals("")){
               JOptionPane.showMessageDialog(this,"Plese Enter Co_Passworld");
               return false;
           }
           
           if(!passworld.equals(con_pass)){
               JOptionPane.showMessageDialog(this,"Enter Write Conform Passworld");
               return false;
           }
           
           if(contact_no.equals("")){
               JOptionPane.showMessageDialog(this,"Plese Enter Contactno");
               return false;
           }
            
           if( dob==(null) ){
               JOptionPane.showMessageDialog(this,"Plese Enter D.B.O");
               return false;
           }
            return true;
       } 

       void insertDetails(){

           SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
           String myDob=format.format(dob);
            try{
               try{
                    Class.forName("org.postgresql.Driver");
                } catch (Exception e){
                    System.out.println("Fail to Load the JDBC Driver");
                }
                Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/fees_management","postgres","system");

               String sql="insert into signup values(?,?,?,?,?,?,?)";
               PreparedStatement stmt=con.prepareStatement(sql);
               stmt.setInt(1,getId());
               stmt.setString(2,fname);
               stmt.setString(3,lname);
               stmt.setString(4,uname);
               stmt.setString(5,passworld);
               stmt.setDate(6,java.sql.Date.valueOf(myDob));
               
               stmt.setString(7,contact_no);
               int i=stmt.executeUpdate();
               if(i>0){
                    JOptionPane.showMessageDialog(this,"Record Inserted");
                    Login login = new Login();
                    login.setVisible(true);
                    this.dispose();
               }else{
                    JOptionPane.showMessageDialog(this,"Record Not Inserted");                   
               }
               con.close();

           }catch(Exception e){
               e.printStackTrace();
           }
       }
       
       public int getId(){
        ResultSet rs=null;
        try{
            try{
                Class.forName("org.postgresql.Driver");
            } catch (Exception e){
                System.out.println("Fail to Load the JDBC Driver");
            }
               Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/fees_management","postgres","system");
               String sql;
               sql = "select max(id) from signup";
               Statement st=con.createStatement();  
               rs=st.executeQuery(sql);
               while(rs.next()){
                   id=rs.getInt(1);
                    id++;

               }
               con.close();
           }catch(Exception e){
               e.printStackTrace();
           }
        return id;
    }
       
       public void checkContactNo(){
           contact_no=txt_contactno.getText();           
           if(contact_no.length()==10){
               lbl_contactno_error.setText("");
           }else{
                lbl_contactno_error.setText("Contactno should be 10 digit");

           }
       }
       
       public void checkpassworld(){
           passworld=txt_passworld.getText();
           
           if(passworld.length()<8){
               lbl_passworld_error.setText("Passworld should be 8 digit");

           }else{
                lbl_passworld_error.setText("");

           }
       }
       
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_firstname = new javax.swing.JTextField();
        txt_lastname = new javax.swing.JTextField();
        txt_username = new javax.swing.JTextField();
        txt_contactno = new javax.swing.JTextField();
        txt_passworld = new javax.swing.JPasswordField();
        txt_con_passworld = new javax.swing.JPasswordField();
        txt_dob = new com.toedter.calendar.JDateChooser();
        btn_signup = new javax.swing.JButton();
        btn_login = new javax.swing.JButton();
        lbl_passworld_error = new javax.swing.JLabel();
        lbl_contactno_error = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Signup");
        setBackground(new java.awt.Color(0, 153, 153));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 45)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Signup");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(295, 295, 295)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(297, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel1)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 170));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Lastname :");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 111, 173, 38));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 25)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Firstname :");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 52, 183, 38));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Username :");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 168, 147, 38));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Password :");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 224, 147, 38));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Conform Password :");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 284, -1, 38));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("D.O.B :");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 340, 190, 38));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Contact No :");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 404, 190, 38));

        txt_firstname.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        txt_firstname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_firstnameActionPerformed(evt);
            }
        });
        jPanel2.add(txt_firstname, new org.netbeans.lib.awtextra.AbsoluteConstraints(292, 59, 207, -1));

        txt_lastname.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        txt_lastname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_lastnameActionPerformed(evt);
            }
        });
        jPanel2.add(txt_lastname, new org.netbeans.lib.awtextra.AbsoluteConstraints(292, 110, 207, -1));

        txt_username.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        txt_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usernameActionPerformed(evt);
            }
        });
        jPanel2.add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(292, 167, 207, -1));

        txt_contactno.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        txt_contactno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_contactnoActionPerformed(evt);
            }
        });
        txt_contactno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_contactnoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_contactnoKeyReleased(evt);
            }
        });
        jPanel2.add(txt_contactno, new org.netbeans.lib.awtextra.AbsoluteConstraints(292, 403, 207, -1));

        txt_passworld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passworldActionPerformed(evt);
            }
        });
        txt_passworld.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_passworldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_passworldKeyReleased(evt);
            }
        });
        jPanel2.add(txt_passworld, new org.netbeans.lib.awtextra.AbsoluteConstraints(292, 225, 207, 41));

        txt_con_passworld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_con_passworldActionPerformed(evt);
            }
        });
        jPanel2.add(txt_con_passworld, new org.netbeans.lib.awtextra.AbsoluteConstraints(292, 281, 207, 41));
        jPanel2.add(txt_dob, new org.netbeans.lib.awtextra.AbsoluteConstraints(292, 340, 207, 38));

        btn_signup.setBackground(new java.awt.Color(0, 0, 0));
        btn_signup.setFont(new java.awt.Font("Segoe UI", 0, 25)); // NOI18N
        btn_signup.setForeground(new java.awt.Color(255, 255, 255));
        btn_signup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/signup.png"))); // NOI18N
        btn_signup.setText("Signup");
        btn_signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_signupActionPerformed(evt);
            }
        });
        jPanel2.add(btn_signup, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 504, -1, 43));

        btn_login.setBackground(new java.awt.Color(0, 0, 0));
        btn_login.setFont(new java.awt.Font("Segoe UI", 0, 25)); // NOI18N
        btn_login.setForeground(new java.awt.Color(255, 255, 255));
        btn_login.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/login.png"))); // NOI18N
        btn_login.setText("Login");
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });
        jPanel2.add(btn_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(478, 504, 146, 43));

        lbl_passworld_error.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_passworld_error.setForeground(new java.awt.Color(255, 102, 102));
        jPanel2.add(lbl_passworld_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(505, 226, 237, 40));

        lbl_contactno_error.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_contactno_error.setForeground(new java.awt.Color(255, 102, 102));
        jPanel2.add(lbl_contactno_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(505, 414, 237, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 164, 748, 610));

        setSize(new java.awt.Dimension(767, 774));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
        Login login=new Login();
        login.show();
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_loginActionPerformed

    private void txt_passworldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passworldActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txt_passworldActionPerformed

    private void btn_signupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_signupActionPerformed

        // TODO add your handling code here:
        if(validation()){
            insertDetails();
        }
    }//GEN-LAST:event_btn_signupActionPerformed

    private void txt_contactnoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_contactnoKeyPressed
        // TODO add your handling code here:
                checkContactNo();

    }//GEN-LAST:event_txt_contactnoKeyPressed

    private void txt_contactnoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_contactnoKeyReleased
        // TODO add your handling code here:
                        checkContactNo();

    }//GEN-LAST:event_txt_contactnoKeyReleased

    private void txt_passworldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_passworldKeyPressed
        // TODO add your handling code here:
               checkpassworld();

    }//GEN-LAST:event_txt_passworldKeyPressed

    private void txt_passworldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_passworldKeyReleased
        // TODO add your handling code here:
               checkpassworld();

    }//GEN-LAST:event_txt_passworldKeyReleased

    private void txt_firstnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_firstnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_firstnameActionPerformed

    private void txt_lastnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_lastnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_lastnameActionPerformed

    private void txt_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_usernameActionPerformed

    private void txt_con_passworldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_con_passworldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_con_passworldActionPerformed

    private void txt_contactnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_contactnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_contactnoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(signupPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(signupPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(signupPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(signupPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new signupPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_login;
    private javax.swing.JButton btn_signup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbl_contactno_error;
    private javax.swing.JLabel lbl_passworld_error;
    private javax.swing.JPasswordField txt_con_passworld;
    private javax.swing.JTextField txt_contactno;
    private com.toedter.calendar.JDateChooser txt_dob;
    private javax.swing.JTextField txt_firstname;
    private javax.swing.JTextField txt_lastname;
    private javax.swing.JPasswordField txt_passworld;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}

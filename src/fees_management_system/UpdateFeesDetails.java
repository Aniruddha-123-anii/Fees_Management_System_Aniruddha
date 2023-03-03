/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fees_management_system;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author DEL
 */
public final class UpdateFeesDetails extends javax.swing.JFrame {

    /**
     * Creates new form AddFees
     */
    public UpdateFeesDetails() {
        initComponents();
        displayCashFirst();
        fillComboBox();
        Integer receiptNo =getReceiptNo();
        txt_reciptNo.setText(receiptNo.toString());
        
        setRecords();
    }

    public void displayCashFirst(){
        lbl_ddNo.setVisible(false);       
        txt_ddno.setVisible(false);
        lbl_chequeNo.setVisible(false);
        lbl_bankName.setVisible(false);
        txt_chequeNo.setVisible(false);
        txt_bankName.setVisible(false);

    }
    
    public boolean validation(){
        if(txt_resivedFrom.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Please Enter Username");
            return false;
        }if(dateChooser.getDate()==(null)){
            JOptionPane.showMessageDialog(this,"Please Enter Date");
            return false;
        }if(txt_resivedFrom.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Please Enter Username");
            return false;
        }if(txt_amount.getText().equals("") || txt_amount.getText().matches("[0-9]+")==false)
        {
            JOptionPane.showMessageDialog(this,"Please Enter Fees Amount In Numbers");
            return false;
        }
        if(combo_paymentmode.getSelectedItem().toString().equalsIgnoreCase("Cheque")){
            if(txt_chequeNo.getText().equals("")){   
                JOptionPane.showMessageDialog(this,"Please Enter Cheque Number");
                return false;
            }if(txt_bankName.getText().equals("")){   
                JOptionPane.showMessageDialog(this,"Please Enter Bank Name");
                return false;
            }
        }
        if(combo_paymentmode.getSelectedItem().toString().equalsIgnoreCase("DD")){
            if(txt_ddno.getText().equals("")){   
                JOptionPane.showMessageDialog(this,"Please Enter DD Number");
                return false;
            }if(txt_bankName.getText().equals("")){   
                JOptionPane.showMessageDialog(this,"Please Enter Bank Name");
                return false;
            }
        }
        if(combo_paymentmode.getSelectedItem().toString().equalsIgnoreCase("Card")){
            if(txt_bankName.getText().equals("")){   
                JOptionPane.showMessageDialog(this,"Please Enter Bank Name");
                return false;
            }
        }
        
        
        return true;
    }
    public void fillComboBox(){
        try{
            try{
                 Class.forName("org.postgresql.Driver");
             } catch (Exception e){
                 System.out.println("Fail to Load the JDBC Driver");
             }
             Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/fees_management","postgres","system");

            PreparedStatement pst=con.prepareStatement("Select cname from course");
            ResultSet rs=pst.executeQuery();
            while(rs.next()){
                combo_course.addItem(rs.getString("cname"));
            }
            con.close();


        }catch(Exception e){
               e.printStackTrace();
           }
    }
    
    public int getReceiptNo(){
        int receiptNo=0;
        try{
            Connection con=DBConnection.getConnection();
            PreparedStatement pst=con.prepareStatement("Select max(reciept_no) from fees_details");
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                receiptNo=rs.getInt(1);
            }
        }catch(Exception e){
               e.printStackTrace();
           }
                return (receiptNo+1);
    }
    public String updateData(){
        
        String status="";
        
        int reciptNo=Integer.parseInt(txt_reciptNo.getText());
        String StudentName = txt_resivedFrom.getText();
        String paymentMode=combo_paymentmode.getSelectedItem().toString();
        String rollNo=(txt_rollNo.getText()); 
        String chequeNo=(txt_chequeNo.getText());
        String bankName=txt_bankName.getText();
        String ddNo=(txt_ddno.getText());
        String course=combo_course.getSelectedItem().toString();
        String gstNo=(lbl_gstNo.getText());
        double totalAmount=Double.parseDouble(txt_total.getText());
        
        SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
        String date=dateFormate.format(dateChooser.getDate());
        
        double amount=Float.parseFloat(txt_amount.getText());
        double cgst=Double.parseDouble(txt_cgst.getText());
        double sgst=Double.parseDouble(txt_sgst.getText());
        String totalInWords=txt_totalinWords.getText();
        String remark=txt_remark.getText();
        int year1=Integer.parseInt(txt_year1.getText());
        int year2=Integer.parseInt(txt_year2.getText());
        
        
        
        try{
            Connection con=DBConnection.getConnection();
            PreparedStatement pst=con.prepareStatement("update fees_details SET reciept_no= ? ,student_name= ?,"
                    + "payment_mode= ? , cheque_no= ? , bank_name= ? , dd_no= ? , courses= ? , gstin= ? , total_amount= ? , date= ? ,"
                    + "amount= ? , cgst= ? , sgst= ? , total_in_words= ? , remark= ?  , year1 = ? , year2 = ?, roll_no = ? where reciept_no ="+reciptNo
            );


            pst.setInt(1, reciptNo);
            pst.setString(2, StudentName);
            pst.setString(3, paymentMode);
            pst.setString(4, chequeNo);
            pst.setString(5, bankName);
            pst.setString(6, ddNo);
            pst.setString(7, course);
            pst.setString(8, gstNo);
            pst.setDouble(9, totalAmount);
            pst.setDate(10,java.sql.Date.valueOf(date));
            pst.setDouble(11, amount);
            pst.setDouble(12, cgst);
            pst.setDouble(13, sgst);
            pst.setString(14, totalInWords);
            pst.setString(15, remark); 
            pst.setInt(16, year1);
            pst.setInt(17, year2);
            pst.setString(18, rollNo);


            int rowCount=pst.executeUpdate();
            if(rowCount==1){
                status="Successfull";
            }else{
                status="Failed";
            }
                     
        }catch(Exception e){
            e.printStackTrace();
        }
        return status;
    }
    
    public void setRecords(){
        try{
            Connection con =DBConnection.getConnection();
            PreparedStatement pst=con.prepareStatement("Select * from fees_details order by reciept_no desc limit 1");
            ResultSet rs=pst.executeQuery();
            rs.next();
            
            txt_reciptNo.setText(""+(rs.getInt(1)));
            txt_resivedFrom.setText(""+(rs.getString(2)));
            combo_paymentmode.setSelectedItem((rs.getString(3)));
            
             if(!rs.getString(4).equals(""))
            {
                txt_ddno.setVisible(false);
                lbl_ddNo.setVisible(false);
                txt_chequeNo.setText(""+(rs.getString(4)));
            }
            if(!rs.getString(5).equals(""))
            {
                txt_bankName.setText(""+(rs.getString(5)));
            }
            if(!rs.getString(6).equals(""))
            {
                txt_chequeNo.setVisible(false);
                lbl_chequeNo.setVisible(false);
                txt_ddno.setText(""+(rs.getString(6)));
            }
            if(rs.getString(6).equals("") && rs.getString(4).equals(""))
            {
                txt_chequeNo.setVisible(false);
                lbl_chequeNo.setVisible(false);
                txt_ddno.setVisible(false);
                lbl_ddNo.setVisible(false);
            }
            
            combo_course.setSelectedItem((rs.getString(7)));

            txt_courseName.setText(""+(rs.getString(7)));
            txt_total.setText(""+(rs.getString(9)));

            SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
            dateChooser.setDate(dateFormate.parse(""+rs.getString(10)));
            
            txt_amount.setText(""+(rs.getString(11)));
            txt_cgst.setText(""+(rs.getDouble(12)));
            txt_sgst.setText(""+(rs.getString(13)));
            txt_totalinWords.setText(""+(rs.getString(14)));
            txt_remark.setText(""+(rs.getString(15)));
            txt_year1.setText(""+(rs.getString(16)));
            txt_year2.setText(""+(rs.getString(17)));
            txt_rollNo.setText(""+(rs.getString(18)));
            
        }catch(Exception e){
            e.printStackTrace();
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

        panelParent = new javax.swing.JPanel();
        lbl_chequeNo = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbl_gstNo = new javax.swing.JLabel();
        lbl_ddNo = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_ddno = new javax.swing.JTextField();
        txt_reciptNo = new javax.swing.JTextField();
        txt_chequeNo = new javax.swing.JTextField();
        dateChooser = new com.toedter.calendar.JDateChooser();
        txt_bankName = new javax.swing.JTextField();
        lbl_bankName = new javax.swing.JLabel();
        combo_paymentmode = new javax.swing.JComboBox<>();
        panelChield = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_year2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        combo_course = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_year1 = new javax.swing.JTextField();
        txt_totalinWords = new javax.swing.JTextField();
        txt_resivedFrom = new javax.swing.JTextField();
        txt_cgst = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        txt_sgst = new javax.swing.JTextField();
        txt_courseName = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_remark = new javax.swing.JTextArea();
        jLabel20 = new javax.swing.JLabel();
        btn_print = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txt_amount = new javax.swing.JTextField();
        txt_rollNo = new javax.swing.JTextField();
        panelsideBar = new javax.swing.JPanel();
        panalHome = new javax.swing.JPanel();
        btnHome = new javax.swing.JLabel();
        panalSearchRecord = new javax.swing.JPanel();
        btnSearchRecord = new javax.swing.JLabel();
        panalEditCourses = new javax.swing.JPanel();
        btnEditCourses = new javax.swing.JLabel();
        panalCourseList = new javax.swing.JPanel();
        btnCourseList = new javax.swing.JLabel();
        panalViewAllRecord = new javax.swing.JPanel();
        btnViewAllRecord = new javax.swing.JLabel();
        panalBack = new javax.swing.JPanel();
        btnBack = new javax.swing.JLabel();
        panalLogout = new javax.swing.JPanel();
        btnLogout = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update Fees Details");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelParent.setBackground(new java.awt.Color(0, 153, 153));
        panelParent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_chequeNo.setBackground(new java.awt.Color(102, 0, 102));
        lbl_chequeNo.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        lbl_chequeNo.setText("Cheque No :");
        panelParent.add(lbl_chequeNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 160, 30));

        jLabel3.setBackground(new java.awt.Color(102, 0, 102));
        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        jLabel3.setText("Recipt No :  ACA :");
        panelParent.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 170, 40));

        jLabel4.setBackground(new java.awt.Color(102, 0, 102));
        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        jLabel4.setText("Mode Of Payment :");
        panelParent.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 170, 40));

        jLabel8.setBackground(new java.awt.Color(102, 0, 102));
        jLabel8.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        jLabel8.setText("Date :");
        panelParent.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 80, 80, 40));

        lbl_gstNo.setBackground(new java.awt.Color(102, 0, 102));
        lbl_gstNo.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        lbl_gstNo.setText("56DG545FG56");
        panelParent.add(lbl_gstNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 130, 160, 40));

        lbl_ddNo.setBackground(new java.awt.Color(102, 0, 102));
        lbl_ddNo.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        lbl_ddNo.setText("DD No :");
        panelParent.add(lbl_ddNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 170, 30));

        jLabel13.setBackground(new java.awt.Color(102, 0, 102));
        jLabel13.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        jLabel13.setText("GST NO :");
        panelParent.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 130, 90, 40));

        txt_ddno.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_ddno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ddnoActionPerformed(evt);
            }
        });
        panelParent.add(txt_ddno, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, 200, -1));

        txt_reciptNo.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_reciptNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_reciptNoActionPerformed(evt);
            }
        });
        panelParent.add(txt_reciptNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 79, 200, 40));

        txt_chequeNo.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_chequeNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_chequeNoActionPerformed(evt);
            }
        });
        panelParent.add(txt_chequeNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, 200, -1));
        panelParent.add(dateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 90, 190, 30));

        txt_bankName.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_bankName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bankNameActionPerformed(evt);
            }
        });
        panelParent.add(txt_bankName, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 230, 200, 40));

        lbl_bankName.setBackground(new java.awt.Color(102, 0, 102));
        lbl_bankName.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        lbl_bankName.setText("Bank Name :");
        panelParent.add(lbl_bankName, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, 120, 40));

        combo_paymentmode.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        combo_paymentmode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DD", "Cheque", "Cash", "Card" }));
        combo_paymentmode.setSelectedIndex(2);
        combo_paymentmode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_paymentmodeActionPerformed(evt);
            }
        });
        panelParent.add(combo_paymentmode, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 129, 200, 40));

        panelChield.setBackground(new java.awt.Color(0, 153, 153));
        panelChield.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setBackground(new java.awt.Color(102, 0, 102));
        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        jLabel5.setText("Resived From :");
        panelChield.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 150, 30));

        jLabel6.setBackground(new java.awt.Color(102, 0, 102));
        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        jLabel6.setText("The following payment in the college office for the year");
        panelChield.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 510, 40));

        txt_year2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_year2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_year2ActionPerformed(evt);
            }
        });
        panelChield.add(txt_year2, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 50, 100, 40));

        jLabel10.setBackground(new java.awt.Color(102, 0, 102));
        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        jLabel10.setText("to");
        panelChield.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 50, 30, 40));

        txt_total.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });
        panelChield.add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 420, 290, 40));

        jLabel7.setBackground(new java.awt.Color(102, 0, 102));
        jLabel7.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        jLabel7.setText("Amount");
        panelChield.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 180, 80, 30));

        combo_course.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        combo_course.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_courseActionPerformed(evt);
            }
        });
        panelChield.add(combo_course, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 450, 40));

        jLabel12.setBackground(new java.awt.Color(102, 0, 102));
        jLabel12.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        jLabel12.setText("Roll No :");
        panelChield.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 10, 100, 40));
        panelChield.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 560, 450, 20));
        panelChield.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 1220, 20));

        jLabel15.setBackground(new java.awt.Color(102, 0, 102));
        jLabel15.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        jLabel15.setText("Remark :");
        panelChield.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 500, 100, 40));

        jLabel16.setBackground(new java.awt.Color(102, 0, 102));
        jLabel16.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        jLabel16.setText("SGST   9%");
        panelChield.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 350, 110, 40));

        jLabel17.setBackground(new java.awt.Color(102, 0, 102));
        jLabel17.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        jLabel17.setText("Head");
        panelChield.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 180, 80, 30));

        txt_year1.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_year1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_year1ActionPerformed(evt);
            }
        });
        panelChield.add(txt_year1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, 100, 40));

        txt_totalinWords.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_totalinWords.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalinWordsActionPerformed(evt);
            }
        });
        panelChield.add(txt_totalinWords, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 420, 520, 40));

        txt_resivedFrom.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_resivedFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_resivedFromActionPerformed(evt);
            }
        });
        panelChield.add(txt_resivedFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 480, -1));

        txt_cgst.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_cgst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cgstActionPerformed(evt);
            }
        });
        panelChield.add(txt_cgst, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 300, 290, 40));
        panelChield.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 1220, 50));

        txt_sgst.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_sgst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_sgstActionPerformed(evt);
            }
        });
        panelChield.add(txt_sgst, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 350, 290, 40));

        txt_courseName.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_courseName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_courseNameActionPerformed(evt);
            }
        });
        panelChield.add(txt_courseName, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 250, 520, 40));

        jLabel18.setBackground(new java.awt.Color(102, 0, 102));
        jLabel18.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        jLabel18.setText("Course :");
        panelChield.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 80, 30));
        panelChield.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 400, 450, 20));

        jLabel19.setBackground(new java.awt.Color(102, 0, 102));
        jLabel19.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        jLabel19.setText("Receiver Signature:");
        panelChield.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 570, 190, 40));

        txt_remark.setColumns(20);
        txt_remark.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_remark.setRows(5);
        jScrollPane1.setViewportView(txt_remark);

        panelChield.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 490, 530, 110));

        jLabel20.setBackground(new java.awt.Color(102, 0, 102));
        jLabel20.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        jLabel20.setText("Total in Words :");
        panelChield.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 420, 170, 40));

        btn_print.setBackground(new java.awt.Color(0, 0, 51));
        btn_print.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        btn_print.setForeground(new java.awt.Color(255, 255, 255));
        btn_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/printer-.png"))); // NOI18N
        btn_print.setText("Print");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });
        panelChield.add(btn_print, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 640, -1, 50));

        jLabel21.setBackground(new java.awt.Color(102, 0, 102));
        jLabel21.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        jLabel21.setText("Sr No ");
        panelChield.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 80, 30));

        jLabel22.setBackground(new java.awt.Color(102, 0, 102));
        jLabel22.setFont(new java.awt.Font("Trebuchet MS", 1, 19)); // NOI18N
        jLabel22.setText("CGST  9% ");
        panelChield.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, 130, 40));

        txt_amount.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_amount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_amountActionPerformed(evt);
            }
        });
        panelChield.add(txt_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 250, 290, 40));

        txt_rollNo.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_rollNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rollNoActionPerformed(evt);
            }
        });
        panelChield.add(txt_rollNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 10, 190, 40));

        panelParent.add(panelChield, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 1310, 710));

        getContentPane().add(panelParent, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 0, 1310, 990));

        panelsideBar.setBackground(new java.awt.Color(0, 102, 102));
        panelsideBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panalHome.setBackground(new java.awt.Color(0, 102, 102));
        panalHome.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panalHome.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnHome.setFont(new java.awt.Font("Sylfaen", 0, 30)); // NOI18N
        btnHome.setForeground(new java.awt.Color(255, 255, 255));
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/home.png"))); // NOI18N
        btnHome.setText("   Home");
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHomeMouseExited(evt);
            }
        });
        panalHome.add(btnHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 290, 70));

        panelsideBar.add(panalHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 330, 70));

        panalSearchRecord.setBackground(new java.awt.Color(0, 102, 102));
        panalSearchRecord.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panalSearchRecord.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSearchRecord.setFont(new java.awt.Font("Sylfaen", 0, 30)); // NOI18N
        btnSearchRecord.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/search2.png"))); // NOI18N
        btnSearchRecord.setText("Search Record");
        btnSearchRecord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchRecordMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSearchRecordMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSearchRecordMouseExited(evt);
            }
        });
        panalSearchRecord.add(btnSearchRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 290, 70));

        panelsideBar.add(panalSearchRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, 330, 70));

        panalEditCourses.setBackground(new java.awt.Color(0, 102, 102));
        panalEditCourses.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panalEditCourses.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnEditCourses.setFont(new java.awt.Font("Sylfaen", 0, 30)); // NOI18N
        btnEditCourses.setForeground(new java.awt.Color(255, 255, 255));
        btnEditCourses.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/edit2.png"))); // NOI18N
        btnEditCourses.setText(" Edit Courses");
        btnEditCourses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditCoursesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditCoursesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditCoursesMouseExited(evt);
            }
        });
        panalEditCourses.add(btnEditCourses, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 290, 70));

        panelsideBar.add(panalEditCourses, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, 330, 70));

        panalCourseList.setBackground(new java.awt.Color(0, 102, 102));
        panalCourseList.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panalCourseList.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCourseList.setFont(new java.awt.Font("Sylfaen", 0, 30)); // NOI18N
        btnCourseList.setForeground(new java.awt.Color(255, 255, 255));
        btnCourseList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/list.png"))); // NOI18N
        btnCourseList.setText(" Course List");
        btnCourseList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCourseListMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCourseListMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCourseListMouseExited(evt);
            }
        });
        panalCourseList.add(btnCourseList, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 300, 70));

        panelsideBar.add(panalCourseList, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 480, 330, 70));

        panalViewAllRecord.setBackground(new java.awt.Color(0, 102, 102));
        panalViewAllRecord.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panalViewAllRecord.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnViewAllRecord.setFont(new java.awt.Font("Sylfaen", 0, 30)); // NOI18N
        btnViewAllRecord.setForeground(new java.awt.Color(255, 255, 255));
        btnViewAllRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/view all record.png"))); // NOI18N
        btnViewAllRecord.setText("View All Record");
        btnViewAllRecord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnViewAllRecordMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnViewAllRecordMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnViewAllRecordMouseExited(evt);
            }
        });
        panalViewAllRecord.add(btnViewAllRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 300, 70));

        panelsideBar.add(panalViewAllRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 590, 330, 70));

        panalBack.setBackground(new java.awt.Color(0, 102, 102));
        panalBack.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panalBack.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBack.setFont(new java.awt.Font("Sylfaen", 0, 30)); // NOI18N
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/back-button.png"))); // NOI18N
        btnBack.setText("  Back");
        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBackMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBackMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBackMouseExited(evt);
            }
        });
        panalBack.add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 290, 70));

        panelsideBar.add(panalBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 710, 330, 70));

        panalLogout.setBackground(new java.awt.Color(0, 102, 102));
        panalLogout.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panalLogout.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnLogout.setFont(new java.awt.Font("Sylfaen", 0, 30)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/logout.png"))); // NOI18N
        btnLogout.setText("  Logout");
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogoutMouseExited(evt);
            }
        });
        panalLogout.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 290, 70));

        panelsideBar.add(panalLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 820, 330, 70));

        getContentPane().add(panelsideBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 1070));

        setSize(new java.awt.Dimension(1929, 1009));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_year2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_year2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_year2ActionPerformed

    private void txt_ddnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ddnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ddnoActionPerformed

    private void txt_reciptNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_reciptNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_reciptNoActionPerformed

    private void txt_chequeNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_chequeNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_chequeNoActionPerformed

    private void txt_bankNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bankNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bankNameActionPerformed

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalActionPerformed

    private void txt_year1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_year1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_year1ActionPerformed

    private void txt_totalinWordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalinWordsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalinWordsActionPerformed

    private void txt_resivedFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_resivedFromActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_resivedFromActionPerformed

    private void txt_cgstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cgstActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cgstActionPerformed

    private void txt_sgstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_sgstActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_sgstActionPerformed

    private void txt_courseNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_courseNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_courseNameActionPerformed

    private void txt_amountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_amountActionPerformed
        Float amount =Float.parseFloat(txt_amount.getText());
        Float cgst= (amount * 0.09f);
        Float sgst= (amount*0.09f);
        float total=amount+cgst+sgst;
        
        txt_cgst.setText(cgst.toString());
        txt_sgst.setText(sgst.toString());
        txt_total.setText(Float.toString(total));
        txt_totalinWords.setText(NumberToWordsConverter.convert((int)total)+" only");
    }//GEN-LAST:event_txt_amountActionPerformed

    private void combo_paymentmodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_paymentmodeActionPerformed
        if(combo_paymentmode.getSelectedIndex() == 0){
            
            lbl_ddNo.setVisible(true);       
            txt_ddno.setVisible(true);
            lbl_chequeNo.setVisible(false);
            txt_chequeNo.setVisible(false);
            lbl_bankName.setVisible(true);
            txt_bankName.setVisible(true);
                    
        }
        if(combo_paymentmode.getSelectedIndex()==1){
            lbl_chequeNo.setVisible(true);
            txt_chequeNo.setVisible(true);
            lbl_ddNo.setVisible(false);       
            txt_ddno.setVisible(false);
            lbl_bankName.setVisible(true);
            txt_bankName.setVisible(true);
        }
        if(combo_paymentmode.getSelectedIndex()==2){
            displayCashFirst();
            
        }
        if(combo_paymentmode.getSelectedIndex()==3){
            lbl_chequeNo.setVisible(false);
            txt_chequeNo.setVisible(false);
            lbl_ddNo.setVisible(false);       
            txt_ddno.setVisible(false);
            lbl_bankName.setVisible(true);
            txt_bankName.setVisible(true);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_paymentmodeActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        if(validation()==true){
            String result="";
            result=  updateData();
            if(result.equals("Successfull")){
                JOptionPane.showMessageDialog(this,"Record Update Successfully");
                PrintReciept pintReciept =new  PrintReciept();
                pintReciept.show();
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(this,"Faild To Update Data");                
            }
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_printActionPerformed

    private void combo_courseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_courseActionPerformed
        txt_courseName.setText(combo_course.getSelectedItem().toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_courseActionPerformed

    private void txt_rollNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rollNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rollNoActionPerformed

    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked
        Home home= new Home();
        home.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHomeMouseClicked

    private void btnHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseEntered
        Color clr= new Color(0,153,153);
        panalHome.setBackground(clr);        // TODO add your handling code here:
    }//GEN-LAST:event_btnHomeMouseEntered

    private void btnHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseExited
        Color clr= new Color(0,103,103);
        panalHome.setBackground(clr);        // TODO add your handling code here:
    }//GEN-LAST:event_btnHomeMouseExited

    private void btnSearchRecordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchRecordMouseClicked
        SearchRecord searchRecord=new SearchRecord();
        searchRecord.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchRecordMouseClicked

    private void btnSearchRecordMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchRecordMouseEntered
        Color clr= new Color(0,153,153);
        panalSearchRecord.setBackground(clr);// TODO add your handling code here:
    }//GEN-LAST:event_btnSearchRecordMouseEntered

    private void btnSearchRecordMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchRecordMouseExited
        Color clr= new Color(0,103,103);
        panalSearchRecord.setBackground(clr);// TODO add your handling code here:
    }//GEN-LAST:event_btnSearchRecordMouseExited

    private void btnEditCoursesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditCoursesMouseClicked
        EditCourse ec = new EditCourse();
        ec.show();
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditCoursesMouseClicked

    private void btnEditCoursesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditCoursesMouseEntered
        Color clr= new Color(0,153,153);
        panalEditCourses.setBackground(clr);// TODO add your handling code here:
    }//GEN-LAST:event_btnEditCoursesMouseEntered

    private void btnEditCoursesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditCoursesMouseExited
        Color clr= new Color(0,103,103);
        panalEditCourses.setBackground(clr);// TODO add your handling code here:
    }//GEN-LAST:event_btnEditCoursesMouseExited

    private void btnCourseListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCourseListMouseClicked
        ViewCourse vc = new ViewCourse();
        vc.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCourseListMouseClicked

    private void btnCourseListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCourseListMouseEntered
        Color clr= new Color(0,153,153);
        panalCourseList.setBackground(clr);// TODO add your handling code here:
    }//GEN-LAST:event_btnCourseListMouseEntered

    private void btnCourseListMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCourseListMouseExited
        Color clr= new Color(0,103,103);
        panalCourseList.setBackground(clr);// TODO add your handling code here:
    }//GEN-LAST:event_btnCourseListMouseExited

    private void btnViewAllRecordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewAllRecordMouseClicked
        ViewAllRecords allRecords=new ViewAllRecords();
        allRecords.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnViewAllRecordMouseClicked

    private void btnViewAllRecordMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewAllRecordMouseEntered
        Color clr= new Color(0,153,153);
        panalViewAllRecord.setBackground(clr);// TODO add your handling code here:
    }//GEN-LAST:event_btnViewAllRecordMouseEntered

    private void btnViewAllRecordMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewAllRecordMouseExited
        Color clr= new Color(0,103,103);
        panalViewAllRecord.setBackground(clr);// TODO add your handling code here:
    }//GEN-LAST:event_btnViewAllRecordMouseExited

    private void btnBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseClicked
        Home home= new Home();
        home.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBackMouseClicked

    private void btnBackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseEntered
        Color clr= new Color(0,153,153);
        panalBack.setBackground(clr);// TODO add your handling code here:
    }//GEN-LAST:event_btnBackMouseEntered

    private void btnBackMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseExited
        Color clr= new Color(0,103,103);
        panalBack.setBackground(clr);// TODO add your handling code here:
    }//GEN-LAST:event_btnBackMouseExited

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked

        Login login=new Login();
        login.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void btnLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseEntered
        Color clr= new Color(0,153,153);
        panalLogout.setBackground(clr);// TODO add your handling code here:
    }//GEN-LAST:event_btnLogoutMouseEntered

    private void btnLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseExited
        Color clr= new Color(0,103,103);
        panalLogout.setBackground(clr);// TODO add your handling code here:
    }//GEN-LAST:event_btnLogoutMouseExited

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
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateFeesDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnBack;
    private javax.swing.JLabel btnCourseList;
    private javax.swing.JLabel btnEditCourses;
    private javax.swing.JLabel btnHome;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JLabel btnSearchRecord;
    private javax.swing.JLabel btnViewAllRecord;
    private javax.swing.JButton btn_print;
    private javax.swing.JComboBox<String> combo_course;
    private javax.swing.JComboBox<String> combo_paymentmode;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lbl_bankName;
    private javax.swing.JLabel lbl_chequeNo;
    private javax.swing.JLabel lbl_ddNo;
    private javax.swing.JLabel lbl_gstNo;
    private javax.swing.JPanel panalBack;
    private javax.swing.JPanel panalCourseList;
    private javax.swing.JPanel panalEditCourses;
    private javax.swing.JPanel panalHome;
    private javax.swing.JPanel panalLogout;
    private javax.swing.JPanel panalSearchRecord;
    private javax.swing.JPanel panalViewAllRecord;
    private javax.swing.JPanel panelChield;
    private javax.swing.JPanel panelParent;
    private javax.swing.JPanel panelsideBar;
    private javax.swing.JTextField txt_amount;
    private javax.swing.JTextField txt_bankName;
    private javax.swing.JTextField txt_cgst;
    private javax.swing.JTextField txt_chequeNo;
    private javax.swing.JTextField txt_courseName;
    private javax.swing.JTextField txt_ddno;
    private javax.swing.JTextField txt_reciptNo;
    private javax.swing.JTextArea txt_remark;
    private javax.swing.JTextField txt_resivedFrom;
    private javax.swing.JTextField txt_rollNo;
    private javax.swing.JTextField txt_sgst;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField txt_totalinWords;
    private javax.swing.JTextField txt_year1;
    private javax.swing.JTextField txt_year2;
    // End of variables declaration//GEN-END:variables
}

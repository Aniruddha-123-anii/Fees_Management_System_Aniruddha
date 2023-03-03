/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fees_management_system;

import com.toedter.calendar.demo.DateChooserPanelBeanInfo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author DEL
 */
public class GenerateReport extends javax.swing.JFrame {

    /**
     * Creates new form GenerateReport
     */
    
    DefaultTableModel model;
    
    public GenerateReport() {
        initComponents();
         fillComboBox();
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
                combo_courseDetails.addItem(rs.getString("cname"));
            }
            con.close();


        }catch(Exception e){
               e.printStackTrace();
           }
    }
    
    
    public void setRecordToTabel(){
        
         String CourseName = combo_courseDetails.getSelectedItem().toString();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
       
        String fromDate =dateFormat.format(date11.getDate());
        String toDate =dateFormat.format(date22.getDate());
        Double totalAmount = 0.0;
        try {
            Connection con=DBConnection.getConnection();
            PreparedStatement pst=con.prepareStatement("Select * from fees_details where date between ? and ? and courses = ?");
            pst.setDate(1,java.sql.Date.valueOf(fromDate));
            pst.setDate(2,java.sql.Date.valueOf(toDate));
            pst.setString(3,CourseName);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int recieptNo=rs.getInt("reciept_no");              
                String rollNo=rs.getString("roll_no"); 
                String studentName = rs.getString("student_name");
                String course = rs.getString("courses");
                double amount = rs.getDouble("amount");
                String remark = rs.getString("remark");

                totalAmount+=amount;
                
                Object[] obj={recieptNo,rollNo,studentName,course,amount,remark};
                model=(DefaultTableModel)tbl_feesDetails.getModel();
                model.addRow(obj);
                
            }
            lbl_course.setText(CourseName);
            lbl_totalAmount.setText(totalAmount.toString());
            lbl_totalInWords.setText(NumberToWordsConverter.convert(totalAmount.intValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void clearTable(){
            DefaultTableModel model=(DefaultTableModel)tbl_feesDetails.getModel();
            model.setRowCount(1);
            
            
        }
    public void exportToExcel(){
        XSSFWorkbook wb =new XSSFWorkbook();   //This classes is imported from the Libraries.
        XSSFSheet ws = wb.createSheet();
        DefaultTableModel model=(DefaultTableModel)tbl_feesDetails.getModel();
        TreeMap<String,Object[]> map = new TreeMap<>();
        
        map.put("0", new Object[]{model.getColumnName(0),model.getColumnName(1),model.getColumnName(2),model.getColumnName(3),model.getColumnName(4),model.getColumnName(5)});
      
        for (int i = 1; i < model.getRowCount(); i++) {
            map.put(Integer.toString(i) ,new  Object[] {model.getValueAt(i, 0), model.getValueAt(i, 1), model.getValueAt(i, 2), model.getValueAt(i, 3), model.getValueAt(i, 4), model.getValueAt(i, 5)});
        }

        Set<String> id = map.keySet();
        
        
        XSSFRow fRow;
        
        int rowId=0;
        for(String key : id){
            
            fRow = ws.createRow(rowId++);
            
            Object[] value = map.get(key);
            int cellId =0;
            for (Object object : value) {
                XSSFCell cell = fRow.createCell(cellId++);
                cell.setCellValue(object.toString());
            }
        }
        try(FileOutputStream fos =new  FileOutputStream(new File(txt_filePath.getText()))) 
        {
            wb.write(fos);
            JOptionPane.showMessageDialog(this, "File Exported Successfully"+txt_filePath.getText());
        } catch (Exception e) {
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        date22 = new com.toedter.calendar.JDateChooser();
        date11 = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_feesDetails = new javax.swing.JTable();
        btn_print = new javax.swing.JButton();
        btn_browse = new javax.swing.JButton();
        txt_filePath = new javax.swing.JTextField();
        btn_exportToExcel = new javax.swing.JButton();
        btn_submit = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lbl_totalInWords = new javax.swing.JLabel();
        lbl_course = new javax.swing.JLabel();
        lbl_totalAmount = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        combo_courseDetails = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Generate Report");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 25)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Select Date :");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 150, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 25)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("From :");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, 70, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 25)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("To :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 120, 40, 30));
        jPanel1.add(date22, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 120, 190, 40));
        jPanel1.add(date11, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, 190, 40));

        tbl_feesDetails.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        tbl_feesDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Reciept No", "Roll No", "Student Name", "Course", "Amount", "Remark"
            }
        ));
        tbl_feesDetails.setRowHeight(50);
        jScrollPane1.setViewportView(tbl_feesDetails);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, 1170, 670));

        btn_print.setBackground(new java.awt.Color(0, 102, 102));
        btn_print.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        btn_print.setForeground(new java.awt.Color(255, 255, 255));
        btn_print.setText("Print");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });
        jPanel1.add(btn_print, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 190, 130, 40));

        btn_browse.setBackground(new java.awt.Color(0, 102, 102));
        btn_browse.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        btn_browse.setForeground(new java.awt.Color(255, 255, 255));
        btn_browse.setText("Browse");
        btn_browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_browseActionPerformed(evt);
            }
        });
        jPanel1.add(btn_browse, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 270, 100, 40));

        txt_filePath.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_filePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_filePathActionPerformed(evt);
            }
        });
        jPanel1.add(txt_filePath, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 360, 40));

        btn_exportToExcel.setBackground(new java.awt.Color(0, 102, 102));
        btn_exportToExcel.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        btn_exportToExcel.setForeground(new java.awt.Color(255, 255, 255));
        btn_exportToExcel.setText("Export to Excel");
        btn_exportToExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exportToExcelActionPerformed(evt);
            }
        });
        jPanel1.add(btn_exportToExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 270, 170, 40));

        btn_submit.setBackground(new java.awt.Color(0, 102, 102));
        btn_submit.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        btn_submit.setForeground(new java.awt.Color(255, 255, 255));
        btn_submit.setText("Submit");
        btn_submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_submitActionPerformed(evt);
            }
        });
        jPanel1.add(btn_submit, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 130, 40));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_totalInWords.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_totalInWords.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(lbl_totalInWords, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 560, 30));

        lbl_course.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_course.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(lbl_course, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 190, 30));

        lbl_totalAmount.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_totalAmount.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(lbl_totalAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 210, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Course Selected :");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 190, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Total Amount Collected :");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 210, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Total Amount In Words :");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 210, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 10, 590, 230));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 25)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Select Course :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 170, 30));

        combo_courseDetails.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        combo_courseDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_courseDetailsActionPerformed(evt);
            }
        });
        jPanel1.add(combo_courseDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 430, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 0, 1350, 1070));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void btnEditCoursesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditCoursesMouseEntered
        Color clr= new Color(0,153,153);
        panalEditCourses.setBackground(clr);// TODO add your handling code here:
    }//GEN-LAST:event_btnEditCoursesMouseEntered

    private void btnEditCoursesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditCoursesMouseExited
        Color clr= new Color(0,103,103);
        panalEditCourses.setBackground(clr);// TODO add your handling code here:
    }//GEN-LAST:event_btnEditCoursesMouseExited

    private void btnCourseListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCourseListMouseEntered
        Color clr= new Color(0,153,153);
        panalCourseList.setBackground(clr);// TODO add your handling code here:
    }//GEN-LAST:event_btnCourseListMouseEntered

    private void btnCourseListMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCourseListMouseExited
        Color clr= new Color(0,103,103);
        panalCourseList.setBackground(clr);// TODO add your handling code here:
    }//GEN-LAST:event_btnCourseListMouseExited

    private void btnViewAllRecordMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewAllRecordMouseEntered
        Color clr= new Color(0,153,153);
        panalViewAllRecord.setBackground(clr);// TODO add your handling code here:
    }//GEN-LAST:event_btnViewAllRecordMouseEntered

    private void btnViewAllRecordMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewAllRecordMouseExited
        Color clr= new Color(0,103,103);
        panalViewAllRecord.setBackground(clr);// TODO add your handling code here:
    }//GEN-LAST:event_btnViewAllRecordMouseExited

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

    private void btn_browseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_browseActionPerformed

        JFileChooser fileChooser=new JFileChooser();
        fileChooser.showOpenDialog(this);
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyy-MM-dd");
        String date=dateFormat.format(new Date());
        try {
            File f = fileChooser.getSelectedFile();
            String path = f.getAbsolutePath();
            
            path=path+"_"+date+".xlsx";
            txt_filePath.setText(path);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_browseActionPerformed

    private void btn_exportToExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exportToExcelActionPerformed
         exportToExcel();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_exportToExcelActionPerformed

    private void combo_courseDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_courseDetailsActionPerformed
       

        // TODO add your handling code here:
    }//GEN-LAST:event_combo_courseDetailsActionPerformed

    private void txt_filePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_filePathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_filePathActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        SimpleDateFormat Date_Format = new SimpleDateFormat("YYYY-MM-dd"); 
        String datefrom=  Date_Format.format(date11.getDate());
        String dateto=  Date_Format.format(date22.getDate());
       
        MessageFormat header=new MessageFormat("Report From "+datefrom+" To " +dateto);
        MessageFormat footer=new MessageFormat("page{0,number,integer}");
        try {
            tbl_feesDetails.print(JTable.PrintMode.FIT_WIDTH, header, footer);
            
        } catch (Exception e) {
            e.getMessage();
        } 
    }//GEN-LAST:event_btn_printActionPerformed

    private void btn_submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_submitActionPerformed
        clearTable();
        setRecordToTabel();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_submitActionPerformed

    private void btnEditCoursesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditCoursesMouseClicked
        EditCourse ec = new EditCourse();
        ec.show();
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditCoursesMouseClicked

    private void btnCourseListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCourseListMouseClicked
        ViewCourse vc = new ViewCourse();
        vc.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCourseListMouseClicked

    private void btnViewAllRecordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewAllRecordMouseClicked
        ViewAllRecords allRecords=new ViewAllRecords();
        allRecords.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnViewAllRecordMouseClicked

    private void btnBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseClicked
        Home home= new Home();
        home.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBackMouseClicked

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
            java.util.logging.Logger.getLogger(GenerateReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GenerateReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GenerateReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GenerateReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GenerateReport().setVisible(true);
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
    private javax.swing.JButton btn_browse;
    private javax.swing.JButton btn_exportToExcel;
    private javax.swing.JButton btn_print;
    private javax.swing.JButton btn_submit;
    private javax.swing.JComboBox<String> combo_courseDetails;
    private com.toedter.calendar.JDateChooser date11;
    private com.toedter.calendar.JDateChooser date22;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_course;
    private javax.swing.JLabel lbl_totalAmount;
    private javax.swing.JLabel lbl_totalInWords;
    private javax.swing.JPanel panalBack;
    private javax.swing.JPanel panalCourseList;
    private javax.swing.JPanel panalEditCourses;
    private javax.swing.JPanel panalHome;
    private javax.swing.JPanel panalLogout;
    private javax.swing.JPanel panalSearchRecord;
    private javax.swing.JPanel panalViewAllRecord;
    private javax.swing.JPanel panelsideBar;
    private javax.swing.JTable tbl_feesDetails;
    private javax.swing.JTextField txt_filePath;
    // End of variables declaration//GEN-END:variables
}

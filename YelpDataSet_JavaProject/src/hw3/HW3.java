package hw3;

        import java.awt.BorderLayout;
        import java.awt.Color;
        import java.awt.Dimension;
        import java.awt.Label;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.awt.event.MouseEvent;
        import java.awt.event.MouseListener;
        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.ResultSetMetaData;
        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Vector;
        import java.util.logging.Level;
        import java.util.logging.Logger;
        import javax.swing.*;
        import javax.swing.event.ListSelectionEvent;
        import javax.swing.event.ListSelectionListener;
        import javax.swing.event.TableModelEvent;
        import javax.swing.table.DefaultTableModel;
        import javax.swing.table.JTableHeader;


public class HW3 extends JPanel{

    JComboBox ltSec4;
    JComboBox ltSec3;
    JComboBox ltSec2;
    JComboBox ltSec1;
    
    Connection conn = null;
    PreparedStatement stmt = null;
    JTable table;
    ResultSet rs = null;
    JScrollPane scrl;
    JScrollPane scrl1;
    JScrollPane scrl2;
    JScrollPane scrl3;
    JScrollPane scrl4;
    JScrollPane scrl5;
    JScrollPane scrl6;
    JScrollPane scrl7;
        
    JList datL1= new JList();
    JList datL2= new JList();
    JList datL3= new JList();
    JList datL4= new JList();
        
    JDialog revDiag = new JDialog();
    JScrollPane diagP;
    JButton button= new JButton();
    
    List datlis= new ArrayList();
    List datlis2= new ArrayList();
    List datlis3= new ArrayList();
    List datlis7= new ArrayList();
    List datlis8= new ArrayList();
    List datlis9= new ArrayList();
    List datlis10= new ArrayList();
    
    
    JTextField txtCategories;
    
    public HW3() throws ClassNotFoundException, SQLException {
    

        diagP= new JScrollPane(new JLabel("Display"));

        revDiag.add(diagP);
        Class.forName("oracle.jdbc.driver.OracleDriver");
        conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
        String sql="Select * from categories";
        stmt = conn.prepareStatement(sql);
        rs=stmt.executeQuery();
       
         while (rs.next()) {
            String catg = rs.getString(1);
            datlis.add(catg);

        }
         txtCategories = new JTextField();
         txtCategories.setText("CATEGORIES");
         // Categories
        datL1= new JList(datlis.toArray());
        datL1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        datL1.addListSelectionListener(new SelectCatListner());
        scrl = new JScrollPane(datL1);
        scrl.setPreferredSize(new Dimension(150, 500));
        add(scrl, BorderLayout.WEST);

        // Sub- Categories
        datlis2 = new ArrayList();
        datL2= new JList(datlis2.toArray());
        datL2.addListSelectionListener(new ShowAttList());
        datL2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        scrl1 = new JScrollPane(datL2);
        scrl1.setPreferredSize(new Dimension(150, 500));
        add(scrl1, BorderLayout.NORTH);
        
        // Attribute List
        datL3= new JList(datlis3.toArray());
        datL3.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        scrl2 = new JScrollPane(datL3);
        scrl2.setPreferredSize(new Dimension(150, 500));
        add(scrl2, BorderLayout.NORTH);
        
         // Business and ratings box
        scrl3 = new JScrollPane(table);
        scrl3.setPreferredSize(new Dimension(300, 500));
        
        scrl3.addMouseListener(new openPopup());
        
    setPreferredSize(new Dimension(1500,1500));
        datlis7= new ArrayList();
        datlis7.add("Sunday");
        datlis7.add("Monday");
        datlis7.add("Tuesday");
        datlis7.add("Wednesday");
        datlis7.add("Thursday");
        datlis7.add("Friday");
        datlis7.add("Saturday");
         ltSec1 = new JComboBox(datlis7.toArray());

         scrl4 = new JScrollPane(ltSec1);
        add(scrl4, BorderLayout.PAGE_END);

         datlis8= new ArrayList();

        datlis8.add("1:00");
        datlis8.add("2:00");
        datlis8.add("3:00");
        datlis8.add("4:00");
        datlis8.add("5:00");
        datlis8.add("6:00");
        datlis8.add("7:00");
        datlis8.add("8:00");
        datlis8.add("9:00");
        datlis8.add("10:00");
        datlis8.add("11:00");
        datlis8.add("12:00");
        datlis8.add("13:00");
        datlis8.add("14:00");
        datlis8.add("15:00");
        datlis8.add("16:00");
        datlis8.add("17:00");
        datlis8.add("18:00");
        datlis8.add("19:00");
        datlis8.add("20:00");
        datlis8.add("21:00");
        datlis8.add("22:00");
        datlis8.add("23:00");
        datlis8.add("00:00");
         ltSec2 = new JComboBox(datlis8.toArray());
         scrl5 = new JScrollPane(ltSec2);
        add(scrl5);



         ltSec3 = new JComboBox(datlis8.toArray());
         scrl6 = new JScrollPane(ltSec3);
        add(scrl6);

        datlis10= new ArrayList();
        datlis10.add("All Attributes");
        datlis10.add("Any Attribute");
        ltSec4 = new JComboBox(datlis10.toArray());
         scrl7 = new JScrollPane(ltSec4);
        add(scrl7);

       

        

        button.setText("Search");
        button.addActionListener(new ShowReviLis());
        button.setBackground(new Color(107, 84, 153));
        button.setForeground(Color.WHITE);
        add(button);
        add(scrl3, BorderLayout.NORTH);
        setBackground(new Color(178, 163, 209));
	

    }



    public class ShowReviLis implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                rs=null;
            
                String str = datL3.getSelectedValue().toString();
                String str2 =ltSec1.getSelectedItem().toString();
               
                String sql3 = "select distinct business_name,city,state,stars from business, business_attribute where business.business_id=business_attribute.bid and business_attribute.attribute_key like ? and business.business_id in (select operatinghours.bid from operatinghours where days=? )";
                stmt = conn.prepareStatement(sql3);
                stmt.setString(1,str);
                stmt.setString(2,str2);
               
                    rs = stmt.executeQuery();


                try {
                    remove(scrl3);
                    table = new JTable(buildTableModel(rs));
                    table.addMouseListener(new openPopup());
                    setPreferredSize(new Dimension(1500,1500));
                    table.repaint();
                    scrl3 = new JScrollPane(table);
                    setPreferredSize(new Dimension(1500,1500));
                    add(scrl3, BorderLayout.NORTH);
            
                    scrl3.repaint();

setPreferredSize(new Dimension(1500,1500));

                    repaint();
                    revalidate();


                } catch (SQLException et) {
                    et.printStackTrace();
                }


            } catch (SQLException et) {
                et.printStackTrace();
            }

        }


    }

    

    public class SelectCatListner implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent lse) {
            
            rs=null;
            datlis2=new ArrayList();
            String str=datL1.getSelectedValue().toString();
            String sql2="select distinct b_category_sub from business_category where b_category=?";
            try {

                stmt = conn.prepareStatement(sql2);
               stmt.setString(1,str);
                rs=stmt.executeQuery();

                while (rs.next()) {
                    String subcatg = rs.getString(1);
                    datlis2.add(subcatg);

                }

                datL2.setListData(datlis2.toArray());
                scrl1.repaint();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }


            public class ShowAttList implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent lse) {

            if(null!=datL2.getSelectedValue()) {
           
                rs = null;
                datlis3= new ArrayList();
                String str= datL2.getSelectedValue().toString();
                String sql3 = "select distinct attribute_key from business_attribute a where a.bid in (select distinct business_id from business_category where b_category_sub=?)";
                try {
                    stmt = conn.prepareStatement(sql3);
                    stmt.setString(1,str);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        String attr = rs.getString(1);
                        datlis3.add(attr);
            
                    }

                    datL3.setListData(datlis3.toArray());
                    scrl2.repaint();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    public DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();


        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

      
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }
    public static void main(String[] args) {
        try {
            JFrame frame = new JFrame("YELP SYSTEM");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setBackground(new Color(178, 163, 112));
            //frame.setTitle("YELP SYSTEM");
	    frame.setContentPane(new HW3());
            frame.pack();
            frame.setVisible(true);
            frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HW3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HW3.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public class openPopup implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
       
       

            try {
                revDiag.remove(diagP);
                String str= table.getValueAt(table.getSelectedRow(), 0).toString();
                String sql2="select distinct r.review_id,r.rev_date,r.stars,r.text,r.user_id,v.useful from votes v, reviews r where  r.business_id in ( select b.business_id from business b where b.business_name like ?) and v.user_id=r.user_id";
                stmt = conn.prepareStatement(sql2);
                stmt.setString(1,str);
                rs=stmt.executeQuery();
      
                JTable table1 = new JTable(buildTableModel(rs));
                revDiag.setTitle("Reviews Table");
                revDiag.setPreferredSize(new Dimension(600, 600));
      
                diagP= new JScrollPane(table1);
                revDiag.add(diagP);
                revDiag.pack();
                revDiag.setVisible(true);
              
                revDiag.setAlwaysOnTop(true);
                revDiag.setVisible(true);
                revDiag.requestFocus();
                revDiag.toFront();
                //revDiag.setFocus(true);

            } catch (SQLException ex) {
      
                ex.printStackTrace();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
       
        }

        @Override
        public void mouseExited(MouseEvent e) {
       
        }

        @Override
        public void mousePressed(MouseEvent e) {
       
        }

        @Override
        public void mouseReleased(MouseEvent e) {
       
        }

    }


}

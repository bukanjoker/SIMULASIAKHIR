/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Manager;
import controller.Serial;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import model.Jalan;
import model.Lampu;
import util.Warna;

/**
 *
 * @author bukanjoker
 */
public class UI extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    
    Object txtDataIn;
    JLabel[][] cars;
    JLabel[][] trafficlight;
    private Serial serial;
    private Timer timer;
    private Jalan[] jalan;
    private Lampu[] lampu;
    private int[] buffer = new int[4];
    private Manager manager;
    private boolean run = true;
    private long tStart;
    
    public UI() 
    {
        initComponents();
        init();
        this.setLocationRelativeTo(null);
    }
    
    public void init()
    {
        manager = new Manager();
        jalan = manager.getJalan();
        lampu = manager.getLampu();
        serial = new Serial();
        cars();
        trafficlight();
        
        text1.setEditable(false);
        text2.setEditable(false);
        text3.setEditable(false);
        text4.setEditable(false);
        text5.setEditable(false);
        text6.setEditable(false);
        text7.setEditable(false);
        text8.setEditable(false);
        
        jCommPort.setVisible(false);
        lPort.setVisible(false);
//        jPanSearch.setVisible(false);
    }
    
    public void cars() 
    {
        cars = new JLabel[4][11];
        
        cars[3][0] = carL1;
        cars[3][1] = carL2;
        cars[3][2] = carL3;
        cars[3][3] = carL4;
        cars[3][4] = carL5;
        cars[3][5] = carL6;
        cars[3][6] = carL7;
        cars[3][7] = carL8;
        cars[3][8] = carL9;
        cars[3][9] = carL10;
        cars[3][10] = carL11;
        
        cars[1][0] = carU1;
        cars[1][1] = carU2;
        cars[1][2] = carU3;
        cars[1][3] = carU4;
        cars[1][4] = carU5;
        cars[1][5] = carU6;
        cars[1][6] = carU7;
        cars[1][7] = carU8;
        cars[1][8] = carU9;
        cars[1][9] = carU10;
        cars[1][10] = carU11;
        
        cars[2][0] = carR1;
        cars[2][1] = carR2;
        cars[2][2] = carR3;
        cars[2][3] = carR4;
        cars[2][4] = carR5;
        cars[2][5] = carR6;
        cars[2][6] = carR7;
        cars[2][7] = carR8;
        cars[2][8] = carR9;
        cars[2][9] = carR10;
        cars[2][10] = carR11;
        
        cars[0][0] = carB1;
        cars[0][1] = carB2;
        cars[0][2] = carB3;
        cars[0][3] = carB4;
        cars[0][4] = carB5;
        cars[0][5] = carB6;
        cars[0][6] = carB7;
        cars[0][7] = carB8;
        cars[0][8] = carB9;
        cars[0][9] = carB10;
        cars[0][10] = carB11;
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 11; j++) {
                cars[i][j].setVisible(false);
            }
        }
    }
    
    public void trafficlight() 
    {
        trafficlight = new JLabel[3][4];
        
        trafficlight[0][0] = redB;
        trafficlight[0][1] = redU;
        trafficlight[0][2] = redR;
        trafficlight[0][3] = redL;
        
        trafficlight[1][0] = yelB;
        trafficlight[1][1] = yelU;
        trafficlight[1][2] = yelR;
        trafficlight[1][3] = yelL;
        
        trafficlight[2][0] = grenB;
        trafficlight[2][1] = grenU;
        trafficlight[2][2] = grenR;
        trafficlight[2][3] = grenL;
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 0) {
                    trafficlight[i][j].setVisible(true);
                }
                else {
                    trafficlight[i][j].setVisible(false);
                }
            }
        }
    }

    public Jalan[] getJalan() {
        return jalan;
    }

    public void setJalan(Jalan[] jalan) {
        this.jalan = jalan;
    }

    public JLabel[][] getCars() {
        return cars;
    }

    public void setCars(JLabel[][] cars) {
        this.cars = cars;
    }

    public JLabel[][] getTrafficlight() {
        return trafficlight;
    }

    public void setTrafficlight(JLabel[][] trafficlight) {
        this.trafficlight = trafficlight;
    }
    
    public void setManager()
    {
        //set statis/dinamis
        if (!dinamis.isSelected()) 
        {
            manager.setKondisi("statis");
        }
        else
        {
            manager.setKondisi("dinamis");
        }
        
        manager.setSerial(serial);
        manager.setGui(this);
        manager.setWaktu(jComboBox2.getSelectedItem().toString());
        manager.setBuffer(30);
        
        if (jComboBox1.getSelectedItem().toString() == "kedatangan")
        {
            double t1 = Double.parseDouble(text1.getText());
            double t2 = Double.parseDouble(text2.getText());
            double t3 = Double.parseDouble(text3.getText());
            double t4 = Double.parseDouble(text4.getText());
            manager.setIntervalDtg(t1, t2, t3, t4);
        }
        else if (jComboBox1.getSelectedItem().toString() == "kepergian")
        {
            double t1 = Double.parseDouble(text5.getText());
            double t2 = Double.parseDouble(text6.getText());
            double t3 = Double.parseDouble(text7.getText());
            double t4 = Double.parseDouble(text8.getText());
            manager.setIntervalKlr(t1, t2, t3, t4);
        }
        
        manager.init();
        //jalan 1, jalan 2, jalan 3, jalan 4
        //lampu default 60,30,60,60
        manager.setDurasi(60, 30, 60, 60);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        carL1 = new javax.swing.JLabel();
        carL2 = new javax.swing.JLabel();
        carL3 = new javax.swing.JLabel();
        carU1 = new javax.swing.JLabel();
        carU2 = new javax.swing.JLabel();
        carU3 = new javax.swing.JLabel();
        carR1 = new javax.swing.JLabel();
        carR2 = new javax.swing.JLabel();
        carR3 = new javax.swing.JLabel();
        carB1 = new javax.swing.JLabel();
        carB2 = new javax.swing.JLabel();
        carB3 = new javax.swing.JLabel();
        carL4 = new javax.swing.JLabel();
        carL5 = new javax.swing.JLabel();
        carL6 = new javax.swing.JLabel();
        carB4 = new javax.swing.JLabel();
        carB5 = new javax.swing.JLabel();
        carB6 = new javax.swing.JLabel();
        carR4 = new javax.swing.JLabel();
        carR5 = new javax.swing.JLabel();
        carR6 = new javax.swing.JLabel();
        carU4 = new javax.swing.JLabel();
        carU5 = new javax.swing.JLabel();
        carU6 = new javax.swing.JLabel();
        redL = new javax.swing.JLabel();
        grenL = new javax.swing.JLabel();
        yelL = new javax.swing.JLabel();
        grenB = new javax.swing.JLabel();
        yelB = new javax.swing.JLabel();
        redB = new javax.swing.JLabel();
        grenU = new javax.swing.JLabel();
        yelU = new javax.swing.JLabel();
        redU = new javax.swing.JLabel();
        grenR = new javax.swing.JLabel();
        yelR = new javax.swing.JLabel();
        redR = new javax.swing.JLabel();
        carL7 = new javax.swing.JLabel();
        carL8 = new javax.swing.JLabel();
        carL9 = new javax.swing.JLabel();
        carL10 = new javax.swing.JLabel();
        carL11 = new javax.swing.JLabel();
        carB7 = new javax.swing.JLabel();
        carB8 = new javax.swing.JLabel();
        carB9 = new javax.swing.JLabel();
        carB10 = new javax.swing.JLabel();
        carB11 = new javax.swing.JLabel();
        carR7 = new javax.swing.JLabel();
        carR8 = new javax.swing.JLabel();
        carR9 = new javax.swing.JLabel();
        carR10 = new javax.swing.JLabel();
        carR11 = new javax.swing.JLabel();
        carU7 = new javax.swing.JLabel();
        carU8 = new javax.swing.JLabel();
        carU9 = new javax.swing.JLabel();
        carU10 = new javax.swing.JLabel();
        carU11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        start = new javax.swing.JButton();
        text4 = new javax.swing.JTextField();
        text1 = new javax.swing.JTextField();
        text2 = new javax.swing.JTextField();
        text3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        statis = new javax.swing.JRadioButton();
        dinamis = new javax.swing.JRadioButton();
        jPanSearch = new javax.swing.JPanel();
        lPort = new javax.swing.JLabel();
        jCommPort = new javax.swing.JComboBox();
        lSearch = new javax.swing.JLabel();
        bConn = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        text5 = new javax.swing.JTextField();
        text6 = new javax.swing.JTextField();
        text7 = new javax.swing.JTextField();
        text8 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        refresh = new javax.swing.JButton();
        stop = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simulasi Lalu Lintas Perempatan Seokarno Hatta");
        setFocusCycleRoot(false);
        setFocusable(false);
        setMinimumSize(new java.awt.Dimension(1000, 630));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        carL1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, -1, -1));

        carL2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carL2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, -1, -1));

        carL3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carL3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, -1, -1));

        carU1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carU1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 200, -1, -1));

        carU2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carU2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 180, -1, -1));

        carU3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carU3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, -1, -1));

        carR1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carR1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 310, -1, -1));

        carR2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carR2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 310, -1, -1));

        carR3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carR3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 310, -1, -1));

        carB1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 380, -1, -1));

        carB2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carB2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 400, -1, -1));

        carB3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carB3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, -1, -1));

        carL4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carL4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, -1, -1));

        carL5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carL5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, -1, -1));

        carL6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carL6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, -1, -1));

        carB4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carB4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 440, -1, -1));

        carB5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carB5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 460, -1, -1));

        carB6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carB6, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 480, -1, -1));

        carR4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carR4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 310, -1, -1));

        carR5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carR5, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 310, -1, -1));

        carR6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carR6, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 310, -1, -1));

        carU4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carU4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 140, -1, -1));

        carU5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carU5, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 120, -1, -1));

        carU6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carU6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, -1, -1));

        redL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/red.png"))); // NOI18N
        getContentPane().add(redL, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, -1, -1));

        grenL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/green.png"))); // NOI18N
        getContentPane().add(grenL, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 30, 30));

        yelL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/yellow.png"))); // NOI18N
        getContentPane().add(yelL, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 160, 30, 30));

        grenB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/green.png"))); // NOI18N
        getContentPane().add(grenB, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 470, 30, 30));

        yelB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/yellow.png"))); // NOI18N
        getContentPane().add(yelB, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 430, 30, 40));

        redB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/red.png"))); // NOI18N
        getContentPane().add(redB, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 400, -1, -1));

        grenU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/green.png"))); // NOI18N
        getContentPane().add(grenU, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, 30, 30));

        yelU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/yellow.png"))); // NOI18N
        getContentPane().add(yelU, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 130, 30, 40));

        redU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/red.png"))); // NOI18N
        getContentPane().add(redU, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 170, -1, -1));

        grenR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/green.png"))); // NOI18N
        getContentPane().add(grenR, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 410, 30, 30));

        yelR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/yellow.png"))); // NOI18N
        getContentPane().add(yelR, new org.netbeans.lib.awtextra.AbsoluteConstraints(438, 410, 30, 30));

        redR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/red.png"))); // NOI18N
        getContentPane().add(redR, new org.netbeans.lib.awtextra.AbsoluteConstraints(403, 410, -1, 30));

        carL7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carL7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, -1, -1));

        carL8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carL8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, -1, -1));

        carL9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carL9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, -1, -1));

        carL10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carL10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, -1));

        carL11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/buffermaxLeftRight.png"))); // NOI18N
        getContentPane().add(carL11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, -1, -1));

        carB7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carB7, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 500, -1, -1));

        carB8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carB8, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 520, -1, -1));

        carB9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carB9, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 540, -1, -1));

        carB10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carB10, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 560, -1, -1));

        carB11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/buffermaxUpDown.png"))); // NOI18N
        getContentPane().add(carB11, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 580, -1, -1));

        carR7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carR7, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 310, -1, -1));

        carR8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carR8, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 310, -1, -1));

        carR9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carR9, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 310, -1, -1));

        carR10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carLeftRight.png"))); // NOI18N
        getContentPane().add(carR10, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 310, -1, -1));

        carR11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/buffermaxLeftRight.png"))); // NOI18N
        getContentPane().add(carR11, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 310, -1, -1));

        carU7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carU7, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, -1, -1));

        carU8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carU8, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, -1, -1));

        carU9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carU9, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, -1, -1));

        carU10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/carUpDown.png"))); // NOI18N
        getContentPane().add(carU10, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, -1, -1));

        carU11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/buffermaxUpDown.png"))); // NOI18N
        getContentPane().add(carU11, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/rsc/background.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        start.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        start.setText("START");
        start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startActionPerformed(evt);
            }
        });
        getContentPane().add(start, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 470, 80, 30));

        text4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text4ActionPerformed(evt);
            }
        });
        getContentPane().add(text4, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 400, 50, -1));

        text1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text1ActionPerformed(evt);
            }
        });
        getContentPane().add(text1, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 310, 50, -1));

        text2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text2ActionPerformed(evt);
            }
        });
        getContentPane().add(text2, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 340, 50, -1));

        text3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text3ActionPerformed(evt);
            }
        });
        getContentPane().add(text3, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 370, 50, -1));

        jLabel3.setText("Jalan 4");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 400, -1, -1));

        jLabel4.setText("Kepergian");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 280, -1, -1));

        jLabel5.setText("Jalan 2");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 340, -1, -1));

        jLabel6.setText("Jalan 3");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 370, -1, -1));

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel11.setText("DATA :");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 200, -1, 20));

        buttonGroup1.add(statis);
        statis.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        statis.setSelected(true);
        statis.setText("statis");
        getContentPane().add(statis, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 160, -1, -1));

        buttonGroup1.add(dinamis);
        dinamis.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dinamis.setText("dinamis");
        getContentPane().add(dinamis, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 160, -1, -1));

        jPanSearch.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(51, 204, 0)));
        jPanSearch.setLayout(null);

        lPort.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lPort.setText("Port  :");
        jPanSearch.add(lPort);
        lPort.setBounds(20, 10, 60, 30);

        jCommPort.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCommPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCommPortActionPerformed(evt);
            }
        });
        jPanSearch.add(jCommPort);
        jCommPort.setBounds(60, 10, 140, 30);

        lSearch.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lSearch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lSearch.setText("Search Port");
        lSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lSearch.setOpaque(true);
        lSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lSearchMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lSearchMouseEntered(evt);
            }
        });
        jPanSearch.add(lSearch);
        lSearch.setBounds(60, 10, 90, 30);

        bConn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bConn.setText("Connect");
        bConn.setBorder(null);
        bConn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bConnActionPerformed(evt);
            }
        });
        jPanSearch.add(bConn);
        bConn.setBounds(60, 50, 100, 30);

        getContentPane().add(jPanSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 40, 220, 90));

        jLabel15.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel15.setText("SET INTERVAL");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 250, -1, -1));

        text5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text5ActionPerformed(evt);
            }
        });
        getContentPane().add(text5, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 310, 50, -1));

        text6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text6ActionPerformed(evt);
            }
        });
        getContentPane().add(text6, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 340, 50, -1));

        text7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text7ActionPerformed(evt);
            }
        });
        getContentPane().add(text7, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 370, 50, -1));

        text8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text8ActionPerformed(evt);
            }
        });
        getContentPane().add(text8, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 400, 50, -1));

        jLabel7.setText("Jalan 1");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 310, -1, -1));

        jLabel8.setText("Kedatangan");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 280, -1, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "default", "kedatangan", "kepergian" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 280, -1, -1));

        refresh.setText("REFRESH");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });
        getContentPane().add(refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 530, -1, 30));

        stop.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        stop.setText("STOP");
        stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopActionPerformed(evt);
            }
        });
        getContentPane().add(stop, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 470, 80, 30));

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel12.setText("TIPE LAMPU :");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 160, -1, 20));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "siang", "sore", "pagi" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 200, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void text4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text4ActionPerformed

    private void text1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text1ActionPerformed

    private void text2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text2ActionPerformed

    private void text3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text3ActionPerformed

    private void startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startActionPerformed
        setManager();
        manager.start();
        System.out.println("start - "+jComboBox2.getSelectedItem().toString()+" - "+manager.getKondisi());
        tStart = System.currentTimeMillis();
    }//GEN-LAST:event_startActionPerformed

    private void jCommPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCommPortActionPerformed
//        if (serial.getConnected() == true) 
//        {
//            serial.disconnect();
//        }
    }//GEN-LAST:event_jCommPortActionPerformed

    private void lSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lSearchMouseClicked
        // TODO add your handling code here:
        jCommPort.setVisible(true);
        lPort.setVisible(true);
        lSearch.setVisible(false);
        serial.cekSerialPort();
        bConn.enable();
        run = false;
        
        for (int i = 0; i < serial.getPortName().size(); i++) {
            jCommPort.addItem(serial.getPortName().get(i));
        }
    }//GEN-LAST:event_lSearchMouseClicked
    
    private void formWindowClosed(java.awt.event.WindowEvent evt) {                                  
            // TODO add your handling code here:
            if (serial.getConnected() == true) {
                serial.disconnect();
            }
    }         
    
    private void lSearchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lSearchMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lSearchMouseEntered

    private void bConnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bConnActionPerformed
        // TODO add your handling code here:
        System.out.println(bConn.getText());
        if (bConn.getText().equals("Connect")) {
            serial.connect((String)jCommPort.getSelectedItem());
            if (serial.getConnected() == true) {
                if (serial.initIOStream() == true) {
                    serial.initListener();
                }
            }
            run = true;
            bConn.setText("Disconnect");

        } else {
            if (serial.getConnected() == true) {
                serial.kirimData("5");
                serial.disconnect();
                run = false;
                bConn.setText("Connect");
            }
            manager.stop();
            init();
            lSearch.setVisible(true);
            bConn.disable();
        }
    }//GEN-LAST:event_bConnActionPerformed

    private void text5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text5ActionPerformed

    private void text6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text6ActionPerformed

    private void text7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text7ActionPerformed

    private void text8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text8ActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
//        manager.print();
//       
        long time = System.currentTimeMillis() - tStart;
        System.out.println("Timer: "+time/1000+" detik");
        System.out.println("");
        
//        serial.kirimData("0");
    }//GEN-LAST:event_refreshActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if (jComboBox1.getSelectedItem().toString() == "kedatangan")
        {
            text1.setEditable(true);
            text2.setEditable(true);
            text3.setEditable(true);
            text4.setEditable(true);
            text5.setEditable(false);
            text6.setEditable(false);
            text7.setEditable(false);
            text8.setEditable(false);
            
            run = false;
        }
        else if (jComboBox1.getSelectedItem().toString() == "kepergian")
        {
            text1.setEditable(false);
            text2.setEditable(false);
            text3.setEditable(false);
            text4.setEditable(false);
            text5.setEditable(true);
            text6.setEditable(true);
            text7.setEditable(true);
            text8.setEditable(true);
            
            run = false;
        }
        else
        {
            text1.setEditable(false);
            text2.setEditable(false);
            text3.setEditable(false);
            text4.setEditable(false);
            text5.setEditable(false);
            text6.setEditable(false);
            text7.setEditable(false);
            text8.setEditable(false);
            
            run = true;
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopActionPerformed
        if (manager.isAlive()) 
        {
            run = false;
            manager.stop();
            manager.end();
            init();
            System.out.println("stop");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Sorry program not running yet");
        }
    }//GEN-LAST:event_stopActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        
    }//GEN-LAST:event_jComboBox2ActionPerformed

    /**
     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new UI().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton bConn;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel carB1;
    private javax.swing.JLabel carB10;
    private javax.swing.JLabel carB11;
    private javax.swing.JLabel carB2;
    private javax.swing.JLabel carB3;
    private javax.swing.JLabel carB4;
    private javax.swing.JLabel carB5;
    private javax.swing.JLabel carB6;
    private javax.swing.JLabel carB7;
    private javax.swing.JLabel carB8;
    private javax.swing.JLabel carB9;
    private javax.swing.JLabel carL1;
    private javax.swing.JLabel carL10;
    private javax.swing.JLabel carL11;
    private javax.swing.JLabel carL2;
    private javax.swing.JLabel carL3;
    private javax.swing.JLabel carL4;
    private javax.swing.JLabel carL5;
    private javax.swing.JLabel carL6;
    private javax.swing.JLabel carL7;
    private javax.swing.JLabel carL8;
    private javax.swing.JLabel carL9;
    private javax.swing.JLabel carR1;
    private javax.swing.JLabel carR10;
    private javax.swing.JLabel carR11;
    private javax.swing.JLabel carR2;
    private javax.swing.JLabel carR3;
    private javax.swing.JLabel carR4;
    private javax.swing.JLabel carR5;
    private javax.swing.JLabel carR6;
    private javax.swing.JLabel carR7;
    private javax.swing.JLabel carR8;
    private javax.swing.JLabel carR9;
    private javax.swing.JLabel carU1;
    private javax.swing.JLabel carU10;
    private javax.swing.JLabel carU11;
    private javax.swing.JLabel carU2;
    private javax.swing.JLabel carU3;
    private javax.swing.JLabel carU4;
    private javax.swing.JLabel carU5;
    private javax.swing.JLabel carU6;
    private javax.swing.JLabel carU7;
    private javax.swing.JLabel carU8;
    private javax.swing.JLabel carU9;
    private javax.swing.JRadioButton dinamis;
    private javax.swing.JLabel grenB;
    private javax.swing.JLabel grenL;
    private javax.swing.JLabel grenR;
    private javax.swing.JLabel grenU;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    public javax.swing.JComboBox jCommPort;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanSearch;
    private javax.swing.JLabel lPort;
    private javax.swing.JLabel lSearch;
    private javax.swing.JLabel redB;
    private javax.swing.JLabel redL;
    private javax.swing.JLabel redR;
    private javax.swing.JLabel redU;
    private javax.swing.JButton refresh;
    private javax.swing.JButton start;
    private javax.swing.JRadioButton statis;
    private javax.swing.JButton stop;
    private javax.swing.JTextField text1;
    private javax.swing.JTextField text2;
    private javax.swing.JTextField text3;
    private javax.swing.JTextField text4;
    private javax.swing.JTextField text5;
    private javax.swing.JTextField text6;
    private javax.swing.JTextField text7;
    private javax.swing.JTextField text8;
    private javax.swing.JLabel yelB;
    private javax.swing.JLabel yelL;
    private javax.swing.JLabel yelR;
    private javax.swing.JLabel yelU;
    // End of variables declaration//GEN-END:variables
}

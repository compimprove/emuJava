/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emujava;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author jBillu
 */
public class TestCaseAnalysis extends javax.swing.JPanel {

    public static boolean PAINT = false;
    /**
     * Creates new form TestCaseAnalysis
     */
    public TestCaseAnalysis() {
        initComponents();
    }

    public void paint( Graphics g ) {
        super.paint( g );
        if( MutationAnalysis.PAINT ) {

            g.setColor( Color.BLACK );
            g.drawString( "Generated Test Cases", 495, 42 );
            g.drawString( "Effective Test Cases", 685, 42 );
            
            g.setColor( Color.YELLOW );
            g.fillRect( 460, 30, 30, 15 );
            g.setColor( Color.GREEN );
            g.fillRect( 650, 30, 30, 15 );
    
            int genTests = Integer.parseInt( TestCaseAnalysis.jLabel11.getText() );
            int effTests = Integer.parseInt( TestCaseAnalysis.jLabel12.getText() );;
            int sum = genTests + effTests;
            
            int angle = 0;
            int angle2 = 0;
            if( sum>0 ) {
                angle2 = ( int )( ( genTests * 360 ) / sum );
            } //END if STATEMENT
            g.setColor( Color.YELLOW );
            g.fillArc( 510, 75, 160, 170, angle, angle2 );
            
            angle = angle + angle2;
            angle2 = 360 - angle;
            g.setColor( Color.GREEN );
            g.fillArc( 510, 75, 160, 170, angle, angle2 );
        } //END if STATEMENT
    } //END paint() MEHTOD
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "   Test Case Analysis   ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 0, 102)));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("The below figures represent test case analysis. It shows information about");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 25, -1, -1));

        jLabel2.setText("number of iterations executed, number of test cases generated and");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 45, -1, -1));

        jLabel3.setText("executed, total number of effective test cases, amount of time elapsed ");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 65, -1, -1));

        jLabel4.setText("in test case generation process.");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 85, -1, -1));

        jLabel5.setText("Iterations Performed ");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 115, -1, -1));

        jLabel6.setText("Test Cases Executed");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 135, -1, -1));

        jLabel7.setText("Effective Test Cases");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 155, -1, -1));

        jLabel8.setText("Effective/Generated Ratio");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 175, -1, -1));

        jLabel9.setText("Time Elapsed");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 195, -1, -1));

        jLabel10.setText("0");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 115, -1, -1));

        jLabel11.setText("0");
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 135, -1, -1));

        jLabel12.setText("0");
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 155, -1, -1));

        jLabel13.setText("0");
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 175, -1, -1));

        jLabel14.setText("0");
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 195, -1, -1));
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    public static javax.swing.JLabel jLabel10;
    public static javax.swing.JLabel jLabel11;
    public static javax.swing.JLabel jLabel12;
    public static javax.swing.JLabel jLabel13;
    public static javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}

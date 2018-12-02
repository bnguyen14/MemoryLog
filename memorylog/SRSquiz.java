/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memorylog;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Bao Luu
 */
public class SRSquiz extends javax.swing.JFrame {
  //static QuizTester qt;
  LocalDate date;
  SubjectTester st = new SubjectTester();
  ArrayList<DateQuestion> todayquestions = new ArrayList<DateQuestion>();
  ArrayList<DateQuestion> missedquestions = new ArrayList<DateQuestion>();
  boolean previouslyWrong = false;
  boolean questionsToAnswer = false;
  //public static int count;
  /**
   * Creates new form QuizRun
   */
  public SRSquiz() {
    initComponents();
    setSize(800,600);
    //qt = new QuizTester();
    date = LocalDate.now();
    //st.load();
    todayquestions = st.run();
    if(todayquestions!=null){
        jLabel2.setText(todayquestions.get(0).getQuestion());
        jLabel4.setText(todayquestions.size() + " Questions"); // dung hang thu nhat so cau hoi
    }else{
        
        //new Main().setVisible(true);
        //new Popup("No Questions Today").setVisible(true);
        jLabel4.setText("No Questions Today");
        jButton1.setText("Exit");
        //this.setVisible(false);
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

        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));
        jPanel2.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Ravie", 0, 14)); // NOI18N
        jLabel1.setText("Answer:");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(380, 180, 90, 30);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Ravie", 0, 14)); // NOI18N
        jLabel2.setText("jLabel2");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.setVerifyInputWhenFocusTarget(false);
        jLabel2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel2.add(jLabel2);
        jLabel2.setBounds(380, 80, 290, 90);

        jButton1.setFont(new java.awt.Font("Ravie", 0, 14)); // NOI18N
        jButton1.setText("Submit");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(570, 390, 100, 40);

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Ravie", 0, 14)); // NOI18N
        jTextArea2.setRows(5);
        jPanel2.add(jTextArea2);
        jTextArea2.setBounds(380, 230, 290, 130);

        jLabel4.setFont(new java.awt.Font("Ravie", 0, 14)); // NOI18N
        jLabel4.setText("Question");
        jLabel4.setMaximumSize(new java.awt.Dimension(87, 26));
        jLabel4.setMinimumSize(new java.awt.Dimension(87, 26));
        jLabel4.setPreferredSize(new java.awt.Dimension(87, 26));
        jPanel2.add(jLabel4);
        jLabel4.setBounds(380, 30, 290, 30);

        jLabel5.setIcon(new javax.swing.ImageIcon("C:\\Users\\Bao Luu\\Documents\\NetBeansProjects\\Log-Master\\pokemon_PNG123.png")); // NOI18N
        jPanel2.add(jLabel5);
        jLabel5.setBounds(30, 160, 300, 330);

        jLabel6.setFont(new java.awt.Font("Ravie", 0, 14)); // NOI18N
        jLabel6.setText("Try it!!!");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(140, 120, 160, 70);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        OurDate today = new OurDate(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        Popup popup;
        if(todayquestions==null){
            new Main().setVisible(true);
            this.setVisible(false);
        }else if(todayquestions.size() > 0) {
            previouslyWrong = false;
            for(int i = 0;i<missedquestions.size();i++) {
                if(todayquestions.get(0).getQuestion().equals(missedquestions.get(i).getQuestion())) {
                    previouslyWrong = true;
                }
            }
            switch(st.ask(todayquestions.get(0), jTextArea2.getText())) {
                case 0:
                    String output = "Inorrect! The Answer Is:\n";
                    for(int i = 0;i<todayquestions.get(0).getAnswers().size();i++) {
                        output += "  " + todayquestions.get(0).getAnswers().get(i);
                    }
                    popup = new Popup(output);
                    //popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    jTextArea2.setText("");
                    popup.setVisible(true);
                    
                    st.match(todayquestions.get(0)).decreasePeriod(today);
                    todayquestions.get(0).decreasePeriod(today);
                    
                    if(previouslyWrong == false)
                        missedquestions.add(todayquestions.get(0));

                    todayquestions.add(new DateQuestion(todayquestions.get(0)));
                    todayquestions.remove(0);
                break;
                case 1:
                    popup = new Popup("Correct!");
                    //popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    jTextArea2.setText("");
                    popup.setVisible(true);
                    
                    if(previouslyWrong == false)
                        st.match(todayquestions.get(0)).increasePeriod(new OurDate(today), st.questions);
                    todayquestions.remove(0);
                break;
            }
            if(todayquestions.size()!=0){
                jLabel2.setText(todayquestions.get(0).getQuestion());
                jLabel4.setText(todayquestions.size() + " Questions");
            }else{
                Collections.sort(st.questions, new DateQuestionComparator());
                st.save();
                new Main().setVisible(true);
                popup = new Popup("No More Questions Today");
                jTextArea2.setText("");
                popup.setVisible(true);
                this.setVisible(false);
            }
        }
        
    }//GEN-LAST:event_jButton1MouseClicked
    
  /**
   * @param args the command line arguments
   */
    /*
  public static void main(String args[]) {
    /* Set the Nimbus look and feel 
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     
    
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(SRSquiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(SRSquiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(SRSquiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(SRSquiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
     //Create and display the form 
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        
        new SRSquiz().setVisible(true);
        
      }
      
    });
    
  }*/
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}

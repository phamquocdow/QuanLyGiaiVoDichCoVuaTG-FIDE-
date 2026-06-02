package view;

import model.User;
import view.pairing.MatchPairingDetailFrm;
import view.updateResult.UpdateResultFrm;

public class OrganizerHomeFrm extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(OrganizerHomeFrm.class.getName());

    private User user;

    public OrganizerHomeFrm(User user) {
        initComponents();
        this.user = user;
        userName.setText(user.getFullname());
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        btnUpdateResult = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        userName = new javax.swing.JLabel();
        btnViewStatistics = new javax.swing.JButton();
        btnPairing = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnUpdateResult.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnUpdateResult.setText("Cập nhật kết quả");
        btnUpdateResult.addActionListener(this::btnUpdateResultActionPerformed);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("Trang chủ");

        userName.setText("user's name");

        btnViewStatistics.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnViewStatistics.setText("Thống Kê");
        btnViewStatistics.addActionListener(this::btnViewStatisticsActionPerformed);

        btnPairing.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnPairing.setText("Xếp cặp thi đấu");
        btnPairing.addActionListener(this::btnPairingActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnUpdateResult, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnPairing, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(btnViewStatistics, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(userName)
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdateResult)
                    .addComponent(btnViewStatistics))
                .addGap(31, 31, 31)
                .addComponent(btnPairing)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewStatisticsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewStatisticsActionPerformed
        (new StatisticMenuFrm(user)).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnViewStatisticsActionPerformed

    private void btnUpdateResultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateResultActionPerformed
       (new SelectRoundFrm(user, "update")).setVisible(true);
       this.dispose();
    }//GEN-LAST:event_btnUpdateResultActionPerformed

    private void btnPairingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPairingActionPerformed
       (new SelectRoundFrm(user, "pairing")).setVisible(true);
       this.dispose();
    }//GEN-LAST:event_btnPairingActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPairing;
    private javax.swing.JButton btnUpdateResult;
    private javax.swing.JButton btnViewStatistics;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel userName;
    // End of variables declaration//GEN-END:variables

}

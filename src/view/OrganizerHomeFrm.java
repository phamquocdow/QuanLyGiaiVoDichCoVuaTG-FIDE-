
package view;

import model.User;


public class OrganizerHomeFrm extends javax.swing.JFrame {

    
    private User user;
    public OrganizerHomeFrm(User user) {
        initComponents();
        this.user = user;
        userName.setText(user.getFullName() + " (" + user.getRole() + ")");
        
        if ("Manager".equals(user.getRole())) {
            btnManagePlayers.setVisible(true);
            btnUpdateResult.setVisible(false);
            btnRanking.setVisible(false);
            btnEloStats.setVisible(false);
            btnPairing.setVisible(false);
        } else if ("Organizer".equals(user.getRole())) {
            btnManagePlayers.setVisible(false);
            btnUpdateResult.setVisible(true);
            btnRanking.setVisible(true);
            btnEloStats.setVisible(true);
            btnPairing.setVisible(true);
        }
        this.setLocationRelativeTo(null);
    }

    
    @SuppressWarnings("unchecked")
    
    private void initComponents() {

        btnManagePlayers = new javax.swing.JButton();
        btnUpdateResult = new javax.swing.JButton();
        btnRanking = new javax.swing.JButton();
        btnEloStats = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        userName = new javax.swing.JLabel();
        btnPairing = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnManagePlayers.setBackground(new java.awt.Color(204, 204, 204));
        btnManagePlayers.setFont(new java.awt.Font("Segoe UI", 0, 18)); 
        btnManagePlayers.setText("Quản lý thông tin kỳ thủ");
        btnManagePlayers.addActionListener(this::btnManagePlayersActionPerformed);

        btnUpdateResult.setBackground(new java.awt.Color(204, 204, 204));
        btnUpdateResult.setFont(new java.awt.Font("Segoe UI", 0, 18)); 
        btnUpdateResult.setText("Cập nhật kết quả");
        btnUpdateResult.addActionListener(this::btnUpdateResultActionPerformed);

        btnRanking.setBackground(new java.awt.Color(204, 204, 204));
        btnRanking.setFont(new java.awt.Font("Segoe UI", 0, 18)); 
        btnRanking.setText("Xem bảng xếp hạng");
        btnRanking.addActionListener(this::btnRankingActionPerformed);

        btnEloStats.setBackground(new java.awt.Color(204, 204, 204));
        btnEloStats.setFont(new java.awt.Font("Segoe UI", 0, 18)); 
        btnEloStats.setText("Thống kê thay đổi Elo");
        btnEloStats.addActionListener(this::btnEloStatsActionPerformed);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); 
        jLabel1.setText("Trang chủ");

        userName.setText("user's name");

        btnPairing.setFont(new java.awt.Font("Segoe UI", 0, 18)); 
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
                        .addGap(21, 21, 21)
                        .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnManagePlayers, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRanking, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEloStats, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdateResult, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPairing, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(userName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(46, 46, 46)
                .addComponent(btnManagePlayers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRanking)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEloStats)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdateResult)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPairing, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        pack();
    }

    private void btnManagePlayersActionPerformed(java.awt.event.ActionEvent evt) {
        (new ManagePlayerFrm(user)).setVisible(true);
        this.dispose();
    }

    private void btnUpdateResultActionPerformed(java.awt.event.ActionEvent evt) {
        (new SelectRoundFrm(user, "update")).setVisible(true);
        this.dispose();
    }

    private void btnRankingActionPerformed(java.awt.event.ActionEvent evt) {
        (new SelectRoundFrm(user, "ranking")).setVisible(true);
        this.dispose();
    }

    private void btnEloStatsActionPerformed(java.awt.event.ActionEvent evt) {
        (new EloStatsFrm(user)).setVisible(true);
        this.dispose();
    }

    private void btnPairingActionPerformed(java.awt.event.ActionEvent evt) {
        (new SelectRoundFrm(user, "pairing")).setVisible(true);
        this.dispose();
    }

    

    
    private javax.swing.JButton btnEloStats;
    private javax.swing.JButton btnManagePlayers;
    private javax.swing.JButton btnPairing;
    private javax.swing.JButton btnRanking;
    private javax.swing.JButton btnUpdateResult;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel userName;
    

}

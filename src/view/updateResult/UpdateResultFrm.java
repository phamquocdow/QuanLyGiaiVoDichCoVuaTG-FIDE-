package view.updateResult;

import controller.PlayerDAO;
import controller.ResultDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Match;
import model.Player;
import model.Result;
import model.User;

public class UpdateResultFrm extends javax.swing.JFrame implements ActionListener {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UpdateResultFrm.class.getName());

    private Match match;
    private User user;
    ArrayList<Result> listResult = new ArrayList<Result>();

    public UpdateResultFrm(User user, Match match) {
        initComponents();
        this.match = match;
        this.user = user;
        matchName.setText(match.getName());
        ResultDAO resultDAO = new ResultDAO();
        listResult = resultDAO.getResultMatch(match);
        player1name.setText(listResult.get(0).getPlayer().getName());
        player2name.setText(listResult.get(1).getPlayer().getName());
        txtPlayer1Elo.setText(listResult.get(0).getPlayer().getEloRating()+"");
        txtPlayer2Elo.setText(listResult.get(1).getPlayer().getEloRating()+"");
        btnUpdate.addActionListener(this);
        btnCancel.addActionListener(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button1 = new java.awt.Button();
        jLabel1 = new javax.swing.JLabel();
        matchName = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPlayer1Score = new javax.swing.JTextField();
        txtPlayer2Score = new javax.swing.JTextField();
        txtPlayer1Elo = new javax.swing.JTextField();
        txtPlayer2Elo = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        player1name = new javax.swing.JLabel();
        player2name = new javax.swing.JLabel();

        button1.setLabel("button1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("Cập nhật kết quả");

        matchName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        matchName.setText("Match's name");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Score:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Score:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Elo:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Elo:");

        btnUpdate.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnUpdate.setText("Cập nhật");

        btnCancel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnCancel.setText("Huỷ");

        player1name.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        player1name.setText("player1's name");

        player2name.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        player2name.setText("player2's name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(192, 192, 192)
                .addComponent(matchName, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(119, 119, 119))
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnCancel)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtPlayer1Score, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                                .addComponent(txtPlayer1Elo))))
                    .addComponent(player1name, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtPlayer2Elo))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txtPlayer2Score, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(65, 65, 65))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnUpdate)
                        .addGap(90, 90, 90))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(player2name, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(matchName)
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(player1name)
                    .addComponent(player2name))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPlayer1Score, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtPlayer2Score, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtPlayer2Elo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtPlayer1Elo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnUpdate))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnUpdate;
    private java.awt.Button button1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel matchName;
    private javax.swing.JLabel player1name;
    private javax.swing.JLabel player2name;
    private javax.swing.JTextField txtPlayer1Elo;
    private javax.swing.JTextField txtPlayer1Score;
    private javax.swing.JTextField txtPlayer2Elo;
    private javax.swing.JTextField txtPlayer2Score;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnUpdate) {
            try {
                ResultDAO resultDAO = new ResultDAO();
                boolean updateResult1 = resultDAO.updateResult(listResult.get(0), Float.parseFloat(txtPlayer1Score.getText()), Float.parseFloat(txtPlayer1Elo.getText()));
                boolean updateResult2 = resultDAO.updateResult(listResult.get(1), Float.parseFloat(txtPlayer2Score.getText()), Float.parseFloat(txtPlayer2Elo.getText()));
                PlayerDAO playerDAO = new PlayerDAO();
                boolean updateElo1 = playerDAO.updateElo(listResult.get(0).getPlayer(), Float.parseFloat(txtPlayer1Elo.getText()));
                boolean updateElo2 = playerDAO.updateElo(listResult.get(1).getPlayer(), Float.parseFloat(txtPlayer2Elo.getText()));
                if (updateResult1 && updateResult2 && updateElo1 && updateElo2) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
                }
                (new SelectRoundFrm(user)).setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == btnCancel) {
            this.dispose();
        }
    }
}

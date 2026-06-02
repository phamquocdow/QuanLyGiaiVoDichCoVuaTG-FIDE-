package view.pairing;

import view.SelectRoundFrm;
import controller.RoundDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Round;
import model.Standing;
import model.User;
import service.PairingService;
import service.StandingService;
import view.OrganizerHomeFrm;


public class MatchPairingDetailFrm extends javax.swing.JFrame implements ActionListener {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MatchPairingDetailFrm.class.getName());
    private Round currentRound;
    private Round previousRoundData;
    private ArrayList<Standing> standings = new ArrayList<>();
    private ArrayList<PairingService.PairingRow> pairingItems = new ArrayList<>();
    private JTable tblStanding;
    private JTable tblPairing;
    private User user;
    private final StandingService standingService = new StandingService();
    private final PairingService pairingService = new PairingService();

    
    public MatchPairingDetailFrm(User user, Round currentRound) {
        initComponents();
        
        
        
        this.user = user;
        this.currentRound = currentRound;
        tblStanding = new JTable();
        tblPairing = new JTable();
        standingList.setViewportView(tblStanding);
        pairingList.setViewportView(tblPairing);
        this.setLocationRelativeTo(null);
        
        roundName.setText("Vòng " + currentRound.getRoundNum());
        roundName1.setText("Vòng " + currentRound.getRoundNum());
        int previousRoundNumber = currentRound.getRoundNum() - 1;
        if (previousRoundNumber >= 1) {
            previousRoundData = new RoundDAO().getLatestRoundByNumber(previousRoundNumber);
            if (previousRoundData != null) {
                previousRound.setText("Vòng " + previousRoundData.getRoundNum());
            }
        }
        loadStandings();
        setPairingViewVisible(false);
        btnPair.addActionListener(this);
        btnSave.addActionListener(this);
        btnBack.addActionListener(this);
    }

    private void loadStandings() {
        String[] columns = {"Hạng", "Tên", "Tổng điểm", "Tổng điểm đối thủ đã gặp", "Điểm Elo hiện tại"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        if (previousRoundData == null) {
            previousRound.setText("Không có vòng trước");
        } else {
            previousRound.setText("Vòng " + previousRoundData.getRoundNum());
        }
        
        roundName.setText("Vòng " + currentRound.getRoundNum());
        roundName1.setText("Vòng " + currentRound.getRoundNum());
        
        standings = standingService.loadRoundStandings(previousRoundData);
        
        if (standings.isEmpty()) {
            tableModel.addRow(new Object[] {"-", "No data", "-", "-", "-"});
        } else {
            for (Standing standing : standings) {
                tableModel.addRow(new Object[] {
                    standing.getRank(),
                    standing.getPlayer().getName(),
                    standing.getTotalScore(),
                    standing.getTotalOpponentScore(),
                    standing.getCurrentElo()
                });
            }
        }
        
        tblStanding.setModel(tableModel);
        
        tblStanding.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblStanding.getColumnModel().getColumn(1).setPreferredWidth(250);
        tblStanding.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblStanding.getColumnModel().getColumn(3).setPreferredWidth(150);
        tblStanding.getColumnModel().getColumn(4).setPreferredWidth(100);
    }

    private void generatePairings() {
        pairingItems = pairingService.createPairings(standings);
        if (pairingItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No previous round standings to pair.", "Error", JOptionPane.WARNING_MESSAGE);
            setPairingViewVisible(false);
            return;
        }
        refreshPairingTable();
        setPairingViewVisible(true);
    }

    private void setPairingViewVisible(boolean visible) {
        jLabel3.setVisible(visible);
        roundName1.setVisible(visible);
        pairingList.setVisible(visible);
        btnSave.setVisible(visible);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void refreshPairingTable() {
        String[] columns = {"No.", "Player 1", "Player 2"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (PairingService.PairingRow pairing : pairingItems) {
            String second = pairing.getPlayer2() == null ? "Bye" : pairing.getPlayer2().getName();
            tableModel.addRow(new Object[] {
                pairing.getMatchNum(),
                pairing.getPlayer1().getName(),
                second
            });
        }
        tblPairing.setModel(tableModel);
        
        tblPairing.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblPairing.getColumnModel().getColumn(1).setPreferredWidth(250);
        tblPairing.getColumnModel().getColumn(2).setPreferredWidth(250);
    }

    private void savePairings() {
        if (pairingItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You need to select Pair before Saving.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean saved = pairingService.savePairings(pairingItems, currentRound);

        if (saved) {
            JOptionPane.showMessageDialog(this, "Lưu cặp đấu thành công.", "Success", JOptionPane.INFORMATION_MESSAGE);
            new OrganizerHomeFrm(user).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lưu cặp đấu thất bại. Hãy thử lại", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPair) {
            generatePairings();
        } else if (e.getSource() == btnSave) {
            savePairings();
        } else if (e.getSource() == btnBack) {
            new SelectRoundFrm(user, "pairing").setVisible(true);
            this.dispose();
        }
    }

    
    @SuppressWarnings("unchecked")
    
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        roundName = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        standingList = new javax.swing.JScrollPane();
        jLabel2 = new javax.swing.JLabel();
        btnPair = new javax.swing.JButton();
        pairingList = new javax.swing.JScrollPane();
        btnSave = new javax.swing.JButton();
        previousRound = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        roundName1 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        roundName.setText("round's name");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); 
        jLabel1.setText("Xếp cặp thi đấu");

        jLabel2.setText("Bảng xếp hạng sau:");

        btnPair.setText("Xếp cặp");

        btnSave.setText("lưu");

        previousRound.setText("previousRound's name");

        jLabel3.setText("Danh sách cặp thi đấu cho vòng");

        roundName1.setText("round's name");

        btnBack.setText("Quay lại");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(roundName, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(340, 340, 340)
                        .addComponent(btnPair))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(previousRound, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(roundName1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(standingList, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pairingList, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnBack)
                                .addGap(220, 220, 220)
                                .addComponent(btnSave))
                            .addComponent(jLabel1))))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(previousRound))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(standingList, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPair)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roundName1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pairingList, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnBack))
                .addGap(22, 22, 22))
        );

        pack();
    }
    
    private javax.swing.JButton btnPair;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnBack;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane pairingList;
    private javax.swing.JLabel previousRound;
    private javax.swing.JLabel roundName;
    private javax.swing.JLabel roundName1;
    private javax.swing.JScrollPane standingList;
    
}


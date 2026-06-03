
package view;

import controller.RoundDAO;
import controller.TournamentDAO;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import model.Round;
import model.User;
import view.pairing.MatchPairingDetailFrm;

import javax.swing.table.DefaultTableModel;


public class SelectRoundFrm extends javax.swing.JFrame implements MouseListener {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SelectRoundFrm.class.getName());

    
    private User user;
    private String mode;
    ArrayList<Round> listRound = new ArrayList<Round>();

    public SelectRoundFrm(User user, String mode) {
        initComponents();
        
        
        this.setLocationRelativeTo(null);
        this.user = user;
        this.mode = mode;
        userName.setText(user.getFullname());
        RoundDAO roundDAO = new RoundDAO();
        listRound = roundDAO.getRoundList((new TournamentDAO()).getLatestTournamentID());
        String data[][] = new String[listRound.size()][1];
        for (int i = 0; i < listRound.size(); i++) {
            Round r = listRound.get(i);
            data[i][0] = "Vòng " + r.getRoundNum();
        }
        String[] columns = {"Chọn vòng"};
        DefaultTableModel dtm = new DefaultTableModel(data, columns);
        tblListRound.setModel(dtm);
        tblListRound.addMouseListener(this);
    }

    
    @SuppressWarnings("unchecked")
    
    private void initComponents() {

        userName = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane = new javax.swing.JScrollPane();
        tblListRound = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        userName.setText("user's name");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); 
        jLabel1.setText("Chọn vòng");

        tblListRound.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "Chọn vòng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane.setViewportView(tblListRound);

        btnBack.setText("Quay lại");
        btnBack.addActionListener(e -> {
            new OrganizerHomeFrm(user).setVisible(true);
            this.dispose();
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(btnBack))))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(userName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBack)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }

    

    
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTable tblListRound;
    private javax.swing.JLabel userName;
    private javax.swing.JButton btnBack;
    

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tblListRound.rowAtPoint(e.getPoint());

        if (row >= 0) {
            Round selectedRound = listRound.get(row);
            if ("pairing".equals(mode)) {
                new MatchPairingDetailFrm(user, selectedRound).setVisible(true);
            } else if ("ranking".equals(mode)) {
                new RankingTableFrm(user, selectedRound).setVisible(true);
            } else {
                new SelectMatchFrm(user, selectedRound).setVisible(true);
            }
            this.dispose();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}


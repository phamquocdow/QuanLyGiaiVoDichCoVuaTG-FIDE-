package view;

import dao.ResultDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Round;
import model.Standing;
import model.User;

public class RankingsFrm extends JFrame implements ActionListener {

    private User user;
    private Round round;
    private JTable tblRankings;

    public RankingsFrm(User user, Round round) {
        initComponents();
        this.user = user;
        this.round = round;
        setTitle("Standings for round " + round.getRoundNum());
        loadRankings();
    }

    private void loadRankings() {
        ArrayList<Standing> standings = new ResultDAO().getRoundStandings(round);
        String[] columns = {"Rank", "Name", "Total Score", "Opponent Score", "Current Elo"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if (standings.isEmpty()) {
            model.addRow(new Object[] {"-", "No data", "-", "-", "-"});
        } else {
            for (Standing standing : standings) {
                model.addRow(new Object[] {
                    standing.getRank(),
                    standing.getPlayer().getName(),
                    standing.getTotalScore(),
                    standing.getTotalOpponentScore(),
                    standing.getCurrentElo()
                });
            }
        }
        tblRankings.setModel(model);
        
        tblRankings.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblRankings.getColumnModel().getColumn(1).setPreferredWidth(250);
        tblRankings.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblRankings.getColumnModel().getColumn(3).setPreferredWidth(120);
        tblRankings.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        roundLabel.setText("Round " + round.getRoundNum());
    }

    private javax.swing.JLabel roundLabel;

    private void initComponents() {
        javax.swing.JLabel titleLabel = new javax.swing.JLabel();
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane();
        tblRankings = new javax.swing.JTable();
        javax.swing.JButton btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        titleLabel.setFont(new java.awt.Font("Segoe UI", 0, 24));
        titleLabel.setText("Standings");

        tblRankings.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"Rank", "Name", "Total Score", "Opponent Score", "Current Elo"}
        ));
        scrollPane.setViewportView(tblRankings);

        roundLabel = new javax.swing.JLabel();
        roundLabel.setText("Round ?");

        btnBack.setText("Back");
        btnBack.addActionListener(this);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel)
                    .addComponent(roundLabel)
                    .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBack))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBack)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new SelectRoundFrm(user, "ranking").setVisible(true);
        dispose();
    }
}

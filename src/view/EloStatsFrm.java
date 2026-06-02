package view;

import dao.PlayerDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Player;
import model.User;

public class EloStatsFrm extends JFrame implements ActionListener {

    private User user;
    private PlayerDAO playerDAO = new PlayerDAO();
    private JTable tblPlayers;

    public EloStatsFrm(User user) {
        initComponents();
        this.user = user;
        setTitle("Elo Statistics");
        loadEloStats();
    }

    private void loadEloStats() {
        ArrayList<Player> players = playerDAO.getAllPlayers();
        players.sort((a, b) -> Float.compare(b.getEloRating(), a.getEloRating()));
        String[] columns = {"ID", "Name", "Elo"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        float totalElo = 0f;
        for (Player player : players) {
            model.addRow(new Object[] {player.getID(), player.getName(), player.getEloRating()});
            totalElo += player.getEloRating();
        }
        tblPlayers.setModel(model);
        
        tblPlayers.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblPlayers.getColumnModel().getColumn(1).setPreferredWidth(250);
        tblPlayers.getColumnModel().getColumn(2).setPreferredWidth(100);
        
        String summary = players.isEmpty() ? "No players." : String.format("Players: %d   Avg Elo: %.1f", players.size(), totalElo / players.size());
        summaryLabel.setText(summary);
    }

    private javax.swing.JLabel summaryLabel;

    private void initComponents() {
        javax.swing.JLabel titleLabel = new javax.swing.JLabel();
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane();
        tblPlayers = new javax.swing.JTable();
        javax.swing.JButton btnRefresh = new javax.swing.JButton();
        javax.swing.JButton btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        titleLabel.setFont(new java.awt.Font("Segoe UI", 0, 24));
        titleLabel.setText("Elo Statistics");

        tblPlayers.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Name", "Elo"}
        ));
        scrollPane.setViewportView(tblPlayers);

        summaryLabel = new javax.swing.JLabel();
        summaryLabel.setText(" ");

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(this);

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
                    .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(summaryLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBack)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(summaryLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRefresh)
                    .addComponent(btnBack))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = ((javax.swing.JButton) e.getSource()).getText();
        if ("Refresh".equals(action)) {
            loadEloStats();
        } else if ("Back".equals(action)) {
            new OrganizerHomeFrm(user).setVisible(true);
            dispose();
        }
    }
}

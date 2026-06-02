package view;

import dao.PlayerDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Player;
import model.User;

public class ManagePlayerFrm extends JFrame implements ActionListener {

    private User user;
    private PlayerDAO playerDAO = new PlayerDAO();
    private ArrayList<Player> players = new ArrayList<>();
    private JTable tblPlayers;

    public ManagePlayerFrm(User user) {
        initComponents();
        this.user = user;
        setTitle("Quản lý kỳ thủ");
        loadPlayers();
    }

    private void loadPlayers() {
        players = playerDAO.getAllPlayers();
        String[] columns = {"ID", "Tên", "Năm sinh", "Quốc gia", "Elo", "Ghi chú"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Player player : players) {
            model.addRow(new Object[] {
                player.getID(),
                player.getName(),
                player.getBornYear(),
                player.getNation(),
                player.getEloRating(),
                player.getNote()
            });
        }
        tblPlayers.setModel(model);
    }

    private void initComponents() {
        javax.swing.JLabel titleLabel = new javax.swing.JLabel();
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane();
        tblPlayers = new javax.swing.JTable();
        javax.swing.JButton btnAdd = new javax.swing.JButton();
        javax.swing.JButton btnUpdateElo = new javax.swing.JButton();
        javax.swing.JButton btnRefresh = new javax.swing.JButton();
        javax.swing.JButton btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        titleLabel.setFont(new java.awt.Font("Segoe UI", 0, 24));
        titleLabel.setText("Manage Players");

        tblPlayers.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Name", "Born Year", "Nation", "Elo", "Note"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tblPlayers.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblPlayers.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblPlayers.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblPlayers.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblPlayers.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblPlayers.getColumnModel().getColumn(5).setPreferredWidth(150);
        scrollPane.setViewportView(tblPlayers);

        btnAdd.setText("Add Player");
        btnAdd.addActionListener(this);

        btnUpdateElo.setText("Update Elo");
        btnUpdateElo.addActionListener(this);

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
                    .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdateElo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBack)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnUpdateElo)
                    .addComponent(btnRefresh)
                    .addComponent(btnBack))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private Player getSelectedPlayer() {
        int selectedRow = tblPlayers.getSelectedRow();
        if (selectedRow < 0 || selectedRow >= players.size()) {
            return null;
        }
        return players.get(selectedRow);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = ((javax.swing.JButton) e.getSource()).getText();
        if ("Add Player".equals(action)) {
            addPlayer();
        } else if ("Update Elo".equals(action)) {
            updateElo();
        } else if ("Refresh".equals(action)) {
            loadPlayers();
        } else if ("Back".equals(action)) {
            new OrganizerHomeFrm(user).setVisible(true);
            dispose();
        }
    }

    private void addPlayer() {
        try {
            String name = JOptionPane.showInputDialog(this, "Player Name:", "Add Player", JOptionPane.PLAIN_MESSAGE);
            if (name == null || name.trim().isEmpty()) {
                return;
            }
            String bornYearText = JOptionPane.showInputDialog(this, "Born Year:", "Add Player", JOptionPane.PLAIN_MESSAGE);
            if (bornYearText == null || bornYearText.trim().isEmpty()) {
                return;
            }
            int bornYear = Integer.parseInt(bornYearText.trim());
            String nation = JOptionPane.showInputDialog(this, "Nation:", "Add Player", JOptionPane.PLAIN_MESSAGE);
            if (nation == null) {
                nation = "";
            }
            String eloText = JOptionPane.showInputDialog(this, "Initial Elo:", "1500");
            float elo = (eloText == null || eloText.trim().isEmpty()) ? 1500f : Float.parseFloat(eloText.trim());
            String note = JOptionPane.showInputDialog(this, "Note:", "");
            Player player = new Player();
            player.setName(name.trim());
            player.setBornYear(bornYear);
            player.setNation(nation.trim());
            player.setEloRating(elo);
            player.setNote(note == null ? "" : note.trim());
            if (playerDAO.insertPlayer(player)) {
                JOptionPane.showMessageDialog(this, "Player added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadPlayers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add player.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input value.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateElo() {
        Player player = getSelectedPlayer();
        if (player == null) {
            JOptionPane.showMessageDialog(this, "Please select a player to update Elo.", "Information", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String eloText = JOptionPane.showInputDialog(this, "New Elo for " + player.getName() + ":", player.getEloRating());
            if (eloText == null || eloText.trim().isEmpty()) {
                return;
            }
            float newElo = Float.parseFloat(eloText.trim());
            if (playerDAO.updateElo(player, newElo)) {
                JOptionPane.showMessageDialog(this, "Elo updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadPlayers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update Elo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Elo must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.eloStats;

import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableRowSorter;

import controller.EloStatsDAO;
import controller.TournamentDAO;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import model.EloStats;
import model.Tournament;
import model.User;
import view.StatisticMenuFrm;

/**
 *
 * @author Storie
 */
public class EloStatsFrm extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(EloStatsFrm.class.getName());
    private final User user;
    private final TournamentDAO tournamentDAO = new TournamentDAO();
    private final EloStatsDAO eloStatsDAO = new EloStatsDAO();
    private java.util.List<Tournament> tournaments;

    /**
     * Creates new form EloStatsFrm
     */
    public EloStatsFrm(User user) {
        this.user = user;
        initComponents();

        TableRowSorter<?> sorter = (TableRowSorter<?>) eloStatsTable.getRowSorter();
        sorter.setSortKeys(java.util.List.of(
                new RowSorter.SortKey(7, SortOrder.DESCENDING),
                new RowSorter.SortKey(6, SortOrder.DESCENDING)));

        tournamentComboBox.addActionListener(e -> loadStatsForSelectedTournament());
        fileExportButton.addActionListener(this::exportToCSV);
        loadTournaments();

        this.setLocationRelativeTo(null);
    }

    private void loadTournaments() {
        tournaments = tournamentDAO.getAllFinishedTournaments();
        if (tournaments == null || tournaments.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu giải đấu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        tournaments.sort(Comparator.comparing(Tournament::getYear).reversed());
        tournamentComboBox.removeAllItems();
        for (Tournament t : tournaments) {
            String display = t.getName() + " (" + t.getYear() + ")";
            tournamentComboBox.addItem(display);
        }
        tournamentComboBox.setSelectedIndex(0);
        loadStatsForSelectedTournament();
    }

    private void loadStatsForSelectedTournament() {
        int idx = tournamentComboBox.getSelectedIndex();
        if (idx < 0 || tournaments == null || idx >= tournaments.size())
            return;
        Tournament selected = tournaments.get(idx);
        ArrayList<EloStats> stats = eloStatsDAO.getEloStats(selected);
        updateTable(stats);
    }

    private void updateTable(ArrayList<EloStats> stats) {
        DefaultTableModel model = (DefaultTableModel) eloStatsTable.getModel();
        model.setRowCount(0);
        if (stats == null || stats.isEmpty())
            return;

        int stt = 1;
        for (EloStats es : stats) {
            if (es.getPlayer() == null)
                continue;
            float oldElo = es.getEloRatingBefore();
            float newElo = es.getEloRatingAfter();
            float change = newElo - oldElo;
            Object[] row = {
                    stt++,
                    es.getPlayer().getFideID(),
                    es.getPlayer().getName(),
                    es.getPlayer().getBornYear(),
                    es.getPlayer().getNation(),
                    oldElo,
                    newElo,
                    change
            };
            model.addRow(row);
        }
    }

    private void exportToCSV(ActionEvent evt) {
        if (tournamentComboBox.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Chưa có giải đấu nào để xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file thống kê Elo");
        String defaultFileName = "Elo_Stats_"
                + tournamentComboBox.getSelectedItem().toString().replaceAll("[/\\\\:*?\"<>|]", "_") + ".csv";
        fileChooser.setSelectedFile(new File(defaultFileName));
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files (*.csv)", "csv"));

        if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        File file = fileChooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".csv")) {
            file = new File(file.getAbsolutePath() + ".csv");
        }

        try {
            service.ExportService.exportToCSV((DefaultTableModel) eloStatsTable.getModel(), file);
            JOptionPane.showMessageDialog(this, "Xuất file thành công!\n" + file.getAbsolutePath(), "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            logger.severe("Lỗi xuất file: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Lỗi khi ghi file: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        eloStatsTable = new javax.swing.JTable();
        fileExportButton = new javax.swing.JButton();
        tournamentComboBox = new javax.swing.JComboBox<>();
        btnReturn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Thống kê thay đổi Elo");

        eloStatsTable.setAutoCreateRowSorter(true);
        eloStatsTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null, null }
                },
                new String[] {
                        "TT", "Mã", "Tên", "Năm sinh", "Quốc tịch", "Elo cũ", "Elo mới", "Thay đổi"
                }) {
            Class[] types = new Class[] {
                    java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class,
                    java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        eloStatsTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(eloStatsTable);

        fileExportButton.setText("Xuất file");

        tournamentComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A" }));

        btnReturn.setText("Quay lại");
        btnReturn.addActionListener(this::btnReturnActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(130, 130, 130)
                                                .addComponent(tournamentComboBox,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(fileExportButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 75,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                layout.createSequentialGroup()
                                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE)
                                                        .addComponent(jScrollPane1,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 506,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(178, 178, 178)
                                .addComponent(jLabel1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(fileExportButton)
                                        .addComponent(tournamentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnReturn))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                .addContainerGap()));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnReturnActionPerformed
        (new StatisticMenuFrm(user)).setVisible(true);
        this.dispose();
    }// GEN-LAST:event_btnReturnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReturn;
    private javax.swing.JTable eloStatsTable;
    private javax.swing.JButton fileExportButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> tournamentComboBox;
    // End of variables declaration//GEN-END:variables
}

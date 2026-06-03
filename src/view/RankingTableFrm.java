package view;

import controller.DataStore;
import model.User;
import view.viewLeaderboad.SelectRoundFrm;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;
import javax.swing.RowSorter.SortKey;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class RankingTableFrm extends javax.swing.JFrame {

    private User user;
    private int round;
    private DataStore store;

    public RankingTableFrm(User user, int round, DataStore store) {
        this.user = user;
        this.round = round;
        this.store = store;
        initComponents();
        loadRanking();
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        jLabel1 = new JLabel("Bảng xếp hạng - Vòng " + round);
        jScrollPane1 = new JScrollPane();
        tblRanking = new JTable();
        btnBack = new JButton();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Bảng xếp hạng - Vòng " + round);

        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        tblRanking.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblRanking.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblRanking.setRowHeight(26);
        tblRanking.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblRanking.setGridColor(new Color(220, 220, 220));

        tblRanking.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "ID", "Tên", "Năm sinh", "Quốc tịch",
                        "Tổng điểm", "Tổng điểm đối thủ đã gặp", "Elo tức thời"
                }) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        });

        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i : new int[] { 0, 2, 4, 5, 6 }) {
            tblRanking.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        jScrollPane1.setViewportView(tblRanking);

        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnBack.setBackground(new Color(204, 204, 204));
        btnBack.setFocusPainted(false);
        btnBack.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160), 1, true));
        btnBack.setText("Quay lại Chọn Vòng");
        btnBack.addActionListener(e -> {
            new SelectRoundFrm(user).setVisible(true);
            dispose();
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 960,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                        javax.swing.GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 38,
                        javax.swing.GroupLayout.PREFERRED_SIZE));

        setSize(1020, 600);
    }

    private void loadRanking() {
        List<DataStore.PlayerRecord> list = store.getRankingForRound(round);

        DefaultTableModel m = (DefaultTableModel) tblRanking.getModel();
        m.setRowCount(0);

        for (DataStore.PlayerRecord p : list) {
            m.addRow(new Object[] {
                    p.id,
                    p.name,
                    p.year,
                    p.nation,
                    p.points,
                    p.oppPoints,
                    p.elo
            });
        }

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(m);
        sorter.setComparator(0, Comparator.comparingInt(o -> ((Number) o).intValue()));
        sorter.setComparator(2, Comparator.comparingInt(o -> ((Number) o).intValue()));
        sorter.setComparator(4, Comparator.comparingDouble(o -> ((Number) o).doubleValue()));
        sorter.setComparator(5, Comparator.comparingDouble(o -> ((Number) o).doubleValue()));
        sorter.setComparator(6, Comparator.comparingInt(o -> ((Number) o).intValue()));

        tblRanking.setRowSorter(sorter);

        List<SortKey> keys = new ArrayList<>();
        keys.add(new SortKey(4, SortOrder.DESCENDING));
        keys.add(new SortKey(5, SortOrder.DESCENDING));
        keys.add(new SortKey(6, SortOrder.DESCENDING));
        sorter.setSortKeys(keys);
        sorter.sort();
    }

    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JTable tblRanking;
    private JButton btnBack;
}
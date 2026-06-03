package view.viewLeaderboad;

import controller.DataStore;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import model.User;
import view.RankingTableFrm;
import view.StatisticMenuFrm;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class SelectRoundFrm extends javax.swing.JFrame {

    private User      user;
    private DataStore store;

    private static final Color COLOR_DONE    = new Color(0, 128, 0);
    private static final Color COLOR_ONGOING = new Color(200, 120, 0);
    private static final Color COLOR_PENDING = new Color(150, 150, 150);

    public SelectRoundFrm(User user) {
        this.user  = user;
        this.store = new DataStore();
        initComponents();
        loadRounds();
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        JLabel lblTitle   = new JLabel("Chọn vòng đấu để xem bảng xếp hạng");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblUser = new JLabel("Người dùng: " + user.getFullname());
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        tblRounds = new JTable();
        tblRounds.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tblRounds.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        tblRounds.setRowHeight(28);
        tblRounds.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblRounds.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Vòng đấu", "Trạng thái"}) {
            public boolean isCellEditable(int r, int c) { return false; }
        });

        tblRounds.getColumnModel().getColumn(1).setCellRenderer(
            new DefaultTableCellRenderer() {
                @Override
                public java.awt.Component getTableCellRendererComponent(
                        JTable table, Object value, boolean isSelected,
                        boolean hasFocus, int row, int col) {
                    java.awt.Component c = super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, col);
                    if (value != null) {
                        switch (value.toString()) {
                            case "Đã diễn ra":
                                c.setForeground(COLOR_DONE);
                                c.setFont(c.getFont().deriveFont(Font.PLAIN));
                                break;
                            case "Đang diễn ra":
                                c.setForeground(COLOR_ONGOING);
                                c.setFont(c.getFont().deriveFont(Font.BOLD));
                                break;
                            default:
                                c.setForeground(COLOR_PENDING);
                                c.setFont(c.getFont().deriveFont(Font.PLAIN));
                                break;
                        }
                    }
                    if (isSelected) c.setForeground(Color.WHITE);
                    return c;
                }
            });

        tblRounds.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblRounds.getSelectedRow();
                if (row < 0) return;

                String status   = tblRounds.getValueAt(row, 1).toString();
                int    roundNum = row + 1;

                switch (status) {
                    case "Chưa diễn ra":
                        JOptionPane.showMessageDialog(
                            SelectRoundFrm.this,
                            "Vòng đấu chưa diễn ra!",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        break;

                    case "Đang diễn ra":
                        JOptionPane.showMessageDialog(
                            SelectRoundFrm.this,
                            "Vòng đấu đang diễn ra!",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        break;

                    case "Đã diễn ra":
                        new RankingTableFrm(user, roundNum, store).setVisible(true);
                        dispose();
                        break;
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblRounds);

        JButton btnBack = new JButton("Quay lại");
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnBack.setBackground(new Color(204, 204, 204));
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(e -> {
            new StatisticMenuFrm(user).setVisible(true);
            dispose();
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Chọn vòng đấu");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addComponent(lblUser,    javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle,   javax.swing.GroupLayout.PREFERRED_SIZE, 560, Short.MAX_VALUE)
            .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 560, Short.MAX_VALUE)
            .addComponent(btnBack,    javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(lblUser)
            .addGap(4)
            .addComponent(lblTitle)
            .addGap(12)
            .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 340,
                          javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(12)
            .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                          javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(620, 500);
    }

    private void loadRounds() {
        DefaultTableModel m = (DefaultTableModel) tblRounds.getModel();
        m.setRowCount(0);
        List<DataStore.RoundInfo> rounds = store.getRounds();
        for (DataStore.RoundInfo ri : rounds) {
            m.addRow(new Object[]{"Vòng " + ri.round, ri.status});
        }
    }

    private JTable tblRounds;
}
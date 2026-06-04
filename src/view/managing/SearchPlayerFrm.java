/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.managing;
/**
 *
 * @author Hung
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import model.Player;
import model.User;
public class SearchPlayerFrm extends javax.swing.JFrame implements ActionListener {
    public enum SearchAction { EDIT, DELETE }
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SearchPlayerFrm.class.getName());
    /**
     * Creates new form SearchPlayerFrm
     */
    private User user;
    private SearchAction action;
    private ArrayList<Player> searchResult;
    public SearchPlayerFrm(User user, SearchAction action) {
        this.user = user;
        this.action = action;
        initComponents();
        btnSearch.addActionListener(this); 
        btnBack.addActionListener(this);
        tblPlayer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int column = tblPlayer.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / tblPlayer.getRowHeight();

                if (row < tblPlayer.getRowCount() && row >= 0 && column < tblPlayer.getColumnCount() && column >= 0 && searchResult != null) {
                    Player selectedPlayer = searchResult.get(row);
                    if (action == SearchAction.EDIT) {
                        new EditPlayerFrm(user, selectedPlayer).setVisible(true);
                    } else if (action == SearchAction.DELETE) {
                        new DeletePlayerFrm(user, selectedPlayer).setVisible(true);
                    }
                    SearchPlayerFrm.this.dispose(); 
                }
            }
        });
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPlayer = new javax.swing.JTable();
        txtKey = new javax.swing.JTextField();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Search player");

        btnSearch.setText("Search");

        tblPlayer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "FIDE ID", "Name", "Born year", "Nation", "Elo Rating", "Note"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblPlayer);

        txtKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKeyActionPerformed(evt);
            }
        });

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtKey)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack)
                        .addGap(109, 109, 109)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnBack))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch)
                    .addComponent(txtKey, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void txtKeyActionPerformed(java.awt.event.ActionEvent evt) {
    }
    // Variables declaration - do not modify                     
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPlayer;
    private javax.swing.JTextField txtKey;
    // End of variables declaration                   


    public void actionPerformed(ActionEvent e) {
        javax.swing.JButton btnClicked = (javax.swing.JButton) e.getSource();
        if (btnClicked.equals(btnSearch)) {
            if ((txtKey.getText() == null) || (txtKey.getText().length() == 0))
                return;
            
            controller.PlayerDAO dao = new controller.PlayerDAO();
            searchResult = dao.searchPlayer(txtKey.getText().trim());
            
            String[] columnNames = {"FIDE ID", "Name", "Born year", "Nation", "Elo Rating", "Note"};
            String[][] value = new String[searchResult.size()][6];
            for (int i = 0; i < searchResult.size(); i++) {
                value[i][0] = searchResult.get(i).getFideID();
                value[i][1] = searchResult.get(i).getName();
                value[i][2] = searchResult.get(i).getBornYear() + "";
                value[i][3] = searchResult.get(i).getNation();
                value[i][4] = searchResult.get(i).getEloRating() + "";
                value[i][5] = searchResult.get(i).getNote();
            }
            DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                   return false;
                }
            };
            tblPlayer.setModel(tableModel);
        } else if (btnClicked.equals(btnBack)) {
            new ManagePlayerInformationFrm(user).setVisible(true);
            this.dispose();
        } 
    }
}

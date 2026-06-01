package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.User;

public class ManagePlayerInformationFrm extends javax.swing.JFrame implements ActionListener {
    
    private User user;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ManagePlayerInformationFrm.class.getName());
        
    public ManagePlayerInformationFrm(User user) {
        initComponents();
        this.user = user;
        userName.setText(user.getFullname());    
        btnAddNewPlayer.addActionListener(this);
        btnEditPlayer.addActionListener(this);
        btnDeletePlayer.addActionListener(this);
        btnBack.addActionListener(this);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnDeletePlayer = new javax.swing.JButton();
        btnAddNewPlayer = new javax.swing.JButton();
        btnEditPlayer = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        userName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Quản lý thông tin kỳ thủ");

        btnDeletePlayer.setText("Xóa kỳ thủ");

        btnAddNewPlayer.setText("Thêm kỳ thủ mới");
        btnAddNewPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewPlayerActionPerformed(evt);
            }
        });

        btnEditPlayer.setText("Chỉnh sửa thông tin kỳ thủ");
        btnEditPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditPlayerActionPerformed(evt);
            }
        });

        btnBack.setText("Quay lại");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        userName.setText("user 's name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(userName)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddNewPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEditPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDeletePlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel1)))
                        .addGap(0, 95, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(userName))
                .addGap(65, 65, 65)
                .addComponent(btnAddNewPlayer)
                .addGap(29, 29, 29)
                .addComponent(btnEditPlayer)
                .addGap(31, 31, 31)
                .addComponent(btnDeletePlayer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(btnBack)
                .addContainerGap())
        );

        pack();
    }

    private void btnAddNewPlayerActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void btnEditPlayerActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAddNewPlayer) {
            new AddPlayerFrm(user).setVisible(true);
            this.dispose();
        } else if (e.getSource() == btnEditPlayer) {
            new SearchPlayerFrm(user, SearchPlayerFrm.SearchAction.EDIT).setVisible(true);
            this.dispose();
        } else if (e.getSource() == btnDeletePlayer) {
            new SearchPlayerFrm(user, SearchPlayerFrm.SearchAction.DELETE).setVisible(true);
            this.dispose();
        } else if (e.getSource() == btnBack) {
            new ManagerHomeFrm(user).setVisible(true);
            this.dispose();
        }
    }

    private javax.swing.JButton btnAddNewPlayer;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDeletePlayer;
    private javax.swing.JButton btnEditPlayer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel userName;
}

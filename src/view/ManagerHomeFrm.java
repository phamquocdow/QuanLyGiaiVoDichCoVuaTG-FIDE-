
package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.User;

public class ManagerHomeFrm extends javax.swing.JFrame implements ActionListener {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ManagerHomeFrm.class.getName());

    private User user;
    public ManagerHomeFrm(User user) {
        initComponents();
        this.user = user;
        userName.setText(user.getFullname());
        btnManagerPlayerInfromation.addActionListener(this);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnManagerPlayerInfromation = new javax.swing.JButton();
        userName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Manager Home");

        btnManagerPlayerInfromation.setText("Manage player information");
        btnManagerPlayerInfromation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManagerPlayerInfromationActionPerformed(evt);
            }
        });

        userName.setText("users's name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnManagerPlayerInfromation, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(userName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(133, 133, 133))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(userName))
                .addGap(65, 65, 65)
                .addComponent(btnManagerPlayerInfromation, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(158, Short.MAX_VALUE))
        );

        pack();
    }

    private void btnManagerPlayerInfromationActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private javax.swing.JButton btnManagerPlayerInfromation;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel userName;

    @Override
    public void actionPerformed(ActionEvent e) {
        (new ManagePlayerInformationFrm(user)).setVisible(true);
        this.dispose(); 
    }
}

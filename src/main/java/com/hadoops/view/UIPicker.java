/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hadoops.view;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author anaem
 */
public class UIPicker extends javax.swing.JFrame {

    private File selectedInputDirectory;
    private File[] filesToUpload;
    private File statisticsFile;
    
    public UIPicker() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        jButton1.addActionListener(ev -> {
            try {
                selectFile(ev);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        jButton2.addActionListener(ev -> {
            try {
                upload();
            } catch (Exception ex) {
                Logger.getLogger(UIPicker.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        jButton3.addActionListener(ev -> {
            try {
                download();
            } catch (Exception ex) {
                Logger.getLogger(UIPicker.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        jButton4.addActionListener(ev -> showStatistics());
    }
    
    private void selectFile(ActionEvent e) throws IOException, InterruptedException {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setFileFilter(new FileNameExtensionFilter("Text file", "txt"));
        jfc.setMultiSelectionEnabled(false);
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedInputDirectory = jfc.getSelectedFile();
            if (selectedInputDirectory != null) {
                jButton2.setEnabled(true);
                jLabel4.setText(selectedInputDirectory.getName());
                filesToUpload = selectedInputDirectory.listFiles();

                DefaultListModel model = new DefaultListModel();
                for (File fileToUpload : filesToUpload) {
                    if (fileToUpload.isFile()) {
                        model.addElement(fileToUpload.getName());
                    }
                }
                jList1.setModel(model);
                jList1.setEnabled(false);
            }
        }
    }

    private void upload() {
        jLabel4.setText("Running...");
        try {
            for (File fileToUpload : filesToUpload) {
                String command = "hdfs dfs -put " + fileToUpload.getAbsolutePath() + "/test";
                jLabel4.setText("Uploading " + fileToUpload.getName() + " to HDFS...");

                Process proc = Runtime.getRuntime().exec(command);

                // Read the output

                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(proc.getInputStream()));

                String line = "";
                while ((line = reader.readLine()) != null) {
                    System.out.print(line + "\n");
                }

                proc.waitFor();

            }
            jLabel4.setText("Upload completed.");
        } catch (InterruptedException | IOException exc) {
            exc.printStackTrace();
        }
    }
    private void download() {
        jLabel4.setText("Running...");
        try {
            for (File fileToUpload : filesToUpload) {
                String command = "hdfs dfs -get /test/" + fileToUpload.getName() + " /media/master/ext/output";
                jLabel4.setText("Downloading " + fileToUpload.getName() + " from HDFS to local storage...");

                Process proc = Runtime.getRuntime().exec(command);

                // Read the output

                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(proc.getInputStream()));

                String line = "";
                while ((line = reader.readLine()) != null) {
                    System.out.print(line + "\n");
                }

                proc.waitFor();

            }
            jLabel4.setText("Download completed. Your files are located in /media/master/ext/output/.");
        } catch (InterruptedException | IOException exc) {
            exc.printStackTrace();
        }
    }
    private void showStatistics() {
        try {
            String path = "/media/master/ext/PAT/PAT/PAT-collecting-data/results/";
            statisticsFile = new File(path);
            Desktop.getDesktop().open(statisticsFile);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "No statistics found!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Trajan Pro", 0, 24)); // NOI18N
        jLabel1.setText("Had-oops");

        jLabel2.setFont(new java.awt.Font("Tiger Expert", 0, 12)); // NOI18N
        jLabel2.setText("Analyze your file within a Hadoop Cluster.");

        jLabel3.setText("To start, choose a folder:");

        jButton1.setText("Browse..");

        jLabel4.setText("No folder selected.");

        jLabel5.setText("Status:");

        jButton2.setText("Upload");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("Show statistics");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton3.setText("Download");
        jButton3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jButton3FocusGained(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(200, 200, 200));
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setEnabled(false);
        jScrollPane1.setFocusable(false);

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1))
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jButton1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton4)
                            .addComponent(jButton3))))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButton3FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3FocusGained
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

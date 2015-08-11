/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaBall_results;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import static javax.swing.text.DefaultCaret.NEVER_UPDATE;

/**
 *
 * @author 2162068h
 */
public class JavaBallGUI extends javax.swing.JFrame {

        
    //Read the TeamsIn.txt file and put its content in an arraylist
    Results resTable = new Results(readFile("TeamsIn.txt"));
        
    /**
     * Creates new form JavaBallGUI
     */
    public JavaBallGUI() {
        initComponents();
        
        //Display the results in the textarea
        resultsTextArea.setText(resTable.toTextList());
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        resultsScrollPane = new javax.swing.JScrollPane();
        resultsTextArea = new javax.swing.JTextArea();
        withdrawButton = new javax.swing.JButton();
        importButton = new javax.swing.JButton();
        enterButton = new javax.swing.JButton();
        processButton = new javax.swing.JButton();
        chartButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("JavaBall Results");
        setLocationByPlatform(true);

        resultsScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        resultsTextArea.setEditable(false);
        resultsTextArea.setColumns(20);
        resultsTextArea.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        resultsTextArea.setRows(5);
        resultsScrollPane.setViewportView(resultsTextArea);
        DefaultCaret caret = (DefaultCaret) resultsTextArea.getCaret();
        caret.setUpdatePolicy(NEVER_UPDATE);

        withdrawButton.setText("Withdraw team");
        withdrawButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                withdrawButtonActionPerformed(evt);
            }
        });

        importButton.setText("Import results");
        importButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importButtonActionPerformed(evt);
            }
        });

        enterButton.setText("Enter results");
        enterButton.setEnabled(false);
        enterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterButtonActionPerformed(evt);
            }
        });

        processButton.setText("Process results");
        processButton.setEnabled(false);
        processButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                processButtonActionPerformed(evt);
            }
        });

        chartButton.setText("Bar chart");
        chartButton.setEnabled(false);
        chartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chartButtonActionPerformed(evt);
            }
        });

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(resultsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(withdrawButton)
                    .addComponent(importButton)
                    .addComponent(enterButton)
                    .addComponent(processButton)
                    .addComponent(chartButton)
                    .addComponent(exitButton))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {chartButton, enterButton, exitButton, importButton, processButton, withdrawButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(resultsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(withdrawButton)
                .addGap(18, 18, 18)
                .addComponent(importButton)
                .addGap(18, 18, 18)
                .addComponent(enterButton)
                .addGap(18, 18, 18)
                .addComponent(processButton)
                .addGap(18, 18, 18)
                .addComponent(chartButton)
                .addGap(18, 18, 18)
                .addComponent(exitButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // Write to ResultsOut.txt
        PrintWriter out = null;
        try {
            out = new PrintWriter("ResultsOut.txt");
            
            if (resTable.areAllPlayed()){
                out.println(resTable.toRankedTable());
            }
            else {
                out.println(resTable.toTextList());
            }
            out.close();
        }
        catch (FileNotFoundException e){
            JOptionPane.showMessageDialog(null, 
                    "Couldn't write to file",
                    "Error writing",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        finally {
            if(out!=null) {
                out.close();
            }
        }
        
        //Terminate the program
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void withdrawButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_withdrawButtonActionPerformed
        TreeSet<Team> teamTree = resTable.getTeamTreeFromMatchTree();
        String[] teamList = new String[teamTree.size()];
        Iterator<Team> itr = teamTree.iterator();
        int i = 0;
        
        //Exit if no team
        if (teamTree.isEmpty()){
            return;
        }
        
        while (itr.hasNext()){
            teamList[i] = itr.next().getName();
            i++;
        }
        
        //Let user choose team
        String teamToWithdraw = (String)JOptionPane.showInputDialog(
                null,
                "Choose the team to withdraw:\n",
                "Withdraw Team",
                JOptionPane.PLAIN_MESSAGE,
                null,
                teamList,
                teamList[0]);
        
        //Get team object from team name
        Team team;
        if ((team = resTable.findTeamFromName(teamToWithdraw)) == null){
            return;
        }
        
        //Remove team from tree
        resTable.removeTeam(team);
        
        //Check the number of team left > 2
        if (Team.nbTeam < 3){
            JOptionPane.showMessageDialog(null, 
                    "With only two teams left, "
                    + "the organisers prefered to cancel the tournament.",
                    "Tournament cancelled",
                    JOptionPane.WARNING_MESSAGE);
            //Terminate the program
            System.exit(0);
        }
        
        //Change textarea value
        resultsTextArea.setText(resTable.toTextList());
    }//GEN-LAST:event_withdrawButtonActionPerformed

    private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importButtonActionPerformed
        // Read the file ResultsIn.txt into an array
        ArrayList<String> matchResults;
        matchResults = readFile("ResultsIn.txt");
        
        //Update the match results
        for (String str : matchResults){
            resTable.updateMatchFromString(str);
        }
        
        //Change textarea value
        resultsTextArea.setText(resTable.toTextList());
        
        //Disable Withdrawing and import buttons
        withdrawButton.setEnabled(false);
        importButton.setEnabled(false);
        
        //Check if every match has been played or not
        if (resTable.areAllPlayed()){
            processButton.setEnabled(true);
            chartButton.setEnabled(true);
        }
        else {
        //Enable enter button
        enterButton.setEnabled(true);
        }
    }//GEN-LAST:event_importButtonActionPerformed

    private void enterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enterButtonActionPerformed
        ArrayList<String> matchList = new ArrayList<>();
        Iterator<Match> itr = resTable.resultsTree.iterator();
        Match m;
        int i;
        
        //Exit if no match
        if (resTable.resultsTree.isEmpty()){
            return;
        }
        
        //Put every unplayed match in the arraylist
        while (itr.hasNext()){
            m = itr.next();
            if (!m.isPlayed()){
                matchList.add(m.toString());
            }
        }
        
        //Need to be a String[]
        String[] unplayedMatchList = new String[matchList.size()];
        for (i = 0; i < matchList.size(); i++){
            unplayedMatchList[i] = matchList.get(i);
        }
        
        //Create the dialog gui
        JComboBox teams = new JComboBox(unplayedMatchList);
        JTextField score1 = new JTextField(5);
        JTextField score2 = new JTextField(5);
        

        JPanel myPanel = new JPanel();
        myPanel.add(teams);
        myPanel.add(Box.createVerticalStrut(5));
        myPanel.add(new JLabel("Score 1 :"));
        myPanel.add(score1);
        myPanel.add(Box.createHorizontalStrut(5));
        myPanel.add(new JLabel("vs"));
        myPanel.add(Box.createHorizontalStrut(5));
        myPanel.add(new JLabel("Score 2 :"));
        myPanel.add(score2);

        int result = JOptionPane.showConfirmDialog(null, myPanel, 
                 "Please Enter you score", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            //Get the match chosen
            String[] splitString = ((String)teams.getSelectedItem())
                                        .split("\\s+", 5);
            m = resTable.findMatchFromTeamName(splitString[0], splitString[2]);
            
            int sc1;
            int sc2;
            
            //Get the number inputed
            try{
                sc1 = Integer.parseInt(score1.getText());
                sc2 = Integer.parseInt(score2.getText());
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, 
                    "Input must be an integer",
                    "Input error",
                    JOptionPane.ERROR_MESSAGE);
                enterButtonActionPerformed(evt);
                return;
            }
            
            if (sc1 > 9 || sc1 < 0 || sc2 > 9 || sc2 < 0){
                JOptionPane.showMessageDialog(null, 
                    "Inputs must be between 0 and 9",
                    "Input error",
                    JOptionPane.ERROR_MESSAGE);
                enterButtonActionPerformed(evt);
                return;
            }
            
            //Set the score
            m.setScore1(sc1);
            m.setScore2(sc2);
            
            //Update the teams
            resTable.updateTeamFromResults(m);
            
            //Update the view
            resultsTextArea.setText(resTable.toTextList());
            
            //If it was the last one, disable button, enable the rest
            if (i == 1){
                enterButton.setEnabled(false);
                processButton.setEnabled(true);
                chartButton.setEnabled(true);
            }
            //Restart this action
            else{
                enterButtonActionPerformed(evt);
            }
        }
    }//GEN-LAST:event_enterButtonActionPerformed

    private void processButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_processButtonActionPerformed
        //Display the ranked table
        
        //Create the dialog gui
        JTextArea table = new JTextArea();
        table.setEditable(false);
        table.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        table.setText(resTable.toRankedTable());

        JPanel myPanel = new JPanel();
        myPanel.add(table);

        JOptionPane.showConfirmDialog(null, myPanel, 
                 "Final Standing", JOptionPane.OK_CANCEL_OPTION);
    }//GEN-LAST:event_processButtonActionPerformed

    private void chartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chartButtonActionPerformed
        //Transform the tree values to String[] 
        //so they can be passed to the main function
        TreeSet<Team> team = resTable.getTeamTreeFromMatchTree();
        //Name plus goals for, goals against for each team
        String[] args = new String[team.size() * 3];
        
        //Add to String array
        int i = 0;
        for (Team t : team){
            args[i++] = t.getName();
            args[i++] = Integer.toString(t.getGoalsFor());
            args[i++] = Integer.toString(t.getGoalsAgainst());
        }
        //String[] args = {};
        ResultsBarChart.main(args);
    }//GEN-LAST:event_chartButtonActionPerformed

    
    //Read a text file and return an array of its content
    //Read line by line
     static ArrayList<String> readFile(String path) {
        ArrayList<String> lineList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineList.add(line);
            }
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                    "Error while reading " + path,
                    "Couldn't read",
                    JOptionPane.ERROR_MESSAGE);
            //Terminate the program
            System.exit(0);
        }
        return lineList;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JavaBallGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JavaBallGUI().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton chartButton;
    private javax.swing.JButton enterButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton importButton;
    private javax.swing.JButton processButton;
    private javax.swing.JScrollPane resultsScrollPane;
    private javax.swing.JTextArea resultsTextArea;
    private javax.swing.JButton withdrawButton;
    // End of variables declaration//GEN-END:variables
}

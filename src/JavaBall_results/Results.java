/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaBall_results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author 2162068h
 */
public class Results {
    
    //Matches are stored in a binary tree
    public TreeSet<Match> resultsTree;

    public Results() {
        this.resultsTree = new TreeSet<>(lexicalSortByTeamName);
    }
    
    //Create tree from arraylist of team name
    public Results(ArrayList<String> nameList) {
        //Create an empty tree
        this.resultsTree = new TreeSet<>(lexicalSortByTeamName);
        
        //Create an arraylist containing the teams
        ArrayList<Team> teamList = new ArrayList<>();
        Iterator<String> itr = nameList.iterator();
        while (itr.hasNext()) {
            teamList.add(new Team(itr.next()));
        }
        
        //Create an arraylist containing all the possible matches
        //Then add it to the tree
        for (int i = 0; i < teamList.size(); i++) {
            for ( int j = i + 1; j < teamList.size(); j++) {
                this.resultsTree.add(new Match(teamList.get(i), teamList.get(j)));
            }
        }
    }
            
    /*
    * Create a comparator for the match TreeSet
    * Sort by the lexical order of the first team, then the second
    * Assumes that team1/team2 in a match is already sorted
    */
    Comparator<Match> lexicalSortByTeamName = new Comparator<Match>() {
        @Override
        public int compare(Match m1, Match m2) {
            String namem1t1 = m1.getTeam1().getName();
            String namem1t2 = m1.getTeam2().getName();
            String namem2t1 = m2.getTeam1().getName();
            String namem2t2 = m2.getTeam2().getName();
            if (namem1t1.compareTo(namem2t1) < 0) {
                return -1;
            }
            else if (namem1t1.compareTo(namem2t1) > 0) {
                return 1;
            }
            else {
                if (namem1t2.compareTo(namem2t2) < 0) {
                    return -1;
                }
                else {
                    return 1;
                }
            }
        }
    };
    
    /*
    * Create a comparator for the team TreeSet
    * Sort by the lexical order
    */
    Comparator<Team> lexicalSortByName = new Comparator<Team>() {
        @Override
        public int compare(Team t1, Team t2) {
            String name1 = t1.getName();
            String name2 = t2.getName();
            if (name1.compareTo(name2) < 0) {
                return -1;
            }
            else {
                return 1;
            }
        }
    };
    
    /*
    * Create a comparator for ranking teams
    * Sort by points, then goal average
    */
    Comparator<Team> rankingComparator = new Comparator<Team>() {
        @Override
        public int compare(Team t1, Team t2) {
            if (t1.getPoints() < t2.getPoints()){
                return 1;
            }
            else if (t1.getPoints() > t2.getPoints()){
                return -1;
            }
            else {
                if (t1.goalDiff() < t2.goalDiff()){
                    return 1;
                }
                else if (t1.goalDiff() > t2.goalDiff()){
                    return -1;
                }
                else {
                    return 0;
                }
            }
        }
    };
    
    //Return the Team tree given the match tree
    //Not efficient but should do for now
    public TreeSet<Team> getTeamTreeFromMatchTree(){
        TreeSet<Team> teamTree = new TreeSet<>(lexicalSortByName);
        
        //Go through the whole tree and find each team
        //Add it to the tree if it's not already present
        Iterator<Match> itr = this.resultsTree.iterator();
        
        Match m = null;
        while (itr.hasNext()){
            m = itr.next();
            
            String team1 = m.getTeam1().getName();
            if(teamTree.isEmpty()){
                teamTree.add(m.getTeam1());
            }
            else {
                boolean added = false;
                Iterator<Team> itTeams = teamTree.iterator();
                while(itTeams.hasNext() && !added){
                    Team currentTeam = itTeams.next();
                    if(currentTeam.getName().equals(team1)) {
                        added = true;
                    }
                }
                if(!added){
                     teamTree.add(m.getTeam1());
                }
            }
        }
        //Add the last team, the one never in the first column
        if (m != null){
            teamTree.add(m.getTeam2());
        }
        return  teamTree;
    }
    
    
    //Add a match to the tree, tree stay sorted
    public void addMatch(Match m)
    {
        this.resultsTree.add(m);
    }
    
    //Remove a match from the tree, tree stay sorted
    public void removeMatch(Match m)
    {
        this.resultsTree.remove(m);
    }
    
    //Remove all matches in which a team appears
    //Remove the team from the team tree as well
    public void removeTeam(Team t) {
        Iterator<Match> itr = this.resultsTree.iterator();
        while (itr.hasNext()) {
            //Match to be removed
            if (itr.next().containsTeam(t)) {
                itr.remove();
            }
        }
        
        //Decremente the number of teams left
        Team.nbTeam--;
    }
    
    //Return the team with the name given in argument
    public Team findTeamFromName(String name) {
        Iterator<Team> itr = this.getTeamTreeFromMatchTree().iterator();
        Team team;
        while (itr.hasNext()){
            team = itr.next();
            if (team.getName().equals(name)){
                return team;
            }
        }
        return null;
    }
    
    //Return the match which contains the two given team name
    //Assume that the names are sorted in lexical order
    public Match findMatchFromTeamName(String name1, String name2){
        Iterator<Match> itr = this.resultsTree.iterator();
        Match m;
        while (itr.hasNext()) {
            m = itr.next();
            if (m.getTeam1().getName().equals(name1) 
                    && m.getTeam2().getName().equals(name2)){
                return m;
            }
        }
        return null;
    }
    
    //Update the relevant match given a string with the results as argument
    public void updateMatchFromString(String resultString){
        String team1;
        String team2;
        int score1;
        int score2;
        
        //Separate the string data 
        //Split the space
        String[] splitString = resultString.split("\\s+", 5);
        
        //Store it sorted into the variables
        if (splitString[0].compareTo(splitString[2]) < 0){
            team1 = splitString[0];
            score1 = Integer.parseInt(splitString[1]);
            team2 = splitString[2];
            score2 = Integer.parseInt(splitString[3]);
        }
        else {
            team1 = splitString[2];
            score1 = Integer.parseInt(splitString[3]);
            team2 = splitString[0];
            score2 = Integer.parseInt(splitString[1]);
        }
        
        //Find the corresponding match in the tree
        Match m;
        if ((m = this.findMatchFromTeamName(team1, team2)) == null){
            return;
        }
        
        //Update the match
        m.setScore1(score1);
        m.setScore2(score2);
        
        //Update the teams
        this.updateTeamFromResults(m);
    }
    
    //Update teams rank
    public void updateTeamRank(){
        TreeSet<Team> teamTree = this.getTeamTreeFromMatchTree();
        List<Team> unorderedTeams = new LinkedList<>();
        
        //Add all the teams to the list
        unorderedTeams.addAll(teamTree);
        
        //Sort the list with the ranking comparator
        Collections.sort(unorderedTeams, rankingComparator);
        
        //Change the rank
        int rank = 1;
        int rankPoints = unorderedTeams.get(0).getPoints();
        int rankDiff = unorderedTeams.get(0).goalDiff();
        int numberOfEquallyRankedTeam = 0;
        for (Team t : unorderedTeams){
            if (t.getPoints() == rankPoints && t.goalDiff() == rankDiff){
                t.setRank(rank);
                numberOfEquallyRankedTeam++;
            }
            else {
                rank += numberOfEquallyRankedTeam;
                numberOfEquallyRankedTeam = 1;
                t.setRank(rank);
                rankPoints = t.getPoints();
                rankDiff = t.goalDiff();
            }
        }
    }   
    
    //Update the teams given a match result
    public void updateTeamFromResults(Match m){
        
        //Update team1
        m.getTeam1().setGoalsFor(m.getTeam1().getGoalsFor() + m.getScore1());
        m.getTeam1().setGoalsAgainst(m.getTeam1().getGoalsAgainst() + m.getScore2());
        
        //Update team2
        m.getTeam2().setGoalsFor(m.getTeam2().getGoalsFor() + m.getScore2());
        m.getTeam2().setGoalsAgainst(m.getTeam2().getGoalsAgainst() + m.getScore1());
        
        //Add points
        if (m.getScore1() < m.getScore2()){
            m.getTeam2().setPoints(m.getTeam2().getPoints() + 3);
            m.getTeam1().setMatchLost(m.getTeam1().getMatchLost() + 1);
            m.getTeam2().setMatchWon(m.getTeam2().getMatchWon() + 1);
        }
        else if (m.getScore1() > m.getScore2()){
            m.getTeam1().setPoints(m.getTeam1().getPoints() + 3);
            m.getTeam2().setMatchLost(m.getTeam2().getMatchLost() + 1);
            m.getTeam1().setMatchWon(m.getTeam1().getMatchWon() + 1);
        }
        else {
            m.getTeam1().setPoints(m.getTeam1().getPoints() + 1);
            m.getTeam2().setPoints(m.getTeam2().getPoints() + 1);
            m.getTeam1().setMatchDrawn(m.getTeam1().getMatchDrawn() + 1);
            m.getTeam2().setMatchDrawn(m.getTeam2().getMatchDrawn() + 1);
        }
        
        //Update the rankings
        this.updateTeamRank();
    }
    
    public boolean areAllPlayed(){
        ArrayList<String> unplayedMatchList = new ArrayList<>();
        for (Match m : this.resultsTree){
            if (!m.isPlayed()){
                unplayedMatchList.add(m.toString());
            }
        }
        return unplayedMatchList.isEmpty();
    }
    
    private String leftAlign(String str, int size){
        int toPad = size - str.length();
        String afterPad = "";
        
        for (int i = 0; i < toPad; i++){
            afterPad += " ";
        }
        
        return str + afterPad;
    }
    
    //Return the string to be displayed for the list of results
    public String toTextList() {
        Iterator<Match> itr = this.resultsTree.iterator();
        Match m;
        StringBuilder str = new StringBuilder();
        
        while (itr.hasNext()) {
            m = itr.next();
            //Match without results
            if (m.getScore1() == -1) {
                str
                        .append(this.leftAlign(m.getTeam1().getName(), 10))
                        .append(this.centerAlign(" vs ", 10))
                        .append(this.leftAlign(m.getTeam2().getName(), 10))
                        .append(this.centerAlign("*** no results yet ***", 30))
                        .append(System.lineSeparator());
            }
            else {
                str
                        .append(this.leftAlign(m.getTeam1().getName(), 10))
                        .append(" ")
                        .append(m.getScore1())
                        .append("        ")
                        .append(this.leftAlign(m.getTeam2().getName(), 10))
                        .append(" ")
                        .append(m.getScore2())
                        .append(System.lineSeparator());
            }
        }
        
        return str.toString();
    }
    
    private String centerAlign(String str, int size){
        int toPad = size - str.length();
        String beforePad = "";
        String afterPad = "";
        
        for (int i = 0; i < toPad / 2; i++){
            beforePad += " ";
            afterPad += " ";
        }
        
        //For small string add it before
        if (toPad % 2 != 0 && str.length() < 4){
            beforePad += " ";
        }
        //Add it after for bigger ones
        else if (toPad % 2 != 0 && str.length() > 4){
            afterPad += " ";
        }
        
        return beforePad + str + afterPad;
    }
    
    //Return the string to be displayed for the list of results
    public String toRankedTable() {
        TreeSet<Team> teamTree = this.getTeamTreeFromMatchTree();
        String medal;
        int i = 1;
        String[] str = new String[teamTree.size() + 1];
        List<Team> unorderedTeams = new LinkedList<>();
        int currentRank = 0;
        int nextMedal = 0;
        
        //Add all the teams to the list
        unorderedTeams.addAll(teamTree);
        
        //Sort the list with the ranking comparator
        Collections.sort(unorderedTeams, rankingComparator);
        
        //Add the columns headings
        str[0] = String.format("%15s%10s%10s%10s%10s%10s%10s%10s%10s%10s%n",
                this.centerAlign("Team", 15),
                this.centerAlign("Rank", 10),
                this.centerAlign("Won", 10),
                this.centerAlign("Drawn", 10),
                this.centerAlign("Lost", 10),
                this.centerAlign("For", 10),
                this.centerAlign("Against", 10),
                this.centerAlign("Points", 10),
                this.centerAlign("Diff", 10),
                this.centerAlign("Medal", 10));
        
        for (Team t : unorderedTeams) {
            //Get medal if any
            //If the team has a different ranking from the previous ones
            if (t.getRank() != currentRank){
                //Get the ranking of the current team
                currentRank = t.getRank();
                //Get the next medal
                nextMedal++;
                if (nextMedal == 1){
                    medal = "Gold";
                }
                else if (nextMedal == 2){
                    medal = "Silver";
                }
                else if (nextMedal == 3){
                    medal = "Bronze";
                }
                else {
                    medal = "";
                }
            }
            //Same ranking as previous team
            //No need to change the medal or ranking
            else {
                if (nextMedal == 1){
                    medal = "Gold";
                }
                else if (nextMedal == 2){
                    medal = "Silver";
                }
                else if (nextMedal == 3){
                    medal = "Bronze";
                }
                else {
                    medal = "";
                }
            }
            
            //Add the team details to the string
            str[i] = String.format("%15s%10s%10s%10s%10s%10s%10s%10s%10s%10s%n",
                this.centerAlign(t.getName(), 15),
                this.centerAlign(Integer.toString(t.getRank()), 10),
                this.centerAlign(Integer.toString(t.getMatchWon()), 10),
                this.centerAlign(Integer.toString(t.getMatchDrawn()), 10),
                this.centerAlign(Integer.toString(t.getMatchLost()), 10),
                this.centerAlign(Integer.toString(t.getGoalsFor()), 10),
                this.centerAlign(Integer.toString(t.getGoalsAgainst()), 10),
                this.centerAlign(Integer.toString(t.getPoints()), 10),
                this.centerAlign(Integer.toString(t.goalDiff()), 10),
                this.centerAlign(medal, 10));
            
            i++;
        }
        
        //Transform the array into a single String
        StringBuilder sb = new StringBuilder();
        for(String s : str) {
            sb.append(s);
        }
        
        return sb.toString();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaBall_results;

/**
 *
 * @author 2162068h
 */
public class Team {
    
    /* 
    * Variables declaration
    */
    
    //Counter for the total number of teams in the tournament
    public static int nbTeam;
    
    private final String name;
    private int goalsFor;
    private int goalsAgainst;
    private int matchWon;
    private int matchDrawn;
    private int matchLost;
    private int points;
    private int rank;

    public int getMatchWon() {
        return matchWon;
    }

    public void setMatchWon(int matchWon) {
        this.matchWon = matchWon;
    }

    public int getMatchDrawn() {
        return matchDrawn;
    }

    public void setMatchDrawn(int matchDrawn) {
        this.matchDrawn = matchDrawn;
    }

    public int getMatchLost() {
        return matchLost;
    }

    public void setMatchLost(int matchLost) {
        this.matchLost = matchLost;
    }
    
    
    /*
     * Methodes declaration
     */
    
    //Constructor
    public Team(String name) {
        this.name = name;
        this.goalsFor = 0;
        this.goalsAgainst = 0;
        this.points = 0;
        this.rank = 0;
        this.matchDrawn = 0;
        this.matchWon = 0;
        this.matchLost = 0;
        nbTeam++;
    }
 

    //Getters and setters
    public String getName() {
        return name;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(int goalsFor) {
        this.goalsFor = goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    
    public int goalDiff(){
        return this.getGoalsFor() - this.getGoalsAgainst();
    }
}

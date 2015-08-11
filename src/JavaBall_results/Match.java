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
public class Match {
    
    private Team team1;
    private Team team2;
    private int score1;
    private int score2;

    
    public Match(Team team1, Team team2) {
        //Put the first team in the lexical order in team1
        if (team1.getName().compareTo(team2.getName()) < 0) {
            this.team1 = team1;
            this.team2 = team2;
        }
        else {
            this.team1 = team2;
            this.team2 = team1;
        }
        this.score1 = -1;
        this.score2 = -1;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }
    
    //Return TRUE if the match contains the team t, FALSE otherwise
    public boolean containsTeam(Team t) {
        return (t.getName().equals(this.getTeam1().getName()) 
                || t.getName().equals(this.getTeam2().getName()));
    }
    
    //Display the teams
    @Override
    public String toString(){
        return this.getTeam1().getName() + " vs " + this.getTeam2().getName();
    }
    
    //Return a bollean that indicates if the match has been played
    public boolean isPlayed(){
        return this.getScore1() != -1;
    }
}

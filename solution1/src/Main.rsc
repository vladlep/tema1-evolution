module Main
import Duplication;
import LinesCode;
import UnitComplexity;
import Rankings;
import IO;

public void main(){
	selectedProject = |project://SmallSql/.| ;
	println("Ranking LOC: " + getRankingForLinesCode(cleanGetLinesCode(selectedProject))); 
	println("Ranking Complexity per Unit: " + getRankingForComplexityPerUnit(unitComplexity(selectedProject)));
	println("Ranking Duplication: " + getRankingForDuplication(complicatedGetDup(selectedProject)));
	
}


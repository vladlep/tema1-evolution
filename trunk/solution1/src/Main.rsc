module Main
import Duplication;
import LinesCode;
import UnitComplexity;
import IO;

public void main(){
	selectedProject = |project://SmallSql/| ;
	
	//unitComplexity(selectedProject);
	
	println("Code duplication percentage:");
	print (calculateDuplication(selectedProject)) ;
	println("%");
}


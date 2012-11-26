module LinesCode

import lang::java::jdt::Java;
import lang::java::jdt::JDT;
import  lang::java::jdt::JavaADT;
import util::Resources;
import IO;
import List;
import Set;
import Node;

public void linesCode(){
	projectLoc = |project://Hello/.|;
	outputFile = |file:///D:/vlad/ast.txt|; 
	methodsFile = |file:///D:/vlad/methods.txt| ;
	
	ast = createAstsFromProject(projectLoc);
	//ast = createAstFromFile(outputFile);

	totalLOC = 0;
	contor = 0;
	
	for (AstNode aNode <- ast)
	{
		linesWithCode = {};
		visit(aNode){
		case p:packageDeclaration(_,_):
			try println(p@location.begin.line); catch Exception(): println("a");
		case p:importDeclaration(_,_,_):
			try println(p@location.begin.line); catch Exception(): println("a");			 
				  	
			//try println(p@location.begin.line); catch Exception(): println("a");
		//linesWithCode  = linesWithCode  +{i | i <- [p@location.begin.line..p@location.end.line]}; //This should be it!
	
		};
		totalLOC += size(linesWithCode); 
	};
	print (contor);
	print (totalLOC);	
	
	//appendToFile(outputFile, ast);
}

/**
short version
*/
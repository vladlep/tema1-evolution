module LinesCode

import lang::java::jdt::Java;
import lang::java::jdt::JDT;
import  lang::java::jdt::JavaADT;
import util::Resources;
import IO;
import List;
import Set;
import Node;
import Map;

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
		println("---------------------");
		visit(aNode){
			case javadoc():
				println("?????????????????????????");
			case AstNode subNode: 
			{
				if("location" in getAnnotations(subNode)) 
				{
					//linesWithCode  = linesWithCode  +{i | i <- [p@location.begin.line..p@location.end.line]}; //This should be it!
					try {
						if("javaType" in getAnnotations(subNode))
							print(subNode@javaType);
						print(subNode@location.begin.line);
						print("+++++");
						println(subNode@location.end.line);
						} 
					catch Exception(): println("a");
				}
			}
			//case p:packageDeclaration(_,_):println(p@location.begin.line); 
			//case p:importDeclaration(_,_,_):println(p@location.begin.line);
			//case p:typeDeclaration(_,_,"class",_,_,_,_,_): 
			//{
			//	print("Class declaration: starts at line ");
			//	print(p@location.begin.line);
			//	print("; ends at line ");
			//	println(p@location.end.line);
			//} 	
			//case p:enumDeclaration(_,_,_,_,_,_):
			//{
			//	print("Enum declaration: starts at line ");
			//	print(p@location.begin.line);
			//	print("; ends at line ");
			//	println(p@location.end.line);
			//}	
			//case p:fieldDeclaration(_,_,_,_): 
			//{
			//	print("Field declaration: starts at line ");
			//	print(p@location.begin.line);
			//	print("; ends at line ");
			//	println(p@location.end.line);
			//}
			//case p:methodDeclaration(_, _, _, _, _, _, _, _) :
			//{
			//	print("Method declaration: starts at line ");
			//	print(p@location.begin.line);
			//	print("; ends at line ");
			//	println(p@location.end.line);
			//}	  	
		//if(getAnnotations(p) contains location)
			//	linesWithCode  = linesWithCode  +{i | i <- [p@location.begin.line..p@location.end.line]}; //This should be it!
			//try println(p@location.begin.line); catch Exception(): println("a");
			//try {
			//	println(getAnnotations(p));
			//	println(p);
			//} catch RuntimeException(v):
					//continue;
				
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
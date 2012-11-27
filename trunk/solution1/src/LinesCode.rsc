module LinesCode

import lang::java::jdt::Java;
import lang::java::jdt::JDT;
import lang::java::jdt::JavaADT;
import util::Resources;
import IO;
import List;
import Set;
import Node;
import Map;

public void linesCode() {
	projectLoc = |project://Hello/.|;
	ast = createAstsFromProject(projectLoc);

	totalLOC = 0;
	
	for (AstNode aNode <- ast)
	{
		contor = 0;
		linesWithCode = {};
		visit(aNode){
			case subNode : compilationUnit(_,_,_): {
				//do nothing; how?
				linesWithCode = linesWithCode;
			}
			//this case will match the method, including its javadoc
			//case p : methodDeclaration(_,_,_,_,_,_,_,_): {
			//	print(p@location.begin.line);
			//	print(";");
			//	println(p@location.end.line);
			//}
			case AstNode subNode: {
				if("location" in getAnnotations(subNode)) {
					//linesWithCode  = linesWithCode  +{i | i <- [p@location.begin.line..p@location.end.line]}; //This should be it!
					linesWithCode += {subNode@location.begin.line} + {subNode@location.end.line};
				}
			}	
		};
		contor += size(linesWithCode);
		println("-----File: lines of code------");
		orderedList = sort(toList(linesWithCode));
		println(orderedList);
		print("No of lines/file: ");
		println(contor);	
		println();
		totalLOC += contor;  
	};
	println("----------The end------------");
	print("Final result: ");
	print(totalLOC);
	println(" lines");
}

public int getUnitSize(implementationAST) {
	linesWithCode = {};
	visit(implementationAST){
		case AstNode subNode: {
			print(subNode@location.begin.line);
			print("++");
			println(subNode@location.end.line);
			linesWithCode += {subNode@location.begin.line} + {subNode@location.end.line};
		}
	};
	return size(linesWithCode);
}
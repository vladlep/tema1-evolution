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

public void linesCode(projectLoc) {
	//projectLoc = |project://Hello/.|;
	ast = createAstsFromProject(projectLoc);
	totalLOC = 0;
	for (AstNode aNode <- ast){
		contor = 0;
		linesWithCode = {};
		visit(aNode){
			case methodNode:methodDeclaration(_, _, _, _, methodName, _, _, implementationAST) :
			{				
				println("METHOD NAME " + methodName);
				endLine = methodNode@location.end.line;
				visit(implementationAST) {
					//case AstNode subNode: {
					//	println(subNode);
					//	println("--------------------");
					//}					
					case subNode : block(_): {
						if (subNode@location.end.line == endLine) {
							linesWithCode += {subNode@location.begin.line} + {subNode@location.end.line};
						}
						//println(subNode);
						//print("Start: ");
						//print(subNode@location.begin.line);
						//print("; ends at line ");
						//println(subNode@location.end.line);
						//println("--------------------");
					}
				};
				
			}	  
			case subNode : compilationUnit(_,_,_): {
				linesWithCode = linesWithCode;//do nothing; how?
			}
			
			case AstNode subNode: {
				if("location" in getAnnotations(subNode)) {
					linesWithCode += {subNode@location.begin.line} + {subNode@location.end.line};
				}
				//println(subNode);
				//print(subNode@location.begin.line);
				//print("; ends at line ");
				//println(subNode@location.end.line);
			}
			//this case will match the method, including its javadoc
			//case p : methodDeclaration(_,_,_,_,_,_,_,_): {
			//	print(p@location.begin.line);
			//	print(";");
			//	println(p@location.end.line);
			//}	
		};
		contor += size(linesWithCode);
		//println("-----File: lines of code------");
		//orderedList = sort(toList(linesWithCode));
		//println(orderedList);
		//print("No of lines/file: ");
		//println(contor);	
		//println();
		totalLOC += contor;  
	};
	print("Total number of LOC/project: ");
	print(totalLOC);
}

public int cleanGetLinesCode(projectLoc) {
	ast = createAstsFromProject(projectLoc);
	totalLOC = 0;
	for (AstNode aNode <- ast){
		contor = 0;
		linesWithCode = {};
		visit(aNode){
			case subNode : compilationUnit(_,_,_): {
				linesWithCode = linesWithCode;//do nothing; how?
			}
			case methodNode:methodDeclaration(_, _, _, _, _, _, _, implementationAST) :
			{				
				endLine = methodNode@location.end.line;
				AstNode metodSubNodeBlock = methodNode; 
				visit(implementationAST) {
					case subNode : block(_): {
						if (subNode@location.end.line == endLine) {
							metodSubNodeBlock = subNode;	
						}
					}
				};
				linesWithCode += {metodSubNodeBlock@location.begin.line} + {metodSubNodeBlock@location.end.line};				
			}
			case AstNode subNode: {
				if("location" in getAnnotations(subNode)) {
					linesWithCode += {subNode@location.begin.line} + {subNode@location.end.line};
				}
			}
		};
		contor += size(linesWithCode);
		totalLOC += contor;  
	};
	print("Total number of LOC/project: ");
	println(totalLOC);
	return totalLOC;
}


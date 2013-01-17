module UnitComplexity

import lang::java::jdt::Java;
import lang::java::jdt::JDT;
import  lang::java::jdt::JavaADT;
import util::Resources;
import IO;
import Set;
import List;
import Node;

public map[str, real] unitComplexity(projectLoc){
	ast = createAstsFromProject(projectLoc);
	moderateCode = 0;
	complexCode=0;
	veryComplexCode=0;
	totalCode=0;
	outputFile = |file:///home/ioana/workspace/evol-proj/tema1-evolution/solution2/compendium.txt|;
	for (AstNode aNode <- ast){
			visit(aNode ){
				case interfaceNode :typeDeclaration(_, _, objectType, _, _, _, _, _):{
					if(objectType != "interface")
						visit (interfaceNode){	 
							case subNode:methodDeclaration(modifiers, _, _, _, methodName, _, _, implementationAST) :{
								if(! (abstract() in modifiers)){
									cyc = cycloComplex(implementationAST);
									unitSize = getUnitSize(implementationAST,subNode);
									appendToFile(outputFile, "<cyc>, <unitSize> \n");
									totalCode += unitSize;
									if(11<= cyc && cyc <= 20) {
										//appendToFile(outputFile, "1\n");
										moderateCode += unitSize;
									}
									if(21<= cyc && cyc <= 50) {
										//appendToFile(outputFile, "2\n");
										complexCode += unitSize;
									}
									if(50< cyc) {
										//appendToFile(outputFile, "3\n");
										veryComplexCode += unitSize;
									}
								}
							}
						};
				}
			};
	}
	println(totalCode);
	modC = moderateCode*100.0/totalCode;
	highC = complexCode*100.0/totalCode;
	veryHighC = veryComplexCode*100.0/totalCode;
	println("\nmoderate complexity: <modC> ");
	println("high complexity: <highC>");	
	println("very high complexity: <veryHighC>");	 

	map[str, real] complex = ();
	complex["moderate"] = modC;
	complex["high"] = modC;
	complex["very high"] = veryHighC; 	
	return complex;
}

public int cycloComplex(implementationAST ){
				numberDecisionPoints = 0;
				exitPoints  = 0; 
				visit(implementationAST){
					case whileStatement(_, _): 
						numberDecisionPoints +=1;
					case forStatement(_,_, _, _):	
						numberDecisionPoints +=1;
					case enhancedForStatement(_,_,_):
						numberDecisionPoints +=1;
					case ifStatement(_,_,_):
						numberDecisionPoints +=1;
					case conditionalExpression() :
						numberDecisionPoints +=1;
					case doStatement(_, _):
						numberDecisionPoints +=1;
					case switchCase(_,_): // includes defaul. First parameter is a boolean that is True if we are in the default case.
						numberDecisionPoints +=1;
					case catchClause(_,_):
						numberDecisionPoints +=1;
					case infixExpression("&&",_,_,_) :
						numberDecisionPoints +=1;
					case infixExpression("||",_,_,_) :
						numberDecisionPoints +=1;		
				}
				return 	(numberDecisionPoints +1);
	
}

public int getUnitSize(implementationAST, methodNode) {
	linesWithCode = {};
	endLine = methodNode@location.end.line;
	AstNode metodSubNodeBlock = methodNode; 
	visit(implementationAST) {
		case subNode : block(_): {
			if (subNode@location.end.line == endLine) {
				metodSubNodeBlock = subNode;	
				}
			}
		case AstNode subNode: {
				if("location" in getAnnotations(subNode)) {
					linesWithCode += {subNode@location.begin.line};
				}
			}
		};
	linesWithCode += {metodSubNodeBlock@location.begin.line};
	return size(linesWithCode);
}
module UnitComplexity

import lang::java::jdt::Java;
import lang::java::jdt::JDT;
import  lang::java::jdt::JavaADT;
import IO;
import Set;
import List;

public map[str, real] unitComplexity(projectLoc){
	ast = createAstsFromProject(projectLoc);
	moderateCode = 0;
	complexCode=0;
	veryComplexCode=0;
	totalCode=0;
	outputFile = |file:///./UnitResults2.txt|;
	for (AstNode aNode <- ast){
			visit(aNode ){
				case interfaceNode :typeDeclaration(_, _, objectType, _, _, _, _, _):{
					if(objectType != "interface")
						visit (interfaceNode){	 
							case subNode:methodDeclaration(modifiers, _, _, _, methodName, _, _, implementationAST) :{
								if(! (abstract() in modifiers)){
									cyc = cycloComplex(implementationAST);
									unitSize = getUnitSize(implementationAST,{subNode@location.begin.line});
									appendToFile(outputFile, "<cyc>, <unitSize>\n");
									if(unitSize ==0) 
										println(methodName);
									totalCode += unitSize;
									if(11<= cyc && cyc <= 20) {
										moderateCode += unitSize;
									}
									if(21<= cyc && cyc <= 50) {
										complexCode += unitSize;
									}
									if(50< cyc) {
										veryComplexCode += unitSize;
									}
								}
							}
						};
				}
			};
	}
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

public int getUnitSize(implementationAST,beginMethodLine) {
	linesWithCode = {beginMethodLine};
	visit(implementationAST){
		case AstNode subNode: {
			linesWithCode += {subNode@location.begin.line};
		}
	};
	return size(linesWithCode);
}
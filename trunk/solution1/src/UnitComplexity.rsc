module UnitComplexity

import lang::java::jdt::Java;
import lang::java::jdt::JDT;
import  lang::java::jdt::JavaADT;
import IO;

public void unitComplexity(projectLoc){
	ast = createAstsFromProject(projectLoc);
	moderateCode = 0;
	complexCode=0;
	veryComplexCode=0;
	totalCode=0;
	for (AstNode aNode <- ast){
			visit(aNode){
				case methodDeclaration(_, _, _, _, methodName, _, _, implementationAST) :{
					cyc = cycloComplex(implementationAST);
					unitSize = getUnitSize(implementationAST);
					totalCode += unitSize;
					if(11<= cyc && cyc <= 20) moderateCode += unitSize;
					if(21<= cyc && cyc <= 50) complexCode += unitSize;
					if(50> cyc) veryComplexCode += unitSize;
					println(cyc);
				}
			};
	}
	println();
	print("moderate complexity: ");
	println(moderateCode/totalCode * 100);
	print("high complexity: ");
	println(complexCode/totalCode * 100);
	print("very high complexity: ");
	println(veryComplexCode/totalCode * 100);
	
	
}

/**
debatable the infix
http://manuel-pichler.de/archives/55-The-value-of-complexity-metrics-Cyclomatic-Complexity-12.html
*/
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
					case switchCase(_,_):
						numberDecisionPoints +=1;
					case catchClause(_,_):
						numberDecisionPoints +=1;
/*					case infixExpression("&&",_,_,_) :
						numberDecisionPoints +=1;
					case infixExpression("||",_,_,_) :
						numberDecisionPoints +=1;
*/							
					case returnStatement(_):
						exitPoints +=1;
					case throwStatement(_):
						exitPoints +=1;
				}
				if (exitPoints == 0) 
					exitPoints =1; 
				return 	(numberDecisionPoints -exitPoints +2 );
	
}

public int getUnitSize(implementationAST){ 
	return 1;
}

/**
short version cycloComplex
*/
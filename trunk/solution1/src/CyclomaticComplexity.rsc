module CyclomaticComplexity

import lang::java::jdt::Java;
import lang::java::jdt::JDT;
import  lang::java::jdt::JavaADT;
import IO;

/**
debatable the infix
http://manuel-pichler.de/archives/55-The-value-of-complexity-metrics-Cyclomatic-Complexity-12.html
*/
public void cycloComplex(){
projectLoc = |project://SmallSql/.|;
outputFile = |file:///D:/vlad/cycloResults2.txt|; 
ast = createAstsFromProject(projectLoc);

for (AstNode aNode <- ast){
		linesWithCode = {};
		visit(aNode){
			case methodDeclaration(_, _, _, _, methodName, _, _, implementationAST) :{
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
					//more decision points:
					//each case from switches is a +1 to decision points -done 
					//enhanced for -done
					// ? - done
					//catch - done
					// infix : && , ||	-- discussable
					
					case returnStatement(_):
						exitPoints +=1;
					case throwStatement(_):
						exitPoints +=1;
					// more exit points: throw exceptions	
				}
				if (exitPoints == 0) 
					exitPoints =1; 
				appendToFile(outputFile, methodName+" ");
//				appendToFile(outputFile,exitPoints );
//				appendToFile(outputFile,"   "  );
				appendToFile(outputFile,(numberDecisionPoints -exitPoints +2 ));
				appendToFile(outputFile,"\n");
			 }
		}
	};	
}

/**
short version
*/
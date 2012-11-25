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
outputFile = |file:///D:/vlad/cycloResults.txt|; 
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
					case ifStatement(AstNode booleanExpression, AstNode thenStatement, Option[AstNode] elseStatement):
						numberDecisionPoints +=1;
					case doStatement(_, _):
						numberDecisionPoints +=1;
					case returnStatement(_):
						exitPoints +=1;
					//more decision points:
					//each case from switches is a +1 to decision points 
					//enhanced for 
					// ? 
					// infix : && , ||
					//catch
					
					// more exit points: throw exceptions	
				}
				if (exitPoints == 0) 
					exitPoints =1; 
				appendToFile(outputFile, methodName+" ");
				appendToFile(outputFile,exitPoints );
				appendToFile(outputFile,"   "  );
				appendToFile(outputFile,(numberDecisionPoints -exitPoints +2 ));
				appendToFile(outputFile,"\n");
			 }
		}
		//whileStatement(AstNode expression, AstNode body)
		//forStatement(list[AstNode] initializers, Option[AstNode] optionalBooleanExpression, list[AstNode] updaters, AstNode body)
		//ifStatement(AstNode booleanExpression, AstNode thenStatement, Option[AstNode] elseStatement)
		//doStatement(AstNode body, AstNode whileExpression)
	};	
}

/**
short version
*/
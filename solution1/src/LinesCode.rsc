module LinesCode
import lang::java::jdt::Java;
import lang::java::jdt::JDT;
import  lang::java::jdt::JavaADT;
import util::Resources;
import IO;
import List;
import Set;

public void linesCode(){
	projectLoc = |project://Hello/.|;
	outputFile = |file:///D:/vlad/ast.txt|; 
	methodsFile = |file:///D:/vlad/methods.txt| ;
	
	theResource = extractProject(projectLoc );
	
	ast = createAstsFromProject(projectLoc);
	//ast = createAstFromFile(outputFile);
	//println(size(ast));

	contor = 0;
	for (AstNode aNode <- ast)
	{
		visit(aNode){
		case p:AstNode : println(p@location.bl);
	
		};
	};
	print (contor);
	//res = [ info|A aNode<-ast, info = aNode@location];
	
	
	//appendToFile(outputFile, ast);
}

public void lines2(){
	projectLoc = |project://Hello/|;
	outputFile = |file:///D:/vlad/ast.txt|; 
	methodsFile = |file:///D:/vlad/methods2.txt| ;
	contor = 0;
	allFiles = extractProject(projectLoc );
	appendToFile(methodsFile, allFiles);
	for ( aFile <- allFiles)
	{
		break;
		fileAst = createAstFromFile(aFile);
		visit(fileAst){
			case methodDeclaration(_, _, _, _, methodName, _, _, _) : {contor = contor +1 ;appendToFile(methodsFile , methodName+"\n"); }
		};
	};
}

public void getAllFiles (){
	projectLoc = |project://Hello/|;
	resour = getProject(projectLoc);
	cont= 0;
	visit (resour) { 
		case file( location2): cont++; 
		}
	println(cont++);
}
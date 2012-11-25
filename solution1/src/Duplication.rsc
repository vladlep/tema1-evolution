module Duplication

import lang::java::jdt::Java;
import lang::java::jdt::JDT;
import  lang::java::jdt::JavaADT;
import util::Resources;
import IO;
import String;
import List;

/**
	"We calculate code duplication as the percentage of all code that occurs more 
than once in equal code blocks of at least 6 lines" [SIG]
	we ignore leading spaces
	long version of the function
*/
public void findClones(){
	allFiles = {|project://Hello/src/apackage/HelloWorld.java|,|project://Hello/src/apackage/MainClass.java|};
	strFile = [];
	for (aFile <- allFiles){
		strFile = [trim(aLine) |aLine <-readFileLines(aFile)];
		allFiles = allFiles - aFile;
		for(int i <-[ 0 ..  (size(strFile)-6)]){
			println(strFile[i]);
			//search same file
			for( j <- [i+5..(size(strFile)-6)]){
				break;
			}
			
			//search rest of files
			for (otherFile <-allFiles){
				break;
			}
		}
	}
}

/**
short version
*/


/**

*/
public set[loc] getAllFiles (){
	projectLoc = |project://Hello/|;
	resour = getProject(projectLoc);
	cont= 0;
	allFiles = {};
	visit (resour) { 
		case file(aFileLoc) : if(aFileLoc.extension == "java") allFiles += aFileLoc;  
	}
	return allFiles;
}

module Duplication

import lang::java::jdt::Java;
import lang::java::jdt::JDT;
import  lang::java::jdt::JavaADT;
import util::Resources;
import IO;
import String;
import List;
import Set;
import Map;

/**
	"We calculate code duplication as the percentage of all code that occurs more 
than once in equal code blocks of at least 6 lines" [SIG]
	we ignore leading spaces
	long version of the function
*/
public void calculateDuplication(selecteProject){
	allFiles = getAllFiles(selecteProject);
	
	totalDupLines = 0;
	totalNumnerLines = 0;
	map[loc,set[int]] dupMap= (aFile:{}|aFile <-allFiles) ;
	
	for (aFile <- allFiles){
		strFile = [trim(aLine) |aLine <-readFileLines(aFile), trim(aLine) !=""];
		allFiles = allFiles - aFile;
		println(aFile);
		println(size(allFiles));
		totalNumnerLines += size(strFile);
		for(i <-[0..(size(strFile)-6)]){
			//search same file
			theFragment = [strFile[i+k] |  k<-[0..5]];
			j =  i+5;
			while(j <size(strFile)-5){
				otherFragment = [strFile[j+k] | k<-[0..5]];
				if( theFragment == otherFragment){
					duplicatedLines = {dupLines+1 | dupLines <- [j ..(j+5)]};
					dupMap[aFile]+=duplicatedLines;
					duplicatedLines = {dupLines+1 | dupLines <- [i ..(i+5)]};
					dupMap[aFile]+=duplicatedLines;
				}
				j+=1;
			}
			
			//search rest of files
			for (otherFile <-allFiles){
				otherFileStr = [trim(aLine) |aLine <-readFileLines(otherFile), trim(aLine) != ""];
				for(int j<-[0..(size(otherFileStr)-6)]){
					otherFragment = [otherFileStr[j+k] | k<-[0..5]];
					if( theFragment == otherFragment){
						duplicatedLines = {dupLines+1 | dupLines <- [j ..(j+5)]};
						
						dupMap[otherFile] += duplicatedLines;
						duplicatedLines = {dupLines+1 | dupLines <- [i ..(i+5)]};
						dupMap[aFile] += duplicatedLines;	
					}
				}
			}
		}
	}
	allFiles = domain(dupMap);
	println(dupMap);
	totalDupLines = sum ([ size(dupMap[aFile]) | aFile <- allFiles ]);
	println(totalDupLines );
	println(totalNumnerLines);
	println("Duplicated code percentage:");
	print(totalDupLines*100.0/totalNumnerLines);
	println("%");
	
}

public set[loc] getAllFiles (projectLoc){
	//projectLoc = |project://Hello/|;
	resour = getProject(projectLoc);
	allFiles = {};
	visit (resour) { 
		case file(aFileLoc) : if(aFileLoc.extension == "java") allFiles += aFileLoc;  
	}
	return allFiles;
}


public real complicatedGetDup(selecteProject){
	allFiles = getAllFiles(selecteProject);
	totalDupLines = 0;
	totalNumnerLines = 0;
	map[list[str],set[tuple[loc file,list [int] lines]] ] dupMap= ([""]:{}) ;
	for (aFile <- allFiles){
		strFile = [trim(aLine) |aLine <-readFileLines(aFile), trim(aLine) !=""];
		println(aFile);
		totalNumnerLines += size(strFile);
		for(i <-[0..(size(strFile)-6)]){
			//search same file
			theFragment = [strFile[i+k] |  k<-[0..5]];
			if(theFragment in domain(dupMap)){
				dupMap[theFragment]+= {<aFile,[i..i+5]>};
			}	
			else{
				dupMap[theFragment] ={ <aFile,[i..i+5]>};
			}
		}
	}
	//cut the keys from the map
	newListDup = [dupMap[key]	|key<-domain(dupMap), size(dupMap[key])>1];
	//get all unique files
	allFilesWithDup = {aTuple.file | setDup<-newListDup, aTuple <-toList(setDup)};
	//initit newMap
	loc aLoc = toList(allFilesWithDup)[0];
	map[loc,set[int]] newMap = (aLoc:{-1});
	for (aFileWithDup <-allFilesWithDup){ 
		if(!( aFileWithDup in domain(newMap))){
			newMap[aFileWithDup ] = {};
		}
	}	
	//put values for all the keys, in the new map
	for (aFileWithDup <-allFilesWithDup){ 
		newMap[aFileWithDup] += {aLine | setDup<-newListDup, aTuple <-toList(setDup), aTuple.file == aFileWithDup, aLine <-aTuple.lines };
		newMap[aFileWithDup] -= {-1};
	}
	totalDupLines = sum ([ size(newMap[aFile]) | aFile <- allFilesWithDup ]);
	println(totalDupLines );
	println(totalNumnerLines);
	println("Duplicated code percentage:");
	result = totalDupLines *100.0 /totalNumnerLines; 
	print(result);
	println("%");	
	return result; 
}

module Rankings

str fiveStars = "*****";
str fourStars = "****";
str threeStars = "***";
str twoStars = "**";
str oneStar = "*";

public str getRankingForLinesCode(int noLinesCode) {

	if(noLinesCode <= 66 * 1000) {
		return fiveStars;
	}
	if (noLinesCode <= 246 * 1000) {
		return fourStars;
	}
	if (noLinesCode <= 665 * 1000) {
		return threeStars;
	}
	if (noLinesCode <= 1310 * 1000) {
		return twoStars;
	}
	return oneStar;
} 

public str getRankingForComplexityPerUnit(map[str, real] complexity) {

	if(complexity["moderate"]<= 25 && complexity["high"] == 0 && complexity["very high"] == 0) {
		return fiveStars;
	}
	if(complexity["moderate"]<= 30 && complexity["high"] <= 5 && complexity["very high"] == 0) {
		return fourStars;
	}
	if(complexity["moderate"]<= 40 && complexity["high"] <= 10 && complexity["very high"] == 0) {
		return threeStars;
	}
	if(complexity["moderate"]<= 50 && complexity["high"] <= 15 && complexity["very high"] <= 5) {
		return twoStars;
	}
	return oneStar;
} 

public str getRankingForDuplication(real percentageDuplicatedLines) {

	if(percentageDuplicatedLines <= 3) {
		return fiveStars;
	}
	if (percentageDuplicatedLines <= 5) {
		return fourStars;
	}
	if (percentageDuplicatedLines <= 10) {
		return threeStars;
	}
	if (percentageDuplicatedLines <= 20) {
		return twoStars;
	}
	return oneStar;
} 



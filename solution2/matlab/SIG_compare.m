prjName = 'allProjects';
%data : 
%    column 1 = CC
%    column 2 = LOC

p = trainFunction( [smallTrain;xmlTrain;hsqlTrain;argoTrain;],prjName );

data = [xmlTrain; xmlTest];
moderateCode = 0;
complexCode=0;
veryComplexCode=0;

for i=1:size(data,1)
    cyc = 1/ p(2) * data(i,2) - p(1)/p(2);
    if(11<= cyc && cyc <= 20) 
            moderateCode = moderateCode +data(i,2);
    end
    if(21<= cyc && cyc <= 50) 
        complexCode = complexCode+data(i,2);
    end
    if(50< cyc) 
        veryComplexCode = veryComplexCode+data(i,2);
    end
    

end
modC = moderateCode*100.0/sum(data(:,2))
highC = complexCode*100.0/sum(data(:,2))
veryHighC = veryComplexCode*100.0/sum(data(:,2))

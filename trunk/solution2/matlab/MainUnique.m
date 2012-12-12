load smallTrain.mat;
load smallTest.mat;
prjName = 'unique-small';
p = trainFunction( unique(smallTrain,'rows'),prjName );
calcError(p(1), p(2),unique(smallTest,'rows'), prjName );

load xmlTrain.mat;
load xmlTest.mat;
prjName = 'unique-xml';
p = trainFunction( unique(xmlTrain,'rows'),prjName );
calcError(p(1), p(2),unique(xmlTest,'rows'), prjName );

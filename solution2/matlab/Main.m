load smallTrain.mat;
load smallTest.mat;
prjName = 'small';
generateHist([smallTrain;smallTest], prjName );
p = trainFunction( smallTrain,prjName );
calcError(p(1), p(2),smallTest, prjName );

load xmlTrain.mat;
load xmlTest.mat;
prjName = 'xml';
generateHist([xmlTrain;xmlTest], prjName );
p = trainFunction( xmlTrain,prjName );
calcError(p(1), p(2),xmlTest, prjName );

load hsqlTrain.mat;
load hsqlTest.mat;
prjName = 'hsql';
generateHist([hsqlTrain;hsqlTest], prjName );
p = trainFunction( hsqlTrain,prjName );
calcError(p(1), p(2),hsqlTest, prjName );

load argoTrain.mat;
load argoTest.mat;
prjName = 'argo';
generateHist([argoTrain;argoTest], prjName );
p = trainFunction( argoTrain,prjName );
calcError(p(1), p(2),argoTest, prjName );

prjName = 'allProjects';
generateHist([smallTrain;smallTest;xmlTrain;xmlTest;hsqlTrain;hsqlTest;argoTrain;argoTest], prjName );
p = trainFunction( [smallTrain;xmlTrain;hsqlTrain;argoTrain;],prjName );

calcError(p(1), p(2),argoTest, 'allFunctionTestedOnArgo' );
calcError(p(1), p(2),xmlTest, 'allFunctionTestedOnXml' );
calcError(p(1), p(2),smallTest, 'allFunctionTestedOnSmall' );
calcError(p(1), p(2),hsqlTest, 'allFunctionTestedOnHsql' );
calcError(p(1), p(2),[argoTest;xmlTest;smallTest;hsqlTest], 'allFunctionTestedOnAll' );

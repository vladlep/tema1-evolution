%compare for unique the best solution : 
%inner project prediction vs general prediction. APROX THE SAME.Chose
%inner.
%train all point or only unique points. WIN: all points. We can have dup in
%traind + test but we accept it.

prjName = 'unique-allProjects';
p = trainFunction( [smallTrain;xmlTrain;hsqlTrain;argoTrain;],prjName );
[squareError,allResults] = calcError(p(1), p(2),unique([xmlTest], 'rows'),'allFunctionTestedOnAll1' );
[squareError,allResults] = calcError(p(1), p(2),unique([smallTest], 'rows'),'allFunctionTestedOnAll2' );
[squareError,allResults] = calcError(p(1), p(2),unique([hsqlTest], 'rows'),'allFunctionTestedOnAll3' );
% 
% p = trainFunction( unique([smallTrain;xmlTrain;hsqlTrain;argoTrain;],'rows'),prjName );
% [squareError,allResults] = calcError(p(1), p(2),unique([argoTest;xmlTest;smallTest;hsqlTest], 'rows'),'allFunctionTestedOnAll2' );


% prjName = 'unique-argo';
% p = trainFunction( argoTrain,prjName );
% [squareError,argoResults] = calcError(p(1), p(2),unique(argoTest,'rows'), prjName );

% p = trainFunction( unique(argoTrain,'rows'),prjName );
% [squareError,argoResults] = calcError(p(1), p(2),unique(argoTest,'rows'), prjName );

prjName = 'unique-hsql';
p = trainFunction( hsqlTrain,prjName );
calcError(p(1), p(2),unique(hsqlTest,'rows'), prjName );
% 
prjName = 'unique-small';
p = trainFunction(smallTrain,prjName );
[squareError,smallResults] = calcError(p(1), p(2),unique(smallTest,'rows'), prjName );

prjName = 'unique-xml';
p = trainFunction( xmlTrain,prjName );
[squareError,xmlResults] = calcError(p(1), p(2),unique(xmlTest,'rows'), prjName );

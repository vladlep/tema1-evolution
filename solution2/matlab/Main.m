load results.mat;

p = trainFunction( results,'argo')
calcError(p(1), p(2),data, 'argo');
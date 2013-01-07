
subplot(1,2,1)
% data = [argoTest;argoTrain];
% data = [xmlTrain;xmlTest];
% data = [hsqlTrain; hsqlTest];
data = [smallTrain;smallTest];
hist(data(:,2),409)
subplot(1,2,2)
hist(data(:,1),100)
% peak for argo: Cyc = 1 >4100 methods; LOC = 3 ~ >9400 methods

subplot(1,2,2)
xlabel('Cyclomatic complexity')
ylabel('Number of methods')
subplot(1,2,1)
xlabel('Lines of code')
ylabel('Number of methods')


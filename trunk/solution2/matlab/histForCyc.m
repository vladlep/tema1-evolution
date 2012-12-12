function [loc] = histForCyc( cyc,results)
%CREATE Summary of this function goes here
%   Detailed explanation goes here
loc = [];
for i=1:size(results,1)
        if(results(i,1) == cyc )
            loc = [loc;results(i,2)];
        end
end

end


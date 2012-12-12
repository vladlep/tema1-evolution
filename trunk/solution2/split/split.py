import random

def split(file, dir):
    rd = open(file, "r")
    traind = open(dir + "/train.txt", "w")
    testd = open(dir + "/test.txt", "w")
    line = rd.readline()
    while line:
        line = rd.readline()
        rand = random.random()
        if rand<=0.66:
            traind.write(line)
        else:
            testd.write(line)
    rd.close()
    traind.close()
    testd.close()

split("small-results.txt","small")	
# split("argouml-unit-results.txt","argo")
# split("hsql-results.txt", "hsql")
# split("xml-commons-results.txt", "xml")
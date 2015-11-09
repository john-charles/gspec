package com.example.gspec
/**
 * Created by jcsokolow on 11/6/15.
 *
 */
class SpecRunner {

    IPrinter printer;
    CaseInfo currentCase;
    SpecInfo currentSpec;
    List<SpecInfo> specs = []


    def addAssertion(arg){
        Assertion assertion = new Assertion(arg);
        currentCase.assertions.add(assertion);
        return assertion;
    }

    def addCaseBlock(String name, Closure body){
        currentSpec.cases.add(new CaseInfo(name, body))
    }

    def addBeforeBlock(Closure body){
        currentSpec.befores.add(body);
    }

    def addSpecBlock(String name, Closure body){
        if(currentSpec){
            currentSpec.specs.add(new SpecInfo(name, body))
        } else {
            specs.add(new SpecInfo(name, body))
        }
    }

    def runCase(CaseInfo caseInfo){
        printer.printLine(caseInfo.name);
        currentCase = caseInfo;
        caseInfo.body()

        for(Assertion assertion: caseInfo.assertions){
            if(!assertion.isOk()){
                printer.printLine("Expected '")
            }
        }
    }

    def runBefore(SpecInfo specInfo){
        for(Closure before: specInfo.befores){
            before();
        }
    }

    def runSpec(SpecInfo specInfo){
        currentSpec = specInfo;
        specInfo.body();

        runBefore(specInfo);

        for(CaseInfo caseInfo: specInfo.cases){
            runCase(caseInfo);
        }

        for(SpecInfo childInfo: specInfo.specs){
            runSpec(childInfo);
        }

        currentSpec = null;

    }


}

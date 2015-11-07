package com.example.gspec
/**
 * Created by jcsokolow on 11/6/15.
 *
 */
class SpecRunner {

    CaseInfo currentCase;
    SpecInfo currentSpec;
    IPrinter printer;
    List<CaseInfo> cases = []
    List<SpecInfo> specs = []


    def addAssertion(arg){
        Assertion assertion = new Assertion(arg);
        currentCase.assertions.add(assertion);
        return assertion;
    }

    def addCaseBlock(String name, Closure body){
        cases.add(new CaseInfo(name, body))
    }

    def addSpecBlock(String name, Closure body){
        specs.add(new SpecInfo(name, body))
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

    def runSpec(SpecInfo specInfo){
        currentSpec = specInfo;
        specInfo.body();

        for(CaseInfo caseInfo: cases){
            runCase(caseInfo);
        }

    }


}

package com.example.old

import com.example.gspec.Assertion

class CaseInfo {

    String name;
    Closure body;
    List<Assertion> assertions;

    CaseInfo(String name, Closure body){
        this.name = name;
        this.body = body;
        this.assertions = new LinkedList<>();
    }

}

interface SpecInfo {

    List<CaseInfo> getCases();
    List<SpecInfo> getChildSpecs();
}

class RealSpecInfo implements SpecInfo {

    String name;
    Closure body;
    List<Closure> afters;
    List<Closure> befores;
    List<CaseInfo> cases;
    List<SpecInfo> childSpecs;

    RealSpecInfo(String name, Closure body){
        this.name = name;
        this.body = body;
        this.befores = new LinkedList<>();
        this.afters = new LinkedList<>();
        this.cases = new LinkedList<>();
        this.childSpecs = new LinkedList<>();
    }
}

class RootSpecInfo implements SpecInfo {

    List<CaseInfo> cases;
    List<SpecInfo> specs;

    RootSpecInfo(){
        cases = new LinkedList<>();
        specs = new LinkedList<>();
    }

    @Override
    List<SpecInfo> getChildSpecs() {
        return specs;
    }
}


class SpecRunner {

    RootSpecInfo root;
    List<Closure> befores;
    List<Closure> afters;
    List<SpecInfo> specList;
    List<CaseInfo> caseList;
    List<Assertion> assertions;

    SpecRunner(){
        root = new RootSpecInfo();
        specList = root.childSpecs;
    }

    def addBefore(Closure before){
        befores.add(before);
    }

    def addAfter(Closure after){
        afters.add(after);
    }

    def addSpec(String description, Closure closure){
        specList.add(new RealSpecInfo(description, closure));
    }

    def addCase(String description, Closure body){
        caseList.add(new CaseInfo(description, body));
    }

    def addAssert(Object arg){

        Assertion assertion = new Assertion(arg);
        assertions.add(assertion);
        return assertion;
    }

    def runCase(CaseInfo caseInfo){

        assertions = caseInfo.assertions;
        caseInfo.body();

    }

    def runSpec(RealSpecInfo specInfo, indent){

        afters = specInfo.afters;
        befores = specInfo.befores;

        specList = specInfo.childSpecs
        caseList = specInfo.cases;

        print indent + specInfo.name + "\n";

        specInfo.body.call();

        for(Closure before: specInfo.befores){
            before();
        }

        for(CaseInfo caseInfo: caseList){
            runCase(caseInfo);
        }

        for(SpecInfo child: specInfo.childSpecs){
            runSpec((RealSpecInfo)child, indent + 1);
        }

        for(Closure after: specInfo.afters){
            after();
        }

    }

    def runReal(){

         for(SpecInfo specInfo: specList){
             runSpec((RealSpecInfo)specInfo, 0);
         }
    }

    def realPrintResults(String description, RealSpecInfo specInfo){

        description = description + " " + specInfo.name;

        print description + "\n"

        for(CaseInfo caseInfo: specInfo.cases){
            print "it should " + caseInfo.name + "\n";

            for(Assertion assertion: caseInfo.assertions){
                if(!assertion.isOk()){
                    print "Error: expected " + assertion.arg + " to be " + assertion.match + "\n";
                }
            }
        }

        for(SpecInfo child: specInfo.childSpecs){
            realPrintResults(description, (RealSpecInfo)child)
        }
    }

    def printResults(){

        for(SpecInfo specInfo: root.childSpecs){
            realPrintResults("", (RealSpecInfo)specInfo);
        }

    }

    static SpecRunner runner;

    static {
        runner = new SpecRunner();
    }

    static def beforeEach(Closure before){
        runner.addBefore before
    }

    static def afterEach(Closure after){
        runner.addAfter after
    }

    static def Describe(String description, Closure body){
        runner.addSpec(description, body);
    }

    static def It(String desc, Closure body){
        runner.addCase(desc, body);
    }

    static def expect(Object thing){
        return runner.addAssert(thing);
    }


    static def runSpecs(){
        runner.runReal()
        runner.printResults();
    }
}
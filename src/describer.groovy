class Assertion {

    Object arg;
    Object match;
    boolean matched;

    Assertion(Object arg){
        this.arg = arg;
        this.match = null;
    }

    def toBe(Object match){
        this.match = match;
        this.matched = this.arg == match;
    }

    def toEqual(Object match){
        this.match = match;
        this.matched = this.arg.equals(match);
    }

    def toBeLessThan(Object match){
        this.match = match;
        this.matched = this.arg < match;
    }

    def toBeGreaterThan(Object match){
        this.match = match;
        this.matched = this.arg > match
    }

    def toBeNull(){
        this.match = null;
        this.matched = this.arg == null
    }

    def check() {
        return matched
    }
}

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

    def addCase(CaseInfo info);
    def addChildSpec(SpecInfo info);
}

class RealSpecInfo implements SpecInfo {

    String name;
    Closure body;
    List<CaseInfo> cases;
    List<SpecInfo> childSpecs;

    RealSpecInfo(String name, Closure body){
        this.name = name;
        this.body = body;
        this.cases = new LinkedList<>();
        this.childSpecs = new LinkedList<>();
    }

    def addCase(CaseInfo caseInfo){
        this.cases.add(caseInfo);
    }

    def addChildSpec(SpecInfo child){
        this.childSpecs.add(child);
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

    @Override
    def addCase(CaseInfo info) {
        cases.add(info);
    }

    @Override
    def addChildSpec(SpecInfo info) {
        specs.add(info);
    }
}


class SpecRunner {

    RootSpecInfo root;
    List<SpecInfo> specList;
    List<CaseInfo> caseList;
    List<Assertion> assertions;

    SpecRunner(){
        root = new RootSpecInfo();
        specList = root.childSpecs;
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

    def runSpec(RealSpecInfo specInfo){

        specList = specInfo.childSpecs
        caseList = specInfo.cases;
        specInfo.body.call();

        for(CaseInfo caseInfo: caseList){
            runCase(caseInfo);
        }

        for(SpecInfo child: specInfo.childSpecs){
            runSpec((RealSpecInfo)child);
        }


    }

    def runReal(){

         for(SpecInfo specInfo: specList){
             runSpec((RealSpecInfo)specInfo);
         }
    }

    def realPrintResults(String description, RealSpecInfo specInfo){

        description = description + " " + specInfo.name;

        print description + "\n"

        for(CaseInfo caseInfo: specInfo.cases){
            print "it should " + caseInfo.name + "\n";

            for(Assertion assertion: caseInfo.assertions){
                if(!assertion.check()){
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
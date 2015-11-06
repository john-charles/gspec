class Assertion {

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

    SpecInfo currentSpec;
    CaseInfo currentCase;
    RootSpecInfo root;

    SpecRunner(){
        root = new RootSpecInfo();
        currentSpec = root;
    }

    def describe(String description, Closure closure){
        currentSpec.addChildSpec(new RealSpecInfo(description, closure));
    }

    def it(String description, Closure body){
        currentSpec.addCase(new CaseInfo(description, body));
    }

    def run(){

         SpecInfo current = this.currentSpec;
         for(SpecInfo info: current.childSpecs){
             this.currentSpec = info;
             info.body()

             for(CaseInfo caseInfo: info.cases){
                 currentCase = caseInfo;
                 caseInfo.body();
             }

             this.currentSpec = current;
         }


    }
}

def main(String[] args){
    print "Stuff"
}







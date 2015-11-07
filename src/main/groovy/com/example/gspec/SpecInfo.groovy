package com.example.gspec

/**
 * Created by john-charles on 11/6/15.
 */
class SpecInfo {

    String name;
    Closure body;
    List<CaseInfo> cases;
    List<SpecInfo> specs;


    SpecInfo(String name, Closure body){
        this.name = name;
        this.body = body;
        this.cases = new LinkedList<>()
        this.specs = new LinkedList<>()
    }

}

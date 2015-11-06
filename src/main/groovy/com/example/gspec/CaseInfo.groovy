package com.example.gspec

/**
 * Created by jcsokolow on 11/6/15.
 */
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

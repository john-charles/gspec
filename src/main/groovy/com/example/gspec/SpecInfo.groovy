package com.example.gspec

/**
 * Created by john-charles on 11/6/15.
 */
class SpecInfo {

    String name;
    Closure body;

    SpecInfo(String name, Closure body){
        this.name = name;
        this.body = body;
    }

}

package com.example.gspec

/**
 * Created by jcsokolow on 11/6/15.
 */
class Assertion {

    private boolean ok;
    private arg;
    private match;

    Assertion(arg){
        this.ok = false;
        this.arg = arg;
    }

    boolean isOk(){
        return ok;
    }

    def toBe(match){
        this.match = match;
        ok = arg == match;
    }

    def toEqual(match){
        this.match = match;
        ok = arg.equals(match);
    }

    def toBeTruthy(){
        this.match = "truthy"
        if(this.arg){
            ok = true;
        } else {
            ok = false;
        }
    }

    def toBeNull(){
        this.match = "null";
        ok = this.arg == null;
    }



}

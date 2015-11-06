package com.example.gspec

import static com.example.old.SpecRunner.*;


Describe("An assertion", {

    Assertion assertion;

    Describe("primitives", {

        It("should be true when a match happens", {

            assertion = new Assertion(true);

            expect(assertion.isOk()).toBe(false);
            assertion.toBe(true);
            expect(assertion.isOk()).toBe(true);

        });

        It("should be false when a match fails", {

            assertion = new Assertion(true);

            expect(assertion.isOk()).toBe(false);
            assertion.toBe(false);
            expect(assertion.isOk()).toBe(false);

        });
    })


    Describe("objects with a .equals", {

        It("should be true when a match happens", {

            assertion = new Assertion("Hello");

            expect(assertion.isOk()).toBe(false);
            assertion.toEqual("Hello");
            expect(assertion.isOk()).toBe(true);

        });

        It("should be false when a match fails", {

            assertion = new Assertion("Hello");

            expect(assertion.isOk()).toBe(false);
            assertion.toBe("World");
            expect(assertion.isOk()).toBe(false);

        });
    })

    Describe("booleaness", {

        It("should be true when a match happens", {

            assertion = new Assertion(true);

            expect(assertion.isOk()).toBe(false);
            assertion.toBeTruthy();
            expect(assertion.isOk()).toBe(true);

        });

        It("should be false when a match fails", {

            assertion = new Assertion(false);

            expect(assertion.isOk()).toBe(false);
            assertion.toBeTruthy();
            expect(assertion.isOk()).toBe(false);

        });
    })

    Describe("nulls", {

        It("should be true when a match happens", {

            assertion = new Assertion(null);

            expect(assertion.isOk()).toBe(false);
            assertion.toBeNull();
            expect(assertion.isOk()).toBe(true);

        });

        It("should be false when a match fails", {

            assertion = new Assertion(false);

            expect(assertion.isOk()).toBe(false);
            assertion.toBeNull();
            expect(assertion.isOk()).toBe(false);

        });
    })



});

runSpecs()
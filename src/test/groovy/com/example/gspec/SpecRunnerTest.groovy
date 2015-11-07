package com.example.gspec

import static com.example.old.SpecRunner.*;

Describe("A Spec Runner", {

    SpecRunner runner;

    beforeEach({
        runner = new SpecRunner();

        def prints = [];

        runner.printer = new IPrinter() {
            @Override
            void printLine(String line) {
                prints.add(line);
            }
        }
    });

    It("should collect it blocks", {

        runner.addCaseBlock("my case", {

        });

        expect(runner.cases.size()).toBe(1);

    });

    Describe("Running an It block", {

        It("should collect assertions when its It blocks are run", {

            runner = new SpecRunner();

            runner.printer = new IPrinter() {
                @Override
                void printLine(String line) {

                }
            }

            runner.addCaseBlock("my case", {
                runner.addAssertion(true).toBe(true);
            });

            expect(runner.cases.size()).toBe(1);
            CaseInfo caseInfo = runner.cases.get(0)

            expect(caseInfo.assertions.size()).toBe(0);

            runner.runCase(caseInfo);

            expect(caseInfo.assertions.size()).toBe(1);

        });

        It("should validate assertions once they are collected", {

            runner = new SpecRunner();

            def prints = [];

            runner.printer = new IPrinter() {
                @Override
                void printLine(String line) {
                    prints.add(line);
                }
            }

            runner.addCaseBlock("my case", {
                runner.addAssertion(true).toBe(true);
                runner.addAssertion(true).toBe(false);
            });

            expect(runner.cases.size()).toBe(1);
            CaseInfo caseInfo = runner.cases.get(0)

            expect(caseInfo.assertions.size()).toBe(0);

            runner.runCase(caseInfo);

            expect(caseInfo.assertions.size()).toBe(2);
            expect(prints.size()).toBe(2);
            expect(prints[0]).toEqual("my case");

        });

        It("should validate assertions once they are collected", {

            runner = new SpecRunner();

            def prints = [];

            runner.printer = new IPrinter() {
                @Override
                void printLine(String line) {
                    prints.add(line);
                }
            }

            runner.addCaseBlock("my case", {
                runner.addAssertion(true).toBe(true);
                runner.addAssertion(true).toBe(false);
            });

            expect(runner.cases.size()).toBe(1);
            CaseInfo caseInfo = runner.cases.get(0)

            expect(caseInfo.assertions.size()).toBe(0);

            runner.runCase(caseInfo);

            expect(caseInfo.assertions.size()).toBe(2);
//            expect(prints.size()).toBe(2);
            expect(prints[0]).toEqual("my case");

        });


    });

    Describe("Running a spec block", {

        It("should collect all the cases", {

            runner = new SpecRunner();

            def prints = [];

            runner.printer = new IPrinter() {
                @Override
                void printLine(String line) {
                    prints.add(line);
                }
            }

            def oneRan = false;
            def twoRan = false;

            runner.addSpecBlock("describe a thing", {

                runner.addCaseBlock("block one", {
                    oneRan = true;
                });

                runner.addCaseBlock("block two", {
                    twoRan = true;
                });

            });

            def firstSpec = runner.specs.get(0);
            runner.runSpec(firstSpec);

            expect(oneRan).toBe(true);
            expect(twoRan).toBe(true);
            expect(firstSpec.cases.size()).toBe(2);

        });

        It("Should collect specs within the spec", {

            runner = new SpecRunner();

            def prints = [];

            runner.printer = new IPrinter() {
                @Override
                void printLine(String line) {
                    prints.add(line);
                }
            }

            def oneRan = false;
            def twoRan = false;

            runner.addSpecBlock("describe a thing", {

                runner.addSpecBlock("spec 1", {
                    oneRan = true;
                });

                runner.addSpecBlock("spec 2", {
                    twoRan = true;
                })

            });

            def firstSpec = runner.specs.get(0)
            runner.runSpec(firstSpec);

            expect(oneRan).toBe(true);
            expect(twoRan).toBe(true);
            expect(runner.currentSpec).toBe(null);
            expect(firstSpec.specs.size()).toBe(2);

        });
    });
});

runSpecs();
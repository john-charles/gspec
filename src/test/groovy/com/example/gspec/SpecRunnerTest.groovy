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

    Describe("Running an It block", {

        It("should collect assertions when its It blocks are run", {

            runner = new SpecRunner();

            runner.printer = new IPrinter() {
                @Override
                void printLine(String line) {

                }
            }

            runner.addSpecBlock("my spec", {
                runner.addCaseBlock("my case", {
                    runner.addAssertion(true).toBe(true);
                });
            });

            def spec = runner.specs.get(0);



            runner.runSpec(spec);

            expect(spec.cases.size()).toBe(1);

            CaseInfo caseInfo = spec.cases.get(0)

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

            runner.addSpecBlock("my block", {
                runner.addCaseBlock("my case", {
                    runner.addAssertion(true).toBe(true);
                    runner.addAssertion(true).toBe(false);
                });
            });

            SpecInfo specInfo = runner.specs.get(0);

            runner.runSpec(specInfo);

            CaseInfo caseInfo = specInfo.cases.get(0)

            expect(caseInfo.assertions.size()).toBe(2);
            expect(caseInfo.assertions[0].ok).toBe(true);
            expect(caseInfo.assertions[1].ok).toBe(false);
            expect(prints.size()).toBe(2);
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

        It("Should run all the beforeEaches", {

            runner = new SpecRunner();

            def prints = [];

            runner.printer = new IPrinter() {
                @Override
                void printLine(String line) {
                    prints.add(line);
                }
            }

            def runs = []

            runner.addSpecBlock("describe a thing", {

                runner.addBeforeBlock({
                    runs.add("before");
                });

                runner.addSpecBlock("spec 1", {
                    runs.add("one")

                    runner.addBeforeBlock {
                        runs.add "inner before"
                    }

                    runner.addCaseBlock("case", {
                        runs.add("case")
                    })

                });

                runner.addSpecBlock("spec 2", {
                    runs.add("two");
                });

            });

            def firstSpec = runner.specs.get(0)
            runner.runSpec(firstSpec);

            expect(runs[0]).toEqual("before");
            expect(runs[1]).toEqual("one");
            expect(runs[2]).toEqual("two");


            expect(runner.currentSpec).toBe(null);
            expect(firstSpec.specs.size()).toBe(2);

        });

    });
});

runSpecs();
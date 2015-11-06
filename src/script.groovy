import static SpecRunner.*;

Describe("Stuff", {

    beforeEach({

        print "outer before\n"

    });

    It("DO Thing", {

        expect(true).toBe(true);

    });

    afterEach({
        print "outer after\n"
    });

    Describe("inner stuff", {

        beforeEach {
            print "inner before\n"
        }

        It("do other thing", {
            expect(true).toBe(false);
            expect(33).toBeGreaterThan(40)
        });

        afterEach {
            print "inner after\n"
        }
    });

});

runSpecs();

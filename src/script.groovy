import static SpecRunner.*;

describe("Stuff", {

    itShould("DO Thing", {

        expect(true).toBe(true);

    });

    describe("inner stuff", {

        itShould("do other thing", {
            expect(true).toBe(false);
        });
    });

});

runSpecs();

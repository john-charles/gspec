import static SpecRunner.*;

Describe("Stuff", {

    It("DO Thing", {

        expect(true).toBe(true);

    });

    Describe("inner stuff", {

        It("do other thing", {
            expect(true).toBe(false);
            expect(33).toBeGreaterThan(40)
        });
    });

});

runSpecs();

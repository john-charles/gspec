import static SpecRunner.*;

class Stack {

    def count = 0;
    def array = new LinkedList<>();

    Stack(){
        for(int i = 0; i < 500; i++) {
            array.add(null)
        }
    }

    def size() {
        return count;
    }

    def push(arg) {
        array.add(++count, arg);
    }

    def pop(){
        array.get(count--);

    }

}

Describe("A Stack", {

    def stack;

    beforeEach({
        stack = new Stack();
    });

    It("should grow when something is added", {

        expect(stack.size()).toBe(0);
        stack.push("Thing")
        expect(stack.size()).toBe(1);

    });

    Describe("getting items", {

        beforeEach({

            stack.push("Thing");

        });

        It("should return the thing pushed on when pop is called", {
            expect(stack.pop()).toBe("Thing");
        });

        Describe("when lots of stuff is on the stack", {

            beforeEach({
                stack.push("1");
                stack.push("2");
                stack.push("3");
                stack.push("4");
                stack.push("5");
                stack.push("6");
                stack.push("7");
                stack.push("8");

            })

            It("should return the stuff in reverse order", {

                expect(stack.pop()).toBe("8")
                expect(stack.pop()).toBe("7")
                expect(stack.pop()).toBe("6")
                expect(stack.pop()).toBe("5")
                expect(stack.pop()).toBe("4")
                expect(stack.pop()).toBe("3")
                expect(stack.pop()).toBe("2")
                expect(stack.pop()).toBe("1")

            })

        })




    });

});

runSpecs();

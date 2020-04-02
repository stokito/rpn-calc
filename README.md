# CLI RPN Calculator
The command-line [Reverse Polish Notation (RPN)](https://en.wikipedia.org/wiki/Reverse_Polish_notation) calculator.

## Specifications

* The calculator should use standard input and standard output
* It should implement the four standard arithmetic operators
* The calculator should handle errors and recover gracefully
* The calculator should exit when it receives a `q` command or an end of input indicator (`EOF` / `Ctrl+D`)

## Build and run
You need a JDK 13 or later and Maven. To build run `mvn clean package`.
The executable jar will be built and placed into `target/rpn-calc.jar`.
To run it use:

    java -jar target/rpn-calc.jar

Then it will prompt for an input with a line started with `> `.

## Usage

For example to calculate an expression `5 / (9 - 1) = 0.625` write each number (operand) and press Enter then write an arithmetic command (operator):  

    > 5
    5
    > 9
    9
    > 1
    1
    > -
    8
    > /
    0.625
    >

The last number is your result. You can use a single line input:

    > 5 9 1 - /
    0.625

To exit the program you can input command `quit` or simply `q`.

You can also use other commands for example `debug` or `d` to see the values stack and `clear` or `c` to remove everything from the stack:

    > 1
    1
    > 2
    2
    > 3
    3
    > d
    3
    2
    1
    > c
    > d
    >


The calculator also have other functions from `java.lang.Math` and to see them use a command `help` or `h`:

    > h
    *
    +
    -
    /
    IEEEremainder
    abs
    acos
    asin
    atan
    atan2
    cbrt
    ceil
    copySign
    cos
    cosh
    exp
    expm1
    floor
    hypot
    log
    log10
    log1p
    max
    min
    neg
    nextAfter
    nextDown
    nextUp
    pow
    rint
    signum
    sin
    sinh
    sqrt
    tan
    tanh
    toDegrees
    toRadians
    ulp
    >

For example to calculate square root and negate it:

    > 4
    4
    > sqrt
    2
    > neg
    -2
    >

## Implementation

The core of the calculator is `Calc` class which contains the values stack and performs all the calculations.
The class designed in a JavaBeans style and have getters and setters so can be easily used with DI.
The actual parsing and interaction with a user via the command line performed by the `StreamProcessor` class.
You can extend the calc and add your custom operator by extending `CalcUnaryOp` or `CalcBinaryOp`. Then you'll need to add them into `Ops.BASIC_OPS` list or this can be done with DI.

Internally the calculator uses a `double` type so all the calculations have the same issues as IEEE-754 float numbers have.

 
# Polynomial Integration Calculator

A mathematical expression parser and evaluator using generic binary search trees to compute indefinite and definite integrals with automatic simplification.

## Overview

Calculus computation tool that parses polynomial expressions, stores terms in a generic BST maintaining sorted order by exponent, and evaluates integrals using recursive algorithms. Demonstrates advanced data structure implementation with practical mathematical applications.

## Features

- **Expression Parsing**: Handles complex polynomial terms with positive/negative integer exponents
- **Generic BST Implementation**: Type-safe tree structure with Comparable interface
- **Recursive Algorithms**: Tree insertion, traversal, and evaluation
- **Integral Evaluation**: 
  - Indefinite integrals (anti-derivatives with constant C)
  - Definite integrals with numerical evaluation to 3 decimal places
- **Automatic Simplification**: 
  - Combine like terms (same exponent)
  - Fraction reduction using GCD
  - Standard form output (highest to lowest exponent)
  - Zero coefficient elimination

## Supported Operations

### Indefinite Integrals
```
Input:  | 3x^2 + 2x + 1 dx
Output: x^3 + x^2 + x + C
```

### Definite Integrals
```
Input:  1|4 x^-2 + 3x + 4 dx
Output: (3/2)x^2 + 4x - x^-1, 1|4 = 35.250
```

### Complex Expressions
```
Input:  -2|2 x^3 - 4x dx
Output: (1/4)x^4 - 2x^2, -2|2 = 0.000
```

### Like Terms Combination
```
Input:  | 2x^2 + 3x^2 + x^2 dx
Output: 2x^3 + C
```

### Negative Exponents
```
Input:  0|2 x^-3 + x^-2 dx
Output: (-1/2)x^-2 - x^-1, 0|2 = -0.625
```

## Technical Implementation

### Class Architecture
```java
Term (Implements Comparable<Term>)
├── int coefficient
├── int exponent
├── compareTo(Term other)  // Compares by exponent
├── toString()             // Formats term for output
└── integrate()            // Returns integrated term

Node<T extends Comparable<T>> (Generic)
├── T data
├── Node<T> left
├── Node<T> right
└── Comparable implementation delegates to data

BinTree<T extends Comparable<T>> (Generic)
├── Node<T> root
├── insert(T data)         // Recursive insertion
├── search(T data)         // Recursive search
├── traverse()             // In-order traversal
└── All operations use recursion
```

### Key Algorithms

**Integration Algorithm (Power Rule)**
```
∫ ax^n dx = (a/(n+1))x^(n+1) + C

Example:
∫ 3x^2 dx = (3/3)x^3 + C = x^3 + C
```

**Definite Integral Evaluation**
```
∫[a,b] f(x)dx = F(b) - F(a)

Example:
∫[1,4] x^-2 dx = [-x^-1] from 1 to 4
                = (-1/4) - (-1/1)
                = -0.25 + 1
                = 0.75
```

**BST Insertion (Recursive)**
```java
if (tree is empty)
    create root
else if (new term < current term)
    insert in left subtree
else if (new term > current term)
    insert in right subtree
else (same exponent)
    combine coefficients
```

**In-Order Traversal for Descending Order**
- Right → Root → Left (reverse in-order)
- Produces terms from highest to lowest exponent
- O(n) time complexity

**Fraction Simplification**
```java
GCD algorithm:
gcd(a, 0) = a
gcd(a, b) = gcd(b, a mod b)

Simplify: coefficient/new_exponent by gcd(coefficient, new_exponent)
```

## Project Structure
```
polynomial-calculator/
├── README.md
├── Main.java           # Input parsing, output formatting, program flow
├── BinTree.java        # Generic binary search tree implementation
├── Node.java           # Generic node class
├── Term.java           # Polynomial term with integration logic
└── sample-input.txt    # Example expressions
```

## How to Run
```bash
javac *.java
java Main
```

### User Prompt
```
Enter input filename: sample-input.txt
```

## Input Format

**General Syntax:**
```
[lower_bound]|[upper_bound] <expression> dx
```

**Rules:**
- `|` symbol represents the integral sign
- Bounds (numbers before/after `|`) indicate definite integral
- No bounds = indefinite integral
- Variable is always `x`
- Expression must end with `dx`
- Space required before first term and before `dx`
- Spaces between terms/operators are optional

**Expression Details:**
- Coefficients: Integers (can be multiple digits, positive/negative)
- Exponents: Integers (positive or negative, NOT -1)
- `^` symbol for exponents
- Missing coefficient assumed to be 1
- Missing exponent assumed to be 1

**Sample Input File (sample-input.txt):**
```
| 3x^2 + 2x + 1 dx
1|4 x^-2 + 3x + 4 dx
-2|2 x^3 - 4x dx
| x^5 - 3x^3 + 2x dx
0|1 4x^3 + x dx
| 2x^2 + 3x^2 + x^2 dx
5|5 x^2 dx
| -x^4 + 6x^2 - 9 dx
```

## Output Format

**Indefinite Integral:**
```
<expression> + C
```

**Definite Integral:**
```
<expression>, <lower>|<upper> = <value>
```

**Expression Formatting:**
- Terms ordered highest to lowest exponent
- Space before and after `+` and `-` operators
- Exponents shown with `^` symbol
- Fractions in parentheses: `(numerator/denominator)`
- Terms with coefficient 0 are omitted
- If all terms are 0, display single `0`

**Sample Output:**
```
x^3 + x^2 + x + C
(3/2)x^2 + 4x - x^-1, 1|4 = 35.250
(1/4)x^4 - 2x^2, -2|2 = 0.000
(1/6)x^6 - (3/4)x^4 + x^2 + C
x^4 + (1/2)x^2, 0|1 = 1.500
2x^3 + C
0, 5|5 = 0.000
(-1/5)x^5 + 2x^3 - 9x + C
```

## Technical Skills Demonstrated

### Data Structures
- **Generic Binary Search Trees**: Type-safe with bounded type parameters
- **Comparable Interface**: Custom comparison logic for term ordering
- **Tree Properties**: Maintains BST invariant (left < root < right)

### Algorithms
- **Recursion**: All tree operations (insert, search, traverse)
- **Mathematical Computation**: Integration, evaluation, fraction arithmetic
- **String Parsing**: Tokenization, expression validation
- **GCD Algorithm**: Euclidean algorithm for fraction reduction

### Object-Oriented Design
- **Generics**: Reusable data structures independent of data type
- **Encapsulation**: Term handles its own integration logic
- **Separation of Concerns**: Parsing (Main) vs. Storage (BinTree) vs. Logic (Term)
- **Interface Implementation**: Comparable for natural ordering

### Complexity Analysis
| Operation | Time Complexity | Space Complexity |
|-----------|----------------|------------------|
| Insert (average) | O(log n) | O(1) |
| Insert (worst) | O(n) | O(1) |
| Search | O(log n) avg | O(1) |
| Traverse | O(n) | O(n) recursion stack |
| Integration | O(n) | O(n) output |

## Edge Cases Handled

✅ **Multiple terms with same exponent**
```
Input:  | 2x^2 + 3x^2 + x^2 dx
Output: 2x^3 + C
```

✅ **Negative exponents (excluding -1)**
```
Input:  | x^-2 dx
Output: -x^-1 + C
```

✅ **Definite integral where lower > upper**
```
Input:  4|1 x dx
Output: (1/2)x^2, 4|1 = -7.500
```

✅ **Zero result**
```
Input:  5|5 x^2 dx
Output: 0, 5|5 = 0.000
```

✅ **Unsorted input terms**
```
Input:  | 1 + 2x + 3x^2 dx
Output: x^3 + x^2 + x + C
```

✅ **Multi-digit coefficients**
```
Input:  | 125x^3 - 64x dx
Output: (125/4)x^4 - 32x^2 + C
```

✅ **Fraction simplification**
```
Input:  | 6x^2 dx
Output: 2x^3 + C     (not (6/3)x^3)
```

## Design Decisions

### Why Use BST for Polynomial Terms?
- ✅ Automatic sorting by exponent (using compareTo)
- ✅ O(log n) insertion for combining like terms
- ✅ In-order traversal produces sorted output
- ✅ Efficient duplicate detection (same exponent)

### Why Store Terms, Not Computed Results?
- ✅ Prevents stale data
- ✅ Integration computed on-demand
- ✅ Easier to combine like terms before integration
- ✅ Separation of data and computation

### Why Generic Implementation?
- ✅ Reusable BST for any Comparable type
- ✅ Demonstrates advanced Java features
- ✅ Type safety at compile time
- ✅ Industry best practice

## Testing Strategy

### Test Cases Validated
1. ✅ Simple indefinite integral (single term)
2. ✅ Simple definite integral (single term)
3. ✅ Multi-term expressions
4. ✅ Negative exponents
5. ✅ Combining like terms
6. ✅ Bounds where a > b
7. ✅ Bounds where a = b (result = 0)
8. ✅ Large coefficients (3+ digits)
9. ✅ Terms out of order in input
10. ✅ Fraction simplification scenarios
11. ✅ Missing coefficients/exponents (assumed 1)
12. ✅ All negative coefficients
13. ✅ Zero coefficient terms (omitted from output)
14. ✅ Complex nested operations
15. ✅ Multiple integrals in single file

## Known Limitations

- Does not support exponent of -1 (would require ln(x))
- No support for trigonometric functions (see extra credit)
- Integer coefficients only (no decimals)
- Single variable (x) only
- No support for constants without variable terms
- No partial fraction decomposition

## Extra Credit: Trigonometric Functions

*If implemented:*
```
Input:  | sin x + cos x dx
Output: -cos x + sin x + C

Input:  | 1 - cos 4x dx
Output: x - (1/4)sin 4x + C

Input:  | 3x^4 - 6x^2 + 2sin 10x dx
Output: (3/5)x^5 - 2x^3 - (1/5)cos 10x + C
```

Integration rules:
- ∫ sin(ax) dx = (-1/a)cos(ax) + C
- ∫ cos(ax) dx = (1/a)sin(ax) + C

## Possible Extensions

- Support for logarithmic integration (x^-1 → ln|x|)
- Multi-variable expressions
- Symbolic differentiation
- Taylor series expansion
- Numerical integration methods (Simpson's rule, trapezoidal)
- GUI with step-by-step solutions
- LaTeX output formatting

## Course Context

Developed as Project 3 for CS 2336 (Computer Science II) at UT Dallas. Learning objectives:
- Generic programming with bounded type parameters
- Recursive algorithm design
- Binary search tree implementation
- Mathematical computing
- Interface-based design

## Author

Huzaifa Nizami | https://www.linkedin.com/in/huzaifa-ahmad-nizami-4619b327a/ | huzaifaahmed230@gmail.com

---

*Part of my Data Structures & Algorithms portfolio. See my other projects:*
- [Baseball Statistics Evolution](../baseball-statistics-evolution)
- [Ant Colony Simulation](../ant-colony-simulation)
```

---

## What to Add to Repository:

**sample-input.txt:**
```
| 3x^2 + 2x + 1 dx
1|4 x^-2 + 3x + 4 dx
-2|2 x^3 - 4x dx
| x^5 - 3x^3 + 2x dx
0|1 4x^3 + x dx
| 2x^2 + 3x^2 + x^2 dx
5|5 x^2 dx
| -x^4 + 6x^2 - 9 dx
0|3 x dx
| x^10 - x^5 + x^2 - x dx

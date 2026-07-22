# POLYSOLVE

Polynomial Calculator & Calculus Suite** — a Java Swing desktop application built with a clean MVC (Model-View-Controller) architecture.

POLYSOLVE lets you type polynomials in natural form (e.g. `3x^2+2x+1`) and perform algebra (add, subtract, multiply) and calculus (evaluate, differentiate, integrate, definite integral) on them through a simple two-tab GUI.

---

## Features

- **Algebra**: add, subtract, multiply two polynomials
- **Calculus**:
  - Evaluate a polynomial at a given `x`
  - Differentiate (`d/dx`)
  - Indefinite integral (with a custom constant `C`)
  - Definite integral over `[a, b]`
- Accepts natural expressions (`3x^2+2x+1`) **or** comma-separated coefficients (`3,2,1`)
- Friendly inline error messages for invalid input — no crashes
- Clean, single-purpose classes following MVC separation

---

## Architecture (MVC)

```
com.polysolve
├── Main.java                     entry point
├── model/
│   ├── Polynomial.java           pure math: add, subtract, multiply,
│   │                             evaluate, differentiate, integrate
│   └── PolynomialParser.java     turns typed text into a Polynomial
├── view/
│   ├── MainFrame.java            top-level window, header + tabs
│   ├── AlgebraPanel.java         add / subtract / multiply UI
│   ├── CalculusPanel.java        evaluate / differentiate / integrate UI
│   └── Theme.java                shared colors and fonts
└── controller/
    └── PolySolveController.java  mediates between View and Model
```

- **Model** (`model/`) has zero Swing imports — it is pure, testable math logic.
- **View** (`view/`) has zero math logic — it only builds UI and reads/writes text fields.
- **Controller** (`controller/`) is the only class both sides depend on, keeping the two layers decoupled.

See `docs/UML_Class_Diagram.png` for the full class relationship diagram.

---

## Running the project

### Option 1: IntelliJ IDEA (recommended)
1. Open IntelliJ → **File → Open** → select the `POLYSOLVE` folder.
2. IntelliJ detects `pom.xml` automatically and imports it as a Maven project.
3. Run `Main.java` (right-click → **Run 'Main.main()'**).

### Option 2: Command line (with Maven)
```bash
mvn clean package
java -jar target/polysolve.jar
```

### Option 3: Command line (plain javac, no Maven)
```bash
cd src/main/java
javac com/polysolve/Main.java com/polysolve/model/*.java com/polysolve/view/*.java com/polysolve/controller/*.java -d ../../../out
cd ../../../out
java com.polysolve.Main
```

---

## Input format

| Style | Example | Meaning |
|---|---|---|
| Natural expression | `3x^2+2x+1` | 3x² + 2x + 1 |
| Natural expression | `x^3-x` | x³ − x |
| Comma-separated | `3,2,1` | 3x² + 2x + 1 (highest degree first) |

---

## Project info

- **Language**: Java 17, Swing (no external GUI libraries)
- **Build tool**: Maven
- **Author**: Siddharth Chandra and Aman Raj, BTech CSE (LPU), reg. no. 12411518 and 12416152.

---

## License

Built as an academic coursework project. Free to reuse and extend for learning purposes.

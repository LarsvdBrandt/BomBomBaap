# BomBomBaap Java Web Game

This project is a browser-based game served from a Java web server (Spark Java) on http://localhost:3004.

## Features
- Starts with a random letter from the alphabet
- Green button to start/continue
- Random category appears with a 5-second timer
- Press button in time to continue, else see a 404 error and red button

## How to Run
1. Install Java (JDK 8+)
2. Run `mvn package` to build
3. Run `java -jar target/bombombaap.jar` to start the server
4. Open http://localhost:3004 in your browser

## Customization
- Categories can be updated in the server code

---
For Copilot instructions, see `.github/copilot-instructions.md`.

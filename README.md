# BomBomBaap Java Web Game

This project is a browser-based game served from a Java web server (Spark Java) on http://localhost:3004.

## Features
- Starts with a random letter from the alphabet
- Green button to start/continue
- Random category appears with a 5-second timer
- Press button in time to continue, else see a 404 error and red button

## How to Run (Java 21)
1. Install Java 21 (LTS)
2. Build the shaded jar:
	- `mvn clean package`
3. Start the server:
	- `java -jar target/bombombaap.jar`
4. Open http://localhost:3004 in your browser

Tip (macOS):
```
export JAVA_HOME="$('/usr/libexec/java_home' -v 21)"
export PATH="$JAVA_HOME/bin:$PATH"
```

## Customization
- Categories can be updated in the server code

---
For Copilot instructions, see `.github/copilot-instructions.md`.

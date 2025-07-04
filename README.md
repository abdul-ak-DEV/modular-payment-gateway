# Modular Payment Gateway (Java Modules + ServiceLoader + jlink)

This is a **modular Java CLI app** that demonstrates:

- Java 21 **JPMS (Java Platform Module System)**
- **ServiceLoader** to load multiple payment providers (UPI, Card)
- Packaging with **modular JARs**
- Runtime image generation using **jlink**
- Optionally package a native installer with **jpackage**

---

##  Modules Overview

| Module Name         | Purpose |
|---------------------|---------|
| `payment.api`       | Declares the `PaymentProcessor` interface |
| `payment.upi`       | UPI implementation (`provides`) |
| `payment.card`      | Card implementation (`provides`) |
| `payment.launcher`  | Main app (`uses` via ServiceLoader) |

---

##  Manual Build Steps (javac → jlink)

###  Prepare output folders
```bash
rm -rf mods dist jlink-image
mkdir -p mods dist
```

###  1. Compile all modules
```bash
javac --module-source-path src -d mods $(find src -name "*.java")
```

###  2. Package each module into a JAR
```bash
jar --create --file dist/payment.api.jar     --module-version=1.0     -C mods/payment.api .

jar --create --file dist/payment.upi.jar     --module-version=1.0     -C mods/payment.upi .

jar --create --file dist/payment.card.jar     --module-version=1.0     -C mods/payment.card .

jar --create --file dist/payment.launcher.jar     --module-version=1.0     --main-class com.payment.launcher.PaymentLauncher     -C mods/payment.launcher .
```

###  3. Run the application manually
```bash
java --module-path dist -m payment.launcher
```

###  4. Create a custom runtime image using jlink
```bash
jlink   --module-path "$JAVA_HOME/jmods:dist"   --add-modules payment.launcher,payment.upi,payment.card   --output jlink-image   --strip-debug   --compress=2   --no-header-files   --no-man-pages
```

###  5. Run from jlink image
```bash
./jlink-image/bin/java -m payment.launcher
```

---

##  build.sh – Automates Everything

```bash
#!/bin/bash
set -e

echo "🧹 Cleaning..."
rm -rf mods dist jlink-image
mkdir -p mods dist

# 1. Compile All Modules
echo "📦 Compiling modules..."
javac --module-source-path src -d mods $(find src -name "*.java")

# 2. Package into Modular JARs
echo "📦 Creating modular JARs..."
jar --create --file dist/payment.api.jar     --module-version=1.0     -C mods/payment.api .

jar --create --file dist/payment.upi.jar     --module-version=1.0     -C mods/payment.upi .

jar --create --file dist/payment.card.jar     --module-version=1.0     -C mods/payment.card .

jar --create --file dist/payment.launcher.jar     --module-version=1.0     --main-class com.payment.launcher.PaymentLauncher     -C mods/payment.launcher .

# 3. Run test
echo " Running app from JARs..."
java --module-path dist -m payment.launcher

# 4. Create custom runtime image with jlink
echo " Creating custom runtime with jlink..."
jlink   --module-path "$JAVA_HOME/jmods:dist"   --add-modules payment.launcher,payment.upi,payment.card   --output jlink-image   --strip-debug   --compress=2   --no-header-files   --no-man-pages

# 5. Run from runtime image
echo " Running app from jlink image..."
./jlink-image/bin/java -m payment.launcher
```

---

##  UML / Architecture Diagram



---

##  Create Native Installer with jpackage

### 🔧 Requirements
- JDK 16+ with `jpackage`
- On macOS: Xcode CLI tools
- On Windows: WiX Toolset

###  Example: macOS DMG
```bash
jpackage   --type dmg   --input dist   --dest installer   --name PaymentApp   --main-jar payment.launcher.jar   --main-class com.payment.launcher.PaymentLauncher   --module payment.launcher/com.payment.launcher.PaymentLauncher   --runtime-image jlink-image   --icon logo.icns   --app-version 1.0.0
```

###  Example: Windows .exe
```bash
jpackage   --type exe   --input dist   --dest installer   --name PaymentApp   --main-jar payment.launcher.jar   --main-class com.payment.launcher.PaymentLauncher   --module payment.launcher/com.payment.launcher.PaymentLauncher   --runtime-image jlink-image   --icon logo.ico   --app-version 1.0.0
```

---

##  How to Run (Manual)

```bash
java --module-path dist -m payment.launcher
```

or from jlink image:

```bash
./jlink-image/bin/java -m payment.launcher
```

---

##  Credits
Created by abdul.sathar.a.k, Built with Java 21.

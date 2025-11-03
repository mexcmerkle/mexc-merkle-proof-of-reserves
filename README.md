# Merkle Proof Validation Tool

## 1. Environmental dependence

JDK1.8

maven 3.6.3 

## 2. Build your executable file

### Download the latest source code files and execute commands to generate an executable file.

`mvn clean package`

### Check your compiled jar package in the current project directory as follows:

`./target/mexc-proof-of-reserves.jar`

## 3. Verification steps

Paste the proof file into a new file in the target current directory, and name the file as myProof.json. Then, execute the following command:

`java -jar ./target/mexc-proof-of-reserves.jar myProof.json`

Observe the execution results:

Verification successful

`validate result is true`

Verification failed

`validate result is false`


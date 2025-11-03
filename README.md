# Merkle Proof Validation Tool

## 1、Environmental dependence

JDK1.8

maven 3.6.3 

## 2、Build the executable file

### Download the latest source code files,and execute commands to generate an executable file

`mvn clean package`

### check the compiled jar package, as follows, in the current project directory.

`./target/mexc-proof-of-reserves.jar`

## 3、Verification steps

You can paste the proof file into a new file in the target current directory, and name the file as myProof.json. Then, execute the following command:

`java -jar ./target/mexc-proof-of-reserves.jar myProof.json`

Observe the execution results:

Verification successful

`validate result is true`

Verification failed

`validate result is false`


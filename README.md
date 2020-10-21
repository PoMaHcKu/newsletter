# Newsletter
Newsletter is a simple example of a broadcast system written in java. It works in a local network.

## Installation
Use git and maven to install the broadcast system. Also, you need java run it.
```bash
git clone https://github.com/pomahcku/newsletter
cd newsletter
mvn clean package
```
The jar archive will be available under the path ```newsletter/target/Newsletter.jar```
## Usage
For launch the newsletter you need run it with two parameters.
First, your role, writer or listener.
The second is the port from which the program will be streamed if you are a writer. 
Or from which the program will listen.
```bash
java -jar target/Newsletter.jar writer 5000
```
or
```bash
java -jar target/Nesletter.jar listener 5000
```
Also, you can set broadcast address manual. Otherwise, messages will be sent to the address 255.255.255.255
```bash
java -jar target/Newsletter.jar writer 5000 192.168.100.255
```
You will see the result, when system runs in different roles with the same ports on different machines on the same local network.
@echo off
java -classpath ../target/mouse-control-1.0.0.jar org.example.MouseShake

# or below command, which means interval is 3 seconds, default value is 180000 (3 minutes).
# java -classpath ../target/mouse-control-1.0.0.jar org.example.MouseShake 3000
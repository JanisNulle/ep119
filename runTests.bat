cd %~dp0
java -cp ".;lib/*;bin/" org.testng.TestNG testng.xml
exit /b 0

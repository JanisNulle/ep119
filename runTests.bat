cd %~dp0
java -cp ".;lib/*;bin/" org.testng.TestNG testng.xml

git checkout master
git add .
git commit -m "Automated test run"
git remote add origin https://github.com/JanisNulle/ep119.git
git push -u origin master
exit /b 0

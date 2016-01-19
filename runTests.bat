cd %~dp0
git checkout master
java -cp ".;lib/*;bin/" org.testng.TestNG testng.xml
git add .
git commit -m "Automated test run"
git remote set-url origin git@github.com:JanisNulle/ep119.git
git push -u origin master
exit /b 0

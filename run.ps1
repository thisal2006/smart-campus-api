Write-Host "Compiling..." -ForegroundColor Yellow
mvn compile

Write-Host "`nStarting Server..." -ForegroundColor Green
Write-Host "Server will run at: http://localhost:8080/api/v1" -ForegroundColor Cyan
Write-Host "Press Ctrl+C to stop`n" -ForegroundColor Yellow

mvn exec:java -Dexec.mainClass=com.smartcampus.api.Main

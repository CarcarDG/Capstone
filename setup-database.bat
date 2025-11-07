@echo off
echo Setting up Recipe Platform Database...
echo.

echo Creating database schema...
mysql -u root -p < database\schema.sql

echo.
echo Loading sample data...
mysql -u root -p < database\sample_data.sql

echo.
echo Database setup complete!
pause

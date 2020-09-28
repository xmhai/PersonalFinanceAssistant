mkdir "dist\nginx"
copy nginx\*.* dist\nginx

mkdir "dist\account-service"
mkdir "dist\account-service\target"
copy account-service\Dockerfile dist\account-service
copy account-service\target\*.jar dist\account-service\target

mkdir "dist\common-service"
mkdir "dist\common-service\target"
copy common-service\Dockerfile dist\common-service
copy common-service\target\*.jar dist\common-service\target

mkdir "dist\job-service"
mkdir "dist\job-service\target"
copy job-service\Dockerfile dist\job-service
copy job-service\target\*.jar dist\job-service\target

mkdir "dist\stock-service"
mkdir "dist\stock-service\target"
copy stock-service\Dockerfile dist\stock-service
copy stock-service\target\*.jar dist\stock-service\target

mkdir "dist\frontend"
mkdir "dist\frontend\build"
xcopy /s /Y frontend\build\*.* dist\frontend\build
copy frontend\Dockerfile-prod dist\frontend

copy docker-compose-prod.yml dist
copy start-pfa.bat dist


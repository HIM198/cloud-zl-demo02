set RETURN_URL=D:\workspace\cloud-zl

call cd ./cloud-entity
call mvn install
call cd %RETURN_URL%


call cd ./cloud-sys-entity
call mvn install -DskipTests
call cd %RETURN_URL%


call cd ./cloud-utils
call mvn install -DskipTests
call cd %RETURN_URL%


call cd ./cloud-dao
call mvn install -DskipTests
call cd %RETURN_URL%

call cd ./cloud-sys-dao
call mvn clean install -DskipTests
call cd %RETURN_URL%


call cd ./cloud-sys-service
call mvn clean install -DskipTests
call cd %RETURN_URL%


call cd ./cloud-service
call mvn clean install -DskipTests
call cd %RETURN_URL%


call cd ./cloud-sys-controller
call mvn clean install -DskipTests
call cd %RETURN_URL%

call cd ./cloud-web
call mvn clean install -DskipTests
call cd %RETURN_URL%

call cd ./cloud-view
call mvn clean install -DskipTests
call mvn clean package -DskipTests

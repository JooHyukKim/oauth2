docker rmi --force $(docker images -q 'pilot/auth-server' | uniq)
./gradlew build -x test &&
  docker build -t pilot/auth-server:latest .

# Spring-Server

### 실행 방법
1. clean build
2. docker buildx build --platform linux/amd64 -t maruhan/wonnit:latest .
3. docker push maruhan/wonnit:latest
4. ec2 접속 후 docker-compose up -d
   - 단 compose.yaml 및 .env 파일 필요
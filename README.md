# Azure AI Inference

종속성 설치
설치하려면 maven `pom.xml`에서 이 `<dependency>`을(를) 추가합니다.
```
<dependency>
<groupId>com.azure</groupId>
<artifactId>azure-ai-inference</artifactId>
<version>1.0.0-beta.1</version>
</dependency>
```
다음 예시와 같이 아래의 각 코드 조각에 대해 콘텐츠를 `sample.java` 파일에 복사하고 패키지로 실행합니다.
```
mvn clean package
mvn exec:java -Dexec.mainClass=com.github.models.inference.samples.BasicChatSample -DGITHUB_TOKEN=[YOUR_GITHUB_TOKEN]
```


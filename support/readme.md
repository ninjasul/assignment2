## 과제 설명
* 개발 프레임워크: 스프링 부트 JPA 2.1.7, maven 사용. DB는 H2 메모리 DB사용.
* 빌드/애플리케이션 구동 방법 
    - support 디렉토리 내의 터미널 상에서 "mvn clean package" (테스트 수행) 혹은 
 "mvn clean package -DskipTests"(테스트 생략) 명령어를 수행
    - 빌드가 성공 하면 "support/target/" 경로에 jar파일이 생성됨
    - "java -jar 'jar파일명'" 명령어로 애플리케이션을 실행함
        - 예를 들어 jar 파일명이 support-0.0.1-SNAPSHOT.jar 인 경우 
        "java -jar support-0.0.1-SNAPSHOT.jar" 수행

* 애플리케이션 실행 방법
    - DB에 csv 데이터 삽입 
      - 메소드: GET
      - http://localhost:8080/api/support/insert
      
    - 모든 지자체 지원 리스트 조회
      - 메소드: GET 
      - URL: http://localhost:8080/api/support
      
    - 지역명으로 지자체 지원 정보 조회: 
      - 메소드: POST       
      - URL: http://localhost:8080/api/support
      - 요청 본문 예) 
        - {"region":"강릉시"}
      
    - 지자체 지원 정보 수정: 
      - 메소드: PUT 
      - URL: http://localhost:8080/api/support
      - 요청 헤더
        - "application/json;charset=UTF-8"
      - 요청 본문 예) 
        - {"region":"강릉시", "target: "지원대상", "usage:" : "용도", "limits" : "10억원 이내", "rate" : "1.5%~2.5%", "institute" : "추천기관", "mgmt" : "관리점", "reception" : "취금점" }
    
    - 지원한도 컬럼에서 지원금액으로 내림차순 정렬(지원금액이 동일하면 이차보전 평균 비율이 적은 순서)하여 특정 개수만 출력하는 API
        - 메소드: GET 
        - URL: http://localhost:8080/api/region/best/{조회개수}
        
    - 이차보전 컬럼에서 보전 비율이 가장 작은 추천 기관명을 출력하는 API
        - 메소드: GET           
        - URL: http://localhost:8080/api/region/smallest/

* DB 접속 방법
    - 접속 주소: http://localhost:8080/h2-console/
    - Driver Class: org.h2.Driver
    - JDBC URL: jdbc:h2:mem:support;
    - User Name: sa
    - Password: 입력하지 않음.        
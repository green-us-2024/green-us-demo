<처리해야할 부분>
---
1. common 밑 retrofitManager에 있는 baseURL의 주소를 본인pc의 주소로 바꾼다. <br>
 - cmd켜서 ipconfig 입력후에 IPv4에 있는 주소를 :8080 앞에 입력<br>
2. AddressDialogFragment 안의 26번째 줄의 주소도 마찬가지로 변경 필요.
3. 실행 시에는 spring의 서버를 먼저 실행시키고, 안드로이드 실행
4. db를 만들고 나서 user테이블의 admin은 null허용을 해줘야함. 아직은 admin 구현이 안됐기 때문.



<변경한 부분>
---
1. common 패키지 아래에 있는 api,dto,manager
2. AddressDialogFragment.kt 추가
3. JoinCompltFragment에서 데이터 넘겨받고, retrofit 통해서 dv로 넘기는 부분
4. JoinAddressFragment도 확인 필요
5. Join1Fragment도 확인 필요
6. layout에 fragment_address_search.xml 만들었습니다.
7. build.gradle에 
 implementation 'com.squareup.retrofit2:retrofit:2.9.0'<br>
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0' <-이거 두줄 추가해주세요<br>
제가 최대한 안건드리고 건들부분만 건드린 것 같긴한데 정확히 어딘지 기억이 잘 안나서 Join 관련된 부분들 한번씩 더블체크해주세요<br>
여기서 언급한 부분을 유진님, 세진님이 지금까지 하신거에 코드만 긁어서 추가해주시고, 완성되면 front에다가 push 해주시면 제가 pull 받겠습니다. <br>
<br><br>
스프링서버의 경우
user 관련된 controller, User 바꿨고, webconfig 추가되었습니다. + resources아래에 templates에 address.html 추가했습니다.

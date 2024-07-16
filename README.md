<처리해야할 부분>
---
1. common 밑 retrofitManager에 있는 baseURL의 주소를 본인pc의 주소로 바꾼다. <br>
 - cmd켜서 ipconfig 입력후에 IPv4에 있는 주소를 :8080 앞에 입력<br>
2. AddressDialogFragment 안의 26번째 줄의 주소도 마찬가지로 변경 필요.
3. 실행 시에는 spring의 서버를 먼저 실행시키고, 안드로이드 실행




<변경한 부분>
---
1. common 패키지 아래에 있는 api,dto,manager
2. AddressDialogFragment.kt 추가
3. JoinCompltFragment에서 데이터 넘겨받고, retrofit 통해서 dv로 넘기는 부분
4. JoinAddressFragment도 확인 필요
5. Join1Fragment도 확인 필요
6. layout에 fragment_address_search.xml 만들었습니다.

제가 최대한 안건드리고 건들부분만 건드린 것 같긴한데 정확히 어딘지 기억이 잘 안나서 Join 관련된 부분들 한번씩 더블체크해주세요<br>


스프링서버의 경우
user 관련된 controller, User 바꿨고, webconfig 추가되었습니다. + resources아래에 templates에 address.html 추가했습니다.

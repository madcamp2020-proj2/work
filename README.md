# CS496 Week 2 Report

## Abstraction

프로젝트의 결과물은 총 3개의 탭으로 구성된 앱이며 각 탭은 다음과 같은 기능을 가진다.

* Tab1: 전화번호부 연동앱으로 스마트폰에 있는 전화번호부 서버와 연동하고 전화번호부의 별명(nickname)을 변경할 수 있다.
* Tab2: 갤러리앱에 있는 사진을 서버에 업로드할 수 있으며, 삭제, 로드가 가능한 앱을 구현하였다.
* Tab3: 줌(ZOOM)과 비슷한 화상 채팅앱을 만들었다. 지정된 서버 주소의 웹페이지로 가게되면 카메라 사용 승인후 접속한 상대와 대화가 가능하다.



## Demo

#### Tab1: Contacts

첫번째 탭은 스마트본에 있는 연락처 정보를 가져와서 보여주는 기능으로 리사이클러뷰를 이용하여 리스트 형식으로 전화번호 데이터를 순서대로 나열하였다.  기존 방식은 1주차와 다르지 않았다.


#### Tab2: Gallery

갤러리의 이미지를 선택(중복가능)하여 서버에 업로드를 할 수 있는 앱을 만들었다. 서버에 요청을 하면 서버에 존재하는 파일을 가져와 리사이클러뷰를 통해 로드를 할 수 있다. 이후 삭제와 저장 버튼이 존재하며 삭제는 서버에 존재하는 사진파일을 삭제하며, 저장시 `/Downloads`폴더에 저장된다.

#### Tab3: Mimi ZOOM

세번째 탭은 웹서핑을 하거나 화상채팅을 구현해보았다. 서버에 접속을 하면 자동으로 룸 아이디(ROOM ID)를 받게되며 같은 방에 접속한 사람들과 스트림을 공유하여 서로의 카메라로 화상 채팅을 할 수 있는 앱을 만들었다. 단, 앱 화면에서 새로운 인텐트를 요청하여 브라우저를 띄워야 한다는 점, 크롬(Chrome) 브라우저에서만 가능하다는 한계점이 존재하며 이는 Discussion부분에서 다루도록 하겠다.

## Functions

* [MongoDB]()

### Tab 1

* [Mongoose]()

### Tab 2

* [Retrofit2](https://square.github.io/retrofit/)

  ![photo_demo](https://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F223DEF3A58956DCA2F)

### Tab 3

* [PeerJS](https://peerjs.com/)
* [Socket.io](https://socket.io/)
* [WebRTC](https://webrtc.org/)

  

## Discussion

#### 비동기 문제

#### HTTPS



## Reference

* [오준석의 안드로이드 생존코딩](http://www.yes24.com/Product/Goods/64494679)
* [Do it! 앱 안드로이드 프로그래밍](http://www.yes24.com/Product/Goods/89393757)

* [Android 개발자](https://developer.android.com/?hl=ko)
* [CardView with RecyclerView](https://medium.com/inside-ppl-b7/recyclerview-inside-fragment-with-android-studio-680cbed59d84)
* [Linkeep 구글 플레이스토어 앱](https://play.google.com/store/apps/details?id=com.francescopennella.linkeep&hl=ko)
* [화면전화 오류 수정](http://jinyongjeong.github.io/2018/09/30/configchange_option/)
* [임시파일 삭제 오류](https://stackoverflow.com/questions/7131930/get-the-file-size-in-android-sdk/23559921)

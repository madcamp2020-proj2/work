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

* [MongoDB](https://www.mongodb.com/)

### Tab 1

* [Mongoose](https://mongoosejs.com/)

### Tab 2

* [Retrofit2](https://square.github.io/retrofit/)

  ![photo_demo](https://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F223DEF3A58956DCA2F)

  사진파일을 보내고 서버에 저장된 사진 관련 정보를 로드하기위해 사용한 안드로이드 라이브러리이다. 이번 프로젝트에서 사용한 레트로핏의 특징은 다음과 같이 정리할 수 있다.

  1. API interface의 설정

  기본적으로 레트로핏의 경우는 인터페이스에서 함수를 설정하면 태그(`@POST`, `@GET`, `@PUT`)에 따라서 서버에 대한 요청을 설정할 수 있으며, 인터페이스 API의 파라미터를 경로 혹은 받으려는 데이터 타입등을 사용자의 맞게 구성을 할 수 있다.

  ```java
  @GET("/load")
  Call<List<Gets>> getImageUrl();
  ```

  위의 코드를 살펴보면 서버에 `[server]/load`로 `@GET`을 보내며 받기위한 데이터 타입은 `List<Gets>`임을 확인 할 수 있다. 이는 서버에서 JSON array를 보내며 클라이언트에서 이를 사용자가 설정한 클래스의 리스트로 설정하여 받을 수 있다.

  2. 비동기적인 처리

  ```java
  Call<ResponseBody> req = apiService.postImage(body, name);
  req.enqueue(new Callback<ResponseBody>() {
  	@Override
  	public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
  		...
  	}
  	@Override
  	public void onFailure(Call<ResponseBody> call, Throwable t) {
  		...
  	}
  });
  ```

  위의 코드는 API를 설정하고 서버에 `POST` 를 요청하는 코드이다. 성공할 경우 `onResponse`를 실패할 경우 `onFailure`를 실행하며 그에 맞는 처리를 해줄 수 있다. 하지만 위의 코드는 비동기적으로 실행되기 때문에 코드를 실행하는 주체는 main Thread가 아니다. 즉, main Thread가 UI를 담당하는 안드로이드의 특성상 위의 코드 안에 `Glide` 혹은 레이아웃을 바꾸는 코드를 적용할 경우 에러가 발생하게된다. 이를 주의하여 사용해야한다. 동기적으로 레트로핏을 사용하는 방법은 아래의 레퍼런스를 참고하자.

  

### Tab 3

* [Socket.io](https://socket.io/)

  소켓 통신의 경우는 실시간으로 서버와 클라이언트가 정보를 주고받으면서 통신을 하기 때문에 채팅이나 영상과 같은 정보르 실시간으로 전달하기에 적합한 방식이다. 따라서 Mimi ZOOM에서는 이를 위해 소켓 통신을 사용하였다.

* [WebRTC](https://webrtc.org/)

  ![photo_demo](https://www.gstatic.com/devrel-devsite/prod/v45f61267e22826169cf5d5f452882f7812c8cfb5f8b103a48c0d88727908b295/webrtc/images/lockup.svg)

  영상  스트림 데이터를 보낼 방식으로 소켓 통신을 선택하였다면 어떻게 데이터를 가져올지를 생각할 차례이다. 이는 `WebRTC`르 이용할 수 있다. `WebRTC`를 요약하자면 웹 브라우저 상에서 특별한 플러그인 없이 영상, 음성 데이터를 교환 할 수 있도록 하는 기술을 말한다. 즉 안드로이드 애플리케이션을 사용하더라도 이를 이용하면 웹 브라우저 상에서 특별한 조작 없이 영상 데이터 송출이 가능하다.

  ```javascript
  navigator.mediaDevices.getUserMedia({
      video: true,
      audio: true
  }).then(stream => {
      ...
  }).catch(err => console.log(err))
  ```

  위의 Node.JS의 프로미스 구문을 살펴보면 유저의 미디어 데이터를 가져와서 스트림 데이터를 바로 전송이 가능하다. 이러한 방식을 이용하여 각 웹 브라우저에 들어가게 되면 영상, 음성 데이터를 송출하는 것이 가능하다.

* [PeerJS](https://peerjs.com/)

  `PeerJS`는 `WebRTC`를 보다 효과적으로 사용하기 위해 만들어진 패키지로 미디어 데이터에 대해서 call과 answer을 통해서 peer-peer간의 데이터 전송을 보다 간변하게 할 수 만들어 준다.  소켓 통신을 같이 활용하면 같은 서버에 상대방이 들어왔을 때 이에 대한 아이디를 만들어주고 이를 인식하고 스트림 데이터를 서버에게 전달 다른 사용자에게 전달 할 수 있다.

## Discussion

#### 비동기 문제

#### HTTPS

세번째 탭에서 사용하는 미디어 데이터의 경우 일반적으로 https 프로토콜에서는 작동하지 않았다.  또한 http에서도 [크롬 http 플래그 변경](https://medium.com/@Carmichaelize/enabling-the-microphone-camera-in-chrome-for-local-unsecure-origins-9c90c3149339) 사이트에서 서버 주소와 포트에 대한 보안을 해결해야 접속하여 영상 송출이 가능하였다. 따라서 앱 내에서 웹뷰를 이요하여 화면을 띄우기 위해서는 https를 승인 받는 방법을 시도하엿다. apache2와 nginx를 이용하여 ssl키를 등록하여 도메인 주소에 대한 https를 승인 받는데 성공하였지만 어떤 이유에서인지 소켓 통신이 제대로 작동하지 않았다. 이에 따라서 웹뷰르 클릭하였을 때, 크롭 브라우저 창으로 인텐트를 요청하는 방식으로 변경을 하였다.



## Reference

* [Retrofit2 사용 설명서](https://github.com/HwangEunmi/Retrofit-Sample#@Body)
* [Zoom Clone Projext](https://github.com/CleverProgrammers/nodejs-zoom-clone)
* [안드로이드 클라이언트 서버 통신]([https://velog.io/@dlrmwl15/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%84%9[…\]D%81%B4%EB%9D%BC%EC%9D%B4%EC%96%B8%ED%8A%B8-%ED%86%B5%EC%8B%A0-2](https://velog.io/@dlrmwl15/안드로이드-서버클라이언트-통신-2))
* [MongoDB 사용 설명서](https://zellwk.com/blog/crud-express-mongodb/)

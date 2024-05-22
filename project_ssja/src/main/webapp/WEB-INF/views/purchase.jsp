<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>T 9</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
    integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous" />
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous">
    </script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>


  <script src="/js/barscript.js">
  </script>
  <script src="/js/footer.js">
  </script>
  
   <link href="/css/footerstyle.css?after" rel="stylesheet">
  <link href="/css/barstyle.css?after" rel="stylesheet">
  <link rel="stylesheet" href="https://webfontworld.github.io/NanumSquare/NanumSquare.css">
  <script src="https://cdn.iamport.kr/v1/iamport.js"></script>
 

  <style>
    input:focus {
      outline: none;
      border-color: #ffffff;
    }

    @font-face {
      font-family: 'fonts';
      src: url("https://webfontworld.github.io/NanumSquare/NanumSquare.css") fotmat('font1');
    }

    body {
      font-family: 'fonts', NanumSquare;
      background-color: #f7f0e8;
    }

    #logo_img {
      width: 3.5em;
      height: 3em;
    }
    /* number타입의 input 화살표제거 */
    input::-webkit-outer-spin-button,
    input::-webkit-inner-spin-button {
      -webkit-appearance: none;
      margin: 0;
    }
    /* 결제용 플로트 바 */
    #payment_title{
      border-bottom: 1px solid #DEE2E6;
      
    }
    #payment_bar{
      margin: 16px 0px 16px 16px;
      width: 68%;
      clear: both;
    }
    #full_amount,#result_price_wrap{
      text-align: right;
    }
    #discount{
      float: right;
    }
    #won{
      display: inline;
    }


    @media screen and (min-width: 861px) {
      #payment_title{
        display: none;
      }
      #payment_bar{
        position: fixed; 
        left: 70%;
        top: 220px; 
        text-align:center;
        width: 240px;
        height: 300px;
        padding-right: 50px;
      }
      #full_amount,#discount{
        padding-right: 50px;
      }
      #result_price_wrap{
        padding-right: 25px;
      }


    }

  </style>
  <script>
   
  //pay함수 호출로 결제 실행
    $(document).ready(function(){
      let point=$("#point");
      let use_point=0;
      let full_use_btn=$("#full_use_btn");
      let post_search_btn=$("#post_search_btn");
      let coupon=$("#coupon");
      let coupon_discount=0;
      let full_amount=$("#full_amount");
      let full_amount_val=10000;
      let discount=$("#discount");
      let discount_val=0;
      let result_price=$("#result_price");
      let result_price_val=full_amount_val-discount_val;
      let buy_btn=$("#buy_btn");
      let address=$("#address");

      var IMP = window.IMP
      IMP.init('imp22716806')
      function pay(f_amount){
        //파라미터로 수정 필요
        IMP.request_pay({
            pg : 'html5_inicis',
            pay_method : 'card', //카드결제
            merchant_uid : 'merchant_' + new Date().getTime(),
            name : '결제상품',
            amount : f_amount, //판매가격
            buyer_email : 'id', 
            buyer_name : 'my',
            buyer_tel : '01012345678 ',
            buyer_addr : address.val(),
            buyer_postcode : '11232'
          }, function(rsp) {
            if ( rsp.success ) {
                var msg = '결제가 완료되었습니다.';
                msg += '고유ID : ' + rsp.imp_uid;
                msg += '상점 거래ID : ' + rsp.merchant_uid;
                msg += '결제 금액 : ' + rsp.paid_amount;
                msg += '카드 승인번호 : ' + rsp.apply_num;
                window.alert(msg);
                console.log(msg);
                
            } else {
                var msg = '결제에 실패하였습니다.';
                msg += '에러내용 : ' + rsp.error_msg;
                window.alert(msg);
            }
        })
      }

      post_search_btn.on("click",function(){
          new daum.Postcode({
              oncomplete: function(data) {
                  // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                  // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                  // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                  var addr = ''; // 주소 변수
                  var extraAddr = ''; // 참고항목 변수

                  //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                  if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                      addr = data.roadAddress;
                  } else { // 사용자가 지번 주소를 선택했을 경우(J)
                      addr = data.jibunAddress;
                  }

                  // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                  if(data.userSelectedType === 'R'){
                      // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                      // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                      if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                          extraAddr += data.bname;
                      }
                      // 건물명이 있고, 공동주택일 경우 추가한다.
                      if(data.buildingName !== '' && data.apartment === 'Y'){
                          extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                      }
                      // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                      if(extraAddr !== ''){
                          extraAddr = ' (' + extraAddr + ')';
                      }
                      // 조합된 참고항목을 해당 필드에 넣는다.
                      document.getElementById("extra_address").value = extraAddr;
                  
                  } else {
                      document.getElementById("extra_address").value = '';
                  }

                  // 우편번호와 주소 정보를 해당 필드에 넣는다.
                  document.getElementById('post').value = data.zonecode;
                  document.getElementById("address").value = addr;
                  // 커서를 상세주소 필드로 이동한다.
                  document.getElementById("detail_address").focus();
              }
          }).open();
      })
      //결제 관련 가격들을 세팅하는 메서드
      full_amount.text(full_amount_val);
      discount.text(discount_val);
      result_price.text(result_price_val);
      //포인트 입력제한을 소지 포인트로 거는 메서드
      point.attr("max",10001);

      //쿠폰사용시 이벤트
      coupon.on("change",function(){
        //쿠폰변경시 포인트 사용 초기화
        use_point=0;
        point.val(use_point);
        //할인액 계산
        coupon_discount=$('#coupon option:selected').attr("discount")
        discount_val=full_amount_val/100*coupon_discount;
        discount.text(discount_val);
        result_price_val=full_amount_val-discount_val;
        result_price.text(result_price_val);
      })

      //포인트 최대값 제한 이벤트, 디스카운트 변경이벤트
      point.on("change", function(){
        //포인트의 기존값이 0이 아닐경우의 처리
        if(use_point!="0"){
          use_point=0;
          discount_val=full_amount_val/100*coupon_discount;
          result_price_val=full_amount_val-discount_val;
        }
        //포인트 최대값 제한
        if(Number(point.val())>point.attr("max")){ 
          if(result_price_val>point.attr("max")){ 
            point.val(point.attr("max"));
          }else{
            point.val(result_price_val);
          }        
        }else if(Number(point.val())>result_price_val){
          point.val(result_price_val);
        }
        use_point=point.val();
        //할인액 수정
        discount_val=full_amount_val/100*coupon_discount+Number(use_point);
        discount.text(discount_val);
        //결제액 수정
        result_price_val=full_amount_val-discount_val;
        result_price.text(result_price_val);
      })
      //클릭시 소지포인트가 가격을 초과하지 않는다면 소지포인트의 전액, 초과한다면 가격으로 포인트를 설정해주는 이벤트
      full_use_btn.on("click",function(){
        //포인트의 기존값이 0이 아닐경우의 처리
        if(use_point!=0){
          point.val(0);
          discount_val=full_amount_val/100*coupon_discount;
          result_price_val=full_amount_val-discount_val;
        }
        //포인트 전액사용시 값에 따른 처리
        if(result_price_val>point.attr("max")){ 
          point.val(point.attr("max"));
        }else{
          point.val(result_price_val);
        }
        use_point=point.val();
        //할인액 수정
        discount_val=full_amount_val/100*coupon_discount+Number(use_point);
        discount.text(discount_val);
        //결제액 수정
        result_price_val=full_amount_val-discount_val;
        result_price.text(result_price_val);
      })
      buy_btn.on("click",function(){
        if(result_price_val>=100){
          pay(result_price_val);
        }else if(result_price_val==0){
          window.alert("결제가 완료되었습니다.")
          //리다이렉트 추가
        }else{
          window.alert("최소 결제단위는 100원입니다.")
        }
      })
    })
  </script>
</head>

<body>
  <header>
    <div id="title_bar" class=" fixed-top">
      <div class="py-2 px-1" id="top-bar">

        <button type="toggle-button" class="top_btn" id="top_btn"></button>
        <a href="${pageContext.request.contextPath}/"><img id="logo_img" src="/images/utilities/logoSSJA.png"></a>
        <form action="http://www.naver.com" id=searchForm method="get">
        
        </form>
        <button id="search_icon"></button>
        <a id="cart_link"><img id="cart_img"></a>
        <a id="user_link"><img id="login_img"></a>
      </div>

    </div>
    <nav id="total_bar">
      <div id="home_user_bar"> </div>
      <div id="sub_bar"></div>
    </nav>
  </header>

  <div id="side_bar"></div>
  <main style="background-color: #f7f0e8;">
    <!--결제 페이지부 -->
    
    <div id="main_container" class="d-flex flex-column " style="min-height:800px;">
      
      <h2 class="mt-3 fw-bold">주문/결제</h2>
      <div class="ms-3" style="margin-right: 30%;">
        <h3 class="mt-5 border-bottom ">배송지</h3>
        <table class="ms-3">
          <tr>
            <td>
              <span>주소</span>
            </td>
            <td>
              <input type="text" size="35" class="mb-1 form-control" id="address" name="M_ADDRESS1">
            </td>
          </tr>
          <tr>
            <td>
              <span>상세주소ㅤ</span>
            </td>
            <td>
              <input type="text" size="23" class=" mb-1 form-control w-50 d-inline" id="detail_address" name="M_ADDRESS2">
              <input type="text" size="6" class="mb-1 d-none" id="extra_address" name="extra_address" >
              <input type="text" size="10" class="mb-1 d-none" id="post" name="M_ZIPCODE">
              <input type="button" value="주소 찾기" class="mb-1 ms-3 btn btn-primary" id="post_search_btn">
            </td>
          </tr>
        </table>
      </div>
      <div class="ms-3" style="margin-right: 30%;">
        <h3 class="my-3 border-bottom">주문상품</h3>
        <!-- 아래부터 c:foreach로 반복 -->
        <div>
          <img src="/images/utilities/logoSSJA.png" alt="" style="width: 150px;height: 150px; float: left;">
          <div class="m-2 pt-3 fs-4"><span id="product_name">상품명:</span></div>
          <div class="m-2 fs-4"><span>옵션 :</span><span id="product_opt">네이비블루</span></div>
          <div class="m-2 fs-4"><span>금액:</span><span id="product_price">1000원</span> <span>수량:</span> <span id="product_pcs">1개</span></div>
        </div>
      </div>
      <div class="ms-3" style="margin-right: 30%;">
        <h3 class="my-3 border-bottom">쿠폰</h3>
        <select class="ms-3 form-control w-50" name="coupon" id="coupon">
          <option value="none" discount="0">선택안함</option>
          <!-- c:foreach로 쿠폰추가 -->
          <option value="coupon1" discount="10">쿠폰1</option>
          <option value="coupon2" discount="20">쿠폰2</option>
          <option value="coupon3" discount="30">쿠폰3</option>
        </select>
      </div>
      <div class="ms-3" style="margin-right: 30%;">
        <h3 class="my-3 border-bottom">포인트</h3>
        <input type="number" class="ms-3 me-0 form-control w-25 d-inline" id="point" value="0" min="0" max="200">
        <button class="ms-0 btn btn-primary" id="full_use_btn">전액사용</button>
      </div>
      <div id="payment_bar">

        <h3 id="payment_title">결제금액</h3>
        <h3 id="full_amount">
          0
        </h3>
        <h3>
          <span>-</span>
          <span id="discount">0</span>
          <hr>
        </h3>
        <h3 id="result_price_wrap"><SPan id="result_price">0</SPan>원</h3>
        <button type="button" class="btn btn-primary ms-3" id="buy_btn">결제하기</button>

      </div>
    </div>
  </main>

  <footer>
    <div id="first_footer" class="p-3"></div>
    <div id="second_footer"></div>
    <div id="third_footer"></div>
  </footer>

</body>

</html>
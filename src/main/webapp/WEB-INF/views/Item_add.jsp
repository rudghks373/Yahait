<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script src="//code.jquery.com/jquery-2.1.3.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<title>Sell</title>
</head>
<body>
	<div class="container">
		<form class="form-horizontal" name="multiform" id="multiform" action="Item_addAct" method="POST" enctype="multipart/form-data">
			<h2>SELL</h2>

			<!-- <div class="form-group">
				<label for="id" class="col-sm-3 control-label">카테고리*</label>
				<div class="col-sm-9">
					<input type="text" name="id" id="category" placeholder="아이디"
						class="form-control" autofocus>
				</div>
			</div> -->

			<div class="form-group">
				<label for="password" class="col-sm-3 control-label">상품 이름*</label>
				<div class="col-sm-9">
					<input type="text" name="item_name" id="item_name"
						placeholder="상품 이름" class="form-control">
				</div>
			</div>
			<hr>
			<div class="form-group">
				<label for="id" class="col-sm-3 control-label">상품 가격*</label>
				<div class="col-sm-9">
					<input type="text" name="item_price" id="item_price"
						class="form-control" placeholder="상품 가격" required="required">
				</div>
			</div>
			<div class="form-group">
				<label for="id" class="col-sm-3 control-label">상품 설명*</label>
				<div class="col-sm-9">
					<input type="text" name="item_contents" id="item_contents"
						class="form-control" placeholder="상품 설명" required="required">
				</div>
			</div>
			<div class="form-group">
				<label for="id" class="col-sm-3 control-label">상품 이미지*</label>
				<div class="col-sm-9">
					<input type="file" name="file" id="item_pic"
						class="form-control" placeholder="상품 이미지" required="required">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-9 col-sm-offset-3">
					<span class="help-block">*필수 기입</span>
				</div>
			</div>
			<input type="hidden" name="shop_num" value="<%=request.getParameter("shop_num") %>">
			<input type="submit" class="btn btn-primary btn-block" value="개설">
		</form>
<!-- 		<button class="btn btn-primary btn-block" onclick="sellact()">개설</button>
 -->
	</div>
</body>

<script>
 $(function(){
	    
		//폼전송 : 해당폼의 submit 이벤트가 발생했을경우 실행  
	    $('#multiform').ajaxForm({
	       cache: false,
	       dataType:'text',
	       //보내기전 validation check가 필요할경우
	       beforeSubmit: function (data, frm, opt) {
		       //console.log(data);
	           alert("전송전!!");
	           return true;
	       },
	       //submit이후의 처리
	       success: function(data){
	    	   if(data=="OK"){
	    	   alert("전송성공!!");
	    	   window.location.href = "Item?shop_num=<%=request.getParameter("shop_num") %>";
	    	   }
	    	   if(data=="NAME"){
	    		   alert("이미 생성된 상품명 입니다. 다른 상품명을 입력해주세요");
	    	   }
	       },
	       //ajax error
	       error: function(e){
	           alert("에러발생!!");
	           console.log(e);
	       }                               
		});
	});
</script>

</html>
$(function() {
	$('#log-out').click(function(){
		$.ajax({
			url : "/xysp/local/logout",
			type : "post",
			async:false,
			cache:false,
			dataType : "json",
			success : function(data) {
				if (data.success) {
					window.localtion.href="/xysp/local/localauthlogin"
				}
			},
			error:function(date,error){
				alert(error);
			}
		});
	});
});
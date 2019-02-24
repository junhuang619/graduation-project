$(function() {
	// 调用，加载数据
	getshoplist();

	function getshoplist() {
		$.ajax({
			url : "/xysp/shopadmin/getshoplist",
			type : "get",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					handleList(data.shopList);
					handleUser(data.user);
				}
			}
		});
	}
	function handleUser(data) {
		$('#user-name').text(data.name);
	}
	function handleList(data) {
		var shopListHtml = '';
		data.map(function(item, index) {
			shopListHtml += '<div class="row row-shop"><div class="col-40">'
					+ item.shopName + '</div><div class="col-40">'
					+ shopStatus(item.shopEnableStatus)
					+ '</div><div class="col-20">'
					+ goShop(item.shopEnableStatus, item.shopId) + '</div></div>'
		});
		$('.shop-wrap').html(shopListHtml);
	}
	function shopStatus(status) {
		if (status == 0) {
			return '审核中';
		} else if (status == 1) {
			return '审核通过';
		} else if (status==-1){
			return '店铺非法';
		}
	}
	// 进入到商铺的管理页面,请求/shopadmin/shopmanagement ，进入到管理页面
	function goShop(status, shopId) {
		if (status == 1) {
			return '<a href="/xysp/shopadmin/shopmanagement?shopId=' + shopId
					+ '">进入</a>';
		} else {
			return '';
		}
	}
});
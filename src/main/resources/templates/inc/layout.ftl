<#macro layout title>
	<!DOCTYPE html>
	<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>ζηζΆθ - dailyhub</title>

		<link rel="stylesheet" href="/static/layui/css/layui.css">
		<link href="/static/css/bootstrap.min.css" rel="stylesheet">
		<link href="/static/css/sidebars.css" rel="stylesheet">
		<link href="/static/css/headers.css" rel="stylesheet">
		<script src="/static/js/jquery-3.6.0.min.js"></script>
		<script src="/static/js/bootstrap.bundle.min.js"></script>
		<script src="/static/layui/layui.all.js"></script>

		<style type="text/css">
			[v-cloak] {
				display: none !important;
			}
		</style>
		<script>
			var _hmt = _hmt || [];
			(function() {
				var hm = document.createElement("script");
				hm.src = "https://hm.baidu.com/hm.js?7a8ec283acf8e35ae729db292611eeca";
				var s = document.getElementsByTagName("script")[0];
				s.parentNode.insertBefore(hm, s);
			})();
		</script>


	</head>
	<body>
	<div class="container" style="max-width: 960px;">

		<#include "./header.ftl" />

		<#nested >

	</div>

<#--	<script>-->

<#--		$(function () {-->

<#--			layui.config({-->
<#--				version: false-->
<#--				, debug: false-->
<#--				, base: ''-->
<#--			});-->
<#--		});-->
<#--	</script>-->
	</body>
	</html>

</#macro>
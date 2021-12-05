<template>
	<view class="login">
		<view class="content">
			<!-- 头部logo -->
			<view class="header">
				<image :src="logoImage"></image>
			</view>
			<!-- 主体表单 -->
			<view class="main">
				<wInput v-model="emailData" type="text" maxlength="50" placeholder="邮箱" :focus="isFocus"></wInput>
				<wInput v-model="passData" type="password" maxlength="20" placeholder="密码"></wInput>
			</view>
			<wButton class="wbutton" text="登 录" :rotate="isRotate" @click="startLogin"></wButton>
			<!-- 底部信息 -->
			<view class="footer">
				<navigator url="forget" open-type="navigate" animation-type="pop-in" animation-duration="200">找回密码
				</navigator>
				<text>|</text>
				<navigator url="register" open-type="navigate" animation-type="pop-in" animation-duration="200">注册账号
				</navigator>
			</view>
		</view>
	</view>
</template>

<script>
	let _this;
	import wInput from '../../components/watch-login/watch-input.vue' //input
	import wButton from '../../components/watch-login/watch-button.vue' //button
	import {
		Request
	} from '@/api/request.js'
	let re = /^([a-zA-Z0-9]+)(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z0-9.]+)$/;
	export default {
		data() {
			return {
				//logo图片 base64
				logoImage: '../../static/images/logo.jpg',
				emailData: '', //邮箱
				passData: '', //密码
				isRotate: false, //是否加载旋转
				isFocus: true, // 是否聚焦
			};
		},
		components: {
			wInput,
			wButton,
		},
		mounted() {
			_this = this;
		},
		methods: {
			startLogin(e) {
				console.log(e)
				//登录
				if (this.isRotate) {
					//判断是否加载中，避免重复点击请求
					return false;
				}
				if (this.emailData.trim().length == "") {
					uni.showToast({
						icon: 'none',
						position: 'bottom',
						title: '用户名不能为空'
					});
					return;
				}

				if (!re.test(this.emailData.trim().toLowerCase())) {
					uni.showToast({
						icon: 'none',
						position: 'bottom',
						title: '邮箱格式不正确'
					});
					return;
				}
				if (this.passData.length < 6) {
					uni.showToast({
						icon: 'none',
						position: 'bottom',
						title: '密码不能小于6位'
					});
					return;
				}
				console.log("登录成功")
				_this.isRotate = true
				this.login();
			},
			login() {
				uni.showLoading({
					title: "正在登录"
				});
				Request({
						url: "user/login",
						method: "post",
						data: {
							"username": this.emailData.trim().toLowerCase(),
							"password": this.passData.trim()
						}
					}).then(res => {
						console.log(res.data == "" || res.data == null)
						if (res.data.userId == "-1") {
							setTimeout(() => {
								uni.hideLoading();
								_this.isRotate = false
								uni.showToast({
									icon: 'none',
									position: 'bottom',
									title: '该用户未注册'
								});
							}, 1000);
							return;
						}
						if (res.data == "" || res.data == null) {
							setTimeout(() => {
								uni.hideLoading();
								_this.isRotate = false
								uni.showToast({
									icon: 'none',
									position: 'bottom',
									title: '密码错误'
								});
							}, 1000);
							return;
						}
						setTimeout(() => {
							uni.setStorageSync('user', res.data);
							uni.hideLoading();
							_this.isRotate = false
							uni.showToast({
								title: '登录成功',
								mask: true
							});
						}, 1500);
						setTimeout(() => {
							uni.switchTab({
								url: '../mine/mine'
							});
						}, 1500);
						console.log(res);
					})
					.catch(cat => {
						console.log(cat);
					});
			},

		},
		onBackPress(e) {
			uni.switchTab({
				url: "../index/index"
			})
			return true;
		}
	}
</script>

<style>
	@import url("../../components/watch-login/css/icon.css");
	@import url("./css/main.css");
</style>

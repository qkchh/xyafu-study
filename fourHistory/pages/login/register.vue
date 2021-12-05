<template>
	<view class="register">

		<view class="content">
			<!-- 头部logo -->
			<view class="header">
				<image :src="logoImage"></image>
			</view>
			<!-- 主体 -->
			<view class="main">
				<wInput v-model="emailData" type="text" maxlength="50" placeholder="邮箱号"></wInput>
				<wInput v-model="passData" type="password" maxlength="20" placeholder="登录密码" isShowPass></wInput>
				<wInput v-model="verCode" type="number" maxlength="6" placeholder="验证码" isShowCode ref="runCode"
					@setCode="getVerCode()"></wInput>

			</view>
			<wButton class="wbutton" text="注 册" :rotate="isRotate" @click.native="startReg()"></wButton>

			<!-- 底部信息 -->
			<view class="footer">
				<text @tap="isShowAgree" class="cuIcon" :class="showAgree?'cuIcon-radiobox':'cuIcon-round'"> 同意</text>
				<!-- 协议地址 -->
				<navigator url="../privacy/privacy" open-type="navigate" animation-type="pop-in" animation-duration="200">《协议》</navigator>
			</view>
		</view>
	</view>
</template>

<script>
	let _this;
	import wInput from '../../components/watch-login/watch-input.vue' //input
	import wButton from '../../components/watch-login/watch-button.vue' //button
	let re = /^([a-zA-Z0-9]+)(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z0-9.]+)$/;
	import {
		Request
	} from '@/api/request.js'
	export default {
		data() {
			return {
				logoImage: '../../static/images/logo.jpg',
				emailData: '', // 用户/电话
				passData: '', //密码
				verCode: "", //验证码
				showAgree: true, //协议是否选择
				isRotate: false, //是否加载旋转
				code:""
			}
		},
		components: {
			wInput,
			wButton,
		},
		mounted() {
			_this = this;
		},
		methods: {
			isShowAgree() {
				//是否选择协议
				_this.showAgree = !_this.showAgree;
			},
			getVerCode() {
				//获取验证码
				if (!re.test(this.emailData.trim().toLowerCase())) {
					uni.showToast({
						icon: 'none',
						position: 'bottom',
						title: '邮箱格式不正确'
					});
					return;
				}
				console.log("获取验证码")
				this.$refs.runCode.$emit('runCode'); //触发倒计时（一般用于请求成功验证码后调用）
				this.sendEmail();
				this.clearCode();
				setTimeout(function() {
					_this.$refs.runCode.$emit('runCode', 0); //假装模拟下需要 终止倒计时
				}, 60 * 1000);
			},
			startReg() {
				// 注册
				if (this.isRotate) {
					//判断是否加载中，避免重复点击请求
					return false;
				}
				if (this.showAgree == false) {
					uni.showToast({
						icon: 'none',
						position: 'bottom',
						title: '请先同意《协议》'
					});
					return false;
				}
				if (!re.test(this.emailData.trim().toLowerCase())) {
					uni.showToast({
						icon: 'none',
						position: 'bottom',
						title: '邮箱格式不正确'
					});
					return;
				}
				if (this.passData.trim().length < 6) {
					uni.showToast({
						icon: 'none',
						position: 'bottom',
						title: '密码不能小于6位'
					});
					return false;
				}
				if (this.verCode.length != 6) {
					uni.showToast({
						icon: 'none',
						position: 'bottom',
						title: '验证码不是6位'
					});
					return false;
				}
				if(this.verCode!=this.code){
					uni.showToast({
						icon: 'none',
						position: 'bottom',
						title: '验证码错误或已经失效'
					});
					return false;
				}
				console.log("注册成功")
				_this.isRotate = true
				this.register();	
			},
			// 获取验证码
			sendEmail() {
				Request({
						url: "user/sendEmail",
						method: "post",
						data: this.emailData.trim().toLowerCase()
					}).then(res => {
						if (res.data=="error") {
							uni.showToast({
								icon: 'error',
								title: '发送错误，请重试'
							});
						}else{
							uni.showToast({
								icon: 'none',
								position: 'bottom',
								title: '验证码已发送'
							});
							this.code=res.data;
						}
						console.log(res);
					})
					.catch(cat => {
						console.log(cat);
					});
			},
			// 注册
			register() {	
				Request({
						url: "user/register",
						method: "post",
						data: {
							"username": this.emailData.trim(),
							"password": this.passData.trim(),
						}
					}).then(res => {
						if (res.data) {
							setTimeout(function() {
								_this.isRotate = false
								uni.showToast({
									icon:"none",
									position:"bottom",
									title: res.data
								});
								setTimeout(()=>{
									if(res.data=="注册成功"){
										uni.navigateTo({
											url:"login",
											animationType: 'pop-in',
											animationDuration: 200,
										})
									}
								},1500);
							}, 1500);
							
						}
						console.log(res);
					})
					.catch(cat => {
						console.log(cat);
					});
			},
			// 清空验证码
			async clearCode(){
				await setTimeout(()=>{
					this.code="";
				},10 * 6 * 1000);
			}
		}
	}
</script>

<style>
	@import url("../../components/watch-login/css/icon.css");
	@import url("./css/main.css");
</style>

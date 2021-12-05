<template>
	<view class="forget">
		<view class="content">
			<!-- 主体 -->
			<view class="main">
				<view class="tips">若你忘记了密码，可在此重置新密码。</view>
				<wInput v-model="emailData" type="text" maxlength="50" placeholder="请输入邮箱"></wInput>
				<wInput v-model="passData" type="password" maxlength="20" placeholder="请输入新密码" isShowPass></wInput>

				<wInput v-model="verCode" type="number" maxlength="6" placeholder="验证码" isShowCode codeText="获取重置码"
					setTime="60" ref="runCode" @setCode="getVerCode()"></wInput>
			</view>

			<wButton class="wbutton" text="重置密码" :rotate="isRotate" @click.native="startRePass()"></wButton>

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
				emailData: "", //电话
				passData: "", //密码
				verCode: "", //验证码
				isRotate: false, //是否加载旋转
				code: '',
				emial:""
			}
		},
		components: {
			wInput,
			wButton
		},
		mounted() {
			_this = this;
		},
		methods: {
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
				}, 60 * 1000)
			},
			startRePass() {
				//重置密码
				if (this.isRotate) {
					//判断是否加载中，避免重复点击请求
					return false;
				}
				if (this.emailData.trim().toLowerCase() == "") {
					uni.showToast({
						icon: 'none',
						position: 'bottom',
						title: '邮箱不能为空'
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
					return false;
				}
				if (this.verCode.length != 6) {
					uni.showToast({
						icon: 'none',
						position: 'bottom',
						title: '验证码不正确'
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
				console.log("重置密码成功")
				_this.isRotate = true;
				this.forget();
			},
			// 获取验证码
			sendEmail() {
				Request({
						url: "user/sendEmail",
						method: "post",
						data: this.emailData.trim().toLowerCase()
					}).then(res => {
						if (res.data == "error") {
							uni.showToast({
								icon: 'error',
								title: '发送错误，请重试'
							});
						} else {
							uni.showToast({
								icon: 'none',
								position: 'bottom',
								title: '验证码已发送'
							});
							this.code = res.data;
						}
						console.log(res);
					})
					.catch(cat => {
						console.log(cat);
					});
			},
			// 清空验证码
			async clearCode() {
				await setTimeout(() => {
					this.code = "";
				}, 10 * 6 * 1000);
			},
			// 重置密码
			forget() {
				Request({
						url: "user/updatePassword",
						method: "post",
						data: {
							"username": this.emailData.trim().toLowerCase(),
							"password": this.passData.trim()
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
								setTimeout(() => {
									if (res.data == "修改成功") {
										uni.navigateBack({
											delta: 1,
											animationType: 'pop-in',
											animationDuration: 200,
										})
									}
								}, 1500);
							}, 1500);
						}
						console.log(res);
					})
					.catch(cat => {
						console.log(cat);
					});
			},
			
		},
		onLoad(options) {
			this.emailData=options.emial;
		}
	}
</script>

<style>
	@import url("../../components/watch-login/css/icon.css");
	@import url("./css/main.css");
</style>

<template>
	<view class="content">
		<view class="header">
			<view class="photo">
				<image :src="userImage==null || userImage=='' ?require('@/static/images/user.png'):userImage"
					mode="aspectFill" class="avatar" @tap="previewImage">
				</image>
				<view class="upload">
					<image class="img" @tap="uploadImage" src="@/static/images/camera.png"></image>
				</view>
			</view>
		</view>
		<view class="forms">
			<view class="form-item">
				<view class="label">昵称<label class="count void ">{{'（'+(nicksName?nicksName.length:0)+'/20）'}}</label>
				</view>
				<view class="box">
					<input class="text" maxlength="20" placeholder="请输入昵称" v-if="!popupShow"
						v-model="nicksName"></input>
				</view>
			</view>
			<view class="form-item">
				<view class="label">个性签名<label class="count">{{'（'+(description?description.length:0)+'/120）'}}</label>
				</view>
				<view class="box">
					<textarea :autoHeight="true" class="area high" :holdKeyboard="false" maxlength="120"
						placeholder="请输入你的个性签名..." v-if="!popupShow" v-model="description"></textarea>
					<view :class="!description?'gray':''" v-else>
						<rich-text :nodes="descText"></rich-text>
					</view>
				</view>
			</view>
		</view>
		<view class="submit">
			<button @tap="saveInfo" class="btn">保存</button>
		</view>

	</view>
</template>

<script>
	import {
		Request
	} from '@/api/request.js'
	import {
		BASE_URL
	} from '@/api/url.js'
	export default {
		data() {
			return {
				popupShow: false,
				nicksName: '',
				descText: '',
				description: '',
				userImage: '',
				UserInfo: '',
				isUploadImage: false,
			}
		},
		onShow() {

		},
		methods: {
			uploadImage: function() {
				let that = this;
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album'], //从相册选择
					success: function(res) {
						const image = res.tempFiles[0];
						if (image.size > 1048576) {
							uni.showToast({
								title: '图片不能大于1MB',
								icon: "error"
							})
							return;
						}
						that.userImage = image.path;
						that.isUploadImage = true;
					}
				});
			},
			previewImage() {
				if (this.userImage == null || this.userImage == '') {
					uni.previewImage({
						urls: ["/static/images/user.png"]
					})
				} else {
					uni.previewImage({
						urls: [this.userImage]
					})
				}
			},
			saveInfo: function() {
				const that = this;
				let UserInfo = this.UserInfo;
				UserInfo.description = this.description;
				UserInfo.nickName = this.nicksName;
				UserInfo.avatar = this.userImage;
				Request({
						url: "user/updateUserData",
						method: "post",
						data: UserInfo
					}).then(res => {
						if (res.data != null) {
							UserInfo = res.data;
							if (this.isUploadImage) {
								uni.showLoading({
									title: '图片上传中...'
								})
								this.isUploadImage = false;
								uni.uploadFile({
									url: BASE_URL + "user/uploadHeadPortrait",
									filePath: that.userImage,
									name: 'headPortrait',
									formData: {
										"userId": UserInfo.userId
									},
									success(res) {
										if (res.data != null) {
											UserInfo = eval('(' + res.data + ')');
											uni.hideLoading();
										} else {
											uni.hideLoading();
											uni.showToast({
												title: '上传失败',
												icon: "error"
											})
										}
										console.log(UserInfo);
									},
									timeout: 10 * 1000,
									fail(res) {
										uni.hideLoading();
										uni.showToast({
											title: '上传失败',
											icon: "error"
										})
										console.log(res);
									}
								});
							}
							uni.showToast({
								title: "保存成功"
							});
							uni.removeStorageSync("user");
							setTimeout(() => {
								console.log(UserInfo);
								this.UserInfo = UserInfo;
								uni.setStorageSync("user", this.UserInfo);
								console.log(this.UserInfo);
							}, 1000);
						}
						console.log(res);
					})
					.catch(cat => {
						console.log(cat);
					});
				setTimeout(() => {
					uni.navigateBack({
						delta: 1,
						animationType: 'pop-out',
						animationDuration: 200
					})
				}, 1500)
			},
			//获取缓存区中是否有用户信息
			getUserInfo() {
				this.UserInfo = uni.getStorageSync("user");
				this.userImage = this.UserInfo.avatarUrl;
				console.log(this.UserInfo)
				if (this.UserInfo == '') {
					uni.showToast({
						title: "你还未登录",
						icon: "error"
					});
					uni.navigateTo({
						url: "../login/login",
						animationType: 'pop-in',
						animationDuration: 200
					});
				}
			},
		},
		onLoad() {
			this.getUserInfo();
		},
		onShow() {

		}
	}
</script>

<style lang="scss" scoped>
	.header {
		padding: 60rpx 0;
		text-align: center;
		margin-bottom: 20rpx;

		.photo {
			width: 200rpx;
			height: 200rpx;
			margin: auto;
			position: relative;

			.avatar {
				width: 100%;
				height: 100%;
				border-radius: 50%;
				margin-bottom: 10rpx;
				background-size: cover;
			}

			.upload {
				width: 48rpx;
				height: 48rpx;
				padding: 10rpx;
				background: #3e3e3f;
				border-radius: 50%;
				position: absolute;
				right: 0;
				bottom: 0;

				.img {
					width: 100%;
					height: 100%;
					z-index: 99;
				}
			}
		}

	}

	.forms {
		padding: 0 30rpx;

		.form-item {
			width: 100%;

			.label {
				margin: 28rpx 0 10rpx;
				color: #4a4a4a;

				.count {
					font-size: 24rpx;
					color: #999;
				}
			}

			.box {
				width: 100%;
				padding: 4rpx 0;
				position: relative;
				z-index: 1;

				.text {
					display: block;
				}

				.area {
					width: calc(100% - 60rpx);
					height: 80rpx;
					line-height: 40rpx;
					display: block;
					background: #f8f8f9;
					border-radius: 12rpx;
					padding: 20rpx 30rpx;
					border: 0;
					font-size: 32rpx;
					vertical-align: middle;
				}

				.high {
					min-height: 300rpx;
					max-height: 560rpx;
					margin-bottom: 40rpx;
					overflow: hidden;
				}

				.text {

					width: calc(100% - 60rpx);
					height: 100rpx;
					line-height: 100rpx;
					background: #f8f8f9;
					border-radius: 12rpx;
					padding: 0 30rpx;
					font-weight: 400;
					font-size: 32rpx;

				}

				.gray {
					color: #888;
				}

			}

		}
	}

	.submit {
		width: 100%;
		background-color: #fff;
		padding: 20rpx 50rpx;
		box-sizing: border-box;
		z-index: 9;

		.btn {
			width: 80%;
			height: 100rpx;
			line-height: 100rpx;
			border-radius: 50rpx;
			background: #d83631;
			color: #fff;
			font-size: 32rpx;
		}
	}
</style>

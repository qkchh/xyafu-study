<template>
	<view class="content">
		<view class="my-topbg">
			<image src="@/static/images/wuxing.png" mode="widthFix" class="wuxing"></image>
			<view class="user-card">
				<view class="my-top" v-if="UserInfo==''" @tap='gotoLogin'>
					<view class="my-top-cell">
						<view class="my-name">
							<view class="name">点击登录</view>
						</view>
						<view class="my-post">
							暂无
						</view>
					</view>
					<view class="my-head">
						<image mode="aspectFill" src="@/static/images/user.png"></image>
					</view>
				</view>

				<view class="my-top" v-else>
					<view class="my-head">
						<image mode="aspectFill"
							:src="UserInfo.avatarUrl==null || UserInfo.avatarUrl=='' ?require('@/static/images/user.png'):UserInfo.avatarUrl">
						</image>
					</view>
					<view class="my-top-cell">
						<view class="my-name">
							<view class="name">{{UserInfo.nickName}}</view>
						</view>
						<view class="my-post">
							{{UserInfo.description?UserInfo.description:'这个人很懒什么都没写'}}
						</view>
					</view>
					<view class="pick-code" @tap="goMyEdit">
						编辑资料
					</view>
				</view>

				<view class="my-other">
					<view class="my-hbox">
						<view class="item" @tap="toCollect">
							<view class="item-box">
								<view class="cell">
									<view class="num">{{account.favs?account.favs:0}}</view>
									<view class="name">收藏</view>
								</view>
							</view>
						</view>
						<view class="item" @tap="toLikes">
							<view class="item-box">
								<view class="cell">
									<view class="num">{{account.likes?account.likes:0}}</view>
									<view class="name">点赞</view>
								</view>
							</view>
						</view>
						<view class="item" @tap="toComment">
							<view class="item-box">
								<view class="cell">
									<view class="num">{{account.comments?account.comments:0}}</view>
									<view class="name">评论</view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="fix-card">
			<view class="flex-wrp flex-other">
				<navigator class="flex-item" url="../feedback/feedback" animation-type="pop-in" animation-duration="200">
					<text>我要反馈</text>
					<view class="value">给我们提个意见或建议</view>
					<image src="@/static/images/icon-link.png"></image>
				</navigator>
				<navigator class="flex-item" url="callme/callme" animation-type="pop-in" animation-duration="200">
					<text>联系我们</text>
					<view class="value">有什么问题可以联系我们</view>
					<image src="@/static/images/icon-link.png"></image>
				</navigator>
			</view>
		</view>

		<view class="fix-card">
			<view class="flex-wrp flex-other">
				<navigator class="flex-item" url="../privacy/privacy" animation-type="pop-in" animation-duration="200">
					<text>隐私政策</text>
					<view class="value">隐私政策</view>
					<image src="@/static/images/icon-link.png"></image>
				</navigator>
				<navigator class="flex-item" :url="emial" animation-type="pop-in" animation-duration="200">
					<text>修改密码</text>
					<view class="value">修改密码</view>
					<image src="@/static/images/icon-link.png"></image>
				</navigator>
				<navigator class="flex-item" url="../imprint/imprint" animation-type="pop-in" animation-duration="200">
					<text>版本说明</text>
					<view class="value">版本说明</view>
					<image src="@/static/images/icon-link.png"></image>
				</navigator>
				<navigator class="flex-item" url="../about/about" animation-type="pop-in" animation-duration="200">
					<text>关于我们</text>
					<view class="value">关于我们</view>
					<image src="@/static/images/icon-link.png"></image>
				</navigator>
			</view>
		</view>

		<view class="fix-card">
			<view class="flex-wrp flex-other">
				<view class="flex-item" @tap='quite'>
					<text class="qutie">退出登录</text>
				</view>
			</view>
		</view>


		<view class="copyright-footer">
			<view class="text">信阳农林学院</view>
		</view>
		<u-toast ref="uToast"></u-toast>
	</view>
</template>

<script>
	import {
		Request
	} from '@/api/request.js'
	export default {
		data() {
			return {
				UserInfo: '',
				account: {
					favs: 0,
					likes: 0,
					comments: 0
				},
				emial: ""
			}
		},
		methods: {
			goMyEdit: function() {
				uni.navigateTo({
					url: '../editme/editme',
					animationType: 'pop-in',
					animationDuration: 200,
				})
			},
			gotoLogin() {
				uni.navigateTo({
					url: '../login/login',
					animationType: 'pop-in',
					animationDuration: 200,
				})
			},
			toLikes() {
				if (this.UserInfo == '') {
					return;
				}
				uni.navigateTo({
					url: './likes/likes',
					animationType: 'pop-in',
					animationDuration: 200,
				})
			},
			toCollect() {
				if (this.UserInfo == '') {
					return;
				}
				uni.navigateTo({
					url: './collect/collect',
					animationType: 'pop-in',
					animationDuration: 200,
				})
			},
			toComment() {
				if (this.UserInfo == '') {
					return;
				}
				uni.navigateTo({
					url: './comment/comment'
				})
			},
			//获取缓存区中是否有用户信息
			getUserInfo() {
				this.UserInfo = uni.getStorageSync("user");
				console.log(this.UserInfo);
			},
			// 获取数据
			async selectCount() {
				if (this.UserInfo == '') {
					this.account.favs = 0;
					this.account.likes = 0;
					this.account.comments = 0;
					return;
				}
				await Request({
						url: "user/selectCount",
						method: "post",
						data: {
							"userId": this.UserInfo.userId
						}
					}).then(res => {
						this.account = res.data;
						console.log(res.data);
					})
					.catch(cat => {
						console.log(cat);
					});
			},
			// 退出账号
			quite() {
				let that = this;
				uni.removeStorage({
					key: "user",
					success() {
						that.UserInfo = '';
						uni.showToast({
							title: "退出成功",
							icon: "success"
						})
					}
				});
				this.emial = "../login/forget"
				this.getUserInfo();
				this.selectCount();
			}
		},
		onLoad() {},
		onPullDownRefresh() {
			this.getUserInfo();
			this.selectCount();
			setTimeout(function() {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		onShow() {
			this.getUserInfo();
			this.selectCount();
			if (this.UserInfo.username != null) {
				this.emial = "../login/forget?emial=" + this.UserInfo.username
			} else {
				this.emial = "../login/forget"
			}
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		background-color: whitesmoke;
		position: absolute;
		width: 100%;
		height: 100%;
	}

	.my-topbg {
		position: relative;
		background: linear-gradient(180deg, #d83632, #d85f5f);
		padding-bottom: 140rpx;
		margin-bottom: -140rpx;

		.wuxing {
			width: 284rpx;
			position: absolute;
			right: 72rpx;
			top: 30rpx;
		}

		.my-top {
			padding: 200rpx 0 40rpx 35rpx;
			margin-bottom: 20rpx;
			position: relative;
			display: flex;
			align-items: center;

			.my-head {
				margin-right: 30rpx;
				position: relative;
				display: flex;
				align-items: center;

				image {
					width: 102rpx;
					height: 102rpx;
					border-radius: 999rem;
					overflow: hidden;
				}
			}

			.pick-code {
				height: 60rpx;
				text-align: center;
				position: relative;
				width: 171rpx;
				background: linear-gradient(311deg, #55aa7f, #5fbf8e);
				font-size: 26rpx;
				color: #f7fffe;
				line-height: 60rpx;
				box-sizing: border-box;
				border-radius: 999rem;
				margin-right: 20rpx;

			}

			.my-top-cell {
				flex: 1;
				overflow: hidden;
				white-space: nowrap;

				.my-name {
					font-size: 42rpx;
					color: #333;
					line-height: 60rpx;
					font-weight: 700;
					display: flex;
					align-items: center;

					.name {
						overflow: hidden;
						white-space: nowrap;
						margin-right: 7rpx;
						width: 300rpx;
						overflow: hidden;
						text-overflow: ellipsis;

					}

					.pag {
						height: 33rpx;
						background: linear-gradient(293deg, #37e2ca, #06d58b);
						border-radius: 4rpx;
						padding: 0 8rpx;
						margin-left: 12rpx;
						white-space: nowrap;
						text-overflow: ellipsis;
						margin-right: 8rpx;
						line-height: 33rpx;
						font-size: 24rpx;
						color: #fff;
						font-weight: 400;
					}
				}


				.my-post {
					display: flex;
					align-items: center;

					.tag {
						background: #fba117;
						border-radius: 4rpx;
						font-size: 22rpx;
						color: #fff;
						padding: 0 10rpx;
						line-height: 32rpx;
						white-space: nowrap;
						overflow: hidden;
						text-overflow: ellipsis;
						margin-right: 20rpx;

					}
				}
			}
		}

		.my-other {
			position: relative;
			display: flex;
			align-items: center;
			justify-content: space-between;

			.my-hbox {
				display: flex;
				align-items: center;
				padding: 0 20rpx;
				flex: 1;

				.item {
					position: relative;
					flex: 1;

					.item-box {
						height: 118rpx;
						border-radius: 10rpx;
						display: flex;
						align-items: center;

						.cell {
							flex: 1;
							text-align: center;

							.num {
								font-size: 38rpx;
								color: white;
								font-family: DINAlternate-Bold, DINAlternate;
								font-weight: 700;
								white-space: nowrap;
								text-overflow: ellipsis;
							}

							.name {
								font-size: 24rpx;
								color: #ededed;
								margin-top: 7rpx;
								white-space: nowrap;
								text-overflow: ellipsis;
							}
						}
					}
				}
			}
		}
	}

	.fix-card {
		position: relative;
		background: #fff;
		border-radius: 20rpx;
		margin: 20rpx;
		box-shadow: 0 0 30px 0 rgba(112, 3, 19, 0.1);

		.fix-header {
			position: relative;
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding: 32rpx 32rpx 0;

			.name {
				font-size: 32rpx;
				color: #384645;
				font-weight: 700;
			}

			.actions {
				font-size: 26rpx;
				color: #a0acaa;
				display: flex;
				align-items: center;

				image {
					width: 20rpx;
					height: 20rpx;
				}
			}
		}

		.flex-wrp {
			display: flex;
			align-items: center;
			flex-wrap: wrap;
			padding: 10rpx 0 20rpx;

			.t-item {

				position: relative;
				text-align: center;
				width: 25%;
				padding: 30rpx 0;

				image {
					width: 56rpx;
					height: 56rpx;
					display: block;
					margin: 0 auto;
				}

				text {
					font-size: 26rpx;
					color: #333;
					margin-top: 20rpx;
					display: block;
				}

				.contact {
					position: absolute;
					width: 100%;
					height: 120rpx;
					top: 0;
					left: 0;
					opacity: 0;
				}
			}
		}

		.flex-other {
			flex-direction: column;
			padding: 0;
			display: flex;
			align-items: center;

			.flex-item {
				position: relative;
				padding: 34rpx 26rpx;
				width: 100%;
				box-sizing: border-box;
				line-height: 42rpx;
				display: flex;
				align-items: center;

				text {
					font-size: 30rpx;
					color: #333837;
				}

				.qutie {
					display: flex;
					justify-content: center;
					width: 100%;
					color: #d83632;
				}

				.value {
					font-size: 26rpx;
					color: #a0acaa;
					margin-left: auto;
				}


				image {
					width: 24rpx;
					height: 24rpx;
					margin-left: 8rpx;
				}
			}
		}
	}

	.copyright-footer {
		text-align: center;
		font-size: 24rpx;
		color: #b6b9bb;
		padding: 40rpx 0;
		line-height: 36rpx;

		image {
			width: 60rpx;
			height: 60rpx;
			display: block;
			margin: 0 auto 8rpx;
		}
	}

	.kefu-justify-center {
		flex-grow: 1;
		flex-direction: column;
		display: flex;
		width: 500rpx;
		height: 700rpx;
		text-align: center;

		.title {
			padding: 20rpx 30rpx;
			font-size: 30rpx;
		}

		.image {
			width: 400rpx;
			margin: 0 auto;

			image {
				width: 400rpx;
			}
		}

		.save {
			width: 200rpx;
			margin: 20rpx auto;
			padding: 20rpx 30rpx;
			border: 1px solid #00aa7f;
			background: #00aa7f;
			border-radius: 5px;
			font-size: 28rpx;
			color: #fff;
		}

	}
</style>

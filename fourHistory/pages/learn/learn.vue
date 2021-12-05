<template>
	<view class="content">
		<view class="articleList">
			<u-tabs :list="cats" :is-scroll="true" :current="current" @change="change" count="cate_count"
				inactive-color="#999" :active-item-style="activeStyle" active-color="#d83632" class="cats"></u-tabs>
			<view class="item" v-for="(item,index) in posts" :key="index"
				@click="gotoLink(item.link,item.learnDetailId)">
				<view class="normal-post">
					<view class="post-content">
						<view class="detail">
							<view class="title">
								{{item.title}}
							</view>
							<view class="info">
								<view class="left">
									<text class="red-border">{{item.channelName}}</text>
								</view>
								<view class="righr">
									<text>{{item.date}}</text>
									<text class="red-border" v-if="item.study">已学习</text>
								</view>
							</view>
						</view>
						<view class="cover">
							<image :src="item.thumbnail" mode="aspectFill"></image>
						</view>
					</view>
				</view>

			</view>
		</view>

		<u-loadmore :status="status" style="padding-top: 30rpx;" />
	</view>
</template>

<script>
	import {
		Request
	} from '@/api/request.js'
	export default {
		data() {
			return {
				activeStyle: {
					'font-size': '38rpx',
					'color': '#d83632'
				},
				status: "loading",
				background: {
					backgroundColor: '#d83632',
				},
				title: '',
				current: 0,
				cats: [{
						categoryId: 0,
						name: '全部',
						except: true
					},
					{
						categoryId: 1,
						name: '党史',
						except: false
					}
				],
				posts: [],
				UserInfo: ''
			}
		},
		methods: {
			//获取缓存区中是否有用户信息
			getUserInfo() {
				this.UserInfo = uni.getStorageSync("user");
				console.log(this.UserInfo);
			},
			getCats() {
				Request({
						url: "learn/getCats",
						method: "get"
					}).then(res => {
						this.cats = res.data;
						console.log(res);
					})
					.catch(cat => {
						console.log(cat);
					});
			},
			change: function(e) {
				this.current = e;
				this.id = this.cats[e].id;
				this.posts = [];
				this.status = "loading";
				this.getPostsById(e + 2);
			},
			getPostsById(id) {
				Request({
						url: "learn/getPostsById",
						method: "post",
						data: {
							"learnId": id,
							"userId": this.UserInfo.userId
						}
					}).then(res => {
						this.status = "";
						this.posts = res.data;
						console.log(res);
					})
					.catch(cat => {
						console.log(cat);
					});
			},
			gotoLink(link, detailId) {
				console.log(link)
				// plus.runtime.openURL(link);
				// window.location.href = link;
				this.studey(detailId);
				uni.navigateTo({
					url: '/pages/webview/webview?url=' + link,
					animationType: 'pop-in',
					animationDuration: 200,
				})
			},
			// 点击后显示已经学习
			studey(detailId) {
				Request({
						url: "learn/study",
						method: "post",
						data: {
							"learnDetailId": detailId,
							"userId": this.UserInfo.userId
						}
					}).then(res => {
						this.status = "";
						this.posts = res.data;
						console.log(res);
					})
					.catch(cat => {
						console.log(cat);
					});
			},
			// 是否已经登录
			idLogin() {
				if (this.UserInfo == '') {
					uni.showToast({
						title: "请先登录",
						icon: "error",
						mask: true
					});
					setTimeout(() => {
						uni.navigateTo({
							url: '../login/login',
							animationType: 'pop-in',
							animationDuration: 200
						});
					}, 1500)
				}
			}
		},
		onLoad() {
			this.getUserInfo();
			this.getCats();
			this.getPostsById(2);
		},
		onPullDownRefresh() {
			this.getCats();
			this.getPostsById(this.current + 2);
			setTimeout(function() {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		onShow() {
			this.getUserInfo();
			this.getPostsById(this.current + 2);
			this.idLogin();
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		background-color: whitesmoke;
		padding-bottom: 30rpx;
	}

	.cats {
		padding-top: 50rpx;
		border-bottom-left-radius: 15px;
		border-bottom-right-radius: 15px;
	}

	.articleList {

		.item {
			padding: 0 30rpx;
			border-bottom: 1rpx solid #f5f5f5;
			background-color: white;
			margin: 10rpx 10rpx 14rpx 10rpx;
			border-radius: 30rpx;
			box-shadow: 0 0 30rpx 0 rgba(112, 0, 0, 0.1);

			.normal-post {
				display: block;
				box-sizing: content-box;
				padding-top: 40rpx;
				position: relative;
				min-height: 180rpx;


				.post-content {
					display: flex;
					justify-content: space-between;

					.detail {

						.title {
							padding-top: 10rpx;
							line-height: 52rpx;
							font-size: 36rpx;
							color: #000;
							white-space: normal;
							word-break: normal;
							word-wrap: break-word;
							-webkit-hyphens: auto;
							-ms-hyphens: auto;
							hyphens: auto;
							display: -webkit-box;
							-webkit-box-orient: vertical;
							-webkit-line-clamp: 1;
							overflow: hidden;
						}

						.info {
							margin-top: 36rpx;
							height: 38rpx;
							display: flex;

							text {
								display: inline-block;
								vertical-align: top;
								height: 32rpx;
								line-height: 32rpx;
								font-size: 24rpx;
								color: #969696;
								margin-right: 10rpx;
							}

							.righr {
								display: flex;
							}

							.red-border {
								display: block;
								background-color: #ffe7dd;
								color: #ff5b60;
								padding: 0 20rpx;
								border-radius: 20rpx;
							}


						}

					}

					.cover {
						-o-object-fit: cover;
						object-fit: cover;


						image {
							width: 160rpx;
							height: 160rpx;
							border-radius: 4px;
						}
					}

				}
			}

		}
	}
</style>

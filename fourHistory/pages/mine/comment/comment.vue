<template>
	<view class="content">
		<view v-if="commentList.length!=0">
			<view class="comment-reply">
				<view class="comment-reply-item" v-for="(item,index) in commentList" :key="index"
					@tap="operationComment(item.detailId,item.commentId)">
					<view class="comment-detail">
						<view class="comment">
							<view class="comment-header">
								<view class="comment-header-left">
									<image catchtap="gotouser" class="comment-avatar" :src="item.user.avatarUrl">
									</image>
									<view class="comment-user-date">
										<text>{{item.user.nickName}}</text>
										<text>{{item.date}}</text>
									</view>
								</view>
							</view>
							<view class="comment-content">
								<view class="commentconent">
									<view class="contentinfo" bindtap="replyComment">
										{{item.content}}
									</view>
								</view>
							</view>
						</view>
						<view class="detail">
							<image :src="item.thumbnail" mode="aspectFill"></image>
						</view>
					</view>
					<view class="comment-footer">
						<view style="height:1px;background:#f9f9f9;"></view>
					</view>
				</view>
			</view>
		</view>
		<view class="collect" v-else>
			<view class="empty-comment">
				<view class="sofa"></view>
				<p class="empty-comment-des">暂无评论，快去抢个沙发吧</p>
			</view>
		</view>

		<u-action-sheet :list="list" v-model="show" @click="selectClick"></u-action-sheet>
	</view>
</template>

<script>
	import {
		Request
	} from '@/api/request.js'
	export default {
		data() {
			return {
				list: [{
					text: "查看详情"
				}, {
					text: "删除",
					color: "#d83632"
				}],
				commentList: [],
				UserInfo: '',
				detailId: '',
				commentId: '',
				show: false,
			};
		},
		methods: {
			showToast(params) {
				this.$refs.uToast.show({
					...params,
				})
			},
			//获取缓存区中是否有用户信息
			getUserInfo() {
				this.UserInfo = uni.getStorageSync("user");
			},
			async getMyCollect() {
				await Request({
						url: "user/getMyComment",
						method: "post",
						data: {
							"userId": this.UserInfo.userId
						}
					}).then(res => {
						this.commentList = res.data;
						console.log(res.data);
					})
					.catch(cat => {
						console.log(cat);
					});
			},
			operationComment(detailId, commentId) {
				this.show = true;
				this.detailId = detailId;
				this.commentId = commentId;
				return;
			},
			selectClick(index) {
				if (index == 0) {
					uni.navigateTo({
						url: '/pages/detail/detail?id=' + this.detailId,
						animationType: 'pop-in',
						animationDuration: 200,
					})
				} else if (index == 1) {
					Request({
							url: "user/deleteComment",
							method: "post",
							data: this.commentId
						}).then(res => {
							this.commentList = res.data;
							console.log(res.data);
						})
						.catch(cat => {
							console.log(cat);
						});
				}
			}
		},
		onLoad() {
			this.getUserInfo();
			this.getMyCollect();
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		position: absolute;
		width: 100%;
		height: 100%;
	}

	.collect {
		position: absolute;
		width: 100%;
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.empty-comment {
		width: 100%;
		padding: 46rpx 32rpx 80rpx;
		box-sizing: border-box;

		.empty-comment-title {
			font-size: 36rpx;
			color: #222;
			font-weight: 600;
		}

		.sofa {
			width: 260rpx;
			height: 260rpx;
			margin: 24px auto 0;
			background: url("@/static/images/no-comment.png") no-repeat 50%/260rpx;
		}

		.empty-comment-des {
			display: block;
			width: 100%;
			text-align: center;
			font-size: 28rpx;
			color: #999;
			margin: 0 auto;
		}
	}

	.comment-reply {
		padding: 30rpx;
		width: 100%;

		.comment-reply-item {
			margin-bottom: 20rpx;


			.comment-detail {
				display: flex;
				justify-content: space-between;

				.comment {
					.comment-header {
						display: flex;
						justify-content: space-between;
						align-items: center;

						.comment-header-left {
							display: flex;
							align-items: center;

							.comment-avatar {
								display: inline-block;
								width: 60rpx;
								height: 60rpx;
								border-radius: 100%;
								margin-right: 20rpx;
							}

							.comment-user-date {
								display: flex;
								flex-direction: column;

								text:first-child {
									font-size: 30rpx;
									font-weight: bold;
									color: #3a3a3a;
								}

								text:last-child {
									font-size: 22rpx;
									color: #a8a8a8;
								}
							}
						}

						.comment-header-right {
							display: flex;
							align-items: center;

							image {
								width: 30rpx;
								height: 30rpx;
								vertical-align: bottom;
							}

							text {
								font-size: 28rpx;
								line-height: 30rpx;
								padding-left: 10rpx;
								color: #7d7d7d;
							}

							.comment-vote-btn {
								position: relative;
								display: flex;
								align-items: center;

								&:first-child {
									margin-right: 34rpx;
								}

								text {
									display: inline-block;
									font-size: 24rpx;
									color: #a8a8a8;
									border: 10rpx solid transparent;
									border-right: 0;
								}

								image {
									display: inline-block;
									width: 20rpx;
									height: 18rpx;
									border: 10rpx solid transparent;
								}
							}

							.vote-count-actived {
								color: #f13b03 !important;
							}
						}

					}

					.comment-content {
						position: relative;
						margin: 10rpx 20rpx 30rpx 80rpx;
						font-size: 30rpx;
						color: #3a3a3a;
					}

				}

				.detail {

					image {
						width: 150rpx;
						height: 150rpx;
						border-radius: 4px;
					}
				}

			}

		}

		.replay-content {
			margin-left: 80rpx;

			.comment-avatar {
				width: 50rpx !important;
				height: 50rpx !important;
			}

		}
	}
</style>

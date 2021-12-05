<template>
	<view class="articleList">
		<view class="item" v-for="(item,index) in posts" :key="index" @click="gotoDetail(item.detailId)">
			<view class="normal-post" v-if="!item.type">
				<view class="message">
					<view class="title">
						{{item.title}}
					</view>
					<view class="info">
						<view class="left">
							<text>{{item.comments}}评论</text>
							<text>{{item.views}}浏览</text>
						</view>
						<view class="righr">
							<text class="time">{{item.time}}</text>
						</view>
					</view>
				</view>
			<view class="cover">
					<image :src="item.tabThumbnailList[0].image" mode="aspectFill"></image>
				</view>
			</view>

			<view class="gallery-post" v-else>
				<view class="title">
					{{item.title}}
				</view>
				<view class="image-content">
					<view class="image-multiple">
						<view class="image" v-for="(img,idx) in item.tabThumbnailList" :key="idx" v-if="idx<3">
							<image :src="img.image" mode="aspectFill"></image>
						</view>
					</view>
				</view>
				<view class="info">
					<view class="left">
						<text>{{item.comments}}评论</text>
						<text>{{item.views}}浏览</text>
					</view>
					<view class="righr">
						<text class="time">{{item.time}}</text>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		Request
	} from '@/api/request.js'
	export default {
		name: "articleList",
		props: {
			posts: {
				type: Array,
				default: function() {
					return [];
				}
			},
		},
		data() {
			return {

			};
		},
		methods: {
			gotoDetail(id) {
				this.updateDetailByViews(id);
				uni.navigateTo({
					url: '/pages/detail/detail?id=' + id,
				})
			},
			// 更改浏览次数
			async updateDetailByViews(id) {
				await Request({
						url: "detail/updateDetailByViews",
						method: "post",
						data: {
							"detailId": id
						}
					}).then(res => {
						console.log(res.data);
					})
					.catch(cat => {
						console.log(cat);
					});
			},
		}
	}
</script>

<style lang="scss" scoped>
	.articleList {
		padding-bottom: 60rpx;

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
				padding-right: 220rpx;
				min-height: 220rpx;
				position: relative;

				.message{
					display: flex;
					flex-direction: column;
					justify-content: space-between;
					height: 220rpx;
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
						-webkit-line-clamp: 2;
						overflow: hidden;
					}
					
					.info {
						margin-top: 60rpx;
						height: 38rpx;
						display: flex;
						justify-content: space-between;
					
						text {
							display: inline-block;
							vertical-align: top;
							height: 32rpx;
							line-height: 32rpx;
							font-size: 24rpx;
							color: #969696;
							margin-right: 20rpx;
						}
					}
					
				}
			
				.cover {
					position: absolute;
					width: 200rpx;
					height: 200rpx;
					right: 0;
					top: 46rpx;
					-o-object-fit: cover;
					object-fit: cover;


					image {
						width: 200rpx;
						height: 200rpx;
						border-radius: 4px;
					}
				}
			}

			.gallery-post {
				padding-top: 40rpx;

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
					-webkit-line-clamp: 2;
					overflow: hidden;
					margin-bottom: 10rpx;
				}

				.image-content {
					display: flex;
					flex-direction: column;

					.image-multiple {
						flex: 1;
						display: flex;
						flex-wrap: nowrap;

						.image {
							width: 212rpx;
							height: 212rpx;
							margin: 10rpx;
							position: relative;
							border-radius: 4px;
							overflow: hidden;

							image {
								width: 100%;
								height: 100%;
							}
						}
					}
				}

				.info {
					height: 38rpx;
					display: flex;
					justify-content: space-between;
					margin-bottom: 8rpx;


					text {
						display: inline-block;
						vertical-align: top;
						height: 32rpx;
						line-height: 32rpx;
						font-size: 24rpx;
						color: #969696;
						margin-right: 20rpx;
					}
				}

			}
		}
	}
</style>

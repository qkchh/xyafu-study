<template>
	<view class="content">
		<view class="showloading" v-if="loading">
			<image class="loadicon image" src="@/static/images/loading.svg"></image>
			<view class="loadtext">
				<text class="text">加载中，请稍后</text>
			</view>
		</view>
		<view class="topic-detail" v-if="!loading">
			<view class="article-info">
				<view class="article-detail-title article-wrap">{{introduce.title}}</view>
			</view>
		</view>

		<view id="entry-content" class="entry-content clearfix">
			<u-parse :show-with-animation="true" :html="introduce.content" ref="article"></u-parse>
		</view>

	</view>
</template>

<script>
	import {Request} from '@/api/request.js'
	export default {
		data() {
			return {
				loading: false,
				id:0,
				introduce: {
					title: "标题",
					content: "<p>我是内容我是内容我是内容我是内容我是内容</p><image src='https://img2.ali213.net/picfile/News/2020/09/04/584_2020090443800677.jpg'></image>"
				},
			};
		},
		methods: {
			//获取分类列表
			async getIntroduceData(){
				await Request({
					url:"cats/getIntroduceData",
					method:"post",
					data:this.id
				}).then(res => {
					this.introduce=res.data;
					console.log(res);
				})
				.catch(cat => {
					console.log(cat);
				});
			}
		},
		onLoad(options) {
			this.id=options.id;
			this.getIntroduceData();
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		padding-bottom: 100rpx;
	}

	.showloading {
		background: #fff;
		position: fixed;
		text-align: center;
		transition: .3s;
		width: 100%;
		height: 100%;
		z-index: 100;
		display: flex;
		align-items: center;
		justify-content: center;
		flex-flow: column;

		.load {
			opacity: 1;
			transition: .3s;
			top: 0;
		}

		.loadtext {
			display: flex;
			align-items: center;
			justify-content: center;

			.text {
				font-size: 28rpx;
				opacity: .5;
			}
		}

		.loadicon {
			width: 100rpx;
			height: 100rpx;
			animation: turn .4s linear infinite;
			position: relative;
			z-index: 10;
			opacity: .6;

			&::before {
				content: "";
				position: absolute;
				border-radius: 50%;
				border-width: 10rpx;
				border-style: solid;
				width: 80rpx;
				height: 80rpx;
				transform: scale(-.7);
				left: 0rpx;
				top: 0rpx;
				z-index: 5;
			}
		}
	}

	.article-info {
		background: #fff;
		padding-top: 40rpx;
		border-radius: 15rpx;

		.article-detail-author {
			display: flex;
			padding: 68rpx 30rpx 68rpx 30rpx;
			font-size: 24rpx;
			line-height: 1;

			text {
				font-weight: bold;
			}

			view {
				width: 2rpx;
				height: 22rpx;
				margin: 0 18rpx;
				background-color: #ABABAB;
			}

			span {
				color: #ababab;
			}
		}

		.article-detail-title {
			font-size: 54rpx;
			color: #3b3b3b;
			line-height: 87rpx;
			font-family: 'zaker-title';
		}

		.article-wrap {
			padding: 0 30rpx;
		}


	}

	.entry-content {
		clear: both;
		color: #404246;
		margin: 0;
		padding: 0 30rpx;
		font-size: 36rpx;
		line-height: 70rpx;
		text-align: justify;
		word-wrap: break-word;
		word-break: normal;

		video {
			width: 100%;
		}

		image {
			width: 100%;
		}

	}

	.item_container {
		height: auto;
		width: auto;
		display: flex;
		flex-direction: column;
		justify-content: flex-start;
		align-items: center;
		padding-top: 40rpx;

		.item_desc {
			font-family: PingFangSC-Regular;
			font-size: 28rpx;
			color: #888;
			letter-spacing: 0;
		}
	}

	.textareacontent {
		border-top-left-radius: 16rpx;
		border-top-right-radius: 16rpx;
		background-color: #fff;
		z-index: 10;

		.textheaders {
			width: 100%;
			height: 96rpx;
			border-bottom: 1rpx solid #eaeaea;
			display: flex;
			justify-content: space-between;

			.cancel {
				color: #333;
				font-size: 32rpx;
				line-height: 96rpx;
				margin-left: 32rpx;
			}

			.publish {
				color: #d83632;
				font-size: 32rpx;
				line-height: 96rpx;
				margin-right: 32rpx;
			}
		}

		.textareaBox {
			height: 360rpx;
			position: relative;

			.textCount {
				position: absolute;
				font-size: 26rpx;
				color: #999;
				right: 32rpx;
				top: 4rpx;
			}

			.textareaInput {
				margin: 30rpx;
			}
		}
	}
 
</style>

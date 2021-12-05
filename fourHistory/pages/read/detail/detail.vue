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
				<view class="article-detail-title article-wrap">{{introduce.name}}</view>
			</view>
		</view>
		<view class="image">
			<image
				:src="introduce.photo"
				mode="widthFix"></image>
		</view>

		<view class="book-description entry-content clearfix">
			内容简介
			<view class="brief">
				<u-parse :show-with-animation="true" :html="introduce.brief" ref="article"></u-parse>
			</view>
		</view>

		<view class="book-description entry-content clearfix"  v-if=" introduce.catalogue!='' && introduce.catalogue!=null">
			书本目录
			<view class="brief">
				<u-parse :show-with-animation="true" :html="introduce.catalogue" ref="article"></u-parse>
			</view>
		</view>


		<view class="book-description entry-content clearfix"  v-if=" introduce.appreciate!='' && introduce.appreciate!=null ">
			作品鉴赏
			<view class="brief">
				<u-parse :show-with-animation="true" :html="introduce.appreciate" ref="article"></u-parse>
			</view>
		</view>

		<view id="entry-content" class="entry-content clearfix">
			<u-parse :show-with-animation="true" :html="introduce.content" ref="article"></u-parse>
		</view>

	</view>
</template>

<script>
	import {
		Request
	} from '@/api/request.js'
	export default {
		data() {
			return {
				loading: false,
				bookId:0,
				introduce: {
					name: "中国共产党简史",
					content: "《中国共产党简史》是中央宣传部组织，中央党史和文献研究院等单位编写的政治著作，首次出版于2021年2月。该书记录了一百年来中国共产党团结带领人民进行革命、建设、改革的光辉历程，充分反映了中国共产党为实现国家富强、民族振兴、人民幸福和人类文明进步事业作出的历史功绩，系统总结了党和国家事业不断从胜利走向胜利的宝贵经验，集中彰显了党在各个历史时期淬炼锻造的伟大精神。",
				},
			};
		},
		methods: {
			async selectAllBookSummary() {
				await Request({
						url: "book/getBookDetail",
						method: "post",
						data:this.bookId
					}).then(res => {
						this.introduce = res.data;
						console.log(res);
					})
					.catch(cat => {
						console.log(cat);
					});
			},
		},
		onLoad(options) {
			this.bookId=options.id;
			this.selectAllBookSummary();
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		padding-bottom: 100rpx;
	}

	.image {
		display: flex;
		justify-content: center;
		align-items: center;

		image {
			width: 50%;
		}
	}

	.book-description {
		font-size: 40rpx;
		font-weight: 600;

		.brief {
			font-size: 30rpx;
			font-weight: normal;
			margin-bottom: 20rpx;

			.title {
				font-weight: 700;
				text-align: center;
			}
		}

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
		text-align: center;
		font-weight: 900;


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

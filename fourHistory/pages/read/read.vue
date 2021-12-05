<template>
	<view class="content">
		<u-navbar :title="title" :is-back="false" :border-bottom="false" :background="background" title-color="#fff"
			v-if="option"></u-navbar>
		<view class="top">
			<image src="@/static/images/top-bg.png" mode="widthFix" class="header-background-pic"></image>
			<view class="logo-pic">四史书籍</view>
		</view>

		<view class="l-main">
			<navigator class="l-dl" v-for="(item,index) in books" :key="index" :url="url+(item.bookId)" animation-type="pop-in" animation-duration="200"> 
				<view>
					<image class="l-dt" :src="item.photo" mode="aspectFill"></image>
				</view>
				<view class="l-dd">
					<view class="l-dd-title">
						{{item.name}}
					</view>
					<view class="l-dd-content">
						{{item.brief}}
					</view>
				</view>
			</navigator>
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
				title: "",
				option: false,
				background: {
					backgroundColor: '#d83632',
				},
				url: "./detail/detail?id=",
				books: [{
						title: "标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题",
						photo: "https://p5.itc.cn/q_70/images03/20200603/d2b6bc82b8184fda9f6cca63fbae4ea4.jpeg",
						synopsis: "《社会主义发展历程》主要内容：回顾马克思主义诞生以来的世界社会主义历程，不难发现其发展的规律：波浪式前进，螺旋式上升，有高潮也有低潮，但总的说是像万里长江一样，一浪高过一浪，有一泻千里也有迂回曲折，但大江东流去的方向不会改变。",
						type: "类型"
					},
					{
						title: "标题",
						photo: "https://p5.itc.cn/q_70/images03/20200603/d2b6bc82b8184fda9f6cca63fbae4ea4.jpeg",
						synopsis: "详情",
						type: "类型"
					},
					{
						title: "标题",
						photo: "https://p5.itc.cn/q_70/images03/20200603/d2b6bc82b8184fda9f6cca63fbae4ea4.jpeg",
						synopsis: "详情",
						type: "类型"
					},
					{
						title: "标题",
						photo: "https://p5.itc.cn/q_70/images03/20200603/d2b6bc82b8184fda9f6cca63fbae4ea4.jpeg",
						synopsis: "详情",
						type: "类型"
					},
					{
						title: "标题",
						photo: "https://p5.itc.cn/q_70/images03/20200603/d2b6bc82b8184fda9f6cca63fbae4ea4.jpeg",
						synopsis: "详情",
						type: "类型"
					},
					{
						title: "标题",
						photo: "https://p5.itc.cn/q_70/images03/20200603/d2b6bc82b8184fda9f6cca63fbae4ea4.jpeg",
						synopsis: "详情",
						type: "类型"
					}
				]
			}
		},
		methods: {
			async selectAllBookSummary() {
				await Request({
						url: "book/selectAllBookSummary",
						method: "get"
					}).then(res => {
						this.books = res.data;
						console.log(res);
					})
					.catch(cat => {
						console.log(cat);
					});
			}
		},
		onPageScroll(res) {
			if (res.scrollTop > 80) {
				this.title = "四史书籍";
				this.option = true
			} else {
				this.title = " ";
				this.option = false
			}
		},
		onLoad() {
			this.selectAllBookSummary();
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		background: whitesmoke;
	}

	.top {
		padding-top: 100rpx;
		font-size: 28rpx;
		color: #333;
		position: relative;

		.header-background-pic {
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
		}

		.logo-pic {
			font-size: 35rpx;
			font-weight: 700;
			color: white;
			position: relative;
			width: 328rpx;
			min-height: 52rpx;
			margin-left: 40rpx;
		}
	}

	.l-main {
		padding: 0rpx 32rpx;
		position: relative;
	}

	.l-dl {
		padding: 20rpx;
		margin-top: 32rpx;
		display: flex;
		height: 262rpx;
		background-color: white;
		border-radius: 20rpx;
		box-shadow: 0 0 30px 0 rgba(112, 0, 0, 0.1);

		.l-dt {
			width: 160rpx;
			height: 100%;
			border-radius: 10rpx;
			margin-right: 24rpx;
		}

		.l-dd {
			flex: 1;
			display: flex;
			flex-direction: column;
			justify-content: space-between;
			z-index: 99;

			.l-dd-title {
				color: #000000;
				font: 32rpx normal;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				-webkit-line-clamp: 1;
				overflow: hidden;
			}

			.l-dd-content {
				margin-bottom: 20rpx;
				color: #151515;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				-webkit-line-clamp: 5;
				overflow: hidden;
			}
		}
	}
</style>

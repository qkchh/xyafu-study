<template>
	<view class="content">
		<u-navbar :title="title" :is-back="false" :border-bottom="false" :background="background" title-color="#fff"
			v-if="option" class="navbar"></u-navbar>
		<view class="top">
			<image src="@/static/images/top-bg.png" mode="widthFix" class="header-background-pic"></image>
			<view class="logo">
				<image class="logo-pic" mode="widthFix" src="@/static/images/danghui.png"></image>
				<view class="logo-text">四史</view>
			</view>
			<view class="search-bar" @tap="gotoSearch">
				<view class="search-bar__icon"></view>
				<input id="key" placeholder="搜索你喜欢的内容" placeholder-style="color:#f9f9f9"
					class="search-bar__placeholder"></input>
			</view>
			<view class="swiper-content">
				<u-swiper :list="swiperList" :effect3d="true" @click="gotoBanner" bg-color="rgba(0,0,0,0)" height="300">
				</u-swiper>
			</view>
			<view class="articleList">
				<u-tabs :list="cats" :is-scroll="true" :current="current" @change="change" count="cate_count"
					inactive-color="#999" :active-item-style="activeStyle" active-color="#d83632" class="cats"></u-tabs>
				<view class="article-content" :style="'min-height:'+(systemHeight-80)+'px'">
					<article-list :posts="posts"></article-list>
					<u-loadmore :status="status" />
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
		data() {
			return {
				swiperList: [
					"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.51wendang.com%2Fpic%2F560a0f49def3bd6eb3aa3b2722ccdfab041c4613%2F1-810-jpg_6-1440-0-0-1440.jpg&refer=http%3A%2F%2Fwww.51wendang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639276619&t=d0e7aef42098ec6495b4040eb7e37d24",
					"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fqywin.cn%2Fupload%2F202104%2F1617776317.jpg&refer=http%3A%2F%2Fqywin.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639276707&t=b287486c59cd20555eb182d2fb6d66af",
					"https://img2.baidu.com/it/u=2524410712,36440927&fm=26&fmt=auto",
					"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.ewenku.net%2Fe7c97cf47a94d6c0d7d72258be1beb59%2F1.png%3Fx-oss-process%3Dimage%2Fresize%2Cw_850&refer=http%3A%2F%2Fimg.ewenku.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639276756&t=254ddf9c762488df84b3e134cae4ae89"
				],
				posts: [],
				showNavs: false,
				offset: 44,
				cats: [{
						categoryId: 0,
						name: '全部',
						except: true
					},
					{
						categoryId: 1,
						name: '党史',
						except: false
					},
					{
						categoryId: 2,
						name: '新中国史',
						except: false
					},
					{
						categoryId: 3,
						name: '改革开放史',
						except: false
					},
					{
						categoryId: 4,
						name: '社会主义发展史',
						except: false
					}
				],
				current: 0,
				activeStyle: {
					'font-size': '38rpx',
					'color': '#d83632'
				},
				title: '',
				option: false,
				systemHeight: 400,
				status: "loading",
				background: {
					backgroundColor: '#d83632',
				},
			}
		},
		onLoad() {

		},
		onShow() {
			this.getCatsData();
			this.getPostsList(this.current);
		},
		onPageScroll(res) {
			if (res.scrollTop > 80) {
				this.title = "学习四史";
				this.option = true
			} else {
				this.title = " ";
				this.option = false
			}
		},
		onPullDownRefresh() {
			this.getPostsList(this.current);
			setTimeout(function() {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		methods: {
			hiddenNav: function() {
				let that = this;
				setTimeout(function() {
					that.showNavs = false;
				}, 1000);
			},
			change: function(e) {
				this.current = e;
				this.id = this.cats[e].id;
				this.posts = [];
				this.status = "loading";
				this.getPostsList(e);
			},
			gotoBanner(e) {
				uni.navigateTo({
					url: '/pages/index/introduce/introduce?id=' + (e + 2),
					animationType: 'pop-in',
					animationDuration: 200,
					success() {
						console.log("success");
					},
					fail(e) {
						console.log(e);
					}
				})
			},
			gotoSearch: function() {
				uni.navigateTo({
					url: '../search/search',
					animationType: 'pop-in',
					animationDuration: 200,
				})
			},
			// 获取列表的信息
			getPostsList(id) {
				if (id == 0) {
					console.log(id)
					this.selectPosts();
				} else {
					this.selectPostsByCategoryId(id + 1);
				}
			},
			async selectPosts() {
				await Request({
						url: "detail/selectPosts",
						method: "get"
					}).then(res => {
						this.status = "";
						this.posts = res.data;
						console.log(res);
					})
					.catch(cat => {
						console.log(cat);
					});
			},
			async selectPostsByCategoryId(id) {
				await Request({
						url: "detail/selectPostsByCategoryId",
						method: "post",
						data: {
							"categoryId": id
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
			//获取分类列表
			async getCatsData() {
				await Request({
						url: "cats/getCatsData",
						method: "get"
					}).then(res => {
						this.status = "";
						this.cats = res.data;
						console.log(res);
					})
					.catch(cat => {
						console.log(cat);
					});
			},
		}

	}
</script>

<style lang="scss" scoped>
	.content {
		background-color: whitesmoke;
	}

	.top {
		padding-top: 50rpx;
		font-size: 28rpx;
		color: #333;
		position: relative;

		.header-background-pic {
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
		}

		.logo {
			display: flex;
			margin-top: 30rpx;

			.logo-text {
				font-size: 66rpx;
				font-weight: 700;
				color: white;
				position: relative;
				width: 52%;
				min-height: 52rpx;
				margin-left: 40rpx;
				display: flex;
				align-items: center;
				justify-content: center;
				font-family: 'font-baizhou';
			}

			.logo-pic {
				position: relative;
				width: 100rpx;
				min-height: 52rpx;
				margin-left: 40rpx;
			}
		}


		.swiper-content {
			padding: 30rpx 0rpx;
			background-color: white;
		}

		.search-bar {
			position: relative;
			margin: 30rpx 40rpx 0;
			display: flex;
			justify-content: center;
			align-items: center;
			flex-flow: row nowrap;
			height: 64rpx;
			border-radius: 38rpx;
			background: rgba(255, 255, 255, 0.15);

			.search-bar__icon {
				width: 32rpx;
				height: 32rpx;
				background-image: url("https://cloud-minapp-16269.cloud.ifanrusercontent.com/product-search.svg");
				background-size: contain;
				opacity: 0.8;
				filter: brightness(2);
			}

			.search-bar__placeholder {
				margin-left: 10rpx;
				font-size: 28rpx;
				color: white;
				opacity: 0.8;
			}

		}

	}

	.articleList {
		padding-bottom: 30rpx;

		.cats {
			border-bottom-left-radius: 15px;
			border-bottom-right-radius: 15px;
		}
	}
</style>

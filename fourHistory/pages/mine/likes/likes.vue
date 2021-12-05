<template>
	<view class="content">
		<view v-if="posts.length!=0">
			<article-list :posts="posts"></article-list>
			<view class="none">
				--没有更多了--
			</view>
		</view>
		<view class="collect" v-else>
			你还没有喜欢的内容，先去给文章点赞吧
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
				UserInfo: '',
				isLastPage: false,
				posts: [],
				status: 'loading'
			};
		},
		methods: {
			//获取缓存区中是否有用户信息
			getUserInfo() {
				this.UserInfo = uni.getStorageSync("user");
			},
			getMyLikes() {
				console.log(this.UserInfo);
				Request({
						url: "user/getMyLikes",
						method: "post",
						data: {
							"userId": this.UserInfo.userId
						}
					}).then(res => {
						this.posts = res.data;
						this.isLoading = false;
						console.log(res.data);
					})
					.catch(cat => {
						console.log(cat);
					});
			}
		},
		onLoad() {
			this.getUserInfo();
			this.getMyLikes();
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		background-color: whitesmoke;
		position: absolute;
		width: 100%;
		height: 100%;
		.none {
			text-align: center;
			color: #818181;
		}
	}

	.collect {
		position: absolute;
		width: 100%;
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
	}
</style>

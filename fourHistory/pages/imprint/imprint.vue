<template>
	<view class="content">
		<image :src="src" mode="aspectFill">
		</image>
		<view>信农学习</view>
		<view>版本号：1.2.5</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				src: "../../static/images/logo.jpg"
			};
		},

	}
</script>

<style lang="scss" scoped>
	.content {
		position: absolute;
		width: 100%;
		height: 100% -200rpx;
		display: flex;
		margin-top: 200rpx;
		align-items: center;
		flex-direction: column;
		font-family: "长城";

		image {
			width: 200rpx;
			height: 200rpx;
			border-radius: 999rem;
			overflow: hidden;
			margin-bottom: 50rpx;
		}
	}
</style>

<template>
	<view class="content">
		<image :src="src" mode="aspectFill">
		</image>
		<view>邮箱</view>
		<text>clownlingyun@qq.com</text>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				src: "../../../static/images/logo.jpg"
			};
		}
	}
</script>

<style lang="scss">
	.content {
		position: absolute;
		width: 100%;
		height: 100% -200rpx;
		display: flex;
		margin-top: 200rpx;
		align-items: center;
		flex-direction: column;

		image {
			width: 200rpx;
			height: 200rpx;
			border-radius: 999rem;
			overflow: hidden;
			margin-bottom: 50rpx;
		}
	}
</style>

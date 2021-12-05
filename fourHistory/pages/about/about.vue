<template>
	<view class="content">
		<text class="title">关于我们</text>
		<text>
			<view id="entry-content" class="entry-content clearfix">
				既然
				你诚心诚意的发问了，
				我们就大发慈悲的告诉你。
				为了防止世界被破坏，为了维护世界的和平，
				贯彻爱与真实的邪恶，可爱又迷人的反派角色，
				我们是穿梭在银河的火箭队，白洞、白色的明天在等着我们，就是这样，喵~
			</view>
		</text>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			};
		}
	}
</script>

<style lang="scss">
	.content {
		padding: 40rpx;

		.title {
			font-weight: 700;
			font-size: 40rpx;
			display: flex;
			justify-content: center;
			margin-bottom: 40rpx;
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
		}
	}
</style>

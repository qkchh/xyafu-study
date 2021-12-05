<template>
	<view class="content">
		<view class="formPost">
			<u-form :model="form" ref="uForm" label-position="top">
				<u-form-item label="反馈内容">
					<u-input v-model="form.content" type="textarea" placeholder="请输入反馈内容" />
				</u-form-item>
				<u-form-item label="联系人">
					<u-input v-model="form.name" placeholder="请输入联系人" />
				</u-form-item>
				<u-form-item label="微信号">
					<u-input v-model="form.weixin" placeholder="请输入微信号" />
				</u-form-item>
				<u-form-item label="手机号码">
					<u-input v-model="form.phone" placeholder="请输入手机号码" />
				</u-form-item>
			</u-form>
			<u-button @click="submit">提交</u-button>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				form: {
					content: '',
					name: '',
					weixin: '',
					phone: ''
				},

			}
		},
		onShow() {

		},
		methods: {
			submit: function() {
				uni.showToast({
					title: '留言成功!',
					icon: 'success',
					duration: 900
				})
				setTimeout(function() {
					uni.reLaunch({
						url: '/pages/mine/mine'
					})
				}, 1000)
			}
		},
	}
</script>

<style>
	.formPost {
		padding: 0 30rpx;
	}
</style>

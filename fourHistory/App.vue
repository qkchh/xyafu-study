<script>
	export default {
		onLaunch() {
			setTimeout(function() {
				uni.loadFontFace({
					family: 'font-lanting',
					// 本地字体路径需转换为平台绝对路径
					source: `url(${plus.io.convertLocalFileSystemURL('_www/static/font/lanting.ttf')})`,
					success() {
						console.log('success')
					},
					fail(e) {
						console.log('fail')
					}
				})
				uni.loadFontFace({
					family: 'font-baizhou',
					// 本地字体路径需转换为平台绝对路径
					source: `url(${plus.io.convertLocalFileSystemURL('_www/static/font/baizhou.ttf')})`,
					success() {
						console.log('success')
					},
					fail(e) {
						console.log('fail')
					}
				})
			}, 3000);
		}
	}
</script>

<style lang="scss">
	/*每个页面公共css */
	@import '@/uview-ui/index.scss';
</style>

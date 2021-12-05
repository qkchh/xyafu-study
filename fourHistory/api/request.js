import {BASE_URL} from '@/api/url.js'
/* 第一种*/
export const Request = (options) => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: BASE_URL + options.url,
			method: options.method || 'GET', //获取方法默认get
			data: options.data || {}, //默认数据空
			success: (res) => {
				// return uni.showToast({
				// 	title: '获取数据失败'
				// })
				resolve(res)
			},
			fail: (err) => {
				uni.showToast({
					title: '获取数据失败'
				})
				reject(err)
			}
		})
	})
}



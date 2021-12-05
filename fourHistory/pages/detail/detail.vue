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
				<view class="article-detail-author">
					<text>{{detail.author}}</text>
					<view></view>
					<span>{{detail.time}}</span>
					<view></view>
					<span>{{detail.views}} 浏览</span>
					<view></view>
					<span>{{detail.likes}} 点赞</span>
				</view>
				<view class="article-detail-title article-wrap">{{detail.title}}</view>
			</view>
		</view>

		<view id="entry-content" class="entry-content clearfix">
			<u-parse :show-with-animation="true" :html="detail.content" ref="article"></u-parse>
		</view>


		<view class="item_container">
			<view>
				<text class="item_desc">- 没有更多了-</text>
			</view>
		</view>

		<comment :commentList="commentList" :commentCountTotal="Number(detail.comments)" @reply="reply"
			@tapLikes="tapcommentLike" @showRecommendList="showRecommend" @showNewestList="showNewest"></comment>


		<!-- 评论 -->
		<view :class="!isInput?'comment-submit is-show':'comment-dialog is-hide'">
			<view class="comment-submit-left">
				<view @tap="tapComment" class="comment-submit-btn">写评论...</view>
			</view>
			<view class="comment-submit-right">
				<view class="comment-submit-icon" @tap='scorllComment'>
					<view class="comment-submit-image comment-comment"></view>
					<view v-if="detail.comments>0">{{detail.comments}}</view>
					<view v-else>评论</view>
				</view>
				<view class="comment-submit-icon" @tap='insertIsFav'>
					<view
						:class="isfav===false?'comment-submit-image comment-star':'comment-submit-image comment-nostar-active'">
					</view>
					<view>{{isfav===false?'收藏':'取消收藏'}}</view>
				</view>

				<view class="comment-submit-icon" @tap=insertIsLike>
					<view
						:class="islike===false?'comment-submit-image comment-zan-2':'comment-submit-image comment-zan-active-2'">
					</view>
					<view v-if="favourSum>0">{{favourSumFormat}}</view>
					<view v-else>点赞</view>
				</view>
			</view>
		</view>

		<u-popup v-model="isComments" mode="bottom" border-radius="14" height="800rpx" z-index="10" @close="outComment">
			<view class="textareacontent">
				<view class="textheaders">
					<view @tap="outComment" class="cancel">取消</view>
					<view class="publish" @tap="commitComment">发布</view>
				</view>
				<view class="textareaBox">
					<textarea class="textareaInput" maxlength="140" :show-confirm-bar="false" name="content"
						@input="bindInput" :placeholder="placeholder" placeholder-class="placeholder-text"
						:value="content"></textarea>
					<view class="textCount">{{count}}/140</view>
				</view>
			</view>
		</u-popup>
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
				loading: false,
				isInput: false,
				isComments: false,
				isfav: false,
				islike: false,
				isFocus: true,
				placeholder: '写评论...',
				isPublish: false,
				content: '',
				favourSum: 0,
				count: 0,
				id: 0,
				userId: "",
				commentId: "",
				detail: {
					title: "标题",
					author: "姓名",
					time: "时间",
					views: 10,
					content: "<div class='post_body'><p class='f_center'><img src='https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2021%2F1113%2Fffb05738j00r2i2e0001zd200u000big00zk00dm.jpg&amp;thumbnail=650x2147483647&amp;quality=80&amp;type=jpg'><br></p><p class='f_center'><img src='https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2021%2F1113%2F9f2434dcj00r2i2e00043d200u000k0g00u000k0.jpg&amp;thumbnail=650x2147483647&amp;quality=80&amp;type=jpg'><br></p><p id='0DM0DQLR'>为持续深入开展党史学习教育，引导党员干部筑牢信仰根基，扛起职责使命。11月10日，市检察院组织党员干部前往福州市宦溪镇降虎村红军北上抗日先遣队纪念园，开展'传承红色基因、筑牢初心使命”主题党日活动。</p><p class='f_center'><img src='https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2021%2F1113%2Fd7ad6115j00r2i2e0003fd200u000k0g00u000k0.jpg&amp;thumbnail=650x2147483647&amp;quality=80&amp;type=jpg'><br></p><p id='0DM0DQM1'>在降虎村'党建初心学堂”，党员干部们一起重温中国共产党的百年光辉历程和伟大成就，在'中央红军北上抗日先遣队进军福建陈列馆”，讲解员结合展板，给党员干部们生动讲述了红军北上抗日先遣队进军福建尤其是降虎之战的历史。这支先遣队前后约有1万人，历时6个多月，行程2800多公里，大小战斗30多次，牵制国民党军队十几万人，掩护了中央红军的战略转移。他们沿途宣传中国共产党的抗日主张，推动了全国抗日救国运动的发展。斑驳的驳壳枪子弹袋、残留的牛角火药杯和子弹、先遣队进军福建路线图……陈列馆内，丰富详实的史料再现了这支部队的战斗足迹和历史贡献。</p><p class='f_center'><img src='https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2021%2F1113%2F43187521j00r2i2e10061d200u000k0g00u000k0.jpg&amp;thumbnail=650x2147483647&amp;quality=80&amp;type=jpg'><br></p><p id='0DM0DQM7'>通过讲解员生动的讲述，在场的党员干部们感悟到了这段令人荡气回肠的历史和战士们不屈不挠、不怕牺牲的战斗精神。参观完毕，党员干部们还瞻仰了红军烈士墓，缅怀革命先烈的丰功伟绩，并纷纷表示，将铭记历史，继承先辈遗志，筑牢信仰之基，切实从党史学习教育中汲取力量，转化为对党忠诚、履职奉献、担当作为的实际行动。</p><p class='f_center'><img src='https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2021%2F1113%2F84da8845j00r2i2e1003wd200u000k0g00u000k0.jpg&amp;thumbnail=650x2147483647&amp;quality=80&amp;type=jpg'><br></p><p id='0DM0DQMD'>自党史学习教育活动开展以来，市检察院高度重视用好用活红色资源，为党史学习教育活动赋能赋效，充分利用福建省革命历史纪念馆、工人文化宫'奋斗·启航——福建省庆祝中国共产党成立100周年主题展”、文林山革命陵园党性锤炼专题展览等阵地资源优势，组织参观活动<strong>17</strong>场次，开展'光影铸魂、追忆党史”系列红色观影活动<strong>12</strong>场次，推动全体党员干部从党史中汲取精神力量。</p><p class='f_center'><img src='https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2021%2F1113%2Ffdb329b4j00r2i2e2005wd200u000k0g00u000k0.jpg&amp;thumbnail=650x2147483647&amp;quality=80&amp;type=jpg'><br></p><p id='0DM0DQMJ'>下一步，市检察院将继续传承红色基因，赓续红色血脉，充分利用红色文化、红色故事、红色教育基地等红色资源，深刻领会中国共产党人精神谱系的丰富内涵，激励广大党员干部勇于做难事、挑重担、开新局，推动党史学习教育走深走实。</p><p id='0DM0DQMP'>素材 | 机关党委</p><p id='0DM0DQMQ'>编辑 | 林峰</p><p class='f_center'><img src='https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2021%2F1113%2F9369fddej00r2i2e2002dd200u000l7g00zk00p4.jpg&amp;thumbnail=650x2147483647&amp;quality=80&amp;type=jpg'><br></p><p class='f_center'><img src='https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2021%2F1113%2Fba30446bj00r2i2e2002bd200u000l7g00zk00p4.jpg&amp;thumbnail=650x2147483647&amp;quality=80&amp;type=jpg'><br></p><p class='f_center'><img src='https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2021%2F1113%2Fc49a9c88j00r2i2e20022d200u000l7g00zk00p4.jpg&amp;thumbnail=650x2147483647&amp;quality=80&amp;type=jpg'><br></p></div>"
				},
				commentList: [{
					commentId: 1,
					user: {
						avatarUrl: "https://thirdwx.qlogo.cn/mmopen/vi_32/IEiawcHX9Y3E3qPlsYibT4sib7j6PScMFKfhdjXKBKOnAmMI7gft4CcTOQHlxYPh0icZaibia3b0CHsc3hH1uQqicrUlA/132",
						nickName: "作者"
					},
					date: "2021-11-15 15:52:30",
					content: "评论内容",
					tabComments: [{
						id: 2,
						user: {
							avatarUrl: "https://thirdwx.qlogo.cn/mmopen/vi_32/IEiawcHX9Y3E3qPlsYibT4sib7j6PScMFKfhdjXKBKOnAmMI7gft4CcTOQHlxYPh0icZaibia3b0CHsc3hH1uQqicrUlA/132",
							nickName: "作者"
						},
						date: "2021-11-15 15:52:30",
						content: "评论内容",
						tabComments: [{
							id: 3,
							user: {
								avatarUrl: "https://thirdwx.qlogo.cn/mmopen/vi_32/IEiawcHX9Y3E3qPlsYibT4sib7j6PScMFKfhdjXKBKOnAmMI7gft4CcTOQHlxYPh0icZaibia3b0CHsc3hH1uQqicrUlA/132",
								nickName: "作者"
							},
							date: "2021-11-15 15:52:30",
							content: "评论内容",
						}],
					}],
				}]
			};
		},
		methods: {
			//获取缓存区中是否有用户信息
			getUserInfo() {
				this.UserInfo = uni.getStorageSync("user");
			},
			tapComment: function() {
				this.isComments = true;
			},
			outComment: function() {
				this.isComments = false;
				this.placeholder = '写点评论...'
				this.userId = "";
				this.commentId = "";
			},
			bindInput: function(e) {
				this.content = e.detail.value;
				this.count = this.content.length;
			},
			reply: function(e) {
				console.log(e);
				let reply = e.data.user.nickName;
				this.isFocus = true;
				this.isComments = true;
				this.placeholder = " @" + reply + ":";
				this.commentId = e.data.commentId;
				console.log(this.userId + this.commentId)
			},
			scorllComment() {
				uni.createSelectorQuery().select(".content").boundingClientRect(data => { //目标节点
					uni.createSelectorQuery().select(".commentlist_wrap").boundingClientRect((res) => { //最外层盒子节点
						uni.pageScrollTo({
							duration: 0, //过渡时间必须为0，uniapp bug，否则运行到手机会报错
							scrollTop: res.top - data.top, //滚动到实际距离是元素距离顶部的距离减去最外层盒子的滚动距离
						})
					}).exec()
				}).exec();
			},
			// 获取详情数据
			async getDetailData(id) {
				console.log(id);
				await Request({
						url: "detail/selectDetail",
						method: "post",
						data: {
							"detailId": id
						}
					}).then(res => {
						this.detail = res.data;
						console.log(res);
					})
					.catch(cat => {
						console.log(cat);
					});
			},
			// 获取自己是否已经点赞已经收藏
			async getUserIsLikesIsFav() {
				console.log(this.id+"111"+this.UserInfo.userId)
				await Request({
						url: "detail/getUserIsLikesIsFav",
						method: "post",
						data: {
							"detailId": this.id,
							"userId": this.UserInfo.userId
						}
					}).then(res => {
						this.islike = res.data.islike ? res.data.islike : false;
						this.isfav = res.data.isfav ? res.data.isfav : false;
						console.log(res.data);
					})
					.catch(cat => {
						console.log(cat);
					});
			},
			// 点赞上传数据到数据库
			async insertIsLike() {
				if (this.UserInfo == '') {
					uni.showToast({
						title: "请先登录",
						icon: "error"
					});
					return;
				}
				await Request({
						url: "detail/insertIsLike",
						method: "post",
						data: {
							"detailId": this.id,
							"userId": this.UserInfo.userId
						}
					}).then(res => {
						this.islike = !this.islike;
						console.log(res.data);
						if (this.islike) {
							uni.showToast({
								title: "点赞成功"
							});
						} else {
							uni.showToast({
								title: "取消点赞"
							});
						}
					})
					.catch(cat => {
						console.log(cat);
						uni.showToast({
							title: "点赞失败"
						});
					});
			},
			// 点击收藏上传数据到数据库
			async insertIsFav() {
				if (this.UserInfo == '') {
					uni.showToast({
						title: "请先登录",
						icon: "error"
					});
					return;
				}
				await Request({
						url: "detail/insertIsFav",
						method: "post",
						data: {
							"detailId": this.id,
							"userId": this.UserInfo.userId
						}
					}).then(res => {
						this.isfav = !this.isfav;
						console.log(res.data);
						if (this.isfav) {
							uni.showToast({
								title: "收藏成功"
							});
						} else {
							uni.showToast({
								title: "取消收藏"
							});
						}
					})
					.catch(cat => {
						console.log(cat);
						uni.showToast({
							title: "收藏失败"
						});
					});
			},
			// 获取评论数据
			async getComment() {
				await Request({
						url: "detail/getComment",
						method: "post",
						data: {
							"detailId": this.id,
						}
					}).then(res => {
						this.commentList = res.data;
						console.log(res.data);
					})
					.catch(cat => {
						console.log(cat);
					});
			},
			// 发布评论内容
			async commitComment() {
				if (this.UserInfo == '') {
					uni.showToast({
						title: "请先登录",
						icon: "error"
					});
					return;
				}
				if (this.content == '') {
					uni.showToast({
						title: "评论内容为空",
						icon: "error"
					});
					return;
				}
				await Request({
						url: "detail/insertComment",
						method: "post",
						data: {
							"detailId": this.id,
							"userId": this.UserInfo.userId,
							"content": this.content,
							"commentId": this.commentId
						}
					}).then(res => {
						this.commentList = res.data;
						this.isComments = false;
						this.getDetailData(this.id);
						this.content="";
						console.log(res.data);
					})
					.catch(cat => {
						console.log(cat);

					});
			},
		},
		onLoad(options) {
			this.id = options.id;
			this.getDetailData(this.id);
			this.getUserInfo();
			this.getUserIsLikesIsFav();
			this.getComment();
		},
		onShow() {

		}
	}
</script>

<style lang="scss" scoped>
	.content {
		padding-bottom: 100rpx;
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


	.comment-submit {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		height: 140rpx;
		padding: 24rpx 18rpx 46rpx 24rpx;
		box-sizing: border-box;
		display: flex;
		justify-content: space-between;
		background: #fff;
		box-shadow: 0 2px 10px 0 rgba(155, 161, 168, .3);
		z-index: 99;

		.comment-submit-btn {
			max-width: 384rpx;
			min-width: 384rpx;
			height: 80rpx;
			background: rgba(187, 194, 202, .2);
			border-radius: 40rpx;
			padding: 18rpx 30rpx 17rpx;
			box-sizing: border-box;
			font-size: 32rpx;
			color: rgba(47, 47, 47, .5);
		}
	}

	.comment-dialog {
		position: fixed;
		flex-direction: column;
	}

	.is-show {
		display: flex;
	}

	.is-hide {
		display: none;
	}

	.comment-submit-icon,
	.comment-submit-right {
		display: flex;
	}

	.comment-submit-icon {
		flex-direction: column;
		align-items: center;
		width: 96rpx;
		height: 96rpx;
		margin-right: 6rpx;
	}

	.comment-submit-icon:last-child {
		margin-right: 0;
	}

	.comment-submit-icon .comment-submit-image {
		display: inline-block;
		width: 36rpx;
		height: 36rpx;
		margin-top: 11rpx;
	}

	.comment-submit-icon .comment-zan-2 {
		background: url(../../static/icon/dianzan_1.png) no-repeat bottom;
		background-size: 36rpx 36rpx;
	}

	.comment-submit-icon .comment-zan-active-2 {
		background: url(../../static/icon/dianzan.png) no-repeat bottom;
		background-size: 36rpx 36rpx;
	}

	.comment-submit-icon .comment-star {
		background: url(../../static/icon/shoucang.png) no-repeat bottom;
		background-size: 36rpx 36rpx;
	}

	.comment-submit-icon .comment-nostar-active {
		background: url(../../static/icon/yishoucang.png) no-repeat bottom;
		background-size: 36rpx 36rpx;
	}

	.comment-submit-icon .comment-comment {
		background: url(../../static/icon/pinglun.png) no-repeat bottom;
		background-size: 36rpx 36rpx;
	}

	.comment-submit-icon view {
		margin-top: 6rpx;
		font-size: 24rpx;
		color: #6c788e;
		line-height: 33rpx;
	}

	.container {
		height: 600rpx;
		overflow: hidden;
		position: relative;
	}
</style>

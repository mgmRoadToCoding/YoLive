<template>
  <view class="container" @click="closeAllDropdowns">
    <!-- 搜索框区域 -->
    <view class="search-container">
      <input
        class="search-input"
        :placeholder="$t('search.placeholder')"
        placeholder-class="placeholder-style"
        :style="{ borderRadius: '10px' }"
        v-model="searchText"
        @input="handleSearch"
      />
      <image
        class="search-icon"
        src="https://img.icons8.com/ios/50/000000/search--v1.png"
      />
    </view>

    <!-- 筛选条件区域 -->
    <view class="filter-container">
      <!-- 位置筛选 -->
      <view class="filter-item" @click.stop="toggleDropdown(0)">
        <text>{{ locationText || $t('filter.location') }}</text>
        <image
          class="arrow-icon"
          src="https://img.icons8.com/ios/50/000000/expand-arrow--v1.png"
        />
        <!-- 位置下拉菜单 -->
        <view v-if="dropdownVisible[0]" class="dropdown-menu">
          <view
            v-for="(item, index) in locationOptions"
            :key="index"
            class="dropdown-item"
            @click.stop="selectItem(0, item)"
          >
            {{ $t(`filter.locationOptions.${item}`) }}
          </view>
        </view>
      </view>

      <!-- 价格筛选 -->
      <view class="filter-item" @click.stop="toggleDropdown(1)">
        <text>{{ priceText || $t('filter.price') }}</text>
        <image
          class="arrow-icon"
          src="https://img.icons8.com/ios/50/000000/expand-arrow--v1.png"
        />
        <!-- 价格下拉菜单 -->
        <view v-if="dropdownVisible[1]" class="dropdown-menu">
          <view
            v-for="(item, index) in priceOptions"
            :key="index"
            class="dropdown-item"
            @click.stop="selectItem(1, item)"
          >
            {{ $t(`filter.priceOptions.${item}`) }}
          </view>
        </view>
      </view>

      <!-- 房间个数筛选 -->
      <view class="filter-item" @click.stop="toggleDropdown(2)">
        <text>{{ roomText || $t('filter.room') }}</text>
        <image
          class="arrow-icon"
          src="https://img.icons8.com/ios/50/000000/expand-arrow--v1.png"
        />
        <!-- 房间下拉菜单 -->
        <view v-if="dropdownVisible[2]" class="dropdown-menu">
          <view
            v-for="(item, index) in roomOptions"
            :key="index"
            class="dropdown-item"
            @click.stop="selectItem(2, item)"
          >
            {{ $t(`filter.roomOptions.${item}`) }}
          </view>
        </view>
      </view>
    </view>

    <!-- 租房列表 - 使用瀑布流布局 -->
    <view class="u-p-l-10 u-p-r-10 waterfall">
      <u-waterfall v-model="flowList" ref="uWaterfall">
        <template v-slot:left="{ leftList }">
          <view
            class="demo-warter"
            v-for="(item, index) in leftList"
            :key="index"
          >
            <u-lazy-load
              threshold="750"
              border-radius="12"
              :image="item.image"
              :index="index"
              @click="clickImage(item.id)"
            ></u-lazy-load>
            <view class="item-title"
              >{{ item.villageName }}
              {{
                item.type == $t('house.whole')
                  ? transformRoomNum(item.houseNum) +
                    transformHallNum(item.houseHall) +
                    transformToiletNum(item.toiletNum)
                  : $t(`house.${item.roomType}`)
              }}
            </view>
            <view class="item-price">₩{{ item.price }}</view>
            <view class="item-desc"
              >{{ item.type }} |
              {{
                item.type == $t('house.whole') ? item.houseArea : item.roomArea
              }}㎡ |
              {{ $t(`house.${item.decoration}`) }}
            </view>
          </view>
        </template>
        <template v-slot:right="{ rightList }">
          <view
            class="demo-warter"
            v-for="(item, index) in rightList"
            :key="index"
          >
            <u-lazy-load
              threshold="750"
              border-radius="10"
              :image="item.image"
              :index="index"
              @click="clickImage(item.id)"
            ></u-lazy-load>
            <view class="item-title"
              >{{ item.villageName }}
              {{
                item.type == $t('house.whole')
                  ? transformRoomNum(item.houseNum) +
                    transformHallNum(item.houseHall) +
                    transformToiletNum(item.toiletNum)
                  : $t(`house.${item.roomType}`)
              }}
            </view>
            <view class="item-price">₩{{ item.price }}</view>
            <view class="item-desc"
              >{{ item.type }} |
              {{
                item.type == $t('house.whole') ? item.houseArea : item.roomArea
              }}㎡ |
              {{ $t(`house.${item.decoration}`) }}
            </view>
          </view>
        </template>
      </u-waterfall>
      <u-loadmore
        bg-color="rgb(240, 240, 240)"
        :status="loadStatus"
        @loadmore="findHouseList"
        style="height: 80rpx; line-height: 80rpx"
      ></u-loadmore>
      <u-back-top :scroll-top="scrollTop" top="1000"></u-back-top>
      <u-no-network></u-no-network>
    </view>
  </view>
</template>

<script>
import config from '@/common/config.js' // 全局配置文件
import searchData from '@/common/utils/searchData.js' //筛选菜单数据
import numberToChinese from '@/common/utils/numberToChinese.js' // 数字转中文
export default {
  data() {
    return {
      searchText: '', // 搜索文本
      dropdownVisible: [false, false, false],
      locationOptions: ['all', 'chaoyang', 'haidian', 'dongcheng', 'xicheng'],
      priceOptions: [
        'unlimited',
        'under200000',
        '200000to500000',
        '500000to1000000',
        'over1000000',
      ],
      roomOptions: ['unlimited', '1room', '2rooms', '3rooms', '4roomsUp'],
      locationText: '',
      priceText: '',
      roomText: '',
      filters: {
        location: '',
        price: '',
        room: '',
      },

      // 从 searchList.vue 复制的数据
      indexArr: [],
      valueArr: [],
      defaultSelected: [],
      filterData: [],
      searchData: {},
      pageNum: 1,
      pageSize: 20,
      scrollTop: 0,
      houseList: [],
      loadStatus: 'loadmore',
      flowList: [],
      isLoading: false, // 加载状态
    }
  },
  computed: {
    // 筛选后的房源列表
    // filteredHouseList() {
    // 	return this.houseList.filter(house => {
    // 		// 搜索条件匹配
    // 		const searchMatch = this.searchText === '' ||
    // 			house.villageName.includes(this.searchText) ||
    // 			house.desc.includes(this.searchText)
    // 		// 位置筛选
    // 		const locationMatch = this.filters.location === '' ||
    // 			this.filters.location === 'all' ||
    // 			house.desc.includes(this.filters.location)
    // 		// 价格筛选
    // 		const priceMatch = this.filters.price === '' ||
    // 			this.filters.price === 'unlimited' ||
    // 			this.checkPriceMatch(house.price, this.filters.price)
    // 		// 户型筛选
    // 		const roomMatch = this.filters.room === '' ||
    // 			this.filters.room === 'unlimited' ||
    // 			house.desc.includes(this.filters.room)
    // 		return searchMatch && locationMatch && priceMatch && roomMatch
    // 	})
    // }
  },
  onLoad(option) {
    let type = option.type
    let villageName = option.villageName
    this.searchData = {}
    if (type) {
      this.searchData.type = type
    }
    if (villageName) {
      this.searchData.villageName = villageName
    }
    // 获取房源数据
    this.findHouseList()
    // 获取小区数据
    this.findVillageList()
  },

  onPageScroll(e) {
    this.scrollTop = e.scrollTop
  },
  onReachBottom() {
    this.loadStatus = 'loading'
    // 获取数据
    this.findHouseList()
  },
  // 下拉刷新
  onPullDownRefresh() {
    this.pageNum = 1
    this.flowList = []
    this.$refs.uWaterfall.clear()
    // 获取数据
    this.findHouseList()
    // 关闭刷新
    uni.stopPullDownRefresh()
  },
  methods: {
    transformRoomNum(num) {
			if(!num) {
				return ''
			}
      return `${numberToChinese(num)}室`
    },
		transformHallNum(num) {
			if(!num) {
				return ''
			}
      return `${numberToChinese(num)}厅`
    },
		transformToiletNum(num) {
			if(!num) {
				return ''
			}
      return `${numberToChinese(num)}卫`
    },
    // 从 searchList.vue 复制的方法
    findHouseList() {
      let url = '/api/houseApi/findHouseRoomList'
      let defaultData = {
        state: 1,
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        orderByColumn: 'update_time,create_time',
        isAsc: 'desc',
      }
      this.$u
        .get(url, {
          ...defaultData,
          ...this.searchData,
        })
        .then((result) => {
          console.log(this.searchData)
          console.log(result)
          const data = result.rows
          if (this.pageNum > 1 && data.length < this.pageSize) {
            return (this.loadStatus = 'nomore')
          }
          this.houseList = data
          for (let i = 0; i < this.houseList.length; i++) {
            // 先转成字符串再转成对象，避免数组对象引用导致数据混乱
            let item = this.houseList[i]
            if (!item.faceUrl.includes(config.staticUrl)) {
              item.image = config.staticUrl + item.faceUrl
            } else {
              item.image = item.faceUrl
            }
            if (item.type == 0) {
              item.type = this.$t('house.whole')
            } else if (item.type == 1) {
              item.type = this.$t('house.shared')
            }
            if (item.roomType == 1) {
              item.roomType = 'master'
            } else if (item.roomType == 2) {
              item.roomType = 'secondary'
            } else {
              item.roomType = 'unknown'
            }
            if (this.$u.test.isEmpty(item.houseNum)) {
              item.houseNum = ''
            }
            if (this.$u.test.isEmpty(item.houseHall)) {
              item.houseHall = ''
            }
            if (this.$u.test.isEmpty(item.toiletNum)) {
              item.toiletNum = ''
            }
            this.flowList.push(item)
          }
          ++this.pageNum
          this.loadStatus = 'loadmore'
        })
    },

    findVillageList() {
      let url = '/api/houseApi/findVillageList'
      this.$u
        .get(url, {
          city: this.currentCity,
          orderByColumn: 'name',
          isAsc: 'desc',
        })
        .then((result) => {
          const data = result.rows
          for (let i = 0; i < data.length; i++) {
            // 先转成字符串再转成对象，避免数组对象引用导致数据混乱
            let item = data[i]
            searchData[0].submenu.push({
              name: item.name,
              value: item.name,
            })
          }
          this.filterData = searchData
        })
    },

    clickImage(houseId) {
      this.$u.route({
        url: '/pages/detail/detail',
        params: {
          houseId: houseId,
        },
      })
    },

    // 原有的 house.vue 方法
    checkPriceMatch(price, filter) {
      const num = Number(price)
      if (!num) return true
      const map = {
        under200000: [0, 200000],
        '200000to500000': [200000, 500000],
        '500000to1000000': [500000, 1000000],
        over1000000: [1000000, 999999999],
      }
      if (!map[filter]) return true
      let [min, max] = map[filter]
      return num >= min && num <= max
    },

    // ✅ 搜索
    handleSearch() {
      this.searchData.keyword = this.searchText || '' // ✅后端字段

      // 重新加载
      this.pageNum = 1
      this.flowList = []
      this.$refs.uWaterfall.clear()
      this.findHouseList()
    },

    closeAllDropdowns() {
      this.dropdownVisible = [false, false, false]
    },

    toggleDropdown(index) {
      // 先关闭所有下拉菜单
      this.closeAllDropdowns()
      // 再切换当前点击的下拉菜单
      this.$set(this.dropdownVisible, index, !this.dropdownVisible[index])
    },

    // ✅ 下拉选择
    selectItem(type, value) {
      switch (type) {
        case 0: // 位置
          this.locationText =
            value === 'all' ? '' : this.$t(`filter.locationOptions.${value}`)
          if (value === 'all') {
            delete this.searchData.villageName
          } else {
            // ✅后端字段 villageName 模糊匹配即可
            this.searchData.region = '西城区'
          }
          break

        case 1: // 价格
          this.priceText =
            value === 'unlimited' ? '' : this.$t(`filter.priceOptions.${value}`)

          if (value === 'unlimited') {
            delete this.searchData.minPrice
            delete this.searchData.maxPrice
          } else {
            const map = {
              under200000: [0, 200000],
              '200000to500000': [200000, 500000],
              '500000to1000000': [500000, 1000000],
              over1000000: [1000000, 999999999],
            }
            let [min, max] = map[value]
            this.searchData.minPrice = min
            this.searchData.maxPrice = max
          }
          break

        case 2: // 户型
          this.roomText =
            value === 'unlimited' ? '' : this.$t(`filter.roomOptions.${value}`)

          switch (value) {
            case 'unlimited':
              delete this.searchData.houseNum
              break
            case '1room':
              this.searchData.houseNum = 1
              break
            case '2rooms':
              this.searchData.houseNum = 2
              break

            case '3rooms':
              this.searchData.houseNum = 3
              break
            case '4roomsUp':
              this.searchData.houseNum = '4roomsUp'
              break
          }
          break
      }

      // 关闭菜单并刷新数据
      this.dropdownVisible[type] = false
      this.pageNum = 1
      this.flowList = []
      this.$refs.uWaterfall.clear()
      this.findHouseList()
    },

    navigateToDetail(id) {
      this.$u.route({
        url: '/pages/map/houseDetailTemplate/houseDetail',
        params: {
          houseId: id,
        },
      })
    },
  },
}
</script>

<style lang="scss">
.container {
  padding: 20rpx;
  background-color: #f5f5f5;
  height: 100vh;
  box-sizing: border-box;
}

/* 搜索框样式 */
.search-container {
  position: relative;
  margin-bottom: 20rpx;
  background-color: #fff;
  border-radius: 10px;
  display: flex;
  align-items: center;
  padding: 0 20rpx;
  height: 80rpx;
  border: 1px solid #2979ff;
}

.search-input {
  flex: 1;
  height: 100%;
  padding-right: 80rpx;
  font-size: 28rpx;
}

.placeholder-style {
  color: #999;
}

.search-icon {
  position: absolute;
  right: 20rpx;
  width: 40rpx;
  height: 40rpx;
}

/* 筛选条件样式 */
.filter-container {
  display: flex;
  justify-content: space-between;
  background-color: #fff;
  border-radius: 10px;
  padding: 20rpx;
  margin-bottom: 20rpx;
}

.filter-item {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  font-size: 28rpx;
  color: #333;
}

.arrow-icon {
  width: 30rpx;
  height: 30rpx;
  margin-left: 10rpx;
}

/* 下拉菜单样式 */
.dropdown-menu {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.1);
  z-index: 10;
  margin-top: 10rpx;
}

.dropdown-item {
  padding: 20rpx;
  text-align: center;
  border-bottom: 1rpx solid #eee;
}

.dropdown-item:last-child {
  border-bottom: none;
}

.dropdown-item:active {
  background-color: #f5f5f5;
}

/* 从 searchList.vue 复制的样式 */
.waterfall {
  // padding-top: 85rpx;
}

.nomore {
  background-color: $u-bg-color;
}

.rowClass {
  border-radius: 8px;
  background-color: rgb(255, 255, 255);
  margin-top: 10rpx;
}

.hoverClass {
  background-color: #e4e7ed;
}

.tabName {
  font-size: 28rpx;
  color: $u-main-color;
}

.demo-warter {
  border-radius: 8px;
  margin-top: 3px;
  background-color: #ffffff;
  padding: 3px;
  position: relative;
}

.u-close {
  position: absolute;
  top: 20rpx;
  right: 20rpx;
}

.item-cover {
  font-size: 55rpx;
  color: $u-type-warning;
}

.item-title {
  font-size: 28rpx;
  color: $u-main-color;
  font-weight: bold;
  padding-top: 5rpx;
  padding-left: 10rpx;
}

.item-price {
  font-weight: normal;
  font-size: 32rpx;
  color: $u-type-warning;
}

.item-desc {
  font-weight: normal;
  font-size: 26rpx;
  color: $u-tips-color;
  padding-bottom: 5rpx;
  padding-left: 10rpx;
}

.item-tag {
  font-size: 24rpx;
  color: $u-tips-color;
  margin-top: 3px;
}
</style>

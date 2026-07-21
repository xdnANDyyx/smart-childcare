/**
 * API 配置
 * H5 模式自动走 devServer 代理，无需修改；
 * App / 小程序真机运行需要改成电脑的局域网 IP，例如 http://192.168.1.100:8080
 */

// ★ 真机运行时，请把下面的地址改成你电脑的局域网IP ★
const APP_API_URL = 'http://192.168.11.60:8080'

// #ifdef H5
const BASE_URL = '/api'
// #endif

// #ifndef H5
const BASE_URL = APP_API_URL + '/api'
// #endif

export default BASE_URL

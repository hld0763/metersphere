const path = require('path');
const { name } = require('./package');
const webpack = require('webpack');
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;

function resolve(dir) {
  return path.join(__dirname, dir);
}

module.exports = {
  productionSourceMap: false,
  devServer: {
    port: 4004,
    client: {
      webSocketTransport: 'sockjs',
    },
    allowedHosts: 'all',
    webSocketServer: 'sockjs',
    proxy: {
      ['^((?!/login)(?!/document))']: {
        target: 'http://192.168.0.41:8004',
        ws: false,
      },
      '/websocket': {
        target: 'http://192.168.0.41:8004',
        ws: true,
      },
    },
    // 跨域
    headers: {
      'Access-Control-Allow-Origin': '*',
    },
  },
  pages: {
    index: {
      entry: 'src/main.js',
      template: 'public/index.html',
      filename: 'index.html',
    },
    shareApiReport: {
      entry: 'src/template/report/share/share-api-report.js',
      template: 'src/template/report/share/share-api-report.html',
      filename: 'share-api-report.html',
    },
    shareDocument: {
      entry: 'src/template/document/share/share-document.js',
      template: 'src/template/document/share/share-document.html',
      filename: 'share-document.html',
    },
  },
  configureWebpack: {
    devtool: 'cheap-module-source-map',
    resolve: {
      alias: {
        '@': resolve('src'),
      },
    },
    output: {
      // 把子应用打包成 umd 库格式(必须)
      library: `${name}-[name]`,
      libraryTarget: 'umd',
      chunkLoadingGlobal: `webpackJsonp_${name}`,
      // 打包后js的名称
      filename: `js/${name}-[name].[contenthash:8].js`,
      chunkFilename: `js/${name}-[name].[contenthash:8].js`,
    },
    externals: {
      vue: 'Vue',
      'vue-router': 'VueRouter',
      // 'echarts': 'echarts',
      // 'echarts/core': 'echarts', // TODO:外链使用的话需要改造导入及 vue-echarts 的源码
      qiankun: 'qiankun',
      // brace: 'brace', // TODO:暂时未发现能外链的方法，本体包未提供cdn 外链形式的包
      'element-ui': 'ELEMENT',
      mockjs: 'mockjs',
      html2canvas: 'html2canvas',
      jspdf: 'jspdf',
      'pdfjs-dist': 'pdfjs-dist',
      jsondiffpatch: 'jsondiffpatch',
      jsencrypt: 'JSEncrypt',
      'vue-shepherd': 'VueShepherd',
    },
    optimization: {
      splitChunks: {
        cacheGroups: {
          'chunk-vendors': {
            test: /[\\/]node_modules[\\/]/,
            name: 'chunk-vendors',
            priority: 1,
            minChunks: 3,
            chunks: 'all',
          },
          'chunk-common': {
            test: /[\\/]src[\\/]/,
            name: 'chunk-common',
            priority: 1,
            minChunks: 5,
            chunks: 'all',
          },
          'mavon-editor': {
            test: /[\\/]mavon-editor[\\/]/,
            name: 'mavon-editor',
            priority: 2,
            chunks: 'all',
          },
          fortawesome: {
            test: /[\\/]@fortawesome[\\/]/,
            name: 'fortawesome',
            priority: 2,
            chunks: 'all',
          },
          'vue-minder-editor-plus': {
            test: /[\\/]vue-minder-editor-plus[\\/]/,
            name: 'vue-minder-editor-plus',
            priority: 2,
            chunks: 'all',
          },
          ckeditor: {
            test: /[\\/]@ckeditor[\\/]/,
            name: 'ckeditor',
            priority: 2,
            chunks: 'all',
          },
          'el-tree-transfer': {
            test: /[\\/]el-tree-transfer[\\/]/,
            name: 'el-tree-transfer',
            priority: 2,
            chunks: 'all',
          },
          pinia: {
            test: /[\\/]pinia[\\/]/,
            name: 'pinia',
            priority: 3,
            chunks: 'all',
          },
          brace: {
            test: /[\\/]brace[\\/]/,
            name: 'brace',
            priority: 3,
            chunks: 'all',
          },
          echarts: {
            test: /[\\/](echarts|zrender)[\\/]/,
            name: 'echarts',
            priority: 3,
            chunks: 'all',
          },
        },
      },
    },
  },
  css: {
    // 将组件内的 CSS 提取到一个单独的 CSS 文件 (只用在生产环境中)
    // 也可以是一个传递给 `extract-text-webpack-plugin` 的选项对象
    // 修改打包后css文件名
    // extract: true,
    extract: {
      ignoreOrder: true,
      filename: `css/${name}-[name].[contenthash:8].css`,
      chunkFilename: `css/${name}-[name].[contenthash:8].css`,
    },
  },
  chainWebpack: (config) => {
    config.module.rule('svg').exclude.add(resolve('../../framework/sdk-parent/frontend/src/assets/module')).end();
    config.module
      .rule('icons')
      .test(/\.svg$/)
      .include.add(resolve('../../framework/sdk-parent/frontend/src/assets/module'))
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]',
      });

    if (process.env.NODE_ENV === 'analyze') {
      config.plugin('webpack-report').use(BundleAnalyzerPlugin, [
        {
          analyzerMode: 'static',
          reportFilename: './dist/webpack-report.html',
          openAnalyzer: false,
        },
      ]);
    }
  },
};

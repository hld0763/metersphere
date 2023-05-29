const path = require('path');
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;

function resolve(dir) {
  return path.join(__dirname, dir);
}

module.exports = {
  productionSourceMap: false,
  devServer: {
    port: 3000,
    client: {
      webSocketTransport: 'sockjs',
    },
    webSocketServer: 'sockjs',
    allowedHosts: 'all',
    proxy: {
      ['^((?!/login)(?!/document))']: {
        target: 'http://192.168.0.41:8000',
        ws: false
      },
      '/websocket': {
        target: 'http://192.168.0.41:8000',
        ws: true
      },
    },
  },
  configureWebpack: {
    devtool: 'cheap-module-source-map',
    resolve: {
      alias: {
        '@': resolve('src')
      }
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
  chainWebpack: config => {
    config.devtool('source-map')
    config.resolve.alias.set('@', resolve('./src'))
    config.output.library("MsFrontend")

    config.module
      .rule('svg')
      .exclude.add(resolve('src/assets/module'))
      .end()
    config.module
      .rule('icons')
      .test(/\.svg$/)
      .include.add(resolve('src/assets/module'))
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]'
      })
    
      if (process.env.NODE_ENV === 'analyze') {
        config.plugin('webpack-report').use(BundleAnalyzerPlugin, [
          {
            analyzerMode: 'static',
            reportFilename: './dist/webpack-report.html',
            openAnalyzer: false,
          },
        ]);
      }
  }
};

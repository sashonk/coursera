var path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');


module.exports = {
    entry: {
        app:'./app/index.js'
    },
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'build'),
        clean: true
    },
    module: {
        rules: [
            {
                test: /\.css$/i,
                use: ["style-loader", "css-loader"],
            },
        ],
    },
    devtool: 'inline-source-map',
    plugins: [
        new HtmlWebpackPlugin({
            title: "Graph Visualization app",
            template: path.resolve(__dirname, "template.html"),
            path
        })
    ],
    devServer: {
        proxy: {
            '/graph': {
                target: 'http://localhost:6060'
            },
            '/digraph': {
                target: 'http://localhost:6060'
            },
        },
        static: {
            directory: path.resolve(__dirname, 'build'),
        },
        compress: true,
        port: 9000,
        hot: 'only',
        open: true
    }
};
var path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');


module.exports = {
    entry: {
        app:'./src/main/js/index.js'
    },
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'src/main/webapp/public'),
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
        static: {
            directory: path.resolve(__dirname, 'src/main/webapp/public'),
        },
        compress: true,
        port: 9000,
        hot: 'only',
        open: true
    }
};
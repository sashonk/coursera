import $ from "jquery";

export const dfsButton = $("#dfs");
export const clearDfsButton = $("#clearDFS");
export const pathToButton = $("#pathTo");
export const clearPathBtn = $("#clearPath");
clearPathBtn.hide();
pathToButton.hide();
clearDfsButton.hide();

export const updateStatus = (text) => {
    $("#status").html(text);
};

export const DFS_COLOR = "#FF0000";
export const PATH_COLOR = "#00FF00";
export const EDGE_COLOR = "#000000";
export const NODE_COLOR = "#CCCCFF";

export const highlightPath = (network, path) => {
    let c = 0;
    let prev = null;
    for (let v = 0; v < path.length; v++) {
        if (c++ > 0) {
            network.updateEdge(prev + '-' + path[v], {color : PATH_COLOR});
        }

        network.updateClusteredNode(path[v], {color : PATH_COLOR});
        prev = path[v];
    }
};

export const unhighlightPath = (network, path) => {
    let c = 0;
    let prev = null;
    for (let v = 0; v < path.length; v++) {
        if (c++ > 0) {
            network.updateEdge(prev + '-' + path[v], {color : DFS_COLOR});
        }

        network.updateClusteredNode(path[v], {color : DFS_COLOR});
        prev = path[v];
    }
};

const markedNodes = [];
const markedEdges = [];

export const markNode = (network, v) => {
    network.updateClusteredNode(v, {color : DFS_COLOR});
    markedNodes.push(v);
};

export const markEdge = (network, v, w) => {
    network.updateEdge(v + "-" + w, {color: DFS_COLOR})
    markedEdges.push(v + "-" + w);
};

export const unmarkAll = (network) => {
    markedNodes.forEach(v => {
        network.updateClusteredNode(v, {color : NODE_COLOR});
    });
    markedEdges.forEach(e => {
        network.updateEdge(e, {color: EDGE_COLOR})
    });
    markedEdges.length = 0;
    markedNodes.length = 0;
};

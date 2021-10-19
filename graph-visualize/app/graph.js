import {DataSet} from "vis-data";
import {Network} from "vis-network";

const options = {
    edges: {
        color: "#000000",
        arrows: {
            to: {enabled: true}
        }
    },
    physics: false
};

export const createNetwork = (vertices, edges) => {
    var data = {
        nodes: new DataSet(vertices),
        edges: new DataSet(edges)
    };

    var container = document.getElementById("mynetwork");
    return new Network(container, data, options);
};
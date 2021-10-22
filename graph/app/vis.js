import {DataSet} from "vis-data";
import {Network} from "vis-network";
import {EDGE_COLOR, NODE_COLOR} from "./ui";

const options = (withArrows = false) => {
    return {
        interaction: {
            multiselect: true
        },
        edges: {
            color: EDGE_COLOR,
            arrows: {
                to: {enabled: withArrows}
            }
        },
        nodes : {
            color: NODE_COLOR
        },
        physics: true
    }
};

export const createNetwork = (vertices, edges, withArrows) => {
    var data = {
        nodes: new DataSet(vertices),
        edges: new DataSet(edges)
    };

    var container = document.getElementById("mynetwork");
    return new Network(container, data, options(withArrows));
};
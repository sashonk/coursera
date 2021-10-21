import {DataSet} from "vis-data";
import {Network} from "vis-network";

const options = (withArrows = false) => {
    return {
        interaction: {
            multiselect: true
        },
        edges: {
            color: "#000000",
            arrows: {
                to: {enabled: withArrows}
            }
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
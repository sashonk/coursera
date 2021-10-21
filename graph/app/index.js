import {greet} from './greeter.js';
import "vis-network/styles/vis-network.css";
import $ from "jquery";
import {createNetwork} from "./graph";
console.log("I'm the entry point");
greet();

const updateStatus = (text) => {
    $("#status").html(text);
}

function submitHandler(e) {
    e.preventDefault();
    const $form = $(this);
    updateStatus("running");
    const formData = $form.serializeArray();
    const isDigraph = formData.find((nv) => nv.name === "type").value.indexOf('digraph')>0;
    $.ajax({
        url: isDigraph ? "/graph/digraph" : "/graph/graph",
        type: "POST",
        data: formData,
        success: (data) => {
            if (data) {
                const vertices = data.vertices;
                const edges = data.edges;
                const network = createNetwork(vertices, edges, isDigraph);

                const dfsButton = $("#dfs");
                dfsButton.unbind("click");
                dfsButton.click(function () {
                    const nodes = network.getSelectedNodes();
                    if (nodes.length !== 2) {
                        alert("Select 2 nodes: " + nodes);
                        return;
                    }

                    $.ajax({
                        url: "/graph/dfs",
                        type: "POST",
                        data: [
                            {name: "source", value: nodes[0]},
                            {name: "to", value: nodes[1]}
                        ],
                        success: (data) => {
                            if (data?.hasPath) {
                                window.int = setInterval(() => {
                                    $.ajax({
                                        url: "/graph/getEvent",
                                        type : "PUT",
                                        success: (data) => {
                                            if (data?.type === "marked") {
                                                network.updateClusteredNode(data.v, {color : "#FF0000"});
                                            }
                                            else if (data?.type === "edgeTo") {
                                               // network.updateClusteredNode(data.v, {color : "#00FF00"});
                                                network.updateEdge(data.v + "-" + data.w, {color: "#FF0000"})
                                            }
                                            else if (data?.type === "pathTo") {
                                                data?.pathTo.forEach((v) => {
                                                    network.updateClusteredNode(v, {color : "#00FF00"});
                                                })
                                            }
                                            else {
                                                clearInterval(window.int);
                                                updateStatus("complete");
                                            }
                                        },
                                        error : (err) => {
                                            console.error(err);
                                            alert(JSON.stringify(err));
                                            updateStatus("error");
                                        }
                                    });
                                }, 1000);
                            }
                        },
                        error : (err) => {
                            console.error(err);
                            alert(JSON.stringify(err));
                            updateStatus("error");
                        }
                    })

                });
            }
        },
        error : (err) => {
            console.error(err);
            alert(JSON.stringify(err));
            updateStatus("error");
        }
    })
}

$("#form").submit(submitHandler);
$("#type").on("change", () => {
    $("#form").submit();
});

import "vis-network/styles/vis-network.css";
import $ from "jquery";
import {createNetwork} from "./vis";
import {pathToButton, clearPathBtn, dfsButton, clearDfsButton, updateStatus, highlightPath, unhighlightPath,
markEdge, markNode, unmarkAll} from "./ui";
const ROOT = "/graph";

$(window.document).ajaxError((event, jqXHR, ajaxSettings, thrownError) => {
    console.error(thrownError);
    alert(JSON.stringify(thrownError));
})

function dfsDone(network) {
    updateStatus("DFS done");
    clearInterval(window.int);
    pathToButton.show();
    pathToButton.click(() => {
        const nodes = network.getSelectedNodes();
        if (nodes.length !== 1) {
            alert("Select one node");
            return;
        }

        $.ajax({
            url: `${ROOT}/hasPathTo?v=${nodes[0]}`,
            type: "GET",
            success: (data) => {
                if (data.hasPath === true) {
                    updateStatus("Path Found");
                    highlightPath(network, data.path);

                    clearPathBtn.click(function() {
                        updateStatus("");
                        unhighlightPath(network, data.path);
                        $(this).hide();
                        pathToButton.show();
                    });
                    clearPathBtn.show();
                    pathToButton.hide();
                }
                else {
                    updateStatus("No path");
                }
            }
        })
    });
}

function processEvents(network) {
    window.int = setInterval(() => {
        $.ajax({
            url: `${ROOT}/getEvent`,
            type : "PUT",
            success: (data) => {
                if (data?.type === "marked") {
                    markNode(network, data.v);
                }
                else if (data?.type === "edgeTo") {
                    markEdge(network, data.v, data.w);
                }
                else {
                    dfsButton.hide();
                    clearDfsButton.show();
                    clearDfsButton.unbind("click");
                    clearDfsButton.click(function () {
                        unmarkAll(network);
                        $(this).hide();
                        dfsButton.show();
                        updateStatus("");
                        pathToButton.hide();
                    });
                    
                    dfsDone(network);
                }
            }
        });
    }, 200);
}

function submitHandler(e) {
    e.preventDefault();

    clearPathBtn.unbind("click");
    clearPathBtn.hide();
    dfsButton.unbind("click");
    pathToButton.unbind("click");
    const $form = $(this);
    updateStatus("creating graph...");
    const formData = $form.serializeArray();
    const isDigraph = formData.find((nv) => nv.name === "type").value.indexOf('digraph')>0;
    $.ajax({
        url: isDigraph ? `${ROOT}/digraph` : `${ROOT}/graph`,
        type: "POST",
        data: formData,
        success: (data) => {
            if (data) {
                updateStatus("graph created");
                const vertices = data.vertices;
                const edges = data.edges;
                const network = createNetwork(vertices, edges, isDigraph);
                dfsButton.click(function () {
                    const nodes = network.getSelectedNodes();
                    if (nodes.length !== 1) {
                        alert("Select one node");
                        return;
                    }
                    updateStatus("running DFS");
                    $.ajax({
                        url: `${ROOT}/dfs`,
                        type: "POST",
                        data: [
                            {name: "source", value: nodes[0]}
                        ],
                        success: () => processEvents(network)
                    })
                });
            }
        }
    })
}

$("#form").submit(submitHandler);
$("#type").on("change", () => {
    $("#form").submit();
});



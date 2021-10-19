import {greet} from './greeter.js';
import "vis-network/styles/vis-network.css";
import $ from "jquery";
import {createNetwork} from "./graph";
console.log("I'm the entry point");
greet();

const submitHandler = (url) => function(e) {
    e.preventDefault();
    const $form = $(this);

    $.ajax({
        url: url,
        type: "POST",
        data: $form.serializeArray(),
        success: (data) => {
            if (data) {
                const vertices = data.vertices;
                const edges = data.edges;
                const network = createNetwork(vertices, edges);
                window.intID = setInterval(() => {
                    $.ajax({
                        url: url,
                        type : "PUT",
                        data: {},
                        success: (data) => {
                            if (data.vertice) {
                                network.updateClusteredNode(data.vertice, {color: "#FF0000"})
                            }
                            else if (data.complete) {
                                clearInterval(window.intID);
                                alert("Complete!");
                            }
                        }
                    })
                }, 1000);
            }
        },
        error : () => {
            alert('errror!!')
        }
    })
};

$("#form").submit(submitHandler("/graph"));
$("#form2").submit(submitHandler("/digraph"));
$("#type").on("change", () => {
    clearInterval(window.intID);
    $("#form").submit();
});

$("#type2").on("change", () => {
    clearInterval(window.intID);
    $("#form2").submit();
});

/**
 * Kruskal's algorithm
 * Task: Find an edge of the least possible weight that connects any two trees in the forest
 */
import _ from 'underscore';

const nodes = ["A", "B", "C", "D", "E", "F", "G"];
const edges = [
    ["A", "B", 7], ["A", "D", 5],
    ["B", "C", 8], ["B", "D", 9], ["B", "E", 7],
    ["C", "E", 5],
    ["D", "E", 15], ["D", "F", 6],
    ["E", "F", 8], ["E", "G", 9],
    ["F", "G", 11]
];

function kruskal(nodes, edges) {
    let mst = [];

    let forest = nodes.map(node => [node]);

    let sortedEdges = _.sortBy(edges, edge => -edge[2]);

    while (forest.length > 1) {
        let edge = sortedEdges.pop();
        let n1 = edge[0];
        let n2 = edge[1];

        let t1 = forest.filter(tree => _.include(tree, n1));

        let t2 = forest.filter(tree => _.include(tree, n2));

        if (t1 != t2) {
            forest = _.without(forest, t1[0], t2[0]);
            forest.push(_.union(t1[0], t2[0]));
            mst.push(edge);
        }
    }

    return mst;
}

console.log(kruskal(nodes, edges));
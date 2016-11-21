/**
 * Maximum flow
 * Task: Compute the maximum flow in a flow network using Ford–Fulkerson algorithm (FFA)
 */

let Edge = function (source, sink, capacity) {
    this.source = source;
    this.sink = sink;
    this.capacity = capacity;
    this.reverseEdge = null;
    this.flow = 0;
};

let Flow = function () {
    this.edges = {};

    this.findEdgeInPath = function (path, edge, residual) {
        for (let i = 0; i < path.length; i++) {
            if (path[i][0] == edge && path[i][1] == residual) {
                return true;
            }
        }

        return false;
    };

    this.addEdge = function (source, sink, capacity) {
        if (source == sink) {
            return;
        }

        let edge = new Edge(source, sink, capacity);
        let reverseEdge = new Edge(sink, source, 0);

        edge.reverseEdge = reverseEdge;
        reverseEdge.reverseEdge = edge;

        if (this.edges[source] == null) {
            this.edges[source] = [];
        }

        if (this.edges[sink] == null) {
            this.edges[sink] = [];
        }

        this.edges[source].push(edge);
        this.edges[sink].push(reverseEdge);
    };

    this.findPath = function (source, sink, path) {
        if (source == sink) {
            return path;
        }

        for (let i = 0; i < this.edges[source].length; i++) {
            let edge = this.edges[source][i];
            let residual = edge.capacity - edge.flow;

            if (residual > 0 && !this.findEdgeInPath(path, edge, residual)) {
                let tPath = path.slice(0);
                tPath.push([edge, residual]);

                let result = this.findPath(edge.sink, sink, tPath);

                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    };

    this.maxFlow = function (source, sink) {
        let path = this.findPath(source, sink, []);

        while (path != null) {
            let flow = 999999;

            for (let i = 0; i < path.length; i++) {
                if (path[i][1] < flow) {
                    flow = path[i][1];
                }
            }

            for (let i = 0; i < path.length; i++) {
                path[i][0].flow += flow;
                path[i][0].reverseEdge.flow -= flow;
            }

            path = this.findPath(source, sink, []);
        }

        let sum = 0;
        for (let i = 0; i < this.edges[source].length; i++) {
            sum += this.edges[source][i].flow;
        }

        return sum;
    };
};

let flow = new Flow();

flow.addEdge('e', 'a', 4);
flow.addEdge('e', 'b', 4);
flow.addEdge('a', 'd', 2);
flow.addEdge('a', 'c', 4);
flow.addEdge('b', 'c', 1);
flow.addEdge('b', 'd', 2);
flow.addEdge('c', 'f', 3);
flow.addEdge('d', 'f', 5);
console.log(flow.edges);
console.log('-------------------');

let max = flow.maxFlow('e', 'f');
console.log(max);


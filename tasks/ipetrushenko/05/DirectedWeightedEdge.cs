
namespace Graph
{
    public class DirectedWeightedEdge
    {
        private readonly int _from;
        private readonly int _to;
        private readonly double _weight;

        public DirectedWeightedEdge(int from, int to, double weight)
        {
            _from = from;
            _to = to;
            _weight = weight;
        }

        public int From()
        {
            return _from;
        }

        public int To()
        {
            return _to;
        }

        public double Weight()
        {
            return _weight;
        }
    }
}

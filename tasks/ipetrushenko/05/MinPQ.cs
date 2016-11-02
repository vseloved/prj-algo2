using System;

namespace Graph
{
    public class MinPQ<T> where T: IComparable<T>
    {
        private readonly int[] pq;
        private readonly int[] qp;  // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
        private readonly T[] keys;

        private readonly int _maxN; // maximum number of elements on PQ
        private int _n;             // number of elements on PQ

        public MinPQ(int maxN)
        {
            _n = 0;
            _maxN = maxN;

            pq = new int[maxN+1];
            qp = new int[maxN+1];
            keys = new T[maxN+1];

            for (int i = 0; i <= maxN; i++)
            {
                qp[i] = -1;
            }
        }

        public bool Contains(int i)
        {
            if (i < 0 || i >= _maxN) throw new IndexOutOfRangeException();
            return qp[i] != -1;
        }

        public bool IsEmpty()
        {
            return _n == 0;
        }

        public void Insert(int i, T key)
        {
            if (i < 0 || i >= _maxN)
            {
                throw new IndexOutOfRangeException();
            }
            if (Contains(i))
            {
                throw new ArgumentException("index is already in the priority queue");
            }

            _n++;
            qp[i] = _n;
            pq[_n] = i;
            keys[i] = key;
            MaxHeapify(_n);
        }

        public void DecreaseKey(int i, T key)
        {
            if (i < 0 || i >= _maxN) throw new IndexOutOfRangeException();
            if (!Contains(i)) throw new ArgumentException("index is not in the priority queue");

            if (keys[i].CompareTo(key) <= 0)
            {
                throw new ArgumentException("Calling DecreaseKey() with given argument would not strictly decrease the key");
            }

            keys[i] = key;
            MaxHeapify(qp[i]);
        }

         public int DelMin()
         {
            if (_n == 0) throw new ArgumentException("Priority queue underflow");

            int min = pq[1];
            Swap(1, _n--);
            Sink(1);
            qp[min] = -1;
            pq[_n+1] = -1;
            return min;
        }


        public void Update(int i, int elem)
        {
            pq[i] = elem;
            MaxHeapify(i);
        }

        private void MaxHeapify(int i)
        {
            while (i > 1 && pq[i] > pq[i/2])
            {
                Swap(i, i/2);
                i = i/2;
            }
        }

        private void Sink(int k)
        {
            while (2 * k <= _n)
            {
                int j = 2 * k;
                if (j < _n && Greater(j, j + 1)) j++;
                if (!Greater(k, j)) break;
                Swap(k, j);
                k = j;
            }
        }

        private bool Greater(int i, int j)
        {
            return keys[pq[i]].CompareTo(keys[pq[j]]) > 0;
        }

        private void Swap(int i, int j)
        {
            int temp = pq[i];
            pq[i] = pq[j];
            pq[j] = temp;
        }

    }
}

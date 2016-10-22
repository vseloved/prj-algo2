using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace TravellingSalesman
{
    public class Program
    {
        static void Main(string[] args)
        {
            if (args.Length == 2 && args[1] == "--test")
            {
                TestAll();
            }
            else
            {
                var coordinates = ReadCoordinatesFromFile();
                var tour = ComputeTravellingSalesmanTour(coordinates);
                WriteCoordinatesToFile(tour);
            }
        }

        public static Dictionary<string, Tuple<int, int>> ReadCoordinatesFromFile()
        {
            const string filePath = @"D:\prj\prj-algo2\tasks\capitals.txt";
            Dictionary<string, Tuple<int, int>> coordinates = new Dictionary<string, Tuple<int, int>>();

            foreach (var line in File.ReadAllLines(filePath))
            {
                var splittedLine = line.Split(' ');

                StringBuilder nameOfCity = new StringBuilder();
                for (int i = 0; i < splittedLine.Length - 2; ++i)
                {
                    nameOfCity.AppendLine(splittedLine[i]);
                }

                coordinates.Add(nameOfCity.ToString(), new Tuple<int, int>(int.Parse(splittedLine[splittedLine.Length - 2]),
                                                                           int.Parse(splittedLine[splittedLine.Length - 1])));
            }

            return coordinates;
        }

        public static List<Tuple<int, int>> ComputeTravellingSalesmanTour(Dictionary<string, Tuple<int, int>> coordinates)
        {
            var point = coordinates.First();
            coordinates.Remove(point.Key);

            var tour = new List<Tuple<int, int>> { point.Value };
     
            tour = TSPInternal(point.Value, coordinates, tour);
            tour.Add(point.Value);

            return tour;
        }

        private static List<Tuple<int, int>> TSPInternal(Tuple<int, int> point, Dictionary<string, Tuple<int, int>> coordinates, List<Tuple<int, int>> tour)
        {
            while (coordinates.Count != 0)
            {
                var nearestPoint = FindClosestPointToGivenPoint(point, coordinates);
                tour.Add(nearestPoint);

                var item = coordinates.First(kvp => Equals(kvp.Value, nearestPoint));
                coordinates.Remove(item.Key);

                point = nearestPoint;
            }

            return tour;
        }

        private static Tuple<int, int> FindClosestPointToGivenPoint(Tuple<int, int> point, Dictionary<string, Tuple<int, int>> coordinates)
        {
            double minDistance = Int32.MaxValue;
            int index = -1;

            for (int i = 0; i < coordinates.Count; ++i)
            {
                double distance = ComputeDistance(point, coordinates.Values.ElementAt(i));
                if (minDistance > distance)
                {
                    minDistance = distance;
                    index = i;
                }
            }

            return coordinates.Values.ElementAt(index);
        }

        private static double ComputeDistance(Tuple<int, int> firstPoint, Tuple<int, int> secondPoint)
        {
            var deltaX = firstPoint.Item1 - secondPoint.Item1;
            var deltaY = firstPoint.Item2 - secondPoint.Item2;

            var distance = Math.Sqrt(deltaX * deltaX + deltaY * deltaY);

            // for testing purposes
            return Math.Round(distance, 3);
        }

        public static void WriteCoordinatesToFile(List<Tuple<int, int>> tour)
        {
            File.WriteAllLines(@"D:\prj\prj-algo2\tasks\output.txt", tour.Select(t => t.ToString()).ToArray());
        }

        public static void CheckResult<T>(IList<T> actualResult, IList<T> expectedResult)
        {
            if (!actualResult.SequenceEqual(expectedResult))
            {
                Console.Write("For a given input");
                
                Console.Write(" expected ");
                WriteToConsole(actualResult);

                Console.Write(" but got ");
                WriteToConsole(expectedResult);

                throw new ArgumentException("Test failed");
            }
            
            
            Console.WriteLine("Success!");
        }

        public static void WriteToConsole(IEnumerable items)
        {
            foreach (object o in items)
            {
                Console.Write(o);
            }
        }

        private static void TestFindClosestPoint(Tuple<int, int> point, Dictionary<string, Tuple<int, int>> coordinates, Tuple<int, int> expectedResult)
        {
            var actualResult = FindClosestPointToGivenPoint(point, coordinates);
            CheckResult(new List<Tuple<int, int>> { actualResult }, new List<Tuple<int, int>> { expectedResult });
        }

        private static void TestFindClosestPointAll()
        {
            TestFindClosestPoint(new Tuple<int, int>(1, 1), new Dictionary<string, Tuple<int, int>> { {"first",  new Tuple<int, int>(1, 1)} }, // input
                                 new Tuple<int, int>(1, 1));                                                                                   // expected outputs


            //8  |                    (8,8) 
            //7  |                      *
            //6  |
            //5  |
            //4  |      (3,3)
            //3  |        *   (5,2)    
            //2  |(1,1)         *     
            //1  |  *           
            //   |__ __ __ __ __ __ __ __ 
            //      1  2  3  4  5  6  7  8

            TestFindClosestPoint(new Tuple<int, int>(1, 1), new Dictionary<string, Tuple<int, int>>
                                                            {
                                                                {"first",  new Tuple<int, int>(3, 3)},
                                                                {"second", new Tuple<int, int>(5, 2)},
                                                                {"third",  new Tuple<int, int>(8, 8)}
                                                            },
                                 new Tuple<int, int>(3,3));
        }

        private static void TestComputeDistanceAll()
        {
            TestComputeDistance(new List<Tuple<int, int>> { new Tuple<int, int>(3, 2), new Tuple<int, int>(9, 7) },
                                7.81d);

            TestComputeDistance(new List<Tuple<int, int>> { new Tuple<int, int>(-3, 5), new Tuple<int, int>(7, -1) },
                                11.662d);
        }

        private static void TestComputeDistance(List<Tuple<int, int>> points, double expectedResult)
        {
            var actualResult = ComputeDistance(points[0], points[1]);
            CheckResult(new List<double> { actualResult }, new List<double> { expectedResult });
        }

        public static void TestAll()
        {
            Console.WriteLine("Start find closest point tests: ");
            TestFindClosestPointAll();

            Console.WriteLine("Start compute distance between two point tests: ");
            TestComputeDistanceAll();
        }
    }
}

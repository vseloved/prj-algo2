'''
Created on Oct 25, 2016

@author: Iegor
'''

class DataReader(object):

    def __init__(self, filename):
        self.filename = filename
        
    def readPoints(self):
        f = open(self.filename, 'r')
        points = []
        for line in f:
            data = line.split()
            long = float(data.pop())
            lat = float(data.pop()) 
            x, y = self.convertToXY(lat, long)
            point = (x, y, (" ").join(data))
            points.append(point)
        return points
    
    def convertToXY(self, lat, long):
        return lat, long
        
'''
Created on Oct 25, 2016

@author: Iegor
'''
import matplotlib.pyplot as plt
import matplotlib
from matplotlib import animation


class GraphAnimator(object):
    def __init__(self, points, pathes):
        self.points = points
        self.pathes = pathes
        self.anim = None
        self.fig = None
        self.pathX = []
        self.pathY = []
        
    def beginAnimation(self):
        self.fig = plt.figure()
        matplotlib.rcParams.update({'font.size': 9})
        self.anim = animation.FuncAnimation(self.fig, self.animate, frames=len(self.pathes), interval=500, blit=True)
        plt.show()
        
    def animate(self, i):
        arrows = self.plot(self.pathes[i])
        if i == len(self.pathes)-1:
            self.anim._stop()
        return arrows
        
    def plot(self, path):
        x = []; y = []
        for i in range(len(self.points)):
            x1 = self.points[i][0]
            y1 = self.points[i][1]
            x.append(x1)
            y.append(y1)
            plt.annotate(self.points[i][2], xy=(x1, y1), xytext=(x1, y1))
    
        plt.plot(x, y, 'co')
        
        plt.xlim(min(x) * 0.99, max(x) * 1.01)
        plt.ylim(min(y) * 0.99, max(y) * 1.01)
        
        self.pathX.append(x[path])
        self.pathY.append(y[path])
        lines = plt.plot(self.pathX, self.pathY, '-',color='b')
        return lines

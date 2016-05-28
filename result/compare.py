import matplotlib.pyplot as plt
import sys

if __name__ == '__main__':
    if len(sys.argv) != 3:
        print 'USAGE: %s [result1.txt] [result2.txt]' % sys.argv[0]
        sys.exit(-1)
    else:
        with open(sys.argv[1], 'r') as f:
            y1 = map(lambda x: float(x), f.readlines()[6:])
        with open(sys.argv[2], 'r') as f:
            y2 = map(lambda x: float(x), f.readlines()[6:])

        x = range(len(y1))

        plt.figure(1)
        plt.subplot(111)
        plt.plot(x, y1, 'bo', label='Result1')
        plt.plot(x, y2, 'ro', label='Result2')
        plt.legend(bbox_to_anchor=(0., 1.02, 1., .102), loc=3,
                   ncol=2, mode="expand", borderaxespad=0.)
        plt.xlim(0, 40)
        plt.ylim(0, int(y1[0] + 1))
        plt.show()
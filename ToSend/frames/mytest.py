import socket
import time
for i in range(1, 8):
    print "frame", i
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    f = open(str(i) + ".png", "rb")
    s.connect(("localhost", 10000))
    bytes = f.read()
    f.close()
    s.send(bytes)
    s.close()
    time.sleep(1)

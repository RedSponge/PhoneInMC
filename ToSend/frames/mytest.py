import socket
import time
for i in range(1, 19):
    print "frame", i
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)


    f = open("cattt.jpg", "rb")
    s.connect(("localhost", 10000))
    bytes = f.read()
    f.close()
    s.send(bytes)
    s.close()
    time.sleep(0)

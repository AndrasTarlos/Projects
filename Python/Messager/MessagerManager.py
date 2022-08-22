import threading

from ConnectionBinder import ConnectionBinder
from ConnectionRec import ConnectionRec
from PacketHandler import PacketHandler

binder = ConnectionBinder()
ph = PacketHandler()
receiver = ConnectionRec()


def run():
    print(f'[STARTING] The program started on device {receiver.MYIP}')
    threading.Thread(target=binder.start()).start()
    threading.Thread(target=receiver.start()).start()


run()

import socket
import threading
import time

from PacketHandler import PacketHandler


# This class has the client role
class ConnectionBinder(PacketHandler):
    ph = PacketHandler
    TARGET_IP = "192.168.0.70"
    ADDR = (TARGET_IP, ph.PORT)

    binder = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    binder.connect(ADDR)

    def handle_connection(self):
        threading.Thread(target=self.send_message2, args=(self.binder, self.TARGET_IP)).start()
        msg = ''
        while True:
            if msg == 'quit':
                self.send_message1(self.DISCONNECT_CODE, self.binder, self.TARGET_IP)
                break
            self.send_message1(None, self.binder, self.TARGET_IP)
            time.sleep(1)

    def start(self):
        print(f'[CONNECTING] Attempting to connect to target...')
        self.handle_connection()


cb = ConnectionBinder()
cb.start()

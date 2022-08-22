import socket
import threading

from PacketHandler import PacketHandler


# This class has the server role
class ConnectionRec(PacketHandler):
    ph = PacketHandler
    MYIP = socket.gethostbyname(socket.gethostname())
    ADDR = (MYIP, ph.PORT)

    # SOCK_STREAM = TCP && SOCK_DGRAM = UDP (potential packet loss)
    receiver = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    receiver.bind(ADDR)

    def handle_connection(self, conn, addr):
        print(f"[NEW CONNECTION] {addr} connected")

        threading.Thread(target=self.ph.rec_message1, args=(self, conn, addr))
        self.ph.connected = True
        while self.ph.connected:
            self.ph.rec_message2(self, conn, addr)
        conn.close()

    def start(self):
        print(f"[LISTENING] Computer is listening on {self.MYIP}")
        self.receiver.listen()

        while True:
            conn, addr = self.receiver.accept()
            self.handle_connection(conn, addr)


cr = ConnectionRec()
cr.start()

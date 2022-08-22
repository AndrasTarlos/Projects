import threading


class PacketHandler:
    PORT = 1234
    FORMAT = 'utf-8'
    HEADER = 128
    DISCONNECT_MESSAGE = 'Connection ended....'
    DISCONNECT_CODE = 'Q29ubmVjdGlvbiBlbmRlZC4uLi4='
    connected = False

    def send_message1(self, msg, binder, target_ip):
        if msg is None:
            msg = str(input(f'Message to {target_ip}: '))
        msg = msg.encode(self.FORMAT)
        msg_length = len(msg)
        send_length = str(msg_length).encode(self.FORMAT)
        send_length += b' ' * (self.HEADER - len(send_length))
        binder.send(send_length)
        binder.send(msg)

    def send_message2(self, binder, target_ip):
        while True:
            msg_length = binder.recv(self.HEADER).decode(self.FORMAT)
            if msg_length:
                msg_length = int(msg_length)
                msg = binder.recv(msg_length).decode(self.FORMAT)
                if msg == self.DISCONNECT_CODE:
                    self.connected = False
                print(f'Message from {target_ip}: {msg}')

    def rec_message1(self, conn, addr):
        while True:
            msg_length = conn.recv(self.HEADER).decode(self.FORMAT)
            if msg_length:
                msg_length = int(msg_length)
                msg = conn.recv(msg_length).decode(self.FORMAT)
                if msg == self.DISCONNECT_CODE:
                    self.connected = False
                print(f'Message from {addr[0]}: {msg}')


    def rec_message2(self, conn, addr):
        msg = str(input(f'Message to {addr[0]}: ')).encode(self.FORMAT)
        msg_length = len(msg)
        send_length = str(msg_length).encode(self.FORMAT)
        send_length += b' ' * (self.HEADER - len(send_length))
        conn.send(send_length)
        conn.send(msg)

    def send_file(self):
        pass

    def rec_file(self):
        pass
